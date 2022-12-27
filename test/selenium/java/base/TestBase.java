package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * Base class with driver and properties objects
 * contains method for driver initialization
 */
public class TestBase {

    /**
     * Driver object used throughout all pages
     */
    public static WebDriver driver;
    /**
     * Properties object used for accessing data in config.properties file
     */
    public static Properties prop;

    /**
     * Base URL (the one the browser opens initially)
     */
    public static String baseUrl;
    /**
     * Current URL
     */
    public static String currentUrl;
    /**
     * WebDriverWait object used for explicit wait
     */
    public static WebDriverWait wait;

    /**
     * Constructor used for initializing and loading data in properties object
     */
    public TestBase() {
        try {
            PageFactory.initElements(driver, this);
            prop = new Properties();
            /**
             * reading data from properties file
             */
            FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir") + "\\test\\selenium\\resources\\config.properties");
            prop.load(fileIn);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes driver object depending on browser property
     */
    public static void init() {
        /**
         * defining browser used for testing
         */
        String browserName = prop.getProperty("browser");
        /**
         * initializing driver object depending on chosen browser
         */
        if (browserName.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", prop.getProperty("chromeDriverPath"));
            driver = new ChromeDriver();
        } else if (browserName.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", prop.getProperty("geckoDriverPath"));
            driver = new FirefoxDriver();
        } else if (browserName.equals("edge")) {
            System.setProperty("webdriver.edge.driver", prop.getProperty("edgeDriverPath"));
            driver = new EdgeDriver();
        }
        baseUrl = prop.getProperty("loginUrl");
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    /**
     * closes web page
     */
    public void close() {
        currentUrl=driver.getCurrentUrl();
        String[] urlArray = currentUrl.split("/");
        String page = urlArray[urlArray.length-1];
        if(currentUrl.equals(prop.getProperty("baseUrl"))){
            page="Homepage";
        }
        Reporter.log("Closing page: "+page);
        driver.close();
        System.out.println("Page: "+page+" closed.");
    }
}
