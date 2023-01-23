package testCases;

import base.TestBase;
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

    @Test(priority = 1)
    public void logInAndOpen() {
        this.homePage = this.loginPage.openLoginPage().userLogin(prop.getProperty("userTestRegisterUsername"),
                prop.getProperty("userTestRegisterPassword"));
        this.singlePlayerGamePage = this.homePage.clickSinglePlayerGame();
        this.slagalicaPage = this.singlePlayerGamePage.openSlagalicaPage();
    }

    @Test(priority = 2)
    public void verifyTimerStart() {
        verifyMethods.verifyTimerStartValue(prop.getProperty("slagalicaPageTimerStart"));
    }

    @Test(priority = 3)
    public void verifyOpen() {
        this.slagalicaPage.verifyTitlesAndContainer();
    }

    @Test(priority = 4)
    public void verifyFirstList() {
        this.slagalicaPage.verifyThatFirstListIsDisplayed();
    }

    @Test(priority = 5)
    public void verifySecondList() {
        this.slagalicaPage.verifyThatSecondListIsDisplayed();
    }

    @Test(priority = 6)
    public void verifyDeleteButton() {
        this.slagalicaPage.verifyThatIzbrisiButtonIsClicked();
    }

    @Test(priority = 7)
    public void verifyStopButton() {
        this.slagalicaPage.verifyStopButtonDisplayed();
    }

    @Test(priority = 8)
    public void verifyPotvrdiButton() {
        this.slagalicaPage.verifyThatPotvrdiButtonIsClicked();
    }

    @Test(priority = 9)
    public void verifyDialog() {
        this.slagalicaPage.verifyPopUpDialog();
    }

    @Test(priority = 10)
    public void verifyCloseButtonClicked() {
        this.slagalicaPage.verifyCloseButtonIsClicked();
    }

    @Test(priority = 11)
    public void verifyDialogClosed() {
        takeSnapShot("Slagalica\\verifyDialogClosed", prop.getProperty("snapShotExtension"));
        this.slagalicaPage.waitForPopuptoClose();
    }

    @Test(priority = 13)
    public void verifyBackButton() {
        this.slagalicaPage.verifyMethods.verifyBackButtonIsClickable();
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.slagalicaPage.goBack();
    }

    @Test(priority = 14)
    public void goBackToHomePage() {
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
        this.homePage = (HomePage) this.singlePlayerGamePage.goBack();
        this.homePage.verifyMethods.verifyContainerDisplayed();
    }

    @Test(priority = 15)
    public void logout() {
        this.homePage.verifyMethods.verifyButtonIsClickable(this.homePage.getLogOutLoc());
        this.homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}