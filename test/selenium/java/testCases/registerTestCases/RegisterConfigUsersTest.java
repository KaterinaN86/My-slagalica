package testCases.registerTestCases;

import org.testng.annotations.Test;
import pages.LoginPage;

public class RegisterConfigUsersTest extends RegisterBaseTest {

    int testNumber = 1;
    String username, password, expectedMessage,desc = "";

    public RegisterConfigUsersTest() {
        super();
    }

    @Test(priority = 2)
    public void registerTestUserTest() {
        this.registerPage.waitForElToBeClickable(locators.getRegisterBtnLoc());
        this.username = prop.getProperty("userTestRegisterUsername");
        this.password = prop.getProperty("userTestRegisterPassword");
        expectedMessage = prop.getProperty("registerSuccessMsg");
        desc = prop.getProperty("registerSuccessDesc") + username;
        this.loginPage = (LoginPage) this.registerPage.register(String.valueOf(testNumber), username, password, expectedMessage, desc);
        this.loginPage.waitForElToBeClickable(locators.getRegisterBtnLoc());
        this.registerPage = this.loginPage.clickRegister();
        testNumber++;
    }

    @Test(priority = 3)
    public void registerDiffTestUserTest() {
        this.registerPage.waitForElToBeClickable(locators.getRegisterBtnLoc());
        this.username = prop.getProperty("userKaterinaUsername");
        this.password = prop.getProperty("userKaterinaPassword");
        desc = prop.getProperty("registerSuccessDesc") + username;
        this.loginPage = (LoginPage) this.registerPage.register(String.valueOf(testNumber), username, password, expectedMessage, desc);
        this.loginPage.waitForElToBeClickable(locators.getRegisterBtnLoc());
        this.registerPage = this.loginPage.clickRegister();
        testNumber++;
    }

    @Test(priority = 4)
    public void registerNewTestUserTest() {
        this.registerPage.waitForElToBeClickable(locators.getRegisterBtnLoc());
        this.username = prop.getProperty("userAdisUsername");
        this.password = prop.getProperty("userAdisPassword");
        desc = prop.getProperty("registerSuccessDesc") + username;
        this.loginPage = (LoginPage) this.registerPage.register(String.valueOf(testNumber), username, password, expectedMessage, desc);
        this.loginPage.waitForElToBeClickable(locators.getRegisterBtnLoc());
        this.registerPage = this.loginPage.clickRegister();
    }

}
