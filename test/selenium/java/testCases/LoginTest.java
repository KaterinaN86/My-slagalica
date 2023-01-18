package testCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import utility.ReadFromFile;

import java.io.IOException;
import java.util.List;

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

    @Test(priority = 0)
    public void loginPageOpenTest() throws IOException {
        this.loginPage = loginPage.openLoginPage();
        ReadFromFile reader = new ReadFromFile();
        List<String> words = reader.readFromFile("https://raw.githubusercontent.com/peterjcarroll/recnik-api/master/serbian-latin.txt");
        for(String word : words){
            System.out.println(word);
        }
    }

    @Test(priority = 1)
    public void userKatrinaLoginTest() {
        this.homePage = this.loginPage.userLogin(prop.getProperty("userKaterinaUsername"), prop.getProperty("userKaterinaPassword"));
    }

    @Test(priority = 2)
    public void differentUsersLoginTest() {
        this.loginPage = this.homePage.logout().userLogin(prop.getProperty("userAdisUsername"), prop.getProperty("userAdisPassword")).logout();
    }

    @Test(priority = 3)
    public void invalidUserLoginTest() {
        loginPage.invalidUserLogin();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}