package testCases.RegisterTestCases;

import org.testng.annotations.Test;
import pages.LoginPage;
import utility.CsvDataProviders;

import java.util.Map;

public class RegisterPositiveTest extends RegisterBaseTest {

    public RegisterPositiveTest() {
        super();
    }

    @Test(priority = 2, dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
    public void registerNewUserTest(Map<String, String> testData) {
        //Data
        String no = testData.get("no");
        String username = testData.get("username");
        String password = testData.get("password");
        String expectedMessage = testData.get("expectedMessage");
        String desc = testData.get("description");
        this.registerPage.waitForElToBeClickable(locators.getRegisterBtnLoc());
        this.loginPage= (LoginPage) this.registerPage.register(no, username, password, expectedMessage, desc);
    }
}
