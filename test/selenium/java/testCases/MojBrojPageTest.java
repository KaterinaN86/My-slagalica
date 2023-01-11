package testCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MojBrojPage;
import pages.SinglePlayerGamePage;
import utility.VerifyMethods;

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
        verifyMethods = new VerifyMethods(this.mojBrojPage);
    }

    @Test(priority = 0)
    public void verifyOpen() {
        this.singlePlayerGamePage = this.loginPage.openLoginPage().userLogin(prop.getProperty("userTestRegisterUsername"), prop.getProperty("userTestRegisterPassword")).clickSinglePlayerGame();
        this.mojBrojPage = singlePlayerGamePage.openMojBrojPage();
    }

    @Test(priority = 1)
    public void verifyPageElements() {
        verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("mojBrojPageTitle"), prop.getProperty("mojBrojPageContainerTitle"));
    }

    @Test(priority = 2)
    public void verifyTimerStart() {
      verifyMethods.verifyTimerStartValue(prop.getProperty("mojBrojPageTimerStart"));
    }

    @Test(priority = 3)
    public void verifyLinks() {
       verifyMethods.verifyValidLinkNumber(this.getValidLinkNumber(), Integer.parseInt(prop.getProperty("mojBrojLinksNumber")));
    }

    @Test(priority = 4)
    public void verifyTargetNumber() {
        this.mojBrojPage.verifyTargetValueIsPositiveInteger();
    }

    @Test(priority = 7)
    public void goBackAndLogOutTest() {
        this.singlePlayerGamePage = (SinglePlayerGamePage) verifyMethods.verifyPageObjectInitialized(this.mojBrojPage.goBack());
        HomePage homePage = (HomePage) this.singlePlayerGamePage.goBack();
        homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
