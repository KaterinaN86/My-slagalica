package pages;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;

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
     * Username and password text input elements locators
     */
    By usernameTextInputLoc = By.xpath("//input[@placeholder='Username']");
    By passwordTextInputLoc = By.xpath("//input[@placeholder='Password']");
    /**
     * Sign in button locator
     */
    By signInBtnLoc = By.xpath("//input[@value='Sign in']");
    /**
     * Register button locator
     */
    By registerBtnLoc = By.xpath("//input[@value='Register']");

    /**
     * Username and password variables for active user.
     */
    private String username;
    private String password;
    private String pageTitle;
    //Initializing variable with page title from config file (used for title verification)
    private String configTitle = prop.getProperty("loginPageTitle");

    /**
     * Empty constructor.
     */
    public LoginPage() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void verifyPageTitle() {
        if (!pageTitle.equals(configTitle)) {
            Reporter.log("Page title '" + pageTitle + "' is not equal to expected '" + configTitle + "'.");
            System.out.println("Page title is not equal to expected.");
        }
        Reporter.log("Page title '" + pageTitle + "' is equal to expected '" + configTitle + "'.");
        System.out.println("Page title '" + pageTitle + "' is equal to expected.");
    }

    public void verifyLoginFormDisplayed() {
        WebElement loginFormEl = driver.findElement(loginFormLoc);
        wait.until(ExpectedConditions.visibilityOf(loginFormEl));
        if (!loginFormEl.isDisplayed()) {
            Reporter.log("Login form displayed not verified.");
            System.out.println("Login form is not displayed.");
        }
        Reporter.log("Verified login form displayed.");
        System.out.println("Login form is displayed.");
    }

    public void verifyFormTitleDisplayed() {
        WebElement loginFormTitleEl = driver.findElement(loginFormTitleLoc);
        if (!loginFormTitleEl.getText().equals(prop.getProperty("loginFormTitle"))) {
            Reporter.log("Login form title does not match specified.");
            System.out.println("Login form title not verified.");
        }
        Reporter.log("Login form title matches specified.");
        System.out.println("Login form title verified.");
    }

    public void verifyUsernamePasswordDisplayed() {
        WebElement usernameEl = driver.findElement(usernameTextInputLoc);
        WebElement passwordEl = driver.findElement(passwordTextInputLoc);
        if (!usernameEl.isDisplayed()) {
            Reporter.log("Username text input element not verified!");
            System.out.println("Username input element not displayed!");
        }
        if (!passwordEl.isDisplayed()) {
            Reporter.log("Password text input element not verified!");
            System.out.println("Password input element not displayed!");
        }
        Reporter.log("Username and password text input elements are displayed.");
        System.out.println("Username and password text input elements are displayed.");
    }

    public void verifySignInDisplayed() {
        if (!driver.findElement(signInBtnLoc).isDisplayed()) {
            Reporter.log("Sign in button not displayed!");
            System.out.println("Sign in button not displayed!");
        }
        Reporter.log("Sign in button verified!");
        System.out.println("Sign in button is displayed.");
    }

    public void verifyRegisterDisplayed() {
        if (!driver.findElement(registerBtnLoc).isDisplayed()) {
            Reporter.log("Register button not displayed!");
            System.out.println("Register button not displayed!");
        }
        Reporter.log("Register button verified!");
        System.out.println("Register button is displayed.");
    }

    /**
     * opens baseURL using driver object, verifies all elements on page
     *
     * @return new LoginPage object
     */
    public LoginPage openLoginPage() {
        Reporter.log("Launching web browser and opening url defined by baseUrl variable.");
        System.out.println("Launching web browser.");
        driver.get(baseUrl);
        pageTitle = driver.getTitle();
        verifyPageTitle();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        verifyLoginFormDisplayed();
        verifyFormTitleDisplayed();
        verifyUsernamePasswordDisplayed();
        verifySignInDisplayed();
        verifyRegisterDisplayed();
        return new LoginPage();
    }

    /**
     * Helper method for defining username and password values
     *
     * @param username
     * @param password
     */
    public void setUsernameAndPassword(String username, String password) {
        driver.findElement(usernameTextInputLoc).sendKeys(username);
        Reporter.log("Entered username: " + username);
        System.out.println("Entered username: " + username);
        driver.findElement(passwordTextInputLoc).sendKeys(password);
        Reporter.log("Entered password: " + password);
        System.out.println("Entered password: " + password);
    }

    /**
     * Login method for user Katerina
     *
     * @return Homepage object
     */
    public HomePage userKaterinaLogin() {
        setUsernameAndPassword(prop.getProperty("userKaterinaUsername"), prop.getProperty("userKaterinaPassword"));
        driver.findElement(signInBtnLoc).click();
        return new HomePage();
    }

    public HomePage invalidUserLogin() {
        setUsernameAndPassword("", "");
        driver.findElement(signInBtnLoc).click();
        wait.until(ExpectedConditions.alertIsPresent());
        //click on OK button on displayed alert window
        Alert invalidLoginAlert = driver.switchTo().alert();
        Reporter.log("Verify error message on invalid user login.");
        String errorMsg = invalidLoginAlert.getText();
        if (errorMsg.equals(prop.getProperty("invalidLoginErrorMsg"))) {
            Reporter.log("Message: " + errorMsg + " is displayed.");
            System.out.println("Message: " + errorMsg + " is displayed.");
        } else {
            Reporter.log("Wrong message displayed!");
            System.out.println("Wrong message displayed!");
        }
        invalidLoginAlert.accept();
        return new HomePage();
    }

    /**
     * Login method for user Adis
     *
     * @return Homepage object
     */
    public HomePage userAdisLogin() {
        setUsernameAndPassword(prop.getProperty("userAdisUsername"), prop.getProperty("userAdisPassword"));
        driver.findElement(signInBtnLoc).click();
        return new HomePage();
    }
}
