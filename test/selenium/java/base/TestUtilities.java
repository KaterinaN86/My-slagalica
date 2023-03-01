package base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestUtilities extends TestBase {

    static String getTodaysDate() {
        return new SimpleDateFormat("yyyMMdd").format(new Date());
    }

    static String getSystemTime() {
        return new SimpleDateFormat("HHmmssSSS").format(new Date());
    }

    public static void takeScreenshot(String fileName) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "screenshots" + File.separator + getTodaysDate() + File.separator + testSuiteName + File.separator + testName + File.separator + testMethodName + File.separator + getSystemTime() + " " + fileName + ".png";
        try {
            FileUtils.copyFile(scrFile,new File(path));
        } catch (IOException e) {
            System.err.println("Screenshot file copy failed!");
        }
    }
    /** Get logs from browser console */
    protected List<LogEntry> getBrowserLogs() {
        LogEntries log = driver.manage().logs().get("browser");
        List<LogEntry> logList = log.getAll();
        return logList;
    }
}
