package testCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;

public class HomePageTest extends TestBase {
    /**
     * HomePage object used in tests.
     */
    HomePage homePage;

    public HomePageTest() {
        super();
    }
    /**
     * Method executed before the first test in the class.
     */
    @BeforeClass
    public void setup() {
        //Calling parent class init method to initialize properties and drivers.
        init();
        this.homePage=new HomePage();
    }
    @Test(priority = 0)
    public void verifyOpen(){
        this.homePage= (HomePage) this.loginPage.openLoginPage().userLogin(prop.getProperty("userKaterinaUsername"), prop.getProperty("userKaterinaPassword")).verifyTitlesAndMenuElements(prop.getProperty("homePageTitle"),prop.getProperty("homePageContainerTitle"));
    }
    @Test(priority = 1)
    public void verifyLinks(){
        this.homePage= (HomePage) this.homePage.verifyValidLinkNumber(this.getValidLinkNumber(),Integer.parseInt(prop.getProperty("homePageLinksNumber")));
    }
    @AfterClass
    public void tearDown() {
        close();
    }
}
