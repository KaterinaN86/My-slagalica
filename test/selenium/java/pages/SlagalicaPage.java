package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import utility.FindWordForSlagalicaGame;
import utility.ReadFromFile;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class SlagalicaPage extends TestBase {

    By stopButtonLocator = new By.ById("stopButton");
    By potvrdiButtonLocator = new By.ById("submitButton");
    By izbrisiButtonLocator = new By.ById("deleteLetter");
    By firstListLocator = new By.ByXPath("//div[@class='pt-5']");
    By secondListLocator = new By.ByXPath("//body/div[@class='container p-5']/div[@class='row d-block text-center']/div[@class='pt-2']");
    By popUpDialog = new By.ByXPath("//div[@class='modal-content']");
    By closeButton = new By.ByXPath("//button[text()='Zatvori']");
    List<String> dictionaryWordsList;

    public SlagalicaPage() {
        super();
    }

    public void printWords() throws IOException {
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
    }

    public void verifyThatPotvrdiButtonIsClicked() {
        click(potvrdiButtonLocator);
        Reporter.log("Potvrdi button is clicked.");
        System.out.println("Potvrdi button is clicked.");
    }

    public void verifyThatIzbrisiButtonIsClicked() {
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

}
