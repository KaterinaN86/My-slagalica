package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

/**
 * Class representing Homepage, using Page Object Model. Inherits TestBase fields and methods.
 */
public class HomePage extends TestBase {
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

    public SinglePlayerGamePage clickSinglePlayerGame() {
        Reporter.log("Clicking single player option.");
        driver.findElement(singlePlayerLoc).click();
        System.out.println("Single player game page option clicked.");
        return (SinglePlayerGamePage) verifyPageObjectInitialized(new SinglePlayerGamePage());
    }
}
