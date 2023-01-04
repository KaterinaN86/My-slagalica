package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import pages.LoginPage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * Base class with driver and properties objects.
 * Contains method for driver initialization and methods used in multiple classes.
 */
public class TestBase {
    /**
     * Driver object used throughout all pages.
     */
    public static WebDriver driver;
    /**
     * Properties object used for accessing data in config.properties file.
     */
    public static Properties prop;
    /**
     * Base URL (the one the browser opens initially).
     */
    public static String baseUrl;
    /**
     * Current URL.
     */
    public static String currentUrl;
    /**
     * WebDriverWait object used for explicit wait.
     */
    public static WebDriverWait wait;
    /**
     * LoginPage instance, used in several classes.
     */
    public static LoginPage loginPage;
    /**
     * Username and password text input elements locators
     */
    By usernameTextInputLoc = By.xpath("//input[@placeholder='Username']");
    By passwordTextInputLoc = By.xpath("//input[@placeholder='Password']");
    /**
     * Register button locator
     */
    By registerBtnLoc = By.xpath("//input[@value='Register']");
    /**
     * Variable used for storing username data.
     */
    private String username;
    /**
     * Variable used for storing password data.
     */
    private String password;
    /**
     * Variable used for storing page title.
     */
    private String pageTitle;
    //Initializing variable with page title from config file (used for title verification).
    private String configTitle;

    /**
     * Empty constructor.
     */
    public TestBase() {

    }

    /**
     * Calls corresponding WebDriverManager setup method depending on browser property. Initializes WebDeiverWait object and baseUrl using config property.
     */
    public void init() {
        try {
            //Initializing properties object that stores data from confing.properties file.
            prop = new Properties();
            /**
             * Reading data from properties file.
             */
            FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir") + "\\test\\selenium\\resources\\config.properties");
            prop.load(fileIn);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * Defining browser used for testing.
         */
        String browserName = prop.getProperty("browser");
        /**
         * Initializing driver object depending on chosen browser
         */
        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        //Initializing wait driver
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //Initializing LoginPage object
        this.loginPage=new LoginPage();
    }

    /**
     * Setter method for pageTitle variable.
     *
     * @param pageTitle
     */
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    /**
     * Setter method for config title variable.
     *
     * @param configTitle
     */
    public void setConfigTitle(String configTitle) {
        this.configTitle = configTitle;
    }

    /**
     * Getter method for "Register" button locator.
     *
     * @return
     */
    public By getRegisterBtnLoc() {
        return registerBtnLoc;
    }

    /**
     * Method that gets called right after a page is open.
     * Used to set up driver and page title.
     * @param url (String containing the url of page to be opened).
     */
    public void openSetup(String url) {
        //Open page specified by url parameter
        driver.get(url);
        //Manage window size and cookies using driver object
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        //Set page title variable
        setPageTitle(driver.getTitle());
        //Log data regarding opened page
        Reporter.log("Web browser launched, " + pageTitle + " page at: " + url + " opened.");
        System.out.println("Web browser launched, " + pageTitle + " page at: " + url + " opened.");
    }

    /**
     * Verifying current page title matches specified.
     */
    public void verifyPageTitle() {
        Reporter.log("Verifying page title.");
        System.out.println("Check if page title matches specified.");
        Assert.assertEquals(pageTitle, configTitle, "Page title '" + pageTitle + "' is not equal to expected '");
        Reporter.log("Page title '" + pageTitle + "' is equal to expected '" + configTitle + "'.");
        System.out.println("Page title '" + pageTitle + "' is equal to expected.");
    }

    /**
     * Verifying form title matches specified.
     * @param formTitle (String containing actual title of form element
     * @param configTitle (String containing form title specified in config.properties file).
     */
    public void verifyFormTitle(String formTitle, String configTitle) {
        Reporter.log("Verifying page title.");
        System.out.println("Check if page title matches specified.");
        Assert.assertEquals(formTitle, configTitle, "Form title: " + formTitle + " does not match specified title: " + configTitle + ".");
        Reporter.log("Page title matches specified.");
        System.out.println("Page form title verified.");
    }

    /**
     * Verifying username and password text input fields are displayed.
     */
    public void verifyUsernamePasswordDisplayed() {
        //Initializing WebElement objects for username and password text input elements.
        WebElement usernameEl = driver.findElement(usernameTextInputLoc);
        WebElement passwordEl = driver.findElement(passwordTextInputLoc);
        Reporter.log("Verifying username and password elements are displayed.");
        System.out.println("Check if username and password are displayed.");
        Assert.assertTrue(usernameEl.isDisplayed(), "Username text input element not displayed!");
        Assert.assertTrue(passwordEl.isDisplayed(), "Password text input element not displayed!");
        Reporter.log("Username and password text input elements are displayed.");
        System.out.println("Username and password text input elements are displayed.");
    }

    /**
     * Verify register button is displayed.
     */
    public void verifyRegisterDisplayed() {
        Reporter.log("Verify register button is displayed.");
        System.out.println("Check if register button is displayed.");
        //Log corresponding message depending on isDsiplayed method result for register button WebElement object.
        Assert.assertTrue(driver.findElement(registerBtnLoc).isDisplayed(), "Register button not displayed!");
        Reporter.log("Register button verified!");
        System.out.println("Register button is displayed.");
    }

    /**
     * Verify object representing a specific page has been initialized.
     *
     * @param page (TestBase object that can be of any page class type)
     * @return (TestBase object passed as parameter)
     */
    public TestBase verifyPageObjectInitialized(TestBase page) {
        //Verifying page object successfully initialized.
        Assert.assertNotNull(page, "Page object not initialized!");
        System.out.println("Successfully initialized instance of " + page.getClass() + " class.");
        Reporter.log("Successfully initialized instance of " + page.getClass() + " class.");
        return page;
    }

    /**
     * Verifying page title and elements found on multiple pages.
     */
    public void verifyStateAfterOpen() {
        verifyPageTitle();
        verifyUsernamePasswordDisplayed();
        verifyRegisterDisplayed();
    }

    /**
     * Helper method for sending username and password values to corresponding text input field elements.
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
     * Verifies message displayed in alert window.
     * @param message (String containing actual alert window message).
     * @param property (String containing alert message specified in config.properties file).
     */
    public void verifyAlertMessage(String message, String property) {
        Assert.assertEquals(message, property, "Wrong message displayed!");
        Reporter.log("Message: " + message + " is displayed and matches specified.");
        System.out.println("Message: " + message + " is displayed and matches specified.");
    }

    /**
     * Closing web page.
     */
    public void close() {
        //Set currentUrl variable to URL of currently opened page.
        currentUrl = driver.getCurrentUrl();
        //Initialize url array in order to separate ip address from current page name.
        String[] urlArray = currentUrl.split("/");
        //Initialize page variable to last element of urlArray variable.
        String page = urlArray[urlArray.length - 1];
        //Check if currently opened page is Homepage
        if (currentUrl.equals(prop.getProperty("baseUrl"))) {
            //Set page variable to Homepage
            page = "Homepage";
        }
        //Close currently opened page and log action.
        Reporter.log("Closing page: " + page);
        driver.close();
        System.out.println("Page: " + page + " closed.");
    }
}
