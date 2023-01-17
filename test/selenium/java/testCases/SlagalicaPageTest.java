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

    @Test(priority = 0)
    public void verifyOpen() {
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.loginPage.openLoginPage().userLogin(prop.getProperty("userTestRegisterUsername"),
                prop.getProperty("userTestRegisterPassword")).clickSinglePlayerGame();
        this.slagalicaPage = this.singlePlayerGamePage.openSlagalicaPage();
    }

    @Test(priority = 1)
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
    public void verifyDialogClosed() throws Exception {
        takeSnapShot("Slagalica\\verifyDialogClosed", prop.getProperty("snapShotExtension"));
        this.slagalicaPage.waitForPopuptoClose();
    }

    @Test(priority = 11)
    public void verifyBackButton() {
        wait.until(ExpectedConditions.elementToBeClickable(this.slagalicaPage.locators.getContainerLoc()));
        this.slagalicaPage.verifyMethods.verifyBackButtonIsClickable();
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.slagalicaPage.goBack();
        wait.until(ExpectedConditions.elementToBeClickable(this.singlePlayerGamePage.locators.getContainerLoc()));
    }

    @Test(priority = 12)
    public void goBackToHomePage() {
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
        wait.until(ExpectedConditions.elementToBeClickable(this.singlePlayerGamePage.locators.getContainerLoc()));
        this.homePage = (HomePage) this.singlePlayerGamePage.goBack();
    }

    @Test(priority = 13)
    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(this.homePage.locators.getContainerLoc()));
        this.homePage.verifyMethods.verifyButtonIsClickable(this.homePage.getLogOutLoc());
        this.homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}