package testCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
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
    }

    /**
     * Open login page, login with user adis (user needs to be registered first), click on button for single player game and verify elements on single player game page after open.
     */
    @Test(priority = 0)
    public void verifyOpen() {
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.loginPage.openLoginPage().userLogin(prop.getProperty("userAdisUsername"), prop.getProperty("userAdisPassword")).clickSinglePlayerGame();
    }

    /**
     * Verifies page elements and titles. Verifies active links number.
     */
    @Test(priority = 1)
    public void verifyElementsAndLinks() {
        this.singlePlayerGamePage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("singlePlayerGamePageTitle"), prop.getProperty("singlePlayerGamePageContainerTitle"));
        this.singlePlayerGamePage.verifyMethods.verifyValidLinkNumber(this.getValidLinkNumber(), Integer.parseInt(prop.getProperty("singlePlayerGamePageLinksNumber")));
    }

    /**
     * Verifies moj broj button. Screen shot is taken.
     * @throws Exception
     */
    @Test(priority = 2)
    public void varifyMojBrojBtn() throws Exception {
        takeSnapShot(prop.getProperty("screenShotMojBrojBtnPath"));
        this.singlePlayerGamePage.verifyMojBrojBtnIsClickable();
    }

    /**
     * Open 'Moj Broj' page and go back to SinglePlayerGamePage.
     */
    @Test(priority = 3)
    public void openMojBrojTest() {
        MojBrojPage mojBrojPage = this.singlePlayerGamePage.openMojBrojPage();
        this.singlePlayerGamePage = (SinglePlayerGamePage) mojBrojPage.goBack();
    }

    /**
     * Verifies go back button (left pointing arrow) is clickable.
     */
    @Test(priority = 4)
    public void varifyGoBackBtn() {
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
    }

    @Test(priority = 7)
    public void goBackAndLogOutTest() throws Exception {

        //HomePage homePage = (HomePage) this.singlePlayerGamePage.goBack();
        this.singlePlayerGamePage.goBack();
        takeSnapShot(prop.getProperty("screenShotGoBackPath"));
        // homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
