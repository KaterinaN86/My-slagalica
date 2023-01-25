package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import utility.FindWordForSlagalicaGame;
import utility.ReadFromFile;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SlagalicaPage extends TestBase {

    public By firstListLocator = new By.ByXPath("//div[@class='pt-5']");
    public By secondListLocator = new By.ByXPath("//div[@class='pt-2']");
    public By closeButton = new By.ByXPath("//button[text()='Zatvori']");
    By stopButtonLocator = new By.ById("stopButton");
    By potvrdiButtonLocator = new By.ById("submitButton");
    By izbrisiButtonLocator = new By.ById("deleteLetter");
    By userWordFieldLocator = new By.ById("userWord");
    By buttonsInListLocator = new By.ByXPath("//button[starts-with(@id,'btn')]");
    By popUpDialog = new By.ByXPath("//div[@class='modal-content']");
    By computerWord = new By.ById("computerWordTxt");
    List<String> dictionaryWordsList;
    String clickedLetters = "";
    String expectedLongestWord = "";
    String actualComputerWord = "";
    String randomLetters = "";

    private Random rnd = new Random();

    public SlagalicaPage() {
        super();
    }

    public void verifyStopButtonDisplayed() {
        Assert.assertTrue(find(stopButtonLocator).isDisplayed(), "Stop button not displayed!");
        Reporter.log("Stop button is displayed.");
        System.out.println("Stop button is displayed.");
    }

    public void verifyThatStopButtonIsClicked() {
        waitForStopButtonClickableDelay();
        verifyMethods.verifyButtonIsClickable(stopButtonLocator);
        click(stopButtonLocator);
        Reporter.log("Stop button is clicked.");
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

    public void waitBeforeGoingBack() {
        waitForElToBeClickable(locators.getH1TitleLoc());
        waitForElToBeClickable(locators.getBackBtnLoc());
    }

    public SinglePlayerGamePage goBackAfterGameFinished() {
        waitForElToBeClickable(locators.getContainerLoc());
        String page = this.getClass().getSimpleName();
        Reporter.log("Click back button on page: " + page);
        System.out.println("Click back button on page: " + page);
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement goBackButton = find(locators.getBackBtnLoc());
            waitForElToBeClickable(goBackButton);
            this.verifyMethods.verifyBackButtonIsClickable();
            waitForElToBeClickable(locators.getH1TitleLoc());
            js.executeScript("arguments[0].click()", goBackButton);
        } catch (Exception e) {
            System.err.println("*******Stale element error*********");
        }
        waitForElToBeClickable(locators.getH1TitleLoc());
        return (SinglePlayerGamePage) verifyMethods.verifyPageObjectInitialized(new SinglePlayerGamePage());
    }

    public void getRandomLettersFromButtons() {
        StringBuilder result = new StringBuilder();
        waitForVisibilityOf(locators.getH1TitleLoc());
        Reporter.log("Get text from letters after clicking stop button.");
        System.out.println("Get text from letters after clicking stop button.");
        waitForVisibilityOf(firstListLocator);
        waitForVisibilityOf(secondListLocator);
        List<WebElement> buttonsList = findAll(buttonsInListLocator);
        for (WebElement el : buttonsList) {
            result.append(el.getText());
        }
        this.randomLetters = result.toString();
        Reporter.log("Computer generated letters: " + randomLetters);
        System.out.println("Computer generated letters: " + randomLetters);
        waitForVisibilityOf(locators.getContainerLoc());
    }

    public void getExpectedLongestWord() {
        this.dictionaryWordsList = new ReadFromFile().readFromFile(prop.getProperty("pathToWordsFIle"));
        FindWordForSlagalicaGame findWord = new FindWordForSlagalicaGame();
        this.expectedLongestWord = findWord.computersLongestWord(this.randomLetters, dictionaryWordsList);
        Reporter.log("Expected longest generated word is: " + expectedLongestWord);
        System.out.println("Expected longest generated word is: " + expectedLongestWord);
    }

    public void getActualComputerWord() {
        waitForVisibilityOf(computerWord);
        String[] generatedTextList = find(computerWord).getText().split(" ");
        actualComputerWord = generatedTextList[generatedTextList.length - 1];
        Reporter.log("Actual computer generated word is: " + actualComputerWord);
        System.out.println("Actual computer generated word is: " + actualComputerWord);
    }

    public void verifyComputerGeneratedWord() {
        getRandomLettersFromButtons();
        getExpectedLongestWord();
        getActualComputerWord();
        Reporter.log("Verifying computer generated longest word matches expected.");
        System.out.println("Verifying computer generated longest word matches expected.");
        Assert.assertEquals(actualComputerWord, this.expectedLongestWord, "Longest generated computer word: " + this.actualComputerWord + " doesn't match expected " + this.expectedLongestWord);
        Reporter.log("Computer generated word: " + this.actualComputerWord.toUpperCase() + " matches expected.");
        System.out.println("Computer generated word: " + this.actualComputerWord.toUpperCase() + " matches expected.");
        waitForVisibilityOf(closeButton);
    }

    public void waitForStopButtonClickableDelay(){
        waitForVisibilityOf(stopButtonLocator);
        waitForElToBeClickable(stopButtonLocator, Duration.ofSeconds(10));
    }

    public void clickRandomLetters() {
        Reporter.log("Click random letters.");
        System.out.println("Click random letters.");
        waitForVisibilityOf(firstListLocator);
        waitForVisibilityOf(secondListLocator);
        List<WebElement> letterButtonsList = findAll(buttonsInListLocator);
        wait.until(ExpectedConditions.visibilityOfAllElements(letterButtonsList));
        for (int i = 0; i < 3; i++) {
            int index = rnd.nextInt(letterButtonsList.size());
            clickButtonWithIndex(index, letterButtonsList);
        }
    }

    public void clickButtonWithIndex(int index, List<WebElement> letterButtonsList) {
        WebElement el = letterButtonsList.get(index);
        waitForElToBeClickable(el, Duration.ofSeconds(10));
        Assert.assertTrue(el.isEnabled(), "Selected letter button not clickable!");
        el.click();
        waitForElToBeClickable(locators.getH1TitleLoc());
        Reporter.log("Clicked button on position " + (index + 1) + " in list of letters.");
        System.out.println("Clicked button on position " + (index + 1) + " in list of letters.");
        if (!find(stopButtonLocator).isEnabled()) {
            String letter = el.getText();
            this.clickedLetters += letter;
            Reporter.log("Clicked letter: " + letter);
            System.out.println("Clicked letter: " + letter);
        }
        waitForVisibilityOf(locators.getBackBtnLoc());
    }

    public void verifyUserWordFieldNotEmpty() {
        waitForVisibilityOf(locators.getBackBtnLoc());
        waitForVisibilityOf(userWordFieldLocator);
        Reporter.log("Checking if user word field has letters displayed.");
        System.out.println("Checking if user word field has letters displayed.");
        String text = find(userWordFieldLocator).getText();
        Assert.assertNotEquals(text, "", "User word field is empty!");
        Reporter.log("User word field not empty! Text displayed: " + text);
        System.out.println("User word field not empty! Text displayed: " + text);
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
        String userWordText = find(userWordFieldLocator).getText();
        Reporter.log("Verifying clicked letters are shown in user word text field.");
        System.out.println("Verifying clicked letters are shown in user word text field.");
        Assert.assertEquals(userWordText, clickedLetters, "Clicked letters are not displayed.");
        Reporter.log("Letters displayed!. Complete user text displayed: " + userWordText);
        System.out.println("Letters displayed!. Complete user text displayed: " + userWordText);
    }

}
