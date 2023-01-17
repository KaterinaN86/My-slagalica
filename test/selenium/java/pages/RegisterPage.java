package pages;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * Class representing Register page using Page Object Model. Inherits TestBase fields and methods.
 */
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

    /**
     * Constructor
     */
    public RegisterPage() {
        super();
    }

    /**
     * Verifying register form is displayed.
     */
    public void verifyRegisterFormDisplayed() {
        WebElement registerFormEl = driver.findElement(registerFormLoc);
        wait.until(ExpectedConditions.visibilityOf(registerFormEl));
        Assert.assertTrue(registerFormEl.isDisplayed(), "Register form displayed not verified.");
        Reporter.log("Verified register form displayed.");
        System.out.println("Register form is displayed.");
    }

    /**
     * Verifying log in button.
     */
    public void verifyLogInDisplayed() {
        Assert.assertTrue(driver.findElement(loginBtnLoc).isDisplayed(), "Log in button not displayed!");
        Reporter.log("Log in button verified!");
        System.out.println("Log in button is displayed.");
    }

    /**
     * Opens register page and verifies titles and web elements on page.
     *
     * @return RegisterPage instance.
     */
    public RegisterPage openRegisterPage() {
        openSetup(prop.getProperty("registerUrl"));
        this.verifyMethods.verifyStateAfterOpen(prop.getProperty("registerPageTitle"));
        verifyRegisterFormDisplayed();
        this.verifyMethods.verifyFormTitle(driver.findElement(registerFormTitleLoc).getText(), prop.getProperty("registerFormTitle"));
        verifyLogInDisplayed();
        return (RegisterPage) verifyMethods.verifyPageObjectInitialized(this);
    }

    /**
     * Checking if message displayed in alert window matches specified.
     *
     * @param message (String value displayed in alert window).
     */
    public void alertResponse(String message) {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert registerAlert = driver.switchTo().alert();
        Reporter.log("Verify successful register or fail.");
        System.out.println("Checking if register succeeded.");
        this.verifyMethods.verifyAlertMessage(registerAlert.getText(), message);
        //click on OK button on displayed alert window
        registerAlert.accept();
    }

    /**
     * Register user and verify action depending on displayed alert message.
     *
     * @param username (String value for username).
     * @param password (String value for password).
     * @param message  (String value for expected alert message).
     * @return TestBase instance (instance is of LoginPage or HomePage type depending on alert message).
     */
    public TestBase register(String username, String password, String message) {
        //Success message value defined in config file.
        String success = prop.getProperty("registerSuccessMsg");
        //Filling in data for username and password.
        setUsernameAndPassword(username, password);
        //Logging entered data.
        Reporter.log("Registering user: " + username);
        System.out.println("Registering user: " + username);
        //Perform register action.
        driver.findElement(locators.getRegisterBtnLoc()).click();
        //In firefox browser alert is not shown when user is successfully registered.
        if (browserName.equals("firefox") && message.equals(success)) {
            Reporter.log("User successfully registered.");
            System.out.println("User successfully registered.");
            return (LoginPage) verifyMethods.verifyPageObjectInitialized(new LoginPage());
        }
        //Check if alert response message matches specified.
        alertResponse(message);
        //Return corresponding instance.
        return message.equals(success) ? (LoginPage) verifyMethods.verifyPageObjectInitialized(new LoginPage()) : this;
    }

}
