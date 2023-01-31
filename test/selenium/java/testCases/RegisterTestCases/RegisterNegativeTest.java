package testCases.RegisterTestCases;

import org.testng.annotations.Test;
import pages.RegisterPage;
import utility.CsvDataProviders;

import java.util.Map;

public class RegisterNegativeTest extends RegisterBaseTest{

    public RegisterNegativeTest(){
        super();
    }

    @Test(priority = 2, dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
    public void registerInvalidUserTest(Map<String, String> testData) {
        //Data
        String no = testData.get("no");
        String username = testData.get("username");
        String password = testData.get("password");
        String expectedMessage = testData.get("expectedMessage");
        String desc = testData.get("description");
        this.registerPage= (RegisterPage) this.registerPage.register(no, username, password, expectedMessage, desc);
    }
}
