package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * Class representing "Moj Broj" page using Page Object Model. Inherits TestBase fields and methods.
 */
public class MojBrojPage extends TestBase {

    /**
     * Target number locator object.
     */
    By targetNumberLoc = new By.ById("targetNumber");

    /**
     * Constructor
     */
    public MojBrojPage() {
        super();
    }

    public void verifyTargetValueIsPositiveInteger() {
        String value = driver.findElement(targetNumberLoc).getText();
        int intValue = Integer.parseInt(value);
        Assert.assertTrue((intValue > 0), "Target number doesn't match specification.");
        Reporter.log("Target number value verified.");
        System.out.println("Target number value verified.");
    }

}
