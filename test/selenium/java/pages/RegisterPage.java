package pages;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;

import java.time.Duration;

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
    //Variables that keep values for data provided for test.

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
        waitForVisibilityOf(registerFormLoc);
        Assert.assertTrue(find(registerFormLoc).isDisplayed(), "Register form displayed not verified.");
        Reporter.log("Verified register form displayed.");
        System.out.println("Register form is displayed.");
    }

    /**
     * Verifying log in button.
     */
    public void verifyLogInDisplayed() {
        Assert.assertTrue(find(loginBtnLoc).isDisplayed(), "Log in button not displayed!");
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
        this.verifyMethods.verifyFormTitle(find(registerFormTitleLoc).getText(), prop.getProperty("registerFormTitle"));
        verifyLogInDisplayed();
        return (RegisterPage) verifyMethods.verifyPageObjectInitialized(this);
    }

    /**
     * Register user and verify action depending on displayed alert message.
     *
     * @param testNumber  (String value for logging test number).
     * @param username    (String value for username).
     * @param password    (String value for password).
     * @param message     (String value for expected alert message).
     * @param description (String value for describing test).
     * @return TestBase instance (instance is of LoginPage or HomePage type depending on alert message).
     */
    public TestBase register(String testNumber, String username, String password, String message, String description) {
        waitForElToBeClickable(find(locators.getUsernameTextInputLoc()));
        waitForElToBeClickable(find(locators.getPasswordTextInputLoc()));
        //Success message value defined in config file.
        String success = prop.getProperty("registerSuccessMsg");
        //Filling in data for username and password.
        setUsernameAndPassword(username, password);
        //Logging entered data.
        Reporter.log("Register test number: " + testNumber);
        System.out.println("Register test number: " + testNumber);
        waitForElToBeClickable(find(locators.getRegisterBtnLoc()));
        //Perform register action.
        click(locators.getRegisterBtnLoc());
        Reporter.log("Clicked register button.");
        System.out.println("Clicked register button.");
        //In firefox browser alert is not shown when user is successfully registered.
        if (browserName.equals("firefox") && message.equals(success)) {
            logRegisterTestDescription(description);
            return verifyMethods.verifyPageObjectInitialized(new LoginPage());
        }
        try {
            waitForAlertToBePresent(Duration.ofSeconds(3));
            Alert registerAlert = driver.switchTo().alert();
            Reporter.log("Verify successful register or fail.");
            System.out.println("Checking if register succeeded.");
            this.verifyMethods.verifyAlertMessage(registerAlert.getText(), message);
            //click on OK button on displayed alert window
            registerAlert.accept();
        } catch (Exception e) {
            System.err.println("Missing alert!");
            return new LoginPage();
        }
        logRegisterTestDescription(description);
        //Return corresponding instance.
        return message.equals(success) ? (LoginPage) verifyMethods.verifyPageObjectInitialized(new LoginPage()) : this;
    }

    public void logRegisterTestDescription(String desc){
        Reporter.log(desc);
        System.out.println(desc);
    }

    public LoginPage clickLogin() {
        this.verifyMethods.verifyButtonIsClickable(loginBtnLoc);
        click(loginBtnLoc);
        Reporter.log("Clicked Login button.");
        System.out.println("Clicked Login button.");
        return (LoginPage) this.verifyMethods.verifyPageObjectInitialized(new LoginPage());
    }
}
