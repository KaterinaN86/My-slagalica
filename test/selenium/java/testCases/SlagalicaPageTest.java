package testCases;

import base.TestBase;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SinglePlayerGamePage;
import pages.SlagalicaPage;
import utility.VerifyMethods;

import java.util.PriorityQueue;

public class SlagalicaPageTest extends TestBase {

    SlagalicaPage slagalicaPage;

    SinglePlayerGamePage singlePlayerGamePage;

    public SlagalicaPageTest() {
        super();
    }

    @BeforeClass
    public void setup() {
        init();
        this.slagalicaPage = new SlagalicaPage();
        verifyMethods = new VerifyMethods(this.slagalicaPage);
    }

    @Test(priority = 0)
    public void verifyOpen() {
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.loginPage.openLoginPage().userLogin(prop.getProperty("userTestRegisterUsername"),
                prop.getProperty("userTestRegisterPassword")).clickSinglePlayerGame();
        this.slagalicaPage = this.singlePlayerGamePage.openSlagalicaPage();
    }

    @Test(priority = 20)
    public void goBackAndLogOutTest() {
        wait.until(ExpectedConditions.presenceOfElementLocated(locators.getTimerLoc()));
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.slagalicaPage.goBack();
//        HomePage homePage = (HomePage) this.singlePlayerGamePage.goBack();
//        homePage.logout();
    }

    @Test(priority = 1)
    public void verifyTimerStart() {
        verifyMethods.verifyTimerStartValue(prop.getProperty("slagalicaPageTimerStart"));
    }

    @Test(priority = 3)
    public void verifyFirstList() {
        this.slagalicaPage.verifyThatFirstListIsDisplayed();
    }

    @Test(priority = 4)
    public void verifySecondList() {
        this.slagalicaPage.verifyThatSecondListIsDisplayed();
    }

    @Test(priority = 5)
    public void verifyDeleteButton() {
        this.slagalicaPage.verifyThatIzbrisiButtonIsClicked();
    }

    @Test(priority = 6)
    public void verifyStopButton() {
        this.slagalicaPage.verifyThatStopButtonIsClicked();
    }

    @Test(priority = 7)
    public void verifyPotvrdiButton() {
        this.slagalicaPage.verifyThatPotvrdiButtonIsClicked();
    }

    @Test(priority = 8)
    public void verifyDialog() {
        this.slagalicaPage.verifyPopUpDialog();
    }

    @Test(priority = 9)
    public void verifyCloseButtonClicked() {
        this.slagalicaPage.verifyCloseButtonIsClicked();
    }

    @Test(priority = 11)
    public void verifyBackButton() {
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.slagalicaPage.goBack();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}