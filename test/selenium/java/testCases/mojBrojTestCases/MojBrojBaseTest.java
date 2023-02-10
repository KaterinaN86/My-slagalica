package testCases.mojBrojTestCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MojBrojPage;
import pages.SinglePlayerGamePage;

public class MojBrojBaseTest extends TestBase {

    /**
     * MojBrojPage instance used in several tests.
     */
    MojBrojPage mojBrojPage;
    /**
     * SinglePlayerGamePage instance used in several tests.
     */
    SinglePlayerGamePage singlePlayerGamePage;
    HomePage homePage;
    TestBase tempPageObject;

    public MojBrojBaseTest() {
        super();
    }

    /**
     * Method executed before the first test in the class.
     */
    @BeforeClass
    public void setup() {
        //Calling parent class init method to initialize properties and drivers.
        init();
    }

    @Test(priority = 1)
    public void verifyOpenMojBroj() {
        this.homePage = this.loginPage.openLoginPage().userLogin("", prop.getProperty("userTestRegisterUsername"), prop.getProperty("userTestRegisterPassword"), "User " + prop.getProperty("userAdisUsername") + " logged in.");
        this.singlePlayerGamePage = this.homePage.clickSinglePlayerGame();
        takeSnapShot(this.getClass().getSimpleName() + "\\verifyOpenMojBroj", prop.getProperty("snapShotExtension"));
        this.mojBrojPage = singlePlayerGamePage.openMojBrojPage();
    }

    @Test(priority = 2)
    public void verifyTimerStart() {
        takeSnapShot(this.getClass().getSimpleName() + "\\verifyTimerStart", prop.getProperty("snapShotExtension"));
        this.mojBrojPage.verifyMethods.verifyTimerValue(prop.getProperty("mojBrojPageTimerStart"));
    }

    @Test(priority = 3)
    public void verifyMojBrojPageElements() {
        this.mojBrojPage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("mojBrojPageTitle"), prop.getProperty("mojBrojPageContainerTitle"));
    }

    @Test(priority = 4)
    public void verifyTargetNumberTest() {
        this.mojBrojPage.verifyTargetValueIsPositiveInteger();
    }

    @Test(priority = 5)
    public void verifyTotalNumbersTest() {
        this.mojBrojPage.verifyNumberButtonsTotal();
    }

    @Test(priority = 6)
    public void verifyNumbersTest() {
        this.mojBrojPage.verifyNumbersValues();
    }

    @Test(priority = 7)
    public void verifyOperatorsTest() {
        this.mojBrojPage.verifyOperators();
    }


    @Test(priority = 24)
    public void GoBackToSinglePlayerPageTest() {
        this.mojBrojPage.verifyMethods.verifyBackButtonIsClickable();
        //Added step as a workaround regarding error in firefox
        this.tempPageObject = this.mojBrojPage.goBack();
    }

    @Test(priority = 25)
    public void singlePlayerPageOpenedTest() {
        //Added because of firefox bug.
        this.singlePlayerGamePage = this.mojBrojPage.firefoxWorkaround(tempPageObject);
        this.singlePlayerGamePage.verifyMethods.verifyContainerDisplayed();
        this.singlePlayerGamePage.verifyMethods.verifyButtonNotClickable(this.singlePlayerGamePage.getMojBrojBtnLoc());
    }

    @Test(priority = 26)
    public void goBackToHomePage() {
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
        this.homePage = (HomePage) this.singlePlayerGamePage.goBack();
    }

    @Test(priority = 27)
    public void logout() {
        this.homePage.logout();
        takeSnapShot(this.getClass().getSimpleName() + "\\logout", prop.getProperty("snapShotExtension"));
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
