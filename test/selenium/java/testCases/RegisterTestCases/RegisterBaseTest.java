package testCases.RegisterTestCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.RegisterPage;

public class RegisterBaseTest extends TestBase{
    RegisterPage registerPage;

    public RegisterBaseTest() {
        super();
    }

    @BeforeClass
    public void setup() {
        init();
        this.registerPage= new RegisterPage();
    }

    @Test(priority = 1)
    public void registerPageOpenTest() {
        this.registerPage=this.registerPage.openRegisterPage();
    }

    @AfterClass
    public void tearDown() {
        close();
    }
}