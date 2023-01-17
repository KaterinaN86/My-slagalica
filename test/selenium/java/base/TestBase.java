package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import pages.HomePage;
import pages.LoginPage;
import pages.SinglePlayerGamePage;
import utility.Locators;
import utility.VerifyBrokenLink;
import utility.VerifyMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
     * WebDriverWait object used for explicit wait.
     */
    public static WebDriverWait wait;
    /**
     * Locators instance, used to access locators for web elements
     */
    public static Locators locators;
    /**
     * Variable that specifies which browser is used for testing.
     */
    public static String browserName;
    /**
     * Current URL.
     */
    public static String currentUrl;
    /**
     * Instance used to access methods for verification.
     */
    public VerifyMethods verifyMethods;
    /**
     * LoginPage instance, used in several classes.
     */
    public LoginPage loginPage;
    /**
     * Variable used for storing page title.
     */
    private String pageTitle;
    /**
     * Variable used for storing page title value from config file (used for title verification).
     */
    private String configTitle;

    /**
     * Empty constructor.
     */
    public TestBase() {
        this.verifyMethods = new VerifyMethods(this);
    }

    /**
     * Getter for WebDriver element.
     *
     * @return WebDriver element.
     */
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * This method will take screenshot.
     *
     * @throws Exception
     */

    public static void takeSnapShot(String methodName, String ext) throws Exception {
        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        //Call getScreenshotAs method to create image file
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
        String date = dtf.format(LocalDateTime.now());
        date = date.replace("/", "-");
        date = date.replace(":", "-");
        String imgName = methodName.concat(" ").concat("(" + date + ")").concat(ext);
        //Move image file to new destination
        File DestFile = new File(System.getProperty("user.dir") + "\\test\\selenium\\resources\\snapshots\\" + imgName);
        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);
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
        this.browserName = prop.getProperty("browser");
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        //Initializing LoginPage object.
        this.loginPage = new LoginPage();
        //Initializing Locators object.
        this.locators = new Locators();
    }

    /**
     * Getter for page title.
     *
     * @return String (Value contains current page title.
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Setter method for pageTitle variable.
     */
    public void setPageTitle() {
        this.pageTitle = driver.getTitle();
    }

    /**
     * Getter method for expected page title value (read from config file).
     *
     * @return String (Variable contains expected page title).
     */
    public String getConfigTitle() {
        return configTitle;
    }

    /**
     * Setter method expected page title value (read from config file).
     *
     * @param configTitle
     */
    public void setConfigTitle(String configTitle) {
        this.configTitle = configTitle;
    }

    /**
     * Method that gets called right after a page is open.
     * Used to set up driver and page title.
     *
     * @param url (String containing the url of page to be opened).
     */
    public void openSetup(String url) {
        //Open page specified by url parameter
        driver.get(url);
        //Manage window size and cookies using driver object
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        //Log data regarding opened page
        Reporter.log("Web browser launched, " + pageTitle + " page at: " + url + " opened.");
        System.out.println("Web browser launched, " + pageTitle + " page at: " + url + " opened.");
    }

    /**
     * Helper method for sending username and password values to corresponding text input field elements. Before text is entered all existing data in text input fields (if any) is deleted.
     *
     * @param username
     * @param password
     */
    public void setUsernameAndPassword(String username, String password) {
        //Initializing username and password web elements.
        WebElement usernameEl = driver.findElement(locators.getUsernameTextInputLoc());
        WebElement passwordEl = driver.findElement(locators.getPasswordTextInputLoc());
        //Deleting existing data in text input fields (CTRL+delete).
        usernameEl.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        usernameEl.sendKeys(Keys.DELETE);
        passwordEl.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        passwordEl.sendKeys(Keys.DELETE);
        //Sending new values for username and password. Logging entered data.
        usernameEl.sendKeys(username);
        Reporter.log("Entered username: " + username);
        System.out.println("Entered username: " + username);
        passwordEl.sendKeys(password);
        Reporter.log("Entered password.");
        System.out.println("Entered password.");
    }

    /**
     * Creates a list of all link elements in page. After verifying each link a list of active links is created.
     *
     * @return int (Number of active links on page).
     */
    public int getValidLinkNumber() {
        //Utility class object used for verifying links.
        VerifyBrokenLink verifyBrokenLink = new VerifyBrokenLink();
        //List of all links on page.
        List<WebElement> linkElementsList = driver.findElements(By.tagName("a"));
        //Initializing list of active links.
        List<WebElement> validLinkElementsList = new ArrayList<WebElement>();
        // Number of total links.
        int total = linkElementsList.size();
        System.out.println("Number of total links: " + total);
        //Looping over every link to verify href attribute.
        for (WebElement el : linkElementsList) {
            //Variable that stores value of href attribute.
            String url = el.getAttribute("href");
            //Links with no href attribute or href that leads to script are ignored.
            if (url != null && !url.contains("javascript")) {
                //Every link that passes previous condition is verified using VerifyBrokenLink object.
                if (verifyBrokenLink.verifyLink(url)) {
                    //Adding link to active links list.
                    validLinkElementsList.add(el);
                }
            }
        }
        //Logging number of total links as well as valid and invalid link number for current page.
        Reporter.log("Number of total links on page: " + total);
        Reporter.log("Number of invalid links on page: " + verifyBrokenLink.invalidLinkNumber);
        System.out.println("Number of total links on page: " + total);
        System.out.println("Number of invalid links on page: " + verifyBrokenLink.invalidLinkNumber);
        //Return the number of active links.
        return validLinkElementsList.size();
    }

    /**
     * Method used to go back to HomePage or SinglePlayerGamePage
     *
     * @return TestBase instance (depending on where the user is in the application different type of instance is returned).
     */
    public TestBase goBack() {
        wait.until(ExpectedConditions.elementToBeClickable(locators.getContainerLoc()));
        String page = this.getClass().getSimpleName();
        Reporter.log("Click back button on page: " + page);
        System.out.println("Click back button on page: " + page);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        WebElement stopEl = driver.findElement(locators.getBackBtnLoc());
        js.executeScript("arguments[0].click()", stopEl);
        //driver.findElement().click();
        dealWithAlert();
        if (this instanceof SinglePlayerGamePage) {
            return verifyMethods.verifyPageObjectInitialized(new HomePage());
        }
        return verifyMethods.verifyPageObjectInitialized(new SinglePlayerGamePage());
    }

    void dealWithAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        Reporter.log("Alert popup displayed.");
        System.out.println("Alert popup displayed.");
        Alert registerAlert = driver.switchTo().alert();
        Reporter.log("Verify accept option in alert popup.");
        System.out.println("Checking if player can accept to go back.");
        //verifyMethods.verifyAlertMessage(registerAlert.getText(), message);
        //click on OK button on displayed alert window
        registerAlert.accept();
        Reporter.log("User successfully accepted.");
        System.out.println("User successfully accepted.");
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
