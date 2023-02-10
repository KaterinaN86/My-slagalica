package testCases.mojBrojTestCases;

import org.testng.annotations.Test;

public class MojBrojLimitedOperatorsTest extends MojBrojBaseTest {
    public MojBrojLimitedOperatorsTest() {
        super();
    }

    @Test(priority = 8)
    public void randomExpressionTest() {
        this.mojBrojPage.clickRandomNumbersAndOperators(true);
    }

    @Test(priority = 9)
    public void clickSubmitTest() {
        this.mojBrojPage.clickSubmitButton();
    }

    @Test(priority = 10)
    public void verifySolutionTest() {
        this.mojBrojPage.verifySolutionExpression();
    }

    @Test(priority = 11)
    public void solutionTest() {
        this.mojBrojPage.getSolutionExpression();
    }
}
