package testCases.asocijacija;

import base.TestBase;
import base.TestUtilities;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AsocijacijaPage;
import pages.HomePage;
import pages.SinglePlayerGamePage;
import utility.VerifyMethods;

import java.lang.reflect.Method;

public class AsocijacijaBaseTest extends TestBase {

    AsocijacijaPage asocijacijaPage;
    HomePage homePage;
    SinglePlayerGamePage singlePlayerGamePage;

    public AsocijacijaBaseTest() {
        super();
    }

    @BeforeClass
    public void setup() {
        init();
    }

    @Parameters({"no", "username", "password", "expectedMessage"})
    @Test(priority = 1)
    public void logInAndOpenTest(String no, String username, String password, String expectedMessage) {
        this.homePage = this.loginPage.openLoginPage().userLogin(no, username, password, expectedMessage);
        this.singlePlayerGamePage = this.homePage.clickSinglePlayerGame();
        this.asocijacijaPage = this.singlePlayerGamePage.openAsocijacijaPage();
    }

    @Test(priority = 2)
    public void verifyTimerStart() {
        this.asocijacijaPage.verifyMethods.verifyTimerValue(prop.getProperty("asocijacijaPageTimerStart"));
        TestUtilities.takeScreenshot("timerStart");
    }

    @Test(priority = 4)
    public void verifyPageElementsTest() {
        this.asocijacijaPage.verifyTitlesAndOtherPageElements(prop.getProperty("asocijacijaConfigTitle"), prop.getProperty("asocijacijaContainerTitle"));
    }

    @AfterClass
    public void tearDown() {
        close();
        driver.quit();
    }
}
