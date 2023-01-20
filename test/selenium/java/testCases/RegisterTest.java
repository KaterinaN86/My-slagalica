package testCases;

import base.TestBase;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.RegisterPage;
import utility.VerifyMethods;

public class RegisterTest extends TestBase {
    RegisterPage registerPage;
    LoginPage loginPage;

    public RegisterTest() {
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

    @Test(priority = 2)
    public void registerNewUserTest(){
     this.loginPage= (LoginPage) this.registerPage.register(prop.getProperty("userTestRegisterUsername"),prop.getProperty("userTestRegisterPassword"), prop.getProperty("registerSuccessMsg"));
    }
    @Test(priority = 3)
    public void registerExistingUserTest(){
       this.registerPage=(RegisterPage) this.loginPage.clickRegister().register(prop.getProperty("userTestRegisterUsername"),prop.getProperty("userTestRegisterPassword"), prop.getProperty("registerTakenMsg"));
    }

    @Test(priority = 4)
    public void registerInvalidUserTest(){
        this.registerPage.register(prop.getProperty("userTestInvalidUsername"),prop.getProperty("userTestInvalidPassword"), prop.getProperty("registerFailMsg"));
    }

@AfterClass
    public void tearDown() {
        close();
    }
}
