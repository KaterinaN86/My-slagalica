package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.List;

/**
 * Class representing "Moj Broj" page using Page Object Model. Inherits TestBase fields and methods.
 */
public class MojBrojPage extends TestBase {

    /**
     * Target number locator object.
     */
    private final By targetNumberLoc = new By.ById("targetNumber");
    /**
     * Locator for page elements that contain button with a number.
     */
    private final By numberButtonsLoc = new By.ByXPath("//button[starts-with(@class,'availableNumber')]");
    private final By userExpressionLoc = new By.ById("userExpression");

    private final By deleteBtnLoc = new By.ByXPath("//button[text()='Izbrisi']");

    private final By submitBtnLoc = new By.ByXPath("//button[text()='Potvrdi']");
    private List<WebElement> numbersList;

    /**
     * Constructor
     */
    public MojBrojPage() {
        super();
    }

    public void verifyTargetValueIsPositiveInteger() {
        String value =find(targetNumberLoc).getText();
        int intValue = Integer.parseInt(value);
        Assert.assertTrue((intValue > 0), "Target number doesn't match specification.");
        Reporter.log("Target number value verified.");
        System.out.println("Target number value verified.");
    }

    public void verifyNumberButtonsTotal() {
        Reporter.log("Verify total amount of buttons with numbers matches specified.");
        System.out.println("Verify total amount of buttons with numbers matches specified.");
        int total = findAll(numberButtonsLoc).size();
        int expected = Integer.parseInt(prop.getProperty("mojBrojTotalNumbers"));
        Assert.assertEquals(total, expected, "There are " + total + " buttons with numbers displayed. Amount doesn't match specified value of: " + expected + ".");
        Reporter.log("There are " + total + " buttons with numbers displayed. Amount matches specified.");
        System.out.println("There are " + total + " buttons with numbers displayed. Amount matches specified.");

    }

    /**
     * Checks if number value of button element is contained in the list of expected values (in config file).
     *
     * @param number    String (Variable that contains number displayed on button element).
     * @param condition String (Variable that contains a list of allowed values for specific button element).
     * @return boolean
     */
    boolean passesCondition(String number, String condition) {
        //Both number value and list of expected values are of type String. Contains method is used to check if value si valid.
        return condition.contains(number);
    }

    /**
     * Checks if values of button elements with numbers are valid.
     */
    public void verifyNumbersValues() {
        //List of all button web elements on page that contain number values.
        numbersList = findAll(numberButtonsLoc);
        //Variable used as index of web element in list.
        int i = 1;
        //Variable used to store allowed values read from config.properties file.
        String condition = "";
        for (WebElement el : numbersList) {
            Reporter.log("Checking value of number button on position: " + i + ".");
            System.out.println("Checking value of number button on position: " + i + ".");
            //Initializing a String variable that contains number displayed on button element.
            String value = el.getText();
            //Condition is same for first 4 numbers.
            if (i < 5) {
                condition = prop.getProperty("mojBrojFirstFourDigits");
            }
            if (i == 5) {
                condition = prop.getProperty("mojBrojFifthNumber");
            }
            if (i == 6) {
                condition = prop.getProperty("mojBrojSixthNumber");
            }
            Assert.assertTrue(passesCondition(value, condition), "Value " + value + " does not match specified condition.");
            Reporter.log("Value " + value + " matches specified condition.");
            System.out.println("Value " + value + " matches specified condition.");
            //Incrementing index variable.
            i++;
        }
    }

    public void clickEachNumberAndDelete() {
        WebElement userExpValue = find(userExpressionLoc);
        int i = 1;
        for (WebElement el : numbersList) {
            String numberValue = el.getText();
            el.click();
            Reporter.log("Clicked on number on position: " + i + ", with value: "+numberValue+".");
            System.out.println("Clicked on number on position: " + i + ", with value: "+numberValue+".");
            verifyUserExp(userExpValue.getText(), numberValue);
            click(deleteBtnLoc);
            Reporter.log("Clicked delete button.");
            System.out.println("Clicked delete button.");
            verifyUserExp(userExpValue.getText(), "");
            i++;
        }
    }

    public void verifyUserExp(String actual, String expected) {
        Reporter.log("Verifying value displayed in user expression text field element.");
        System.out.println("Verifying value displayed in user expression text field element.");
        Assert.assertEquals(actual, expected, "Value " + actual + " doesn't match displayed.");
        Reporter.log("Expected value is displayed in user expression text field element.");
        System.out.println("Expected value is displayed in user expression text field element.");
    }

    public void verifyEmtpyExpressionSubmit() {
        Reporter.log("Submitting empty expression as potential solution.");
        System.out.println("Submitting empty expression as potential solution.");
        driver.findElement(submitBtnLoc).click();
        dealWithAlert();
        verifyMethods.verifyAlertMessage(this.getAlertMsg(), prop.getProperty("mojBrojBadExpMsg"));
    }

}