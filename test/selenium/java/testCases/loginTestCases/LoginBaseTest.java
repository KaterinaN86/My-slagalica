package testCases.loginTestCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;

public class LoginBaseTest extends TestBase {

    /**
     * HomePage instance, used in several methods.
     */
    HomePage homePage;

    /**
     * Empty constructor.
     */
    public LoginBaseTest() {
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

    @AfterClass
    public void tearDown() {
        close();
    }
}
