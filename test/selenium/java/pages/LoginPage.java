package pages;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * Class representing Login page using Page Object Model. Inherits TestBase fields and methods.
 */
public class LoginPage extends TestBase {
    /**
     * Locator object for login form element
     */
    By loginFormLoc = By.xpath("//form[@id='form']");
    /**
     * Locator object for login form title
     */
    By loginFormTitleLoc = By.xpath("//h1[text()='Login']");
    /**
     * Sign in button locator
     */
    By signInBtnLoc = By.xpath("//input[@value='Sign in']");

    /**
     * Empty constructor.
     */
    public LoginPage() {
        super();
    }

    /**
     * Opens initial state URL using driver object, verifies all elements on page are displayed.
     *
     * @return new LoginPage object
     */
    public LoginPage openLoginPage() {
        //Calling method declared in TestBase class. Opens specified page and sets up driver and page title.
        openSetup(prop.getProperty("loginUrl"));
        //Verifying page elements.
        this.verifyMethods.verifyStateAfterOpen(prop.getProperty("loginPageTitle"));
        //Verifying register form title
        this.verifyMethods.verifyFormTitle(driver.findElement(loginFormTitleLoc).getText(), prop.getProperty("loginFormTitle"));
        //Verifying elements found on register page only.
        verifyLoginFormDisplayed();
        verifySignInDisplayed();
        //Return LoginPage instance after not null verification.
        return (LoginPage) verifyMethods.verifyPageObjectInitialized(this);
    }

    /**
     * Verifies login from element is displayed.
     */
    public void verifyLoginFormDisplayed() {
        //Initializes webElement object for login form element.
        WebElement loginFormEl = driver.findElement(loginFormLoc);
        //Defines explicit wait to ensure visibility of specified element.
        wait.until(ExpectedConditions.visibilityOf(loginFormEl));
        Assert.assertTrue(loginFormEl.isDisplayed(), "Login form is not displayed!");
        Reporter.log("Verified login form displayed.");
        System.out.println("Login form is displayed.");

    }

    /**
     * Verifies "Sign in" button displayed in login form.
     */
    public void verifySignInDisplayed() {
        //Displaying and logging messages that inform if WebElement object for "Sign in" button is displayed.
        Assert.assertTrue(driver.findElement(signInBtnLoc).isDisplayed(), "Sign in button not displayed!");
        Reporter.log("Sign in button verified!");
        System.out.println("Sign in button is displayed.");
    }

    /**
     * Login method for user specified with parameters.
     *
     * @param username (String containing username specified on method call).
     * @param password (String containing password specified on method call)
     * @return new HomePage instance after not null verification.
     */
    public HomePage userLogin(String username, String password) {
        //Setting username and password data.
        setUsernameAndPassword(username, password);
        //Click "Sign in" button.
        driver.findElement(signInBtnLoc).click();
        Reporter.log("Clicked \"Sign in\" button in login form.");
        System.out.println("Clicked \"Sign in\" button in login form.");
        return (HomePage) verifyMethods.verifyPageObjectInitialized(new HomePage());
    }

    /**
     * Login method for invalid user.
     *
     * @return
     */
    public LoginPage invalidUserLogin() {
        //Sending empty strings as username and password data.
        setUsernameAndPassword("", "");
        driver.findElement(signInBtnLoc).click();
        //Explicit wait for alert to be displayed.
        wait.until(ExpectedConditions.alertIsPresent());
        //Click on OK button on displayed alert window.
        Alert invalidLoginAlert = driver.switchTo().alert();
        Reporter.log("Verify error message on invalid user login.");
        System.out.println("Check error message in alert for invalid user login.");
        verifyMethods.verifyAlertMessage(invalidLoginAlert.getText(), prop.getProperty("invalidLoginErrorMsg"));
        //Click on "OK" button in alert window.
        invalidLoginAlert.accept();
        Reporter.log("Clicked \"OK\" button in alert window.");
        System.out.println("Clicked \"OK\" button in alert window.");
        return this;
    }

    public RegisterPage clickRegister() {
        Reporter.log("Click register button.");
        System.out.println("Click register button.");
        driver.findElement(locators.getRegisterBtnLoc()).click();
        return (RegisterPage) verifyMethods.verifyPageObjectInitialized(new RegisterPage());
    }
}
