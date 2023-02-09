package testCases.mojBrojTestCases;

import org.testng.annotations.Test;

public class MojBrojRandomGameTest extends MojBrojBaseTest {
    public MojBrojRandomGameTest() {
        super();
    }

    @Test(priority = 8)
    public void randomExpressionTest() {
        this.mojBrojPage.clickRandomNumbersAndOperators();
    }
    @Test(priority = 9)
    public void clickSubmitTest() {
        this.mojBrojPage.clickSubmitButton();
    }
}
