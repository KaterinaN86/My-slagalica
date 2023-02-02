package testCases.spojniceTestCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utility.spojnice.SpojniceData;

public class SpojniceBaseTest extends TestBase {


    public SpojniceBaseTest() {
        super();
    }

    @BeforeClass
    public void setup() {
        init();
    }

    @Test(priority = 1)
    public void readTest() {
        SpojniceData spojniceData = new SpojniceData();
        spojniceData.readData();
    }

    @AfterClass
    public void tearDown() {
        close();
    }

}
