package testCases.mojBrojTestCases;

import org.testng.annotations.Test;

public class MojBrojExpressionsTest extends MojBrojBaseTest {
    public MojBrojExpressionsTest() {
        super();
    }

    @Test(priority = 7)
    public void userExpressionMatchesClickedNumberTest() {
        this.mojBrojPage.clickEachNumberAndDelete();
    }

    @Test(priority = 8)
    public void userExpressionMatchesClickedOperatorTest() {
        this.mojBrojPage.clickEachOperatorAndDelete();
    }

    @Test(priority = 9)
    public void submitEmptyExpressionTest() {
        this.mojBrojPage.verifyEmptyExpressionSubmit();
    }
}
