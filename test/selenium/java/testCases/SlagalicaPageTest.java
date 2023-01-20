package testCases;

import base.TestBase;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SinglePlayerGamePage;
import pages.SlagalicaPage;
import utility.VerifyMethods;

import java.io.IOException;

public class SlagalicaPageTest extends TestBase {

    SlagalicaPage slagalicaPage;

    SinglePlayerGamePage singlePlayerGamePage;

    HomePage homePage;

    public SlagalicaPageTest() {
        super();
    }

    @BeforeClass
    public void setup() {
        init();
        this.slagalicaPage = new SlagalicaPage();
        verifyMethods = new VerifyMethods(this.slagalicaPage);
    }

    @Test(priority = 1)
    public void verifyOpen() {
        this.singlePlayerGamePage = this.loginPage.openLoginPage().userLogin(prop.getProperty("userTestRegisterUsername"),
                prop.getProperty("userTestRegisterPassword")).clickSinglePlayerGame();
        this.slagalicaPage = this.singlePlayerGamePage.openSlagalicaPage();
    }

    @Test(priority = 2)
    public void verifyTimerStart() {
        verifyMethods.verifyTimerStartValue(prop.getProperty("slagalicaPageTimerStart"));
    }

    @Test(priority = 3)
    public void verifyFirstList() {
        this.slagalicaPage.verifyThatFirstListIsDisplayed();
    }

    @Test(priority = 4)
    public void verifySecondList() {
        this.slagalicaPage.verifyThatSecondListIsDisplayed();
    }

    @Test(priority = 5)
    public void verifyDeleteButton() {
        this.slagalicaPage.verifyThatIzbrisiButtonIsClicked();
    }

    @Test(priority = 6)
    public void verifyStopButton() {
        this.slagalicaPage.verifyThatStopButtonIsClicked();
    }

    @Test(priority = 7)
    public void verifyPotvrdiButton() {
        this.slagalicaPage.verifyThatPotvrdiButtonIsClicked();
    }

    @Test(priority = 8)
    public void verifyDialog() {
        this.slagalicaPage.verifyPopUpDialog();
    }

    @Test(priority = 9)
    public void verifyCloseButtonClicked() {
        this.slagalicaPage.verifyCloseButtonIsClicked();
    }

    @Test(priority = 10)
    public void verifyDialogClosed() {
        takeSnapShot("Slagalica\\verifyDialogClosed", prop.getProperty("snapShotExtension"));
        this.slagalicaPage.waitForPopuptoClose();
    }

    @Test(priority = 12)
    public void verifyBackButton() {
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getBackBtnLoc()));
        this.slagalicaPage.verifyMethods.verifyBackButtonIsClickable();
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.slagalicaPage.goBack();
        wait.until(ExpectedConditions.elementToBeClickable(locators.getContainerLoc()));
    }

    @Test(priority = 13)
    public void goBackToHomePage() {
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getBackBtnLoc()));
        wait.until(ExpectedConditions.elementToBeClickable(locators.getBackBtnLoc()));
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
        this.homePage = (HomePage) this.singlePlayerGamePage.goBack();
    }

    @Test(priority = 14)
    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(locators.getContainerLoc()));
        this.homePage.verifyMethods.verifyButtonIsClickable(this.homePage.getLogOutLoc());
        this.homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}