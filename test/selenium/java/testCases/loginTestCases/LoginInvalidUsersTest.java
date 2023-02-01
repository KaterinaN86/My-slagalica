package testCases.loginTestCases;

import org.testng.annotations.Test;
import pages.LoginPage;
import utility.CsvDataProviders;

import java.util.Map;

public class LoginInvalidUsersTest extends LoginBaseTest {

    @Test(priority = 2, dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
    public void userLoginTest(Map<String, String> testData) {
        //Data
        String no = testData.get("no");
        String username = testData.get("username");
        String password = testData.get("password");
        String msg = testData.get("expectedMessage");
        String desc = testData.get("description");
        this.loginPage = (LoginPage) this.loginPage.userLogin(no, username, password, msg, desc);
    }
}
