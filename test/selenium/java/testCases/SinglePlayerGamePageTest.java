package testCases;

import base.TestBase;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.MojBrojPage;
import pages.SinglePlayerGamePage;
import pages.SlagalicaPage;

public class SinglePlayerGamePageTest extends TestBase {
    /**
     * SinglePlayerGamePage object used in tests.
     */
    SinglePlayerGamePage singlePlayerGamePage;

    MojBrojPage mojBrojPage;

    SlagalicaPage slagalicaPage;

    /**
     * Constructor
     */
    public SinglePlayerGamePageTest() {
        super();
    }

    /**
     * Method executed before the first test in the class.
     */
    @BeforeClass
    public void setup() {
        //Calling parent class init method to initialize properties and drivers.
        init();
        this.singlePlayerGamePage = new SinglePlayerGamePage();
    }

    /**
     * Open login page, login with user adis (user needs to be registered first), click on button for single player game and verify elements on single player game page after open.
     */
    @Test(priority = 0)
    public void verifyOpen() {
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.loginPage.openLoginPage().userLogin(prop.getProperty("userAdisUsername"), prop.getProperty("userAdisPassword")).clickSinglePlayerGame();
    }

    /**
     * Verifies page elements and titles. Verifies active links number.
     */
    @Test(priority = 1)
    public void verifyElementsAndLinks() {
        this.singlePlayerGamePage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("singlePlayerGamePageTitle"), prop.getProperty("singlePlayerGamePageContainerTitle"));
        this.singlePlayerGamePage.verifyMethods.verifyValidLinkNumber(this.getValidLinkNumber(), Integer.parseInt(prop.getProperty("singlePlayerGamePageLinksNumber")));
    }

    /**
     * Open 'Moj Broj' page and go back to SinglePlayerGamePage.
     */
    @Test(priority = 2)
    public void openMojBrojTest() {
        this.mojBrojPage = this.singlePlayerGamePage.openMojBrojPage();
        wait.until(ExpectedConditions.presenceOfElementLocated(this.mojBrojPage.locators.getContainerLoc()));
        this.mojBrojPage.verifyMethods.verifyContainerDisplayed();

    }

    @Test(priority = 3)
    public void mojBrojGoBackTest() {
        wait.until(ExpectedConditions.presenceOfElementLocated(this.mojBrojPage.locators.getBackBtnLoc()));
        this.mojBrojPage.verifyMethods.verifyBackButtonIsClickable();
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.mojBrojPage.goBack();
        wait.until(ExpectedConditions.presenceOfElementLocated(this.singlePlayerGamePage.locators.getContainerLoc()));
        this.singlePlayerGamePage.verifyMojBrojBtnNotClickable();
    }

    @Test(priority = 5)
    public void openSlagalicaTest() throws Exception {
        this.slagalicaPage = this.singlePlayerGamePage.openSlagalicaPage();
        wait.until(ExpectedConditions.presenceOfElementLocated(this.slagalicaPage.locators.getContainerLoc()));
        this.slagalicaPage.verifyMethods.verifyContainerDisplayed();
    }

    /**
     * Verifies go back button (left pointing arrow) is clickable.
     */
    @Test(priority = 6)
    public void slagalicaGoBackTest() {
        wait.until(ExpectedConditions.presenceOfElementLocated(this.slagalicaPage.locators.getBackBtnLoc()));
        this.slagalicaPage.verifyMethods.verifyBackButtonIsClickable();
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.slagalicaPage.goBack();
        wait.until(ExpectedConditions.presenceOfElementLocated(this.singlePlayerGamePage.getSlagalicaBtnLoc()));
        this.singlePlayerGamePage.verifySlagalicaButtonIsNotClickable();
    }

    @Test(priority = 7)
    public void goBackTest() throws Exception {
        wait.until(ExpectedConditions.presenceOfElementLocated(this.singlePlayerGamePage.locators.getBackBtnLoc()));
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
        this.singlePlayerGamePage.goBack();
        takeSnapShot("OnePlayer\\goBackTest",prop.getProperty("snapShotExtension"));
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
