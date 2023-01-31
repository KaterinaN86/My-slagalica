package testCases.loginTestCases;

import org.testng.annotations.Test;
import pages.RegisterPage;

public class LoginRegisterButtonTest extends LoginBaseTest {
    RegisterPage registerPage;

    @Test(priority = 6)
    public void clickRegisterButtonTest() {
        this.loginPage.waitForVisibilityOf(locators.getH1TitleLoc());
        this.loginPage.verifyMethods.verifyButtonIsClickable(locators.getRegisterBtnLoc());
        this.registerPage = this.loginPage.clickRegister();
    }

    @Test(priority = 7)
    public void goBackToLoginTest() {
        this.registerPage.verifyLogInDisplayed();
        this.loginPage = this.registerPage.clickLogin();
    }
}
