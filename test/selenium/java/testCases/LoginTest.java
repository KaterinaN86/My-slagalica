package testCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;

/**
 * Class with test methods for Login page. Inherits TestBase fields and methods.
 */
public class LoginTest extends TestBase {

    /**
     * HomePage instance, used in several methods.
     */
    HomePage homePage;

    /**
     * Empty constructor.
     */
    public LoginTest() {
        //Calling parent class constructor.
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
    public void loginPageOpenTest() {
        this.loginPage = loginPage.openLoginPage();
    }

    @Test(priority = 2)
    public void userLoginTest() {
        this.homePage = this.loginPage.userLogin(prop.getProperty("userKaterinaUsername"), prop.getProperty("userKaterinaPassword"));
    }

    @Test(priority = 3)
    public void verifyStateAfterLogin() {
        this.homePage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("homePageTitle"), prop.getProperty("homePageContainerTitle"));
    }

    @Test(priority = 4)
    public void verifyLogOut() {
        this.homePage.verifyMethods.verifyButtonIsClickable(this.homePage.getLogOutLoc());
    }

    @Test(priority = 5)
    public void logout() {
        this.loginPage = this.homePage.logout();
    }

    @Test(priority = 6)
    public void secondUserLoginTest() {
        this.loginPage.verifyLoginFormDisplayed();
        this.homePage = this.loginPage.userLogin(prop.getProperty("userAdisUsername"), prop.getProperty("userAdisPassword"));
    }

    @Test(priority = 7)
    public void secondUserLogoutTest() {
        this.homePage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("homePageTitle"), prop.getProperty("homePageContainerTitle"));
        this.homePage.verifyMethods.verifyButtonIsClickable(this.homePage.getLogOutLoc());
        this.loginPage = this.homePage.logout();
    }

    @Test(priority = 8)
    public void invalidUserLoginTest() {
        this.loginPage.verifyLoginFormDisplayed();
        this.loginPage.invalidUserLogin();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}