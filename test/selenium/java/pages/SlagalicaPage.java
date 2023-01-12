package pages;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;

public class SlagalicaPage extends TestBase {

    public SlagalicaPage() {super();}

    By stopButtonLocator = new By.ById("stopButton");

    By potvrdiButtonLocator = new By.ById("submitButton");

    By izbrisiButtonLocator = new By.ById("deleteLetter");

    By firstListLocator = new By.ByXPath("//div[@class='pt-5']");

    By secondListLocator = new By.ByXPath("//body/div[@class='container p-5']/div[@class='row d-block text-center']/div[@class='pt-2']");

    By popUpDialog = new By.ByXPath("//div[@class='modal-content']");

    By closeButton = new By.ByXPath("//button[text()='Zatvori']");


    public void verifyThatStopButtonIsClicked(){
        WebElement stop = driver.findElement((stopButtonLocator));
        Assert.assertTrue(stop.isDisplayed(), "Stop button not displayed!");
        Reporter.log("Stop button is clicked.");
        System.out.println("Stop button is clicked.");
    }

    public void verifyThatPotvrdiButtonIsClicked(){
        driver.findElement((potvrdiButtonLocator)).click();
        Reporter.log("Potvrdi button is clicked.");
        System.out.println("Potvrdi button is clicked.");
    }

    public void verifyThatIzbrisiButtonIsClicked(){
        driver.findElement((izbrisiButtonLocator)).click();
        Reporter.log("Izbrisi button is clicked.");
        System.out.println("Izbrisi button is clicked.");
    }

    public SinglePlayerGamePage verifyThatBackButtonIsClicked(){
        driver.findElement((locators.getBackBtnLoc())).click();
        Reporter.log("Back button is clicked.");
        System.out.println("Back button is clicked.");
        return new SinglePlayerGamePage();
    }

    public void verifyThatFirstListIsDisplayed(){
        WebElement firstlist = driver.findElement((firstListLocator));
        Assert.assertTrue(firstlist.isDisplayed(), "First list not displayed!");
        Reporter.log("First list is displayed.");
        System.out.println("First list is displayed.");
    }

    public void verifyThatSecondListIsDisplayed(){
        WebElement secondlist = driver.findElement((secondListLocator));
        Assert.assertTrue(secondlist.isDisplayed(), "Second list not displayed!");
        Reporter.log("First list is displayed.");
        System.out.println("First list is displayed.");
    }

    public void verifyPopUpDialog(){
        wait.until(ExpectedConditions.elementToBeClickable(popUpDialog));
        WebElement popup = driver.findElement((popUpDialog));
        Assert.assertTrue(popup.isDisplayed(), "Popup dialog not displayed!");
        Reporter.log("Dialog is displayed.");
        System.out.println("Dialog is displayed.");
    }

    public void verifyCloseButtonIsClicked(){
        wait.until(ExpectedConditions.elementToBeClickable(closeButton));
        driver.findElement((closeButton)).click();
        Reporter.log("Dialog is displayed.");
        System.out.println("Dialog is displayed.");

    }


}
