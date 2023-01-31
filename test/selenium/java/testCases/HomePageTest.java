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
    public void verifyOpenLogin() {
        this.loginPage.openLoginPage();
    }

    @Test(priority = 2)
    public void verifySignIn() {
        this.homePage = this.loginPage.userLogin("",prop.getProperty("userKaterinaUsername"), prop.getProperty("userKaterinaPassword"),"User "+ prop.getProperty("userAdisUsername")+ " logged in.");
    }

    @Test(priority = 3)
    public void verifyStateAfterSingIn() {
        this.homePage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("homePageTitle"), prop.getProperty("homePageContainerTitle"));
    }

    @Test(priority = 4)
    public void verifyLinks() {
        this.homePage.verifyMethods.verifyValidLinkNumber(this.getValidLinkNumber(), Integer.parseInt(prop.getProperty("homePageLinksNumber")));
    }

    @Test(priority = 5)
    public void verifyLogout() {
        this.homePage.verifyMethods.verifyButtonIsClickable(this.homePage.getLogOutLoc());
    }

    @Test(priority = 6)
    public void logout() {
        this.homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
