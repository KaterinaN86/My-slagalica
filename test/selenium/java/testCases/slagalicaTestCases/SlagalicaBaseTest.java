package testCases.slagalicaTestCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SinglePlayerGamePage;
import pages.SlagalicaPage;
import utility.VerifyMethods;

public class SlagalicaBaseTest extends TestBase {
    SlagalicaPage slagalicaPage;

    SinglePlayerGamePage singlePlayerGamePage;

    HomePage homePage;

    public SlagalicaBaseTest() {
        super();
    }

    @BeforeClass
    public void setup() {
        init();
        verifyMethods = new VerifyMethods(this.slagalicaPage);
    }

    @Test(priority = 1)
    public void logInAndOpen() {
        this.homePage = this.loginPage.openLoginPage().userLogin("", prop.getProperty("userTestRegisterUsername"), prop.getProperty("userTestRegisterPassword"), "User " + prop.getProperty("userTestRegisterUsername") + " logged in.");
        this.singlePlayerGamePage = this.homePage.clickSinglePlayerGame();
        this.slagalicaPage = this.singlePlayerGamePage.openSlagalicaPage();
    }

    @Test(priority = 2)
    public void verifyTimerStart() {
        this.slagalicaPage.verifyMethods.verifyTimerStartValue(prop.getProperty("slagalicaPageTimerStart"));
    }

    @Test(priority = 3)
    public void verifyOpen() {
        this.slagalicaPage.verifyTitlesAndContainer();
    }

    @Test(priority = 24)
    public void verifyBackButton() {
        this.slagalicaPage.waitForVisibilityOf(locators.getBackBtnLoc());
    }

    @Test(priority = 25)
    public void goBackToSinglePlayerGamePage() {
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.slagalicaPage.goBack();
    }

    @Test(priority = 26)
    public void verifyBeforeGoBackToHomePage() {
        this.singlePlayerGamePage.verifyBeforeGoingBack();
    }

    @Test(priority = 27)
    public void goBackToHomePage() {
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
        this.homePage = (HomePage) this.singlePlayerGamePage.goBack();
        this.homePage.verifyMethods.verifyContainerDisplayed();
    }

    @Test(priority = 28)
    public void waitBeforeLogoutTest() {
        this.waitForElToBeClickable(locators.getH1TitleLoc());
        this.homePage.waitBeforeLogout();
    }

    @Test(priority = 29)
    public void logout() {
        this.homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
