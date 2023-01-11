package testCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SinglePlayerGamePage;
import pages.SlagalicaPage;
import utility.VerifyMethods;

public class SlagalicaPageTest extends TestBase {

    SlagalicaPage slagalicaPage;

    SinglePlayerGamePage singlePlayerGamePage;

    public SlagalicaPageTest() {
        super();
    }

    @BeforeClass
    public void setup(){
        init();
        this.slagalicaPage = new SlagalicaPage();
        verifyMethods = new VerifyMethods(this.slagalicaPage);
    }

    @Test(priority = 0)
    public void verifyOpen() {
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.loginPage.openLoginPage().userLogin(prop.getProperty("userTestRegisterUsername"),
                prop.getProperty("userTestRegisterPassword")).clickSinglePlayerGame();
        verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("singlePlayerGamePageTitle"), prop.getProperty("singlePlayerGamePageContainerTitle"));
        this.slagalicaPage = (SlagalicaPage) singlePlayerGamePage.openSlagalicaPage();
    }

    @Test
    public void goBackAndLogOutTest() {
        this.singlePlayerGamePage = (SinglePlayerGamePage) this.slagalicaPage.goBack();
        HomePage homePage = (HomePage) this.singlePlayerGamePage.goBack();
        homePage.logout();
    }

    @AfterClass
    public void tearDown() {close();}
}