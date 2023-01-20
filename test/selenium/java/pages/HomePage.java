package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;

/**
 * Class representing Homepage, using Page Object Model. Inherits TestBase fields and methods.
 */
public class HomePage extends TestBase {
    /**
     * Locator objects for all options in menu.
     */
    private final By singlePlayerLoc = By.xpath("//button[text()='Jedan igrac']");
    By twoPlayersLoc = By.xpath("//button[text()='Dva igraca']");
    By rulesLoc = By.xpath("//button[text()='Pravila']");
    By rangListLoc = By.xpath("//button[text()='Rang Lista']");
   private final By logOutLoc = By.xpath("//button[text()='Log out']");

    /**
     * Constructor.
     */
    public HomePage() {
        super();
    }

    public By getLogOutLoc() {
        return logOutLoc;
    }

    public By getSinglePlayerLoc() {
        return singlePlayerLoc;
    }

    /**
     * Logs out active user.
     *
     * @return LoginPage (new instance of LoginPage, after verification).
     */
    public LoginPage logout() {
        wait.until(ExpectedConditions.presenceOfElementLocated(logOutLoc));
        this.verifyMethods.verifyButtonIsClickable(logOutLoc);
        Reporter.log("Logging out active user.");
        System.out.println("Logging out active user.");
        driver.findElement(logOutLoc).click();
        Reporter.log("User logged out.");
        System.out.println("User logged out.");
        return (LoginPage) verifyMethods.verifyPageObjectInitialized(new LoginPage());
    }

    public SinglePlayerGamePage clickSinglePlayerGame() {
        Reporter.log("Clicking single player option.");
        driver.findElement(singlePlayerLoc).click();
        System.out.println("Single player game page option clicked.");
        return (SinglePlayerGamePage) verifyMethods.verifyPageObjectInitialized(new SinglePlayerGamePage());
    }

}
