package testCases;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LoginTest extends TestBase {
    LoginPage loginPage;
    HomePage homePage;

    public LoginTest() {
        super();
    }

    @BeforeClass
    public void setup() {
        init();
        this.loginPage = new LoginPage();
    }

    @Test(priority = 0)
    public void loginPageOpenTest() {
        Assert.assertNotNull(loginPage.openLoginPage());
    }

    @Test(priority = 1)
    public void userKatrinaLoginTest(){
        homePage=loginPage.userKaterinaLogin();
        Assert.assertNotNull(homePage);
    }
    @Test(priority = 2)
    public void differentUsersLoginTest(){
        Assert.assertNotNull(homePage.logout().userAdisLogin().logout());
    }

    @Test(priority = 3)
    public void invalidUserLoginTest(){
        Assert.assertNotNull(loginPage.invalidUserLogin());
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}