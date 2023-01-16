package testCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MojBrojPage;
import pages.SinglePlayerGamePage;

public class MojBrojPageTest extends TestBase {
    /**
     * MojBrojPage instance used in several tests.
     */
    MojBrojPage mojBrojPage;
    /**
     * SinglePlayerGamePage instance used in several tests.
     */
    SinglePlayerGamePage singlePlayerGamePage;

    /**
     * Constructor
     */
    public MojBrojPageTest() {
        super();
    }

    /**
     * Method executed before the first test in the class.
     */
    @BeforeClass
    public void setup() {
        //Calling parent class init method to initialize properties and drivers.
        init();
        this.mojBrojPage = new MojBrojPage();
    }

    @Test(priority = 0)
    public void verifyOpen() throws Exception {
        this.singlePlayerGamePage = this.loginPage.openLoginPage().userLogin(prop.getProperty("userTestRegisterUsername"), prop.getProperty("userTestRegisterPassword")).clickSinglePlayerGame();
        takeSnapShot(prop.getProperty("screenShotMojBrojBtnPath"));
        this.singlePlayerGamePage.verifyMojBrojBtnIsClickable();
        this.mojBrojPage = singlePlayerGamePage.openMojBrojPage();
    }

    @Test(priority = 2)
    public void verifyPageElements() throws Exception {
        takeSnapShot(prop.getProperty("screenShotMojBrojPagePath"));
        this.mojBrojPage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("mojBrojPageTitle"), prop.getProperty("mojBrojPageContainerTitle"));
    }

    @Test(priority = 1)
    public void verifyTimerStart() {
       this.mojBrojPage.verifyMethods.verifyTimerStartValue(prop.getProperty("mojBrojPageTimerStart"));
    }

    @Test(priority = 3)
    public void verifyTargetNumber() {
        this.mojBrojPage.verifyTargetValueIsPositiveInteger();
    }

    @Test(priority = 4)
    public void verifyTotalNumbers() {
        this.mojBrojPage.verifyNumberButtonsTotal();
    }
    @Test(priority = 5)
    public void verifyNumbers() {
        this.mojBrojPage.verifyNumbersValues();
    }

    @Test(priority = 7)
    public void goBackAndLogOutTest() throws Exception {
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.mojBrojPage.goBack();
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
        HomePage homePage = (HomePage) this.singlePlayerGamePage.goBack();
        takeSnapShot(prop.getProperty("screenShotLogoutPath"));
        homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
