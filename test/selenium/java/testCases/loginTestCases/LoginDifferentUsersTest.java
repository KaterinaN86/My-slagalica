package testCases.loginTestCases;

import org.testng.annotations.Test;
import utility.CsvDataProviders;

import java.util.Map;

public class LoginDifferentUsersTest extends LoginBaseTest {

    @Test(priority = 2, dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
    public void userLoginTest(Map<String, String> testData) {
        //Data
        String no = testData.get("no");
        String username = testData.get("username");
        String password = testData.get("password");
        String desc = testData.get("description");
        this.homePage = this.loginPage.userLogin(no, username, password, desc);
    }

    @Test(priority = 3)
    public void verifyStateAfterLogin() {
        this.homePage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("homePageTitle"), prop.getProperty("homePageContainerTitle"));
    }

}
