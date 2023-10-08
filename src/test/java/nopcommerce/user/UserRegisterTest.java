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
import pageObjects.user.UserPageGeneratorManager;
import pageObjects.user.UserRegisterPageObject;
import reportConfigs.ExtentTestManager;
import testData.UserRegisterDataMapper;
import utilities.logs.Log;

import java.lang.reflect.Method;
import java.util.Random;
public class UserRegisterTest extends BaseTest {
    private WebDriver driver;
    private UserHomePageObject userHomePage;
    private UserRegisterPageObject userRegisterPage;
    private UserRegisterDataMapper userRegisterData;
    static String validEmail, validPassword;

    @Parameters({"browser","environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName) {
        driver = getBrowserDriver(browserName, environmentName);

        userHomePage = UserPageGeneratorManager.getUserHomePage(driver);
        userRegisterData = UserRegisterDataMapper.getUserRegisterData();

        driver.manage().window().maximize();

        validEmail = userRegisterData.getValidEmail();
        validPassword = "123456demo";

        Log.info("Pre_Condition - Step 01: Navigate to 'Register' Page");
        userRegisterPage = (UserRegisterPageObject) userHomePage.clickToLinkByClass("ico-register");
    }

    @Test
    public void TC_01_Register_With_Empty_Data(Method method){
        ExtentTestManager.startTest(method.getName(),"Register_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 01: Click to Register button");
        userRegisterPage.clickToButtonById("register-button");

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 02: Verify that the error message at first name displayed with value is 'First name is required.'");
        Assert.assertEquals(userRegisterPage.getErrorMessageById( "FirstName-error"),"First name is required.");

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 03: Verify that the error message at last name displayed with value is 'Last name is required.'");
        Assert.assertEquals(userRegisterPage.getErrorMessageById("LastName-error"),"Last name is required.");

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 04: Verify that the error message at email displayed with value is 'Email is required.'");
        Assert.assertEquals(userRegisterPage.getErrorMessageById("Email-error"),"Email is required.");

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 05: Verify that the error message at password displayed with value is 'Password is required.'");
        Assert.assertEquals(userRegisterPage.getErrorMessageById("Password-error"),"Password is required.");

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 06: Verify that the error message at confirm password displayed with value is 'Password is required.'");
        Assert.assertEquals(userRegisterPage.getErrorMessageById("ConfirmPassword-error"),"Password is required.");
    }

    @Test
    public void TC_02_Register_With_Invalid_Email(Method method) {
        ExtentTestManager.startTest(method.getName(),"Register_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 01: Input to email text box with value is '" + userRegisterData.getInvalidEmail() + "'");
        userRegisterPage.inputToTextboxById("Email" , userRegisterData.getInvalidEmail());

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 02: Verify that the error message at email displayed with value is 'Wrong email'");
        Assert.assertEquals(userRegisterPage.getErrorMessageById("Email-error"),"Wrong email");
    }

    @Test
    public void TC_03_Register_With_Password_Less_Than_Six_Character(Method method) {
        ExtentTestManager.startTest(method.getName(),"Register_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 01: Input to passoword text box with value is '" + userRegisterData.getInvalidPassword() + "'");
        userRegisterPage.inputToTextboxById("Password", userRegisterData.getInvalidPassword());

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 02: Verify that the error message at password displayed with value is 'Password must meet the following rules:\\n\"+\"must have at least 6 characters'");
        Assert.assertEquals(userRegisterPage.getErrorMessageById("Password-error"),"Password must meet the following rules:\n"+"must have at least 6 characters");
    }

    @Test
    public void TC_04_Register_With_Confirm_Password_Not_Match_Password(Method method) {
        ExtentTestManager.startTest(method.getName(),"Register_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 01: Input to confirm password text box with value is '" + userRegisterData.getInvalidConfirmPassword() + "'");
        userRegisterPage.inputToTextboxById("ConfirmPassword", userRegisterData.getInvalidConfirmPassword());

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 02: Verify that the error message at confirm password displayed with value is 'The password and confirmation password do not match.'");
        Assert.assertEquals(userRegisterPage.getErrorMessageById("ConfirmPassword-error"),"The password and confirmation password do not match.");
    }

    @Test
    public void TC_05_Register_With_Correct_Information(Method method) {
        ExtentTestManager.startTest(method.getName(),"Register_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 01: Input to first name text box with value is '" + userRegisterData.getFirstName() + "'");
        userRegisterPage.inputToTextboxById("FirstName", userRegisterData.getFirstName());

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 02: Input to last name text box with value is '" + userRegisterData.getLastName() + "'");
        userRegisterPage.inputToTextboxById("LastName", userRegisterData.getLastName());

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 03: Input to email text box with value is '" + validEmail + "'");
        userRegisterPage.inputToTextboxById("Email", validEmail);

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 04: Input to password text box with value is '" + userRegisterData.getValidPassword() + "'");
        userRegisterPage.inputToTextboxById("Password", userRegisterData.getValidPassword());

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 05: Input to confirm password text box with value is '" + userRegisterData.getValidPassword() + "'");
        userRegisterPage.inputToTextboxById("ConfirmPassword", userRegisterData.getValidPassword());

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 06: Click to Register button");
        userRegisterPage.clickToButtonById("register-button");

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 07: Verify that the error message displayed with value is 'Your registration completed'");
        Assert.assertEquals(userRegisterPage.getRegisterMessageSusscessfully(),"Your registration completed");
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
