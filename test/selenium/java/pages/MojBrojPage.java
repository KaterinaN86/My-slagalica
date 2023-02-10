package pages;

import base.TestBase;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.w3c.dom.ls.LSOutput;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class representing "Moj Broj" page using Page Object Model. Inherits TestBase fields and methods.
 */
public class MojBrojPage extends TestBase {

    /**
     * Target number locator object.
     */
    private final By targetNumberLoc = new By.ById("targetNumber");
    /**
     * Locators for page elements that contain button with a number and buttons with operator.
     */
    private final By numberButtonsLoc = new By.ByXPath("//button[starts-with(@class,'availableNumber')]");
    private final By operatorButtonsLoc = new By.ByXPath("//button[starts-with(@class,'btnChar')]");
    private final By userExpressionLoc = new By.ById("userExpression");
    private final By deleteBtnLoc = new By.ByXPath("//button[text()='Izbrisi']");
    private final By submitBtnLoc = new By.ByXPath("//button[text()='Potvrdi']");
    //Locator object for computer solution expression UI element.
    private final By computerSolution = new By.ById("solution");
    //List of WebElement objects that represent number button elements on page.
    private List<WebElement> numbersList;
    //List of WebElement objects that represent operator button elements on page.
    private List<WebElement> operatorsList;
    //Element that displays expression selected by user.
    private WebElement userExpValue;

    /**
     * Constructor
     */
    public MojBrojPage() {
        super();
    }

    // Helper method that converts String to List of Strings (characters as strings).
    public static List<String>
    convertStringToCharList(String str) {
        return new AbstractList<String>() {
            @Override
            public String get(int index) {
                return String.valueOf(str.charAt(index));
            }

            @Override
            public int size() {
                return str.length();
            }
        };
    }

    public void verifyTargetValueIsPositiveInteger() {
        String value = find(targetNumberLoc).getText();
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
     * @return boolean - indicates whether condition is satisfied.
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

    //Checks if operators displayed on operator buttons are valid.
    public void verifyOperators() {
        //Initializing list of operator buttons.
        this.operatorsList = findAll(operatorButtonsLoc);
        Reporter.log("Check if buttons with operators match specified values.");
        System.out.println("Verifying operator buttons displayed text values.");
        List<String> configOperators = List.of(prop.getProperty("mojBrojOperators").split(","));
        //Temporary variable used as index for looping over config operators.
        int index = 0;
        for (WebElement operatorButton : this.operatorsList) {
            Assert.assertEquals(operatorButton.getText(), configOperators.get(index), "Operator button with value: " + operatorButton.getText() + " doesn't match specified value.");
            index++;
        }
        Reporter.log("Operator buttons verified!");
        System.out.println("Operator buttons verified!");
    }

    public void clickEachNumberAndDelete() {
        userExpValue = find(userExpressionLoc);
        int i = 1;
        for (WebElement el : numbersList) {
            String numberValue = el.getText();
            el.click();
            Reporter.log("Clicked on number on position: " + i + ", with value: " + numberValue + ".");
            System.out.println("Clicked on number on position: " + i + ", with value: " + numberValue + ".");
            verifyUserExp(userExpValue.getText(), numberValue);
            click(deleteBtnLoc);
            Reporter.log("Clicked delete button.");
            System.out.println("Clicked delete button.");
            verifyUserExp(userExpValue.getText(), "");
            i++;
        }
    }

    public void clickEachOperatorAndDelete() {
        userExpValue = find(userExpressionLoc);
        for (WebElement el : operatorsList) {
            String operatorValue = el.getText();
            el.click();
            Reporter.log("Clicked on operator: " + operatorValue + ".");
            System.out.println("Clicked on operator: " + operatorValue + ".");
            verifyUserExp(userExpValue.getText(), operatorValue);
            click(deleteBtnLoc);
            Reporter.log("Clicked delete button.");
            System.out.println("Clicked delete button.");
            verifyUserExp(userExpValue.getText(), "");
        }
    }

    public void verifyUserExp(String actual, String expected) {
        Reporter.log("Verifying value displayed in user expression text field element.");
        System.out.println("Verifying value displayed in user expression text field element.");
        Assert.assertEquals(actual, expected, "Value " + actual + " doesn't match displayed.");
        Reporter.log("Expected value is displayed in user expression text field element.");
        System.out.println("Expected value is displayed in user expression text field element.");
    }

    public void verifyEmptyExpressionSubmit() {
        Reporter.log("Submitting empty expression as potential solution.");
        System.out.println("Submitting empty expression as potential solution.");
        driver.findElement(submitBtnLoc).click();
        this.dealWithAlert();
        verifyMethods.verifyAlertMessage(this.getAlertMsg(), prop.getProperty("mojBrojBadExpMsg"));
        this.waitForElToBeClickable(locators.getH1TitleLoc());
    }

    public void clickRandomNumbersAndOperators(boolean... limitedOperators) {
        //If not all operators are used, longer expression is generated.
        int repetitions = limitedOperators.length > 0 ? 3 : 4;
        //Control variables used to generate random index value.
        int numbersMaxValue = numbersList.size();
        int operatorsMaxValue = operatorsList.size();
        /*
          If no arguments are passed when calling this method operators are not limited and all of them can be used when generating user expression. If limitedOperators value is passed when method is called, it will most likely be true. In this case not all operators are used when generating user expression.
         */
        boolean operatorsAreLimited = (limitedOperators.length > 0) && limitedOperators[0];
        //List of already generated random values. Used to avoid repeating index value.
        ArrayList<Integer> generatedNumbers = new ArrayList<>();
        ArrayList<Integer> generatedOperators = new ArrayList<>();
        Reporter.log("Click three random numbers and two operators, to form an expression.");
        System.out.println("Click three random numbers and two operators, to form an expression.");
        for (int i = 0; i < repetitions; i++) {
            int index = this.generateUnique(generatedNumbers, numbersMaxValue);
            WebElement randomLetter = this.numbersList.get(index);
            this.waitForElToBeClickable(randomLetter);
            randomLetter.click();
            Reporter.log("Clicked number: " + randomLetter.getText());
            System.out.println("Clicked number: " + randomLetter.getText());
            if (i < repetitions - 1) {
                //Operators "(", and ")" are excluded. Operators can be repeated in expression.
                if (operatorsAreLimited) {
                    index = ThreadLocalRandom.current().nextInt(2, operatorsMaxValue);
                    ;
                }
                WebElement randomOperator = this.operatorsList.get(index);
                this.waitForElToBeClickable(randomOperator);
                randomOperator.click();
                Reporter.log("Clicked operator: " + randomOperator.getText());
                System.out.println("Clicked operator: " + randomOperator.getText());
            }
        }
    }

    public void clickSubmitButton() {
        this.waitForElToBeClickable(locators.getH1TitleLoc());
        Reporter.log("User expression before submit: " + find(userExpressionLoc).getText());
        System.out.println("User expression before submit: " + find(userExpressionLoc).getText());
        click(submitBtnLoc);
        Reporter.log("Clicked submit button.");
        System.out.println("Clicked submit button.");
        this.dealWithAlert();
        Reporter.log("Displayed alert after submit: " + this.getAlertMsg());
        System.out.println("Displayed alert after submit: " + this.getAlertMsg());
    }

    public void verifySolutionExpression() {
        boolean isValid;
        List<String> solutionExpressionChars = convertStringToCharList(find(computerSolution).getText());
        Reporter.log("Verifying expression elements.");
        System.out.println("Verifying expression elements.");
        for (String expChar : solutionExpressionChars) {
            isValid = prop.getProperty("mojBrojFirstFourDigits").contains(expChar) || prop.getProperty("mojBrojFifthNumber").contains(expChar) || prop.getProperty("mojBrojSixthNumber").contains(expChar) || prop.getProperty("mojBrojOperators").contains(expChar);
            Assert.assertTrue(isValid, "Expression element: " + expChar + " is not valid!");
        }
        Reporter.log("Computer calculated expression elements verified.");
        System.out.println("Computer calculated expression elements verified.");
        //Evaluating computer generated String solution expression.
        Expression expression = new ExpressionBuilder(find(computerSolution).getText()).build();
        int result = (int) Math.floor(expression.evaluate());
        Reporter.log("Verifying solution expression value.");
        System.out.println("Verifying solution expression value.");
        Assert.assertEquals(find(targetNumberLoc).getText(), String.valueOf(result), "Expression value: " + result + " doesn't match target number: " + find(targetNumberLoc).getText());
        Reporter.log("Expression value: " + result + " matches target number: " + find(targetNumberLoc).getText());
        System.out.println("Expression value: " + result + " matches target number: " + find(targetNumberLoc).getText());
    }

    public void getSolutionExpression() {
        List<Integer> nums = new ArrayList<>();
        for (WebElement number : numbersList) {
            nums.add(Character.getNumericValue(number.getText().charAt(0)));
        }
        StringBuilder expression = new StringBuilder();
        HashMap<Integer, String> op = new HashMap<>();
        op.put(0, "*");
        op.put(4, "*");
        op.put(1, "+");
        op.put(2, "-");
        op.put(3, "/");
        for (Integer num : nums) {
            expression.append(num.toString() + op.get((int) (Math.random() * 5)));
        }
        String result = expression.substring(0, expression.length() - 1);
        Expression exprs = new ExpressionBuilder(result).build();
        int res = (int) Math.floor(exprs.evaluate());
        System.err.println( result+ " = "+res);
        result = find(computerSolution).getText();
        exprs = new ExpressionBuilder(result).build();
        res = (int) Math.floor(exprs.evaluate());
        System.out.println( result+ " = "+res);
    }


}