package testCases;

import base.TestBase;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.MojBrojPage;
import pages.SinglePlayerGamePage;

public class MojBrojPageTest extends TestBase {
    /**
     * MojBrojPage instance used in several tests.
     */
    MojBrojPage mojBrojPage;
    /**
     * SinglePlayerGamePage instance used in several tests.
     */
    SinglePlayerGamePage singlePlayerGamePage;

    HomePage homePage;

    TestBase tempPageObject;

    /**
     * Constructor
     */
    public MojBrojPageTest() {
        super();
    }

    /**
     * Method executed before the first test in the class.
     */
    @BeforeClass
    public void setup() {
        //Calling parent class init method to initialize properties and drivers.
        init();
        this.mojBrojPage = new MojBrojPage();
    }

    @Test(priority = 1)
    public void verifyOpenMojBroj() {
        this.homePage = this.loginPage.openLoginPage().userLogin("",prop.getProperty("userTestRegisterUsername"), prop.getProperty("userTestRegisterPassword"),"User "+ prop.getProperty("userAdisUsername")+ " logged in.");
        this.singlePlayerGamePage = this.homePage.clickSinglePlayerGame();
        takeSnapShot("MojBroj\\verifyOpenMojBroj", prop.getProperty("snapShotExtension"));
        this.mojBrojPage = singlePlayerGamePage.openMojBrojPage();
    }

    @Test(priority = 2)
    public void verifyTimerStart() {
        takeSnapShot("MojBroj\\verifyTimerStart", prop.getProperty("snapShotExtension"));
        this.mojBrojPage.verifyMethods.verifyTimerStartValue(prop.getProperty("mojBrojPageTimerStart"));
    }

    @Test(priority = 3)
    public void verifyMojBrojPageElements() {
        this.mojBrojPage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("mojBrojPageTitle"), prop.getProperty("mojBrojPageContainerTitle"));
    }

    @Test(priority = 4)
    public void verifyTargetNumber() {
        this.mojBrojPage.verifyTargetValueIsPositiveInteger();
    }

    @Test(priority = 5)
    public void verifyTotalNumbers() {
        this.mojBrojPage.verifyNumberButtonsTotal();
    }

    @Test(priority = 6)
    public void verifyNumbers() {
        this.mojBrojPage.verifyNumbersValues();
    }

    @Test(priority = 7)
    public void userExpressionMatchesClickedNumberTest() {
        this.mojBrojPage.clickEachNumberAndDelete();
    }

    @Test(priority = 8)
    public void GoBackToSinglePlayerPageTest() {
        this.mojBrojPage.verifyMethods.verifyBackButtonIsClickable();
        //Added step as a workaround regarding error in firefox
        this.tempPageObject = this.mojBrojPage.goBack();
    }

    @Test(priority = 9)
    public void singlePlayerPageOpenedTest() {
        //Added because of firefox bug.
        this.singlePlayerGamePage = this.mojBrojPage.firefoxWorkaround(tempPageObject);
        this.singlePlayerGamePage.verifyMethods.verifyContainerDisplayed();
        this.singlePlayerGamePage.verifyMethods.verifyButtonNotClickable(this.singlePlayerGamePage.getMojBrojBtnLoc());
    }

    @Test(priority = 10)
    public void goBackToHomePage() {
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
        this.homePage = (HomePage) this.singlePlayerGamePage.goBack();
    }

    @Test(priority = 11)
    public void logout() {
        this.homePage.logout();
        takeSnapShot("MojBroj\\logout", prop.getProperty("snapShotExtension"));
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
