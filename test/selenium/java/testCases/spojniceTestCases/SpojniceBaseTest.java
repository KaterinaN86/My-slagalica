package testCases.spojniceTestCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SinglePlayerGamePage;
import pages.SpojnicePage;
import utility.VerifyMethods;

public class SpojniceBaseTest extends TestBase {

    SpojnicePage spojnicePage;
    HomePage homePage;
    SinglePlayerGamePage singlePlayerGamePage;
    TestBase tempPageObject;

    public SpojniceBaseTest() {
        super();
    }

    @BeforeClass
    public void setup() {
        init();
        this.verifyMethods = new VerifyMethods(this.spojnicePage);
    }

    @Test(priority = 1)
    public void logInAndOpen() {
        this.homePage = this.loginPage.openLoginPage().userLogin("", prop.getProperty("userTestRegisterUsername"), prop.getProperty("userTestRegisterPassword"), prop.getProperty("loginSuccessMsg"));
        this.singlePlayerGamePage = this.homePage.clickSinglePlayerGame();
        this.spojnicePage = this.singlePlayerGamePage.openSpojnicePage();
    }

    @Test(priority = 2)
    public void verifyTimerStart() {
        this.spojnicePage.verifyMethods.verifyTimerStartValue(prop.getProperty("spojnicePageTimerStart"));
    }

    @Test(priority = 3)
    public void verifyTitlesTest() {
        this.spojnicePage.verifyTitlesAfterOpen(prop.getProperty("spojnicePageTitle"), prop.getProperty("spojniceContentTitle"));
    }

    @Test(priority = 4)
    public void verifyGameHeadlineTest() {
        this.spojnicePage.verifyGameHeadline();
        this.spojnicePage.waitForElToBeClickable(locators.getH1TitleLoc());
    }

    @Test(priority = 5)
    public void verifyButtonElementsTest() {
        this.spojnicePage.verifyTableContent();
    }

    @Test(priority = 24)
    public void GoBackToSinglePlayerPageTest() {
        this.spojnicePage.verifyMethods.verifyBackButtonIsClickable();
        this.tempPageObject = this.spojnicePage.goBack();
    }

    @Test(priority = 25)
    public void singlePlayerPageOpenedTest() {
        //Added because of firefox bug.
        this.singlePlayerGamePage = this.spojnicePage.firefoxWorkaround(tempPageObject);
        this.singlePlayerGamePage.verifyMethods.verifyContainerDisplayed();
    }

    @Test(priority = 26)
    public void verifyBeforeGoBackToHomePage() {
        this.singlePlayerGamePage.waitForElToBeClickable(locators.getH1TitleLoc());
        this.singlePlayerGamePage.verifyBeforeGoingBack();
    }

    @Test(priority = 27)
    public void verifySpojnicePointsTest() {
        this.singlePlayerGamePage.verifySpojnicePoints(this.spojnicePage);
        takeSnapShot(this.getClass().getSimpleName() + "\\verifySpojnicePointsTest", prop.getProperty("snapShotExtension"));
    }
    @Test(priority = 28)
    public void verifyTotalPointsAfterGame() {
        this.singlePlayerGamePage.verifyTotalPoints(this.spojnicePage);
        takeSnapShot(this.getClass().getSimpleName() + "\\verifyTotalPointsAfterGame", prop.getProperty("snapShotExtension"));
    }

    @Test(priority = 29)
    public void goBackToHomePage() {
        this.singlePlayerGamePage.waitForElToBeClickable(locators.getH1TitleLoc());
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
        this.homePage = (HomePage) this.singlePlayerGamePage.goBack();
        this.homePage.verifyMethods.verifyContainerDisplayed();
    }

    @Test(priority = 30)
    public void waitBeforeLogoutTest() {
        this.homePage.waitForElToBeClickable(locators.getH1TitleLoc());
        this.homePage.waitBeforeLogout();
    }

    @Test(priority = 31)
    public void logout() {
        this.homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }

}
