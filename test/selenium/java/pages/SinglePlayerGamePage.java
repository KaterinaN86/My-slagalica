package pages;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
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
    By spojnicePointsLoc = new By.ById("numOfPointsSpojnice");

    /**
     * New game button locator object.
     */
    By newGameBtnLoc = new By.ByXPath("//button[text()='New Game']");
    //Used for verification of total points value.
    int pointsBeforeGame;

    public SinglePlayerGamePage() {
        super();
    }

    public By getSlagalicaBtnLoc() {
        return slagalicaBtnLoc;
    }

    public By getMojBrojBtnLoc() {
        return mojBrojBtnLoc;
    }

    public By getTotalPointsLoc() {
        return totalPointsLoc;
    }

    /**
     * After game is played user has to start new game to be able to play it again. Before clicking each game New Game button is clicked to make sure game button is enabled.
     */
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
        //Storing value of points before game is played.
        this.pointsBeforeGame= Integer.parseInt(find(totalPointsLoc).getText());
        click(mojBrojBtnLoc);
        return (MojBrojPage) verifyMethods.verifyPageObjectInitialized(new MojBrojPage());
    }

    public SlagalicaPage openSlagalicaPage() {
        clickNewGameButton();
        this.verifyMethods.verifyButtonIsClickable(slagalicaBtnLoc);
        Reporter.log("Click \"Slagalica\" button");
        System.out.println("Click \"Slagalica\" button");
        //Storing value of points before game is played.
        this.pointsBeforeGame= Integer.parseInt(find(totalPointsLoc).getText());
        click(slagalicaBtnLoc);
        return (SlagalicaPage) verifyMethods.verifyPageObjectInitialized(new SlagalicaPage());
    }

    public SpojnicePage openSpojnicePage() {
        clickNewGameButton();
        this.verifyMethods.verifyButtonIsClickable(spojniceBtnLoc);
        Reporter.log("Click \"Spojnice\" button");
        System.out.println("Click \"Spojnice\" button");
        //Storing value of points before game is played.
        this.pointsBeforeGame= Integer.parseInt(find(totalPointsLoc).getText());
        click(spojniceBtnLoc);
        return (SpojnicePage) verifyMethods.verifyPageObjectInitialized(new SpojnicePage());
    }

    public void verifyBeforeGoingBack() {
        this.waitForElToBeClickable(locators.getH1TitleLoc());
        this.waitForElToBeClickable(locators.getContainerLoc());
        this.waitForElToBeClickable(locators.getBackBtnLoc());
    }

    /**
     * Compared values for calculated points during game Spojnice and points for game displayed on One Player page UI.
     * @param spojnicePage SpojnicePage object used to access points calculated during game.
     */
    public void verifySpojnicePoints(SpojnicePage spojnicePage) {
        Reporter.log("Verifying points for game \"Spojnice\".");
        System.out.println("Verifying points for game \"Spojnice\".");
        int actualPoints = Integer.parseInt(find(spojnicePointsLoc).getText());
        Assert.assertEquals(actualPoints, spojnicePage.getCalculatedPoints(), "Points for game Spojnice: " + actualPoints + " doesn't match expected value: " + spojnicePage.getCalculatedPoints());
        Reporter.log("Points for game Spojnice: " + actualPoints + " match expected value.");
        System.out.println("Points for game Spojnice: " + actualPoints + " match expected value.");
    }

    /**
     * Checks if player's total points increased by value of points calculated during last played game game.
     * @param page TestBase object
     */
    public void verifyTotalPoints(TestBase page) {
        Reporter.log("Verifying total points after played game.");
        System.out.println("Verifying total points after played game.");
        int actualPoints = Integer.parseInt(find(totalPointsLoc).getText());
        Assert.assertEquals(actualPoints, pointsBeforeGame + page.getCalculatedPoints(), "Total points value for single player games: " + actualPoints + " doesn't match expected value: " + pointsBeforeGame + page.getCalculatedPoints());
        Reporter.log("Total points for single player games: " + actualPoints + " matches expected value.");
        System.out.println("Total points for single player games: " + actualPoints + " matches expected value.");
    }
}
