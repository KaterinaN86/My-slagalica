package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import pages.HomePage;
import pages.LoginPage;
import pages.MojBrojPage;
import pages.SinglePlayerGamePage;
import utility.Locators;
import utility.VerifyBrokenLink;
import utility.VerifyMethods;

import java.io.File;
import java.io.FileInputStream;
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

    private String alertMsg;

    /**
     * Empty constructor.
     */
    public TestBase() {
        this.verifyMethods = new VerifyMethods(this);
    }

    /**
     * Getter for alert message variable.
     * @return String
     */
    public String getAlertMsg() {
        return alertMsg;
    }

    /**
     * This method will take screenshot.
     */

    public static void takeSnapShot(String methodName, String ext) {
        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        //Call getScreenshotAs method to create image file
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        //Date formatter used to create unique snapshot name.
        String date = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss").format(LocalDateTime.now());
        //Replace characters that can't be used for snapshot path
        date = date.replace("/", "-").replace(":", "-");
        String imgName = methodName.concat(" ").concat("(" + date + ")").concat(ext);
        //Move image file to new destination
        File DestFile = new File(System.getProperty("user.dir") + "\\test\\selenium\\resources\\snapshots\\" + imgName);
        //Copy file at destination
        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException e) {
            System.out.println("Error creating destination file for snapshot.");
            throw new RuntimeException(e);
        }
    }

    /**
     * Calls corresponding WebDriverManager setup method depending on browser property. Initializes WebDeiverWait object and baseUrl using config property.
     */
    public void init() {
        try {
            //Initializing properties object that stores data from confing.properties file.
            prop = new Properties();
            //Loading properties from confing.properties file
            FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir") + "\\test\\selenium\\resources\\config.properties");
            prop.load(fileIn);
        } catch (IOException e) {
            System.out.println("Error reading loading properties from config file.");
            e.printStackTrace();
        }
        //Defining browser used for testing.
        browserName = prop.getProperty("browser");
        //Initializing driver object depending on chosen browser
        switch (browserName) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
        }
        //Initializing wait driver
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        //Initializing LoginPage object.
        this.loginPage = new LoginPage();
        //Initializing Locators object.
        locators = new Locators();
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
     * @param configTitle String
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
     * Type given text into element with given locator
     */
    public void type(String text, By locator) {
        waitForVisibilityOf(locator, Duration.ofSeconds(5));
        find(locator).sendKeys(text);
    }

    /**
     * Helper method for sending username and password values to corresponding text input field elements. Before text is entered all existing data in text input fields (if any) is deleted.
     *
     * @param username String
     * @param password String
     */
    public void setUsernameAndPassword(String username, String password) {
        //Initializing username and password web elements.
        WebElement usernameEl = find(locators.getUsernameTextInputLoc());
        WebElement passwordEl = find(locators.getPasswordTextInputLoc());
        //Deleting existing data in text input fields (CTRL+delete).
        usernameEl.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        usernameEl.sendKeys(Keys.DELETE);
        passwordEl.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        passwordEl.sendKeys(Keys.DELETE);
        //Sending new values for username and password. Logging entered data.
        type(username, locators.getUsernameTextInputLoc());
        Reporter.log("Entered username: " + username);
        System.out.println("Entered username: " + username);
        type(password, locators.getPasswordTextInputLoc());
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
        waitForElToBeClickable(locators.getContainerLoc());
        String page = this.getClass().getSimpleName();
        Reporter.log("Click back button on page: " + page);
        System.out.println("Click back button on page: " + page);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", find(locators.getBackBtnLoc()));
        //driver.findElement().click();
        dealWithAlert();
        if (this instanceof SinglePlayerGamePage) {
            return verifyMethods.verifyPageObjectInitialized(new HomePage());
        }
        if (this instanceof MojBrojPage && prop.getProperty("browser").equals("firefox")) {
            return verifyMethods.verifyPageObjectInitialized(new HomePage());
        }
        return verifyMethods.verifyPageObjectInitialized(new SinglePlayerGamePage());

    }

    public void dealWithAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        Reporter.log("Alert popup displayed.");
        System.out.println("Alert popup displayed.");
        Alert registerAlert = driver.switchTo().alert();
        alertMsg=registerAlert.getText();
        Reporter.log("Verify accept option in alert popup.");
        System.out.println("Checking if player can accept to go back.");
        registerAlert.accept();
        Reporter.log("User successfully accepted.");
        System.out.println("User successfully accepted.");
    }

    /**
     * Wait for specific ExpectedCondition for the given amount of time in seconds
     */
    private void waitFor(ExpectedCondition<WebElement> condition, Duration timeOutInSeconds) {
        timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : Duration.ofSeconds(5);
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(condition);
    }

    /**
     * Wait for given number of seconds for element with given locator to be visible
     * on the page
     */
    public void waitForVisibilityOf(By locator, Duration... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }

    /**
     * Wait for given number of seconds for element with given locator to be clickable
     * on the page
     */
    public void waitForElToBeClickable(By locator, Duration... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitFor(ExpectedConditions.elementToBeClickable(locator),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }

    /**
     * Find element using given locator
     */
    public WebElement find(By locator) {
        waitForVisibilityOf(locator);
        return driver.findElement(locator);
    }
    /** Find all elements using given locator */
    public List<WebElement> findAll(By locator) {
        waitForVisibilityOf(locator);
        return driver.findElements(locator);
    }

    /**
     * Click on element with given locator when its visible
     */
    public void click(By locator) {
        waitForVisibilityOf(locator, Duration.ofSeconds(5));
        find(locator).click();
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
