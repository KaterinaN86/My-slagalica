package testCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MojBrojPage;
import pages.SinglePlayerGamePage;
import utility.VerifyMethods;

public class SinglePlayerGamePageTest extends TestBase {
    /**
     * SinglePlayerGamePage object used in tests.
     */
    SinglePlayerGamePage singlePlayerGamePage;

    /**
     * Constructor
     */
    public SinglePlayerGamePageTest() {
        super();
    }

    /**
     * Method executed before the first test in the class.
     */
    @BeforeClass
    public void setup() {
        //Calling parent class init method to initialize properties and drivers.
        init();
        this.singlePlayerGamePage = new SinglePlayerGamePage();
        this.verifyMethods = new VerifyMethods(this.singlePlayerGamePage);
    }

    /**
     * Open login page, login with user adis (user needs to be registered first), click on button for single player game and verify elements on single player game page after open.
     */
    @Test(priority = 0)
    public void verifyOpen() {
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.loginPage.openLoginPage().userLogin(prop.getProperty("userAdisUsername"), prop.getProperty("userAdisPassword")).clickSinglePlayerGame();
    }

    @Test(priority = 1)
    public void verifyElementsAndLinks() {
        verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("singlePlayerGamePageTitle"), prop.getProperty("singlePlayerGamePageContainerTitle"));
        verifyMethods.verifyValidLinkNumber(this.getValidLinkNumber(), Integer.parseInt(prop.getProperty("singlePlayerGamePageLinksNumber")));
    }

    @Test(priority = 2)
    public void openMojBrojTest() {
        MojBrojPage mojBrojPage = this.singlePlayerGamePage.openMojBrojPage();
        this.singlePlayerGamePage = (SinglePlayerGamePage) mojBrojPage.goBack();
    }

    @Test(priority = 7)
    public void goBackAndLogOutTest() {
        HomePage homePage = (HomePage) verifyMethods.verifyPageObjectInitialized(this.singlePlayerGamePage.goBack());
        homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
