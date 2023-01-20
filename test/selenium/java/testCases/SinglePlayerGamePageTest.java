package testCases;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MojBrojPage;
import pages.SinglePlayerGamePage;
import pages.SlagalicaPage;

public class SinglePlayerGamePageTest extends TestBase {
    /**
     * Objects for pages used in tests.
     */
    SinglePlayerGamePage singlePlayerGamePage;

    MojBrojPage mojBrojPage;

    SlagalicaPage slagalicaPage;

    HomePage homePage;

    TestBase tempPageObject;

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
    @Test(priority = 1)
    public void verifyOpen() {
        this.singlePlayerGamePage = this.loginPage.openLoginPage().userLogin(prop.getProperty("userAdisUsername"), prop.getProperty("userAdisPassword")).clickSinglePlayerGame();
    }

    /**
     * Verifies page elements and titles. Verifies active links number.
     */
    @Test(priority = 2)
    public void verifyElementsAndLinks() {
        this.singlePlayerGamePage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("singlePlayerGamePageTitle"), prop.getProperty("singlePlayerGamePageContainerTitle"));
        this.singlePlayerGamePage.verifyMethods.verifyValidLinkNumber(this.getValidLinkNumber(), Integer.parseInt(prop.getProperty("singlePlayerGamePageLinksNumber")));
    }

    @Test(priority = 3)
    public void openMojBrojTest() {
        wait.until(ExpectedConditions.presenceOfElementLocated(this.singlePlayerGamePage.getMojBrojBtnLoc()));
        this.mojBrojPage = this.singlePlayerGamePage.openMojBrojPage();
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getContainerLoc()));
        this.mojBrojPage.verifyMethods.verifyContainerDisplayed();
    }

    @Test(priority = 4)
    public void mojBrojGoBackTest() {
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getBackBtnLoc()));
        this.mojBrojPage.verifyMethods.verifyBackButtonIsClickable();
        tempPageObject = this.mojBrojPage.goBack();
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getContainerLoc()));
    }

    /**
     * Verify One Player page is displayed. Verify button Moj Broj is disabled once user has opened Moj Broj page and navigated back to One Player page.
     */
    @Test(priority = 5)
    public void singlePlayerOpenedTest() {
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getContainerLoc()));
        this.tempPageObject.verifyMethods.verifyContainerDisplayed();
        //Added because of firefox bug.
        this.singlePlayerGamePage = this.mojBrojPage.firefoxWorkaround(tempPageObject);
        takeSnapShot("OnePlayer\\mojBrojGoBackTest", prop.getProperty("snapShotExtension"));
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getContainerLoc()));
        wait.until(ExpectedConditions.presenceOfElementLocated(this.singlePlayerGamePage.getMojBrojBtnLoc()));
        this.singlePlayerGamePage.verifyMojBrojBtnNotClickable();
    }

    @Test(priority = 6)
    public void openSlagalicaTest(){
        wait.until(ExpectedConditions.presenceOfElementLocated(this.singlePlayerGamePage.getNewGameBtnLoc()));
        this.slagalicaPage = this.singlePlayerGamePage.openSlagalicaPage();
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getContainerLoc()));
        this.slagalicaPage.verifyMethods.verifyContainerDisplayed();
        takeSnapShot("OnePlayer\\openSlagalicaTest",prop.getProperty("snapShotExtension"));
    }

    /**
     * Verifies go back button (left pointing arrow) is clickable.
     */
    @Test(priority = 7)
    public void slagalicaGoBackTest() {
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getBackBtnLoc()));
        this.slagalicaPage.verifyMethods.verifyBackButtonIsClickable();
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.slagalicaPage.goBack();
        wait.until(ExpectedConditions.presenceOfElementLocated(this.singlePlayerGamePage.getSlagalicaBtnLoc()));
        this.singlePlayerGamePage.verifySlagalicaButtonIsNotClickable();
    }

    @Test(priority = 8)
    public void goBackTest() {
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getBackBtnLoc()));
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
        this.homePage = (HomePage) this.singlePlayerGamePage.goBack();
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getContainerLoc()));

    }

    @Test(priority = 9)
    public void logout(){
       wait.until(ExpectedConditions.presenceOfElementLocated(this.homePage.getLogOutLoc()));
       this.homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
