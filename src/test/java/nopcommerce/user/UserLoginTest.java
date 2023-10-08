package nopcommerce.user;

import com.aventstack.extentreports.Status;
import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.user.UserHomePageObject;
import pageObjects.user.UserLoginPageObject;
import pageObjects.user.UserPageGeneratorManager;
import reportConfigs.ExtentTestManager;
import testData.UserLoginDataMapper;
import testData.UserRegisterDataMapper;
import utilities.logs.Log;

import java.lang.reflect.Method;
import java.util.Random;

public class UserLoginTest extends BaseTest {
    private WebDriver driver;
    private UserHomePageObject userHomePage;
    private UserLoginPageObject userLoginPage;
    private String invalidEmail, emailNotRegister;
    private UserLoginDataMapper userLoginData;
    private UserRegisterDataMapper userRegisterData;
    @Parameters({"browser","environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName) {
        driver = getBrowserDriver(browserName, environmentName);
        userHomePage = UserPageGeneratorManager.getUserHomePage(driver);
        driver.manage().window().maximize();
        userLoginData = UserLoginDataMapper.getLoginUserData();
        userRegisterData = UserRegisterDataMapper.getUserRegisterData();

        invalidEmail = "demonopcommerce@gmail@.com";
        emailNotRegister = "demonopcommerce@gmail.com";

        Log.info("Pre_Condition - Step 01: Navigate to 'Login' Page");
        userLoginPage = (UserLoginPageObject) userHomePage.clickToLinkByClass("ico-login");
    }

    @Test
    public void TC_06_Login_With_Empty_Data(Method method) {
        ExtentTestManager.startTest(method.getName(),"Login_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 01: Click to Login button");
        userLoginPage.clickToButtonByText("Log in");

        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 02: Verify that the error message at email displayed with value is 'Please enter your email.'");
        Assert.assertEquals(userLoginPage.getErrorMessageById("Email-error"),"Please enter your email");
    }

    @Test
    public void TC_07_Login_With_Invalid_Email(Method method) {
        ExtentTestManager.startTest(method.getName(),"Login_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 01: Input to email text box with value is '" + invalidEmail + "'");
        userLoginPage.inputToTextboxById("Email", userLoginData.getInvalidEmail());

        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 02: Verify that the error message at email displayed with value is 'Wrong email'");
        Assert.assertEquals(userLoginPage.getErrorMessageById("Email-error"),"Wrong email");
    }

    @Test
    public void TC_08_Login_With_Email_Not_Register(Method method) {
        ExtentTestManager.startTest(method.getName(),"Login_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 01: Input to email text box with value is '" + userLoginData.getEmailNotRegister() + "'");
        userLoginPage.inputToTextboxById("Email", userLoginData.getEmailNotRegister());

        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 02: Click to Login button");
        userLoginPage.clickToButtonByText("Log in");

        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 03: Verify that the error message at email displayed with value is 'Login was unsuccessful. Please correct the errors and try again.\nNo customer account found.'");
        Assert.assertEquals(userLoginPage.getErrorMessageAtLoginPage(driver),"Login was unsuccessful. Please correct the errors and try again.\nNo customer account found");
    }

    @Test
    public void TC_09_Login_With_Valid_Email_And_Blank_Password(Method method) {
        ExtentTestManager.startTest(method.getName(),"Login_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 01: Input to email text box with value is '" + UserRegisterTest.validEmail + "'");
        userLoginPage.inputToTextboxById("Email", UserRegisterTest.validEmail);

        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 02: Click to Login button");
        userLoginPage.clickToButtonByText("Log in");

        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 03: Verify that the error message at email displayed with value is 'Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect.'");
        Assert.assertEquals(userLoginPage.getErrorMessageAtLoginPage(driver),"Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");
    }

    @Test
    public void TC_10_Login_With_Valid_Email_And_Invalid_Password(Method method) {
        ExtentTestManager.startTest(method.getName(),"Login_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 01: Input to email text box with value is '" + UserRegisterTest.validEmail + "'");
        userLoginPage.inputToTextboxById("Email", UserRegisterTest.validEmail);

        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 02: Input to email text box with value is '" + userRegisterData.getInvalidPassword() + "'");
        userLoginPage.inputToTextboxById("Password", userRegisterData.getInvalidPassword());

        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 02: Click to Login button");
        userLoginPage.clickToButtonByText("Log in");

        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 03: Verify that the error message at email displayed with value is 'Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect.'");
        Assert.assertEquals(userLoginPage.getErrorMessageAtLoginPage(driver),"Login was unsuccessful. Please correct the errors and try again.\nThe credentials provided are incorrect");
    }

    @Test
    public void TC_11_Login_With_Valid_Email_And_Password(Method method) {
        ExtentTestManager.startTest(method.getName(),"TC_06_Login_With_Valid_Email_And_Password");
        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 01: Input to email text box with value is '" + userRegisterData.getValidEmail() + "'");
        userLoginPage.inputToTextboxById("Email", userRegisterData.getValidEmail());

        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 02: Input to password text box with value is '" + userRegisterData.getValidPassword() + "'");
        userLoginPage.inputToTextboxById("Password", userRegisterData.getValidPassword());

        ExtentTestManager.getTest().log(Status.INFO,"Login - Step 02: Click to Login button");
        userHomePage = userLoginPage.clickToLoginButton();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass(){
        closeBrowserDriver();
    }

    public int generateFakeNumber(){
        Random rand = new Random();
        return rand.nextInt(99999);
    }
}
