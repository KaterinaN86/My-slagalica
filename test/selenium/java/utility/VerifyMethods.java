package utility;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import pages.HomePage;
import pages.SinglePlayerGamePage;

import java.util.ArrayList;
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
        usernameEl = base.driver.findElement(base.locators.getUsernameTextInputLoc());
        passwordEl = base.driver.findElement(base.locators.getPasswordTextInputLoc());
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
        Assert.assertTrue(base.driver.findElement(base.locators.getRegisterBtnLoc()).isDisplayed(), "Register button not displayed!");
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
        base.setPageTitle(base.driver.getTitle());
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
        Reporter.log("Verifying main container element is displayed.");
        System.out.println("Verifying main container element is displayed.");
        Assert.assertTrue(base.driver.findElement(base.locators.getContainerLoc()).isDisplayed(), "Main container element not displayed!");
        Reporter.log("Main container element is displayed.");
        System.out.println("Main container element is displayed.");
    }

    /**
     * Verifies page title, container (or menu) and container (or menu) title
     *
     * @param configTitle
     * @param containerTitle
     */
    public void verifyTitles(String configTitle, String containerTitle) {
        base.setConfigTitle(configTitle);
        base.setPageTitle(base.driver.getTitle());
        verifyPageTitle();
        String actualContainerTitle = base.driver.findElement(base.locators.getContainerTitleLoc()).getText();
        Reporter.log("Verifying container title matches specified.");
        Assert.assertEquals(actualContainerTitle, containerTitle, "Container title doesn't match specified value!");
        Reporter.log("Container title " + actualContainerTitle + " verified.");
        System.out.println("Container title " + actualContainerTitle + " verified.");
    }

    /**
     * Verifies timer element and go back button element.
     */
    public void verifyTimerAndBackElements() {
        Reporter.log("Verifying timer element.");
        System.out.println("Verifying timer element.");
        Assert.assertTrue(base.driver.findElement(base.locators.getTimerLoc()).isDisplayed(), "Timer element not displayed!");
        Reporter.log("Timer verified.");
        System.out.println("Timer verified.");
        Reporter.log("Verifying back button.");
        System.out.println("Verifying back button.");
        Assert.assertTrue(base.driver.findElement(base.locators.getBackBtnLoc()).isDisplayed(), "Go back button not displayed!");
        Reporter.log("Back button verified.");
        System.out.println("Back button verified.");
    }
    /**
     * Verifies titles, container and elements in menus or elements with buttons for playing game.
     */
    public void verifyTitlesAndOtherPageElements(String configTitle, String containerTitle) {
        verifyTitles(configTitle, containerTitle);
        verifyContainerDisplayed();
        Reporter.log("Checking all button elements on page are displayed.");
        System.out.println("Checking buttons are displayed.");
        List<WebElement> buttonDivsList = base.driver.findElements(base.locators.getAllButtonDivsLoc());
        for (WebElement el : buttonDivsList) {
            Assert.assertTrue(el.isDisplayed(), "Menu element not displayed.");
        }
        Reporter.log("Verified all button div elements.");
        System.out.println("All divs for button elements are displayed.");
        if (!(this.base instanceof HomePage) && !(this.base instanceof SinglePlayerGamePage)) {
           verifyTimerAndBackElements();
        }
    }

    /**
     * Verifies starting value of timer on pages that have one.
     * @param startValue (String value specified in config file.)
     */
    public void verifyTimerStartValue(String startValue) {
        String actualValue = base.driver.findElement(base.locators.getTimerLoc()).getText();
        Reporter.log("Verifying timer element start value.");
        System.out.println("Verifying timer element start value.");
        Assert.assertEquals(actualValue, startValue, "Timer start value "+ actualValue + " doesn't match expected " + startValue + ".");
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
        Assert.assertEquals(expectedNumber, actualNumber, "Number of valid links doesn't match expected.");
        Reporter.log("Number of valid links matches expected.");
        System.out.println("Number of valid links matches expected.");
    }
}
