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
    public void verifyOpen() {
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.loginPage.openLoginPage().userLogin(prop.getProperty("userTestRegisterUsername"), prop.getProperty("userTestRegisterPassword")).clickSinglePlayerGame().verifyTitlesAndMenuElements(prop.getProperty("singlePlayerGamePageTitle"), prop.getProperty("singlePlayerGamePageContainerTitle"));
        this.mojBrojPage = (MojBrojPage) singlePlayerGamePage.openMojBrojPage();
    }

    @Test(priority = 7)
    public void goBackAndLogOutTest() {
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.mojBrojPage.goBack();
        HomePage homePage = (HomePage) this.singlePlayerGamePage.goBack();
        homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
