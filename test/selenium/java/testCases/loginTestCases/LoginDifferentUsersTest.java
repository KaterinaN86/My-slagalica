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
        String msg = testData.get("expectedMessage");
        this.homePage = this.loginPage.userLogin(no, username, password, msg);
        this.homePage.verifyMethods.verifyTitlesAndOtherPageElements(prop.getProperty("homePageTitle"), prop.getProperty("homePageContainerTitle"));
        this.homePage.verifyMethods.verifyButtonIsClickable(this.homePage.getLogOutLoc());
        this.loginPage = this.homePage.logout();
    }
}
