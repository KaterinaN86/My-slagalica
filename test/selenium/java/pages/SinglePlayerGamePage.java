package pages;

import base.TestBase;
import org.openqa.selenium.By;
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

    public MojBrojPage openMojBrojPage(){
        Reporter.log("Click \"Moj broj\" button.");
        System.out.println("Click \"Moj broj\" button.");
        driver.findElement(mojBrojBtnLoc).click();
        return (MojBrojPage) verifyMethods.verifyPageObjectInitialized(new MojBrojPage());
    }

    public SlagalicaPage openSlagalicaPage(){
        driver.findElement(slagalicaBtnLoc).click();
        return  (SlagalicaPage) verifyMethods.verifyPageObjectInitialized(new SlagalicaPage());
    }

}
