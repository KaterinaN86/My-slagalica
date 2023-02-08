package testCases.spojniceTestCases;

import org.testng.annotations.Test;
import pages.SinglePlayerGamePage;

public class SpojniceGameTest extends SpojniceBaseTest {

    public SpojniceGameTest() {
        super();
    }

    @Test(priority = 6)
    public void clickMatchingButtonsTest() {
        this.spojnicePage.selectSpecifiedPairs();
        //TestBase.takeSnapShot("clickMatchingButtonsTest", prop.getProperty("snapShotExtension"));
    }

    @Test(priority = 7)
    public void calculateExpectedPointsTest() {
        this.spojnicePage.calculateGamePoints();
    }

    @Test(priority = 8)
    public void clickSubmitBtnTest() {
        this.spojnicePage.clickSubmitBtn();
    }

    @Test(priority = 9)
    public void verifyPointsTest() {
        this.spojnicePage.verifyCalculatedPoints();
    }

    @Test(priority = 24)
    public void GoBackToSinglePlayerPageTest() {
        this.spojnicePage.verifyMethods.verifyBackButtonIsClickable();
        //Added step as workaround for bug - headline and word buttons not displayed.
        this.tempPageObject = this.spojnicePage.pickGoBackMethod();
    }
}
