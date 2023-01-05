package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * Class representing Homepage, using Page Object Model. Inherits TestBase fields and methods.
 */
public class HomePage extends TestBase {
    /**
     * Locator object for menu title.
     */
    By menuTitleLoc = By.xpath("//h1[@class='text-center text-white']");
    /**
     * Locator object for menu div element.
     */
    By menuLoc = By.xpath("//div[@class='row d-block text-center']");
    /**
     * Locator objects for all options in menu.
     */
    By singlePlayerLoc = By.xpath("//button[text()='Jedan igrac']");
    By twoPlayersLoc = By.xpath("//button[text()='Dva igraca']");
    By rulesLoc = By.xpath("//button[text()='Pravila']");
    By rangListLoc = By.xpath("//button[text()='Rang Lista']");
    By logOutLoc = By.xpath("//button[text()='Log out']");

    /**
     * Constructor.
     */
    public HomePage() {
        super();
    }

    /**
     * Verifies all menu items are displayed.
     */
    public void verifyMenuItems() {
        Reporter.log("Checking all options in menu are displayed.");
        System.out.println("Checking all options in menu are displayed.");
        Assert.assertTrue(driver.findElement(singlePlayerLoc).isDisplayed(), "Menu option: \"Jedan igrac\" not displayed!");
        Reporter.log("Menu option: \"Jedan igrac\" is displayed.");
        System.out.println("Menu option: \"Jedan igrac\" is displayed.");
        Assert.assertTrue(driver.findElement(twoPlayersLoc).isDisplayed(), "Menu option: \"Dva igraca\" not displayed!");
        Reporter.log("Menu option: \"Dva igraca\" is displayed.");
        System.out.println("Menu option: \"Dva igraca\" is displayed.");
        Assert.assertTrue(driver.findElement(rulesLoc).isDisplayed(), "Menu option: \"Pravila\" not displayed!");
        Reporter.log("Menu option: \"Pravila\" is displayed.");
        System.out.println("Menu option: \"Pravila\" is displayed.");
        Assert.assertTrue(driver.findElement(rangListLoc).isDisplayed(), "Menu option: \"Rang Lista\" not displayed!");
        Reporter.log("Menu option: \"Rang Lista\" is displayed.");
        System.out.println("Menu option: \"Rang Lista\" is displayed.");
        Assert.assertTrue(driver.findElement(logOutLoc).isDisplayed(), "Menu option: \"Log out\" not displayed!");
        Reporter.log("Menu option: \"Log out\" is displayed.");
        System.out.println("Menu option: \"Log out\" is displayed.");
    }

    public HomePage verifyElementsAfterOpen() {
        verifyPageTitle();
        verifyContainerDisplayed();
        Reporter.log("Verifying menu title matches specified.");
        Assert.assertEquals(driver.findElement(menuTitleLoc).getText(), prop.getProperty("Slagalica"), "Menu title doesn't match specified value!");
        Reporter.log("Menu title verified.");
        System.out.println("Menu title verified.");
        Reporter.log("Verifying menu element.");
        Assert.assertTrue(driver.findElement(menuLoc).isDisplayed(),"Menu element is not displayed!");
        Reporter.log("Menu element verified.");
        System.out.println("Menu element verified.");
        verifyMenuItems();
        return (HomePage) verifyPageObjectInitialized(this);

    }

    /**
     * Logs out active user.
     *
     * @return LoginPage (new instance of LoginPage, after verification).
     */
    public LoginPage logout() {
        Reporter.log("Logging out active user.");
        driver.findElement(logOutLoc).click();
        System.out.println("User logged out.");
        return (LoginPage) verifyPageObjectInitialized(new LoginPage());
    }

    public SinglePlayerGamePage clickOnePlayerGame() {
        Reporter.log("Clicking single player option.");
        driver.findElement(singlePlayerLoc).click();
        System.out.println("Single player game page option clicked.");
        return (SinglePlayerGamePage) verifyPageObjectInitialized(new SinglePlayerGamePage());
    }
}
