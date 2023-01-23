package utility;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import pages.HomePage;
import pages.MojBrojPage;
import pages.SlagalicaPage;

import java.util.List;

public class VerifyMethods {

    WebElement usernameEl;
    WebElement passwordEl;

    TestBase base;

    public VerifyMethods(TestBase base) {
        this.base = base;
    }

    /**
     * Verifying current page title matches specified.
     */
    public void verifyPageTitle() {
        base.setPageTitle();
        Reporter.log("Verifying page title.");
        System.out.println("Check if page title matches specified.");
        Assert.assertEquals(base.getPageTitle(), base.getConfigTitle(), "Page title '" + base.getPageTitle() + "' is not equal to expected '");
        Reporter.log("Page title '" + base.getPageTitle() + "' is equal to expected '" + base.getConfigTitle() + "'.");
        System.out.println("Page title '" + base.getPageTitle() + "' is equal to expected.");
    }

    /**
     * Verifying form title matches specified.
     *
     * @param formTitle   (String containing actual title of form element
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
        base.waitForVisibilityOf(TestBase.locators.getUsernameTextInputLoc());
        base.waitForVisibilityOf(TestBase.locators.getPasswordTextInputLoc());
        Reporter.log("Verifying username and password elements are displayed.");
        System.out.println("Check if username and password are displayed.");
        Assert.assertTrue(base.find(TestBase.locators.getUsernameTextInputLoc()).isDisplayed(), "Username text input element not displayed!");
        Assert.assertTrue(base.find(TestBase.locators.getPasswordTextInputLoc()).isDisplayed(), "Password text input element not displayed!");
        Reporter.log("Username and password text input elements are displayed.");
        System.out.println("Username and password text input elements are displayed.");
    }

    /**
     * Verify register button is displayed.
     */
    public void verifyRegisterDisplayed() {
        base.waitForVisibilityOf(TestBase.locators.getRegisterBtnLoc());
        Reporter.log("Verify register button is displayed.");
        System.out.println("Check if register button is displayed.");
        //Log corresponding message depending on isDisplayed method result for register button WebElement object.
        Assert.assertTrue(base.find(TestBase.locators.getRegisterBtnLoc()).isDisplayed(), "Register button not displayed!");
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
        Assert.assertNotNull(page, "Instance of " + page.getClass() + " not initialized!");
        System.out.println("Verified instance of " + page.getClass() + " class.");
        Reporter.log("Verified instance of " + page.getClass() + " class.");
        return page;
    }

    /**
     * Verifying page title and elements found on multiple pages.
     */
    public void verifyStateAfterOpen(String configTitle) {
        base.setConfigTitle(configTitle);
        verifyPageTitle();
        verifyUsernamePasswordDisplayed();
        verifyRegisterDisplayed();
    }

    /**
     * Verifies message displayed in alert window.
     *
     * @param message  (String containing actual alert window message).
     * @param property (String containing alert message specified in config.properties file).
     */
    public void verifyAlertMessage(String message, String property) {
        Assert.assertEquals(message, property, "Wrong message displayed!");
        Reporter.log("Message: " + message + " is displayed and matches specified.");
        System.out.println("Message: " + message + " is displayed and matches specified.");
    }

    /**
     * Verifies container element in pages with menus containing links (like HomePage and SinglePlayerGamePage) or pages with buttons for playing game (like SlagalicaPage and MojBrojPage.
     */
    public void verifyContainerDisplayed() {
        base.waitForVisibilityOf(TestBase.locators.getContainerLoc());
        Reporter.log("Verifying main container element is displayed.");
        System.out.println("Verifying main container element is displayed.");
        Assert.assertTrue(base.find(TestBase.locators.getContainerLoc()).isDisplayed(), "Main container element not displayed!");
        Reporter.log("Main container element is displayed.");
        System.out.println("Main container element is displayed.");
    }

    /**
     * Helper method used by verifyTitlesAndOtherPageElements. Verifies main content container (or menu) title.
     *
     * @param containerTitle (String containing title specified in config.properties file.
     */
    public void verifyContainerTitle(String containerTitle) {
        base.waitForVisibilityOf(TestBase.locators.getContainerTitleLoc());
        String actualContainerTitle = base.find(TestBase.locators.getContainerTitleLoc()).getText();
        Reporter.log("Verifying container title matches specified.");
        Assert.assertEquals(actualContainerTitle, containerTitle, "Container title doesn't match specified value!");
        Reporter.log("Container title " + actualContainerTitle + " verified.");
        System.out.println("Container title " + actualContainerTitle + " verified.");
    }

    /**
     * Helper method used by verifyTitlesAndOtherPageElements. Verifies timer element.
     */
    public void verifyTimerElement() {
        base.waitForVisibilityOf(TestBase.locators.getTimerLoc());
        Reporter.log("Verifying timer element.");
        System.out.println("Verifying timer element.");
        Assert.assertTrue(base.find(TestBase.locators.getTimerLoc()).isDisplayed(), "Timer element not displayed!");
        Reporter.log("Timer verified.");
        System.out.println("Timer verified.");
    }

    /**
     * Helper method used by verifyTitlesAndOtherPageElements. Verifies go back button element.
     */
    public void verifyGoBackButton() {
        base.waitForVisibilityOf(TestBase.locators.getBackBtnLoc());
        WebElement goBackButtonEl = base.find(TestBase.locators.getBackBtnLoc());
        Reporter.log("Verifying back button on page " + this.base.getClass().getSimpleName() + ".");
        System.out.println("Verifying back button on page " + this.base.getClass().getSimpleName() + ".");
        Assert.assertTrue(goBackButtonEl.isDisplayed(), "Go back button not displayed!");
        Assert.assertTrue(goBackButtonEl.isEnabled(), "Go back button not enabled!");
        Reporter.log("Back button verified.");
        System.out.println("Back button verified.");
    }

    /**
     * Verifies page title, container title and menu elements or button elements used for playing game.
     *
     * @param configTitle    String
     * @param containerTitle String
     */
    public void verifyTitlesAndOtherPageElements(String configTitle, String containerTitle) {
        // Setting expected title value before calling verify method.
        base.setConfigTitle(configTitle);
        base.setPageTitle();
        verifyPageTitle();
        // Verify container and container title.
        verifyContainerDisplayed();
        verifyContainerTitle(containerTitle);
        // Verify all buttons on page.
        Reporter.log("Checking all button elements on page are displayed.");
        System.out.println("Checking buttons are displayed.");
        base.waitForVisibilityOf(TestBase.locators.getAllButtonDivsLoc());
        //Creating a list of all div web elements on current page, that contain button elements.
        List<WebElement> buttonDivsList = base.findAll(TestBase.locators.getAllButtonDivsLoc());
        //Iterating over divs list.
        for (WebElement el : buttonDivsList) {
            TestBase.wait.until(ExpectedConditions.visibilityOf(el));
            //Creating an object for the button element inside the div element.
            WebElement btnEl = el.findElement(By.tagName("button"));
            Assert.assertTrue(btnEl.isDisplayed(), "Button element " + el.getText() + " not displayed.");
        }
        Reporter.log("Verified all button elements.");
        System.out.println("All button elements are displayed.");
        // If current object is a MojBrojPage or SlagalicaPage instance, verify timer.
        if ((this.base instanceof MojBrojPage) || (this.base instanceof SlagalicaPage)) {
            verifyTimerElement();
        }
        // If current object is not a HomePage instance, verify go back button.
        if (!(this.base instanceof HomePage)) {
            verifyGoBackButton();
        }
    }

    /**
     * Verifies starting value of timer on pages that have one.
     *
     * @param startValue (String value specified in config file.)
     */
    public void verifyTimerStartValue(String startValue) {
        base.waitForVisibilityOf(TestBase.locators.getTimerLoc());
        String actualValue = base.find(TestBase.locators.getTimerLoc()).getText();
        Reporter.log("Verifying timer element start value.");
        System.out.println("Verifying timer element start value.");
        Assert.assertEquals(actualValue, startValue, "Timer start value " + actualValue + " doesn't match expected " + startValue + ".");
        Reporter.log("Timer start value matches expected.");
        System.out.println("Timer start value matches expected.");
    }

    /**
     * Verifies number of valid links on page matches specified number
     *
     * @param actualNumber   (int value equal to number of valid links on current page).
     * @param expectedNumber (int value equal to number specified in config.properties file).
     */
    public void verifyValidLinkNumber(int actualNumber, int expectedNumber) {
        Reporter.log("Number of valid links on page: " + actualNumber + "; Expected valid links number: " + expectedNumber + ".");
        System.out.println("Number of valid links on page: " + actualNumber + "; Expected valid links number: " + expectedNumber + ".");
        Reporter.log("Verifying number of valid links matches expected.");
        System.out.println("Check number of valid links matches specified.");
        Assert.assertEquals(actualNumber, expectedNumber, actualNumber, "Number of valid links doesn't match expected.");
        Reporter.log("Number of valid links matches expected.");
        System.out.println("Number of valid links matches expected.");
    }

    /**
     * Verifies specified button element is clickable.
     *
     * @param locator By
     */
    public void verifyButtonIsClickable(By locator) {
        base.waitForVisibilityOf(locator);
        //Initializing WebElement object for specified button element.
        WebElement btnEl = base.find(locator);
        Reporter.log("Check if button " + btnEl.getText() + " is clickable.");
        System.out.println("Check if button " + btnEl.getText() + " is clickable.");
        Assert.assertTrue(btnEl.isEnabled(), "Button " + btnEl.getText() + " is not enabled!");
        Reporter.log("Button " + btnEl.getText() + " is clickable.");
        System.out.println("Button " + btnEl.getText() + " is clickable.");
    }

    /**
     * Verifies specified button element is not clickable.
     *
     * @param locator By
     */
    public void verifyButtonNotClickable(By locator) {
        base.waitForVisibilityOf(locator);
        //Initializing WebElement object for specified button element.
        WebElement btnEl = base.find(locator);
        Reporter.log("Check if button " + btnEl.getText() + " is clickable.");
        System.out.println("Check if button " + btnEl.getText() + " is clickable.");
        Assert.assertFalse(btnEl.isEnabled(), "Button " + btnEl.getText() + " is enabled!");
        Reporter.log("Button " + btnEl.getText() + " is not clickable.");
        System.out.println("Button " + btnEl.getText() + " is not clickable.");
    }

    public void verifyBackButtonIsClickable() {
        base.waitForVisibilityOf(TestBase.locators.getBackBtnLoc());
        base.waitForElToBeClickable(TestBase.locators.getH1TitleLoc());
        Reporter.log("Check if go back button is clickable.");
        System.out.println("Check if go back button is clickable.");
        Assert.assertTrue(base.find(TestBase.locators.getBackBtnLoc()).isEnabled(), "Go back button is not enabled.");
        Reporter.log("Go back button is clickable.");
        System.out.println("Go back button is clickable.");
    }
}