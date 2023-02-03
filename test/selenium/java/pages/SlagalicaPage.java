package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import utility.ReadFromFile;
import utility.slagalica.DictionaryWord;
import utility.slagalica.LogicForSlagalicaGame;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SlagalicaPage extends TestBase {
    //Locators for lists of buttons with generated letters.
    public By firstListLocator = new By.ByXPath("//div[@class='pt-5']"),
            secondListLocator = new By.ByXPath("//div[@class='pt-2']"),
    //Locator for "Zatvori" button on pop up dialog that appears when button "Potvrdi" is clicked.
    closeButton = new By.ByXPath("//button[text()='Zatvori']");
    By stopButtonLocator = new By.ById("stopButton"),
            potvrdiButtonLocator = new By.ById("submitButton"),
            izbrisiButtonLocator = new By.ById("deleteLetter"),
    //Locator for element displaying text composed of letters clicked by player.
    userWordFieldLocator = new By.ById("userWord"),
    //Locator for list of button elements that display random letters.
    buttonsInListLocator = new By.ByXPath("//button[starts-with(@id,'btn')]"),
    //Locator for pop up dialog container.
    popUpDialog = new By.ByXPath("//div[@class='modal-content']"),
    //Locator for points calculated by application.
    finalPointsLocator = new By.ById("finalPoints"),
    //Locator for computer generated word (solution word).
    computerWord = new By.ById("computerWordTxt");
    //Stores letters clicked by player.
    String clickedLetters = "",
    //Stores the expected longest word.
    expectedLongestWord = "",
    //Stores actual computer word (solution word), needs to match expectedLongestWord.
    actualComputerWord = "",
    //Stores random letters generated after "Stop" button is clicked.
    randomLetters = "";
    //List of all DictionaryWord objects (objects that contain words).
    List<DictionaryWord> wordObjects;
    //LogicForSlagalicaGame object used for accessing methods for calculating expected word and points.
    LogicForSlagalicaGame gameLogicObject;
    //List of elements containing buttons with letters.
    List<WebElement> letterButtonsList;

    //Constructor.
    public SlagalicaPage() {
        //Call to parent constructor.
        super();
        //Initializing object for game logic methods.
        gameLogicObject = new LogicForSlagalicaGame();
    }

    public void verifyStopButtonDisplayed() {
        Assert.assertTrue(find(stopButtonLocator).isDisplayed(), "Stop button not displayed!");
        Reporter.log("Stop button is displayed.");
        System.out.println("Stop button is displayed.");
    }

    public void verifyThatStopButtonIsClicked() {
        waitForStopButtonClickableDelay();
        verifyMethods.verifyButtonIsClickable(stopButtonLocator);
        waitForStopButtonClickableDelay();
        click(stopButtonLocator);
        Reporter.log("Stop button is clicked.");
        System.out.println("Stop button is clicked.");
    }

    public void verifyThatPotvrdiButtonIsClicked() {
        waitForElToBeClickable(potvrdiButtonLocator);
        click(potvrdiButtonLocator);
        Reporter.log("Potvrdi button is clicked.");
        System.out.println("Potvrdi button is clicked.");
    }

    public void verifyThatIzbrisiButtonIsClicked() {
        waitForElToBeClickable(izbrisiButtonLocator);
        click(izbrisiButtonLocator);
        Reporter.log("Izbrisi button is clicked.");
        System.out.println("Izbrisi button is clicked.");
        waitForVisibilityOf(stopButtonLocator);
    }

    public void verifyThatFirstListIsDisplayed() {
        Assert.assertTrue(find(firstListLocator).isDisplayed(), "First list not displayed!");
        Reporter.log("First list is displayed.");
        System.out.println("First list is displayed.");
    }

    public void verifyThatSecondListIsDisplayed() {
        Assert.assertTrue(find(secondListLocator).isDisplayed(), "Second list not displayed!");
        Reporter.log("Second list is displayed.");
        System.out.println("Second list is displayed.");
    }

    public void verifyPopUpDialog() {
        waitForElToBeClickable(popUpDialog);
        WebElement popup = find(popUpDialog);
        Assert.assertTrue(popup.isDisplayed(), "Popup dialog not displayed!");
        Reporter.log("Dialog is displayed.");
        System.out.println("Dialog is displayed.");
    }

    //Verify "Zatvori" button on "Potvrdi" pop up dialog is closed.
    public void verifyCloseButtonIsClicked() {
        waitForVisibilityOf(closeButton);
        verifyMethods.verifyButtonIsClickable(closeButton);
        click((closeButton));
        Reporter.log("Close button clicked.");
        System.out.println("Close button clicked.");
        waitForElToBeClickable(locators.getBackBtnLoc());
    }

    public void waitForPopupToClose() {
        waitForVisibilityOf(locators.getContainerLoc());
        waitForElToBeClickable(locators.getBackBtnLoc());
    }

    public void verifyTitlesAndContainer() {
        setPageTitle();
        setConfigTitle(prop.getProperty("slagalicaPageTitle"));
        verifyMethods.verifyPageTitle();
        verifyMethods.verifyContainerDisplayed();
        verifyMethods.verifyContainerTitle(prop.getProperty("slagalicaContainerTitle"));
    }

    // Adding waits before going back to SinglePlayerGamePage.
    public void waitBeforeGoingBack() {
        waitForElToBeClickable(locators.getH1TitleLoc());
        waitForElToBeClickable(locators.getBackBtnLoc());
    }

    //Initialize randomLetters class variable.
    public void getRandomLettersFromButtons() {
        waitForVisibilityOf(locators.getH1TitleLoc());
        Reporter.log("Get text from letters after clicking stop button.");
        System.out.println("Get text from letters after clicking stop button.");
        waitForVisibilityOf(firstListLocator);
        waitForVisibilityOf(secondListLocator);
        List<WebElement> buttonsList = findAll(buttonsInListLocator);
        for (WebElement el : buttonsList) {
            randomLetters = randomLetters.concat(el.getText());
        }
        Reporter.log("Computer generated letters: " + randomLetters);
        System.out.println("Computer generated letters: " + randomLetters);
        waitForVisibilityOf(locators.getContainerLoc());
    }

    public void getWordsFromDictionary() {
        List<String> words = new ReadFromFile().readFromFile(prop.getProperty("pathToWordsFIle"));
        Set<DictionaryWord> dictionaryWords = this.gameLogicObject.sortWords(words);
        this.wordObjects = new ArrayList<>(dictionaryWords);

    }

    //Initializes expectedLongestWord. Reads from text file and finds the longest one to match generated letters.
    public void getExpectedLongestWord() {
        getRandomLettersFromButtons();
        getWordsFromDictionary();
        expectedLongestWord = gameLogicObject.computersLongestWord(randomLetters, wordObjects);
        Reporter.log("Expected longest generated word is: " + expectedLongestWord);
        System.out.println("Expected longest generated word is: " + expectedLongestWord);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(buttonsInListLocator));
    }

    public void getPoints() {
        String[] finalPoints = find(finalPointsLocator).getText().split(" ");
        System.err.println("Calculated pints: " + finalPoints[2]);
        int points = gameLogicObject.calculateNumberOfPoints(wordObjects, find(userWordFieldLocator).getText(), actualComputerWord);
        System.err.println("Expected points: " + points);
        this.slagalicaPoints = points;
    }

    //Initializes actualComputerWord by getting text from corresponding WebElement object.
    public void getActualComputerWord() {
        // String actualComputerWord = "";
        waitForVisibilityOf(computerWord);
        String text = find(computerWord).getText();
        //List is created by separating each word from web element's text.
        String[] generatedTextList = text.split(" ");
        //Only the last word of list is needed to initialize variable.
        actualComputerWord = generatedTextList[generatedTextList.length - 1];
        Reporter.log("Actual computer generated word is: " + actualComputerWord);
        System.out.println("Actual computer generated word is: " + actualComputerWord);
    }

    public void verifyComputerGeneratedWord() {
        getActualComputerWord();
        Reporter.log("Verifying computer generated word matches expected longest word.");
        System.out.println("Verifying computer generated word matches expected longest word.");
        Assert.assertEquals(actualComputerWord, expectedLongestWord, "Longest generated computer word: " + actualComputerWord + " doesn't match expected " + expectedLongestWord);
        Reporter.log("Computer generated word: " + actualComputerWord + " matches expected.");
        System.out.println("Computer generated word: " + actualComputerWord + " matches expected.");
        waitForVisibilityOf(closeButton);
    }

    //Wait for stop button to be clickable. Workaround for delay bug.
    public void waitForStopButtonClickableDelay() {
        waitForVisibilityOf(stopButtonLocator);
        waitForElToBeClickable(stopButtonLocator, Duration.ofSeconds(10));
    }

    //Helper method that sets up environment  before clicking on letter buttons.
    public void clickLettersSetup() {
        getExpectedLongestWord();
        //Initialize class variable that stores clicked letters.
        this.clickedLetters = "";
        waitForVisibilityOf(firstListLocator);
        waitForVisibilityOf(secondListLocator);
        //List of button WebElement objects that contain letter as text.
        this.letterButtonsList = findAll(buttonsInListLocator);
    }

    //Clicks on three random letters form list of letter buttons.
    public void clickRandomLetters() {
        clickLettersSetup();
        Reporter.log("Click random letters.");
        System.out.println("Click random letters.");
        int maxValue = letterButtonsList.size();
        ArrayList<Integer> generated = new ArrayList<>();
        wait.until(ExpectedConditions.visibilityOfAllElements(letterButtonsList));
        for (int i = 0; i < 3; i++) {
            //Random int from 0 to 12 (excluding 12).
            int index = generateUnique(generated, maxValue);
            clickButtonWithIndex(index);
        }
    }

    /**
     * Helper method that clicks on WebElement with specified index. Web element is part of list of letter button elements.
     *
     * @param index int (Position of element in list).
     */
    public void clickButtonWithIndex(int index) {
        //Initializing button element.
        WebElement el = letterButtonsList.get(index);
        if (find(stopButtonLocator).isEnabled()) {
            Reporter.log("Stop button is not clicked!");
            System.out.println("Stop button is not clicked!");
            Assert.assertFalse(el.isEnabled(), "Letter button is not disabled!");
            Reporter.log("Letter button on position " + (index + 1) + " not clickable.");
            System.out.println("Letter button on position " + (index + 1) + " not clickable.");
        } else {
            try {
                waitForElToBeClickable(el);
                Assert.assertTrue(el.isEnabled(), "Selected letter button not clickable!");
                el.click();
                waitForElToBeClickable(locators.getH1TitleLoc());
                Reporter.log("Clicked button on position " + (index + 1) + " in list of letters.");
                System.out.println("Clicked button on position " + (index + 1) + " in list of letters.");
                String letter = el.getText();
                this.clickedLetters += letter;
                Reporter.log("Clicked letter: " + letter);
                System.out.println("Clicked letter: " + letter);
                wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(el)));
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        waitForVisibilityOf(locators.getBackBtnLoc());
    }

    public void verifyUserWordFieldNotEmpty() {
        waitForVisibilityOf(locators.getBackBtnLoc());
        waitForVisibilityOf(userWordFieldLocator);
        Reporter.log("Checking if user word field has letters displayed.");
        System.out.println("Checking if user word field has letters displayed.");
        String text = find(userWordFieldLocator).getText();
        Assert.assertNotEquals(text, "[]", "User word field is empty!");
        Reporter.log("User word field not empty! Text displayed: " + text);
        System.out.println("User word field not empty! Text displayed: " + text);
    }

    public void verifyUserWordFieldIsEmpty() {
        waitForVisibilityOf(locators.getBackBtnLoc());
        waitForVisibilityOf(userWordFieldLocator);
        Reporter.log("Checking if user word field is empty.");
        System.out.println("Checking if user word field is empty.");
        String text = find(userWordFieldLocator).getText();
        waitForVisibilityOf(userWordFieldLocator);
        Assert.assertNotEquals(text, "[]", "User word field not empty!");
        Reporter.log("No text displayed in user word field.");
        System.out.println("No text displayed in user word field.");
    }

    public void verifyTimerNotStarted() {
        waitForVisibilityOf(locators.getTimerLoc());
        String currentTimerValue = find(locators.getTimerLoc()).getText();
        Reporter.log("Verify timer has not started.");
        System.out.println("Verify timer has not started.");
        Assert.assertEquals(currentTimerValue, prop.getProperty("slagalicaPageTimerStart"), "Current timer value: " + currentTimerValue + " not equal to starting time.");
        Reporter.log("Timer not started.");
        System.out.println("Timer not started.");
        waitForVisibilityOf(locators.getContainerLoc());
    }

    public void verifyLettersAppearsInUserWordField() {
        waitForElToBeClickable(locators.getH1TitleLoc());
        waitForVisibilityOf(userWordFieldLocator);
        verifyUserWordFieldNotEmpty();
        String userWordText = find(userWordFieldLocator).getText();
        Reporter.log("Verifying clicked letters are shown in user word text field.");
        System.out.println("Verifying clicked letters are shown in user word text field.");
        Assert.assertEquals(userWordText, clickedLetters, "Clicked letters are not displayed.");
        Reporter.log("Letters displayed!. Complete user text displayed: " + userWordText);
        System.out.println("Letters displayed!. Complete user text displayed: " + userWordText);
    }

    public void clickSpecificLetter(int i) {
        Reporter.log("Clicking letter contained in expected solution word.");
        System.out.println("Clicking letter contained in expected solution word.");
        wait.until(ExpectedConditions.visibilityOfAllElements(letterButtonsList));
        for (WebElement button : this.letterButtonsList) {
            String letter = button.getText();
            if (button.isEnabled() && (this.expectedLongestWord.charAt(i) == letter.toLowerCase().charAt(0))) {
                waitForElToBeClickable(button, Duration.ofSeconds(10));
                button.click();
                Reporter.log("Clicked letter: " + letter);
                System.out.println("Clicked letter: " + letter);
                this.clickedLetters += letter;
                break;
            }
        }
    }

    public void clickLettersMatchingComputerWord() {
        clickLettersSetup();
        waitForElToBeClickable(potvrdiButtonLocator);
        wait.until(ExpectedConditions.visibilityOfAllElements(letterButtonsList));
        int i = 0;
        while (i < this.expectedLongestWord.length()) {
            clickSpecificLetter(i);
            waitForElToBeClickable(potvrdiButtonLocator);
            i++;
        }
        System.out.println(this.clickedLetters);
    }
}
