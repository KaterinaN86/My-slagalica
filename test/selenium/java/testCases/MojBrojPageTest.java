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

    @Test(priority = 0)
    public void verifyOpenMojBroj() throws Exception {
        this.singlePlayerGamePage = this.loginPage.openLoginPage().userLogin(prop.getProperty("userTestRegisterUsername"), prop.getProperty("userTestRegisterPassword")).clickSinglePlayerGame();
        wait.until(ExpectedConditions.presenceOfElementLocated(this.singlePlayerGamePage.locators.getContainerLoc()));
        takeSnapShot("MojBroj\\verifyOpenMojBroj", prop.getProperty("snapShotExtension"));
        this.mojBrojPage = singlePlayerGamePage.openMojBrojPage();
    }

    @Test(priority = 1)
    public void verifyTimerStart() throws Exception {
        wait.until(ExpectedConditions.presenceOfElementLocated(this.mojBrojPage.locators.getContainerLoc()));
        takeSnapShot("MojBroj\\verifyTimerStart", prop.getProperty("snapShotExtension"));
        this.mojBrojPage.verifyMethods.verifyTimerStartValue(prop.getProperty("mojBrojPageTimerStart"));
    }

    @Test(priority = 2)
    public void verifyMojBrojPageElements() {
        this.mojBrojPage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("mojBrojPageTitle"), prop.getProperty("mojBrojPageContainerTitle"));
    }

    @Test(priority = 3)
    public void verifyTargetNumber() {
        this.mojBrojPage.verifyTargetValueIsPositiveInteger();
    }

    @Test(priority = 4)
    public void verifyTotalNumbers() {
        this.mojBrojPage.verifyNumberButtonsTotal();
    }

    @Test(priority = 5)
    public void verifyNumbers() {
        this.mojBrojPage.verifyNumbersValues();
    }

//    @Test(priority=6)
//    public void verifyTimeIsUp() throws Exception {
//        this.mojBrojPage.verifyBadExpressionWhenTimeIsUp();
//        takeSnapShot("MojBroj\\verifyBadExpressionWhenTimeIsUp", prop.getProperty("snapShotExtension"));
//        this.mojBrojPage.wait.until(ExpectedConditions.alertIsPresent());
//    }

    @Test(priority = 7)
    public void GoBackToSinglePlayerPage() throws Exception {
        this.mojBrojPage.verifyMethods.verifyBackButtonIsClickable();
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.mojBrojPage.goBack();
        wait.until(ExpectedConditions.presenceOfElementLocated(this.singlePlayerGamePage.locators.getContainerLoc()));
    }

    @Test(priority = 8)
    public void goBackToHomePage() throws Exception {
        wait.until(ExpectedConditions.presenceOfElementLocated(this.singlePlayerGamePage.locators.getBackBtnLoc()));
        this.singlePlayerGamePage.verifyMethods.verifyBackButtonIsClickable();
        homePage = (HomePage) this.singlePlayerGamePage.goBack();
        wait.until(ExpectedConditions.presenceOfElementLocated(this.homePage.locators.getContainerLoc()));
        takeSnapShot("MojBroj\\goBackToHomePage", prop.getProperty("snapShotExtension"));
    }

    @Test(priority = 9)
    public void logout() throws Exception {
        homePage.logout();
        takeSnapShot("MojBroj\\logout", prop.getProperty("snapShotExtension"));
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}
