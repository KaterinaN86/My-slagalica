package testCases;

import base.TestBase;
import org.springframework.context.annotation.Primary;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import utility.VerifyMethods;

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
    @Test(priority = 1)
    public void verifyOpen(){
        this.homePage= this.loginPage.openLoginPage().userLogin(prop.getProperty("userKaterinaUsername"), prop.getProperty("userKaterinaPassword"));
        this.homePage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("homePageTitle"),prop.getProperty("homePageContainerTitle"));
    }
    @Test(priority = 2)
    public void verifyLinks(){
       this.homePage.verifyMethods.verifyValidLinkNumber(this.getValidLinkNumber(),Integer.parseInt(prop.getProperty("homePageLinksNumber")));
    }

    @Test(priority = 3)
    public void verifyLogout(){
        this.homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
