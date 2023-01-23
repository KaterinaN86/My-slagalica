package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;

/**
 * Class representing page where user chooses single player game. Defined using Page Object Model, inherits TestBase fields and methods.
 */
public class SinglePlayerGamePage extends TestBase {

    /**
     * Locator objects for different game options in menu.
     */
    By slagalicaBtnLoc = new By.ById("btnSlagalica");
    By mojBrojBtnLoc = new By.ById("btnMojBroj");
    By skockoBtnLoc = new By.ById("btnSkocko");
    By spojniceBtnLoc = new By.ById("btnSpojnice");
    By koZnaZnaBtnLoc = new By.ById("btnKoZnaZna");
    By asocijacijeBtnLoc = new By.ById("btnAsocijacije");

    /**
     * Locator objects for different points elements.
     */
    By totalPointsLoc = new By.ById("numOfPoints");
    By slagalicaPointsLoc = new By.ById("numOfPointsSlagalica");
    By mojBrojPointsLoc = new By.ById("numOfPointsMojBroj");
    By skockoPointsLoc = new By.ById("numOfPointsSkocko");
    By koZnaZnaPointsLoc = new By.ById("numOfPointsKoZnaZna");

    /**
     * New game button locator object.
     */
    By newGameBtnLoc = new By.ByXPath("//button[text()='New Game']");


    public SinglePlayerGamePage() {
        super();
    }

    public By getNewGameBtnLoc() {
        return newGameBtnLoc;
    }

    public By getSlagalicaBtnLoc() {
        return slagalicaBtnLoc;
    }

    public By getMojBrojBtnLoc() {
        return mojBrojBtnLoc;
    }

    public void clickNewGameButton() {
        //wait.until(ExpectedConditions.presenceOfElementLocated(newGameBtnLoc));
        this.verifyMethods.verifyButtonIsClickable(newGameBtnLoc);
        click(newGameBtnLoc);
        waitForElToBeClickable(locators.getContainerLoc());
        Reporter.log("New Game button is clicked");
        System.out.println("New Game button is clicked");
    }

    public MojBrojPage openMojBrojPage() {
        clickNewGameButton();
        this.verifyMethods.verifyButtonIsClickable(mojBrojBtnLoc);
        Reporter.log("Click \"Moj broj\" button.");
        System.out.println("Click \"Moj broj\" button.");
        click(mojBrojBtnLoc);
        return (MojBrojPage) verifyMethods.verifyPageObjectInitialized(new MojBrojPage());
    }

    public SlagalicaPage openSlagalicaPage() {
        clickNewGameButton();
        this.verifyMethods.verifyButtonIsClickable(slagalicaBtnLoc);
        Reporter.log("Click \"Slagalica\" button");
        System.out.println("Click \"Slagalica\" button");
        click(slagalicaBtnLoc);
        return (SlagalicaPage) verifyMethods.verifyPageObjectInitialized(new SlagalicaPage());
    }
}
