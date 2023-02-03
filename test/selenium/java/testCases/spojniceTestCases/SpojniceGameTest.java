package testCases.spojniceTestCases;

import org.testng.annotations.Test;

public class SpojniceGameTest extends SpojniceBaseTest{
    public SpojniceGameTest() {
        super();
    }
    @Test(priority = 6)
    public void clickSubmitBtnTest() {
        this.spojnicePage.clickSubmitBtn();
    }
    @Test(priority = 24)
    public void GoBackToSinglePlayerPageTest() {
        this.spojnicePage.verifyMethods.verifyBackButtonIsClickable();
      //Added step as workaround for bug - headline and word buttons not displayed.
        this.tempPageObject = this.spojnicePage.pickGoBackMethod();
    }
}
