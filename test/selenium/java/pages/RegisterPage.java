package pages;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;

public class RegisterPage extends TestBase {
    /**
     * Locator object for register container div element
     */
    By registerFormLoc = By.xpath("//div[@class='register-form']");

    /**
     * Locator for login button
     */
    By loginBtnLoc = By.xpath("//input[@value='login']");
    /**
     * Locator object for register form title
     */
    By registerFormTitleLoc = By.xpath("//h1[text()='Register']");

    public RegisterPage() {
        super();
    }

    public void verifyRegisterFormDisplayed() {
        WebElement registerFormEl = driver.findElement(registerFormLoc);
        wait.until(ExpectedConditions.visibilityOf(registerFormEl));
        if (!registerFormEl.isDisplayed()) {
            Reporter.log("Register form displayed not verified.");
            System.out.println("Register form is not displayed.");
        }
        Reporter.log("Verified register form displayed.");
        System.out.println("Register form is displayed.");
    }

    public void verifyLogInDisplayed() {
        Assert.assertTrue(driver.findElement(loginBtnLoc).isDisplayed(),"Log in button not displayed!") ;
        Reporter.log("Log in button verified!");
        System.out.println("Log in button is displayed.");
    }

    public RegisterPage openRegisterPage() {
        openSetup(prop.getProperty("registerUrl"));
        setConfigTitle(prop.getProperty("registerPageTitle"));
        verifyStateAfterOpen();
        verifyRegisterFormDisplayed();
        verifyFormTitle(driver.findElement(registerFormTitleLoc).getText(),prop.getProperty("registerFormTitle"));
        verifyUsernamePasswordDisplayed();
        verifyLogInDisplayed();
        verifyRegisterDisplayed();
        return (RegisterPage) verifyPageObjectInitialized(this);
    }

    public void registerUserAlertResponse(Alert registerAlert) {
        String registerMsg = registerAlert.getText();
        if (registerMsg.equals(prop.getProperty("registerSuccessMsg"))) {
            Reporter.log("Verified user successfully registered.");
            System.out.println("User successfully registered!");
        } else if (registerMsg.equals(prop.getProperty("registerFailMsg"))) {
            Reporter.log("User not registered. Username is not available!");
            System.out.println("Username already taken!");

        }else {
            Reporter.log("Message: " + registerMsg + " is displayed.");
            System.out.println("Message: " + registerMsg + " is displayed.");
        }
    }

    public RegisterPage register(String username, String password) {
        setUsernameAndPassword(username, password);
        Reporter.log("Register user: " + username + " with password: " + password);
        System.out.println("Register user: " + username + " with password: " + password);
        driver.findElement(getRegisterBtnLoc()).click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert registerAlert = driver.switchTo().alert();
        Reporter.log("Verify successful register or fail register due to existing user.");
        System.out.println("Checking if register succeeded.");
        registerUserAlertResponse(registerAlert);
        //click on OK button on displayed alert window
        registerAlert.accept();
        return (RegisterPage) verifyPageObjectInitialized(this);
    }

}
