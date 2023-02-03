package testCases;

import base.TestBase;
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
        this.homePage = this.loginPage.openLoginPage().userLogin("1",prop.getProperty("userAdisUsername"), prop.getProperty("userAdisPassword"), "User "+ prop.getProperty("userAdisUsername")+ " logged in.");
        verifyMethods.verifyContainerDisplayed();
        this.singlePlayerGamePage = this.homePage.clickSinglePlayerGame();
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
        this.mojBrojPage = this.singlePlayerGamePage.openMojBrojPage();
        this.mojBrojPage.verifyMethods.verifyContainerDisplayed();
    }

    @Test(priority = 4)
    public void mojBrojGoBackTest() {
        this.mojBrojPage.verifyMethods.verifyBackButtonIsClickable();
        tempPageObject = this.mojBrojPage.goBack();
    }

    /**
     * Verify One Player page is displayed. Verify button Moj Broj is disabled once user has opened Moj Broj page and navigated back to One Player page.
     */
    @Test(priority = 5)
    public void singlePlayerOpenedTest() {
        //Added because of firefox bug.
        this.singlePlayerGamePage = this.mojBrojPage.firefoxWorkaround(tempPageObject);
        takeSnapShot(this.getClass().getSimpleName()+"\\mojBrojGoBackTest", prop.getProperty("snapShotExtension"));
        //wait.until(ExpectedConditions.presenceOfElementLocated(locators.getContainerLoc()));
        this.singlePlayerGamePage.verifyMethods.verifyContainerDisplayed();
        verifyMethods.verifyButtonNotClickable(this.singlePlayerGamePage.getMojBrojBtnLoc());
    }

    @Test(priority = 6)
    public void openSlagalicaTest() {
        this.slagalicaPage = this.singlePlayerGamePage.openSlagalicaPage();
        this.slagalicaPage.verifyMethods.verifyContainerDisplayed();
        takeSnapShot(this.getClass().getSimpleName()+"\\openSlagalicaTest", prop.getProperty("snapShotExtension"));
    }

    /**
     * Verifies go back button (left pointing arrow) is clickable.
     */
    @Test(priority = 7)
    public void slagalicaGoBackTest() {
        this.slagalicaPage.verifyMethods.verifyBackButtonIsClickable();
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.slagalicaPage.goBack();
        verifyMethods.verifyButtonNotClickable(this.singlePlayerGamePage.getSlagalicaBtnLoc());
    }

    @Test(priority = 8)
    public void goBackTest() {
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
        this.homePage = (HomePage) this.singlePlayerGamePage.goBack();
        verifyMethods.verifyContainerDisplayed();
    }

    @Test(priority = 9)
    public void logout() {
        this.homePage.logout();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
