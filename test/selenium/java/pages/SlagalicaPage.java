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

    By stopButtonLocator = new By.ById("stopButton");
    By potvrdiButtonLocator = new By.ById("submitButton");
    By izbrisiButtonLocator = new By.ById("deleteLetter");
    public By firstListLocator = new By.ByXPath("//div[@class='pt-5']");
    public By secondListLocator = new By.ByXPath("//div[@class='pt-2']");
    By userWordFieldLocator = new By.ById("userWord");
    By buttonsInListLocator = new By.ByXPath("//*[starts-with(@id,'btn')]");
    By popUpDialog = new By.ByXPath("//div[@class='modal-content']");
    By closeButton = new By.ByXPath("//button[text()='Zatvori']");
    List<String> dictionaryWordsList;
    List<WebElement> firstListLetterButtons;
    List<WebElement> secondListLetterButtons;
    String letters = "";

    public SlagalicaPage() {
        super();
    }

    public void printWords() {
        this.dictionaryWordsList = new ReadFromFile().readFromFile(prop.getProperty("pathToWordsFIle"));
        FindWordForSlagalicaGame findWord = new FindWordForSlagalicaGame();
        System.out.println(findWord.computersLongestWord("ULjONjENEDžUSET", dictionaryWordsList));
    }

    public void verifyStopButtonDisplayed() {
        Assert.assertTrue(find(stopButtonLocator).isDisplayed(), "Stop button not displayed!");
        Reporter.log("Stop button is displayed.");
        System.out.println("Stop button is displayed.");
    }

    public void verifyThatStopButtonIsClicked() {
        waitForElToBeClickable(stopButtonLocator, Duration.ofSeconds(10));
        verifyMethods.verifyButtonIsClickable(stopButtonLocator);
        click(stopButtonLocator);
        Reporter.log("Stop button is clicked.");
        System.out.println("Stop button is clicked.");
        waitForElToBeClickable(locators.getH1TitleLoc());
        waitForVisibilityOf(locators.getContainerLoc());
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

    public SinglePlayerGamePage goBackAfterGameFinished() {
        waitForElToBeClickable(locators.getContainerLoc());
        String page = this.getClass().getSimpleName();
        Reporter.log("Click back button on page: " + page);
        System.out.println("Click back button on page: " + page);
        verifyMethods.verifyBackButtonIsClickable();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", find(locators.getBackBtnLoc()));
        return (SinglePlayerGamePage) verifyMethods.verifyPageObjectInitialized(new SinglePlayerGamePage());
    }

    private void initializeButtonsLists(){
        firstListLetterButtons = find(firstListLocator).findElements(buttonsInListLocator);
        secondListLetterButtons = find(secondListLocator).findElements(buttonsInListLocator);
    }

    public void clickLetterFromList(List<WebElement> btnList) {
        waitForVisibilityOf(firstListLocator);
        Random rn = new Random();
        int index = rn.nextInt(btnList.size());
        WebElement letterButton = btnList.get(index);
        wait.until(ExpectedConditions.visibilityOf(letterButton));
        Assert.assertTrue(letterButton.isEnabled(), "Selected letter button not clickable!");
        letterButton.click();
        String letter = letterButton.getText();
        letters += letter;
        Reporter.log("Clicked letter: " + letter);
        System.out.println("Clicked letter: " + letter);
    }

    public void clickLettersBeforeStop(By locator) {
        Reporter.log("Click letters before clicking stop button.");
        System.out.println("Click letters before clicking stop button.");
        waitForVisibilityOf(locator);
        List <WebElement> buttonsList = find(locator).findElements(buttonsInListLocator);
        int index = new Random().nextInt(buttonsList.size());
        wait.until(ExpectedConditions.visibilityOfAllElements(buttonsList));
        buttonsList.get(index).click();
        waitForElToBeClickable(locators.getH1TitleLoc());
        Reporter.log("Clicked button on position " + index + " in list of letters.");
        System.out.println("Clicked button on position " + index + " in list of letters.");
        waitForVisibilityOf(userWordFieldLocator);
        System.out.println(find(userWordFieldLocator).getText());
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

    public void verifyLettersAppearsInUserWrodField() {
        waitForVisibilityOf(userWordFieldLocator);
        verifyMethods.verifyButtonNotClickable(userWordFieldLocator);
        String userWordText = find(userWordFieldLocator).getText();
        Reporter.log("Verifying clicked letter is shown in user word text field.");
        System.out.println("Verifying clicked letter is shown in user word text field.");
        Assert.assertEquals(userWordText, letters, "Clicked letter is not displayed.");
        Reporter.log("Letter is displayed!. Complete user text displayed: " + userWordText);
        System.out.println("Letter is displayed!. Complete user text displayed: " + userWordText);
    }

}
