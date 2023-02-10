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
import pages.*;
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
import java.util.Random;

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
    //Variable used to store text message from alert popup.
    private String alertMsg;
    //Variable that stores expected points after playing game.
    private int calculatedPoints = 0;

    /**
     * Empty constructor.
     */
    public TestBase() {
        this.verifyMethods = new VerifyMethods(this);
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
     * Getter for alert message variable.
     *
     * @return String
     */
    public String getAlertMsg() {
        return alertMsg;
    }

    /**
     * Getter method for calculated expected points for a game.
     *
     * @return int, number of calculated expected points for a game.
     */
    public int getCalculatedPoints() {
        return calculatedPoints;
    }

    /**
     * Setter method for calculated expected points for a game.
     *
     * @param calculatedPoints int
     */
    public void setCalculatedPoints(int calculatedPoints) {
        this.calculatedPoints = calculatedPoints;
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
        usernameEl.sendKeys(username);
        //Assert.assertEquals(usernameEl.getText(),username,"Username not entered!");
        Reporter.log("Entered username: " + username);
        System.out.println("Entered username: " + username);
        waitForElToBeClickable(locators.getH1TitleLoc());
        passwordEl.sendKeys(password);
        //Assert.assertEquals(passwordEl.getText(),username,"Password not entered!");
        Reporter.log("Entered password.");
        System.out.println("Entered password.");
        waitForElToBeClickable(locators.getRegisterBtnLoc());
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
        waitForElToBeClickable(locators.getH1TitleLoc());
        waitForVisibilityOf(locators.getBackBtnLoc());
        String page = this.getClass().getSimpleName();
        Reporter.log("Click back button on page: " + page);
        System.out.println("Click back button on page: " + page);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("arguments[0].click()", find(locators.getBackBtnLoc()));
        } catch (Exception e) {
            System.err.println("Stale element error.");
        }
        try {
            dealWithAlert();
        } catch (Exception e) {
            System.err.println("No alert displayed!");
        }
        if (this instanceof SinglePlayerGamePage) {
            return verifyMethods.verifyPageObjectInitialized(new HomePage());
        }
        if ((this instanceof MojBrojPage && prop.getProperty("browser").equals("firefox"))||(this instanceof SpojnicePage && prop.getProperty("browser").equals("firefox"))) {
            return verifyMethods.verifyPageObjectInitialized(new HomePage());
        }
        return verifyMethods.verifyPageObjectInitialized(new SinglePlayerGamePage());
    }

    public void dealWithAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        Reporter.log("Alert popup displayed.");
        System.out.println("Alert popup displayed.");
        Alert registerAlert = driver.switchTo().alert();
        this.alertMsg = registerAlert.getText();
        Reporter.log("Verify accept option in alert popup.");
        System.out.println("Checking if player can accept to go back.");
        registerAlert.accept();
        Reporter.log("User successfully accepted.");
        System.out.println("User successfully accepted.");
    }

    /**
     * Since there is no alert after player finishes game, we need different method to back to SinglePlayerGame page.
     *
     * @return SinglePlayerGamePage object
     */
    public SinglePlayerGamePage goBackAfterGameFinished() {
        waitForElToBeClickable(locators.getH1TitleLoc());
        //Variable for storing name of current class.
        String page = this.getClass().getSimpleName();
        Reporter.log("Click back button on page: " + page);
        System.out.println("Click back button on page: " + page);
        //Added because of pop up dialog covering back button bug.
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement goBackButton = find(locators.getBackBtnLoc());
            waitForElToBeClickable(goBackButton);
            this.verifyMethods.verifyBackButtonIsClickable();
            js.executeScript("arguments[0].click()", goBackButton);
        } catch (Exception e) {
            System.err.println("*******Stale element error*********");
        }
        try {
            waitForElToBeClickable(locators.getH1TitleLoc());
        } catch (Exception e) {
            System.err.println("********Unhandled alert exception!**********");
        }
        return (SinglePlayerGamePage) verifyMethods.verifyPageObjectInitialized(new SinglePlayerGamePage());
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
            } catch (StaleElementReferenceException ignored) {
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
            } catch (StaleElementReferenceException ignored) {
            }
            attempts++;
        }
    }

    /**
     * Wait for given number of seconds for given WebElement object to be clickable
     * on the page
     */
    public void waitForElToBeClickable(WebElement element, Duration... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitFor(ExpectedConditions.elementToBeClickable(element),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                break;
            } catch (StaleElementReferenceException ignored) {
            }
            attempts++;
        }
    }

    public void waitForAlertToBePresent(Duration timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : Duration.ofSeconds(3);
                WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
                wait.until(ExpectedConditions.alertIsPresent());
                break;
            } catch (StaleElementReferenceException ignored) {
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

    /**
     * Find all elements using given locator
     */
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
     * Workaround method for firefox go back bug.
     *
     * @param pageObject TestBase object (depending on browser object type can be HomePage or SinglePlayerGame).
     * @return SinglePlayerGamePage object
     */
    public SinglePlayerGamePage firefoxWorkaround(TestBase pageObject) {

        waitForElToBeClickable(locators.getH1TitleLoc());
        //If browser is set to firefox back button on some game pages leads to HomePage directly instead of going back to One Player first. In order to get SinglePlayerGamePage object as return value steps for going back to that page sre added.
        //Object that will be returned if browser is set to firefox.
        SinglePlayerGamePage singlePlayerGamePage;
        //Checking browser value.
        if (prop.getProperty("browser").equals("firefox") && (pageObject instanceof HomePage)) {
            //Initializing HomePage object to return value of goBack method called by mojBroj instance in test.
            HomePage homePage = (HomePage) pageObject;

            waitForVisibilityOf(homePage.getSinglePlayerLoc());
            this.verifyMethods.verifyButtonIsClickable(homePage.getSinglePlayerLoc());
            //Initializing SinglePlayerGamePage object to return value of clickSinglePlayerGame method in HomePage class.
            singlePlayerGamePage = homePage.clickSinglePlayerGame();
            waitForVisibilityOf(locators.getContainerLoc());
            //Returning previously initialized object;
            return singlePlayerGamePage;
        }

        //if browser is not set to firefox goBack method called by MojBroj instance results in SinglePlayerGamePage object, which is received as method parameter and simply returned.
        return (SinglePlayerGamePage) pageObject;
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

    public int generateUnique(ArrayList<Integer> generated, int maxValue) {
        // Random object used for clicking letters.
        Random rnd = new Random();
        int index = rnd.nextInt(maxValue);
        while (generated.contains(index)) {
            index = rnd.nextInt(maxValue);
        }
        generated.add(index);
        return index;
    }

    /**
     * Sets timer element value to 00:00. Verifies timer value and deals with alert that pops up when time is up. Verifies alert message.
     * @param expectedMessage String (Read from config.properties file. Appears in alert popup.)
     */
    public void setTimerToZero(String expectedMessage){
        //Reading new timer value from config.properties file.
        String timerValue = prop.getProperty("timerEndValue");
        Reporter.log("Setting timer to zero.");
        System.out.println("Setting timer to zero.");
        //Script that stops running timer and sets time to 0.
        String script = "startTimer(\"stop\"); timerInterval = setInterval(function(){submit();},1000)";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            //Executing script.
            js.executeScript(script);
            //Initialize timer WebElement object.
            WebElement timerEl = find(locators.getTimerLoc());
            //Set web element value to 00:00.
            js.executeScript("arguments[0].innerHTML = arguments[1]", timerEl, timerValue);
            //Verifying timer value.
            this.verifyMethods.verifyTimerValue(timerValue, true);
        } catch (Exception e) {
            System.err.println("Stale element error.");
        }
        try {
            dealWithAlert();
            //Verifying alert message.
            this.verifyMethods.verifyAlertMessage(this.getAlertMsg(),expectedMessage);
        } catch (Exception e) {
            System.err.println("No alert displayed!");
        }
    }
}
