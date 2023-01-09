package testCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SinglePlayerGamePage;

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
        this.singlePlayerGamePage=new SinglePlayerGamePage();
    }

    /**
     * Open login page, login with user adis (user needs to be registered first), click on button for single player game and verify elements on single player game page after open.
     */
    @Test(priority = 0)
    public void verifyOpen(){
        this.singlePlayerGamePage= (SinglePlayerGamePage) this.loginPage.openLoginPage().userLogin(prop.getProperty("userAdisUsername"), prop.getProperty("userAdisPassword")).clickSinglePlayerGame().verifyTitlesAndMenuElements(prop.getProperty("singlePlayerGamePageTitle"),prop.getProperty("singlePlayerGamePageContainerTitle"));
    }
    @Test(priority = 1)
    public void verifyLinks(){
        this.singlePlayerGamePage= (SinglePlayerGamePage) this.singlePlayerGamePage.verifyValidLinkNumber(this.getValidLinkNumber(),Integer.parseInt(prop.getProperty("singlePlayerGamePageLinksNumber")));
    }
    @AfterClass
    public void tearDown() {
        close();
    }
}
