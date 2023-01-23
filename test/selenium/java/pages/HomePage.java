package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Reporter;

/**
 * Class representing Homepage, using Page Object Model. Inherits TestBase fields and methods.
 */
public class HomePage extends TestBase {
    /**
     * Locator objects for all options in menu.
     */
    private final By singlePlayerLoc = By.xpath("//button[text()='Jedan igrac']");
    private final By logOutLoc = By.xpath("//button[text()='Log out']");
    By twoPlayersLoc = By.xpath("//button[text()='Dva igraca']");
    By rulesLoc = By.xpath("//button[text()='Pravila']");
    By rangListLoc = By.xpath("//button[text()='Rang Lista']");

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
        waitForVisibilityOf(locators.getContainerLoc());
        waitForElToBeClickable(locators.getH1TitleLoc());
        this.verifyMethods.verifyButtonIsClickable(logOutLoc);
        Reporter.log("Logging out active user.");
        System.out.println("Logging out active user.");
        click(logOutLoc);
        Reporter.log("User logged out.");
        System.out.println("User logged out.");
        return (LoginPage) verifyMethods.verifyPageObjectInitialized(new LoginPage());
    }

    public SinglePlayerGamePage clickSinglePlayerGame() {
        waitForVisibilityOf(singlePlayerLoc);
        verifyMethods.verifyButtonIsClickable(singlePlayerLoc);
        Reporter.log("Clicking single player option.");
        click(singlePlayerLoc);
        System.out.println("Single player game page option clicked.");
        return (SinglePlayerGamePage) verifyMethods.verifyPageObjectInitialized(new SinglePlayerGamePage());
    }

}
