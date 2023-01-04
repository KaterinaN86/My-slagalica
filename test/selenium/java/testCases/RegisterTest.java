package testCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.RegisterPage;

public class RegisterTest extends TestBase {
    RegisterPage registerPage;

    public RegisterTest() {
        super();
    }

    @BeforeClass
    public void setup() {
        init();
        this.registerPage = new RegisterPage();
    }

    @Test(priority = 0)
    public void registerPageOpenTest() {
       this.registerPage=this.registerPage.openRegisterPage();
    }

    @Test(priority = 1)
    public void registerNewUserTest(){
       this.registerPage=this.registerPage.register(prop.getProperty("userTestRegisterUsername"),prop.getProperty("userTestRegisterPassword"));
    }

@AfterClass
    public void tearDown() {
        close();
    }
}
