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
import utilities.logs.Log;

import java.lang.reflect.Method;
import java.util.Random;
public class UserRegisterTest extends BaseTest {
    private WebDriver driver;
    private UserHomePageObject userHomePage;
    private UserRegisterPageObject userRegisterPage;
    private String invalidEmail;
    static String validEmail, validPassword, lastName, firstName, invalidPassword, invalidConfirmPassword;

    @Parameters({"browser","environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName) {
        driver = getBrowserDriver(browserName, environmentName);

        userHomePage = UserPageGeneratorManager.getUserHomePage(driver);

        driver.manage().window().maximize();

        invalidEmail = "demonopcommerce@gmail@.com";
        firstName = "demo";
        lastName = "nopcommerce";
        validEmail = "demonopcommerce" + generateFakeNumber() +"@gmail.com";
        validPassword = "123456demo";
        invalidPassword = "12345";
        invalidConfirmPassword = "1234";

        Log.info("Pre_Condition - Step 01: Navigate to 'Register' Page");
        userRegisterPage = (UserRegisterPageObject) userHomePage.clickToLinkByClass(driver, "ico-register");


    }

    @Test
    public void TC_01_Register_With_Empty_Data(Method method){
        ExtentTestManager.startTest(method.getName(),"Register_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 01: Click to Register button");
        userRegisterPage.clickToButtonById(driver, "register-button");

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 02: Verify that the error message at first name displayed with value is First name is required.");
        Assert.assertEquals(userRegisterPage.getErrorMessageById(driver, "FirstName-error"),"First name is required.");

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 03: Verify that the error message at last name displayed with value is Last name is required.");
        Assert.assertEquals(userRegisterPage.getErrorMessageById(driver, "LastName-error"),"Last name is required.");

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 04: Verify that the error message at email displayed with value is Email is required.");
        Assert.assertEquals(userRegisterPage.getErrorMessageById(driver, "Email-error"),"Email is required.");

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 05: Verify that the error message at password displayed with value is Password is required.");
        Assert.assertEquals(userRegisterPage.getErrorMessageById(driver, "Password-error"),"Password is required.");

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 06: Verify that the error message at confirm password displayed with value is Password is required.");
        Assert.assertEquals(userRegisterPage.getErrorMessageById(driver, "ConfirmPassword-error"),"Password is required.");
    }

    @Test
    public void TC_02_Register_With_Invalid_Email(Method method) {
        ExtentTestManager.startTest(method.getName(),"Register_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 01: Input to email text box with value is " + invalidEmail);
        userRegisterPage.inputToTextboxById(driver, "Email" , invalidEmail);

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 02: Verify that the error message at email displayed with value is Wrong email");
        Assert.assertEquals(userRegisterPage.getErrorMessageById(driver,"Email-error"),"Wrong email");
    }

    @Test
    public void TC_03_Register_With_Password_Less_Than_Six_Character(Method method) {
        ExtentTestManager.startTest(method.getName(),"Register_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 01: Input to passoword text box with value is " + invalidPassword);
        userRegisterPage.inputToTextboxById(driver, "Password", invalidPassword);

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 02: Verify that the error message at password displayed with value is Password must meet the following rules:\\n\"+\"must have at least 6 characters");
        Assert.assertEquals(userRegisterPage.getErrorMessageById(driver, "Password-error"),"Password must meet the following rules:\n"+"must have at least 6 characters");
    }

    @Test
    public void TC_04_Register_With_Confirm_Password_Not_Match_Password(Method method) {
        ExtentTestManager.startTest(method.getName(),"Register_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 01: Input to confirm password text box with value is " + invalidConfirmPassword);
        userRegisterPage.inputToTextboxById(driver, "ConfirmPassword", invalidConfirmPassword);

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 02: Verify that the error message at confirm password displayed with value is The password and confirmation password do not match.");
        Assert.assertEquals(userRegisterPage.getErrorMessageById(driver, "ConfirmPassword-error"),"The password and confirmation password do not match.");
    }

    @Test
    public void TC_05_Register_With_Correct_Information(Method method) {
        ExtentTestManager.startTest(method.getName(),"Register_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 01: Input to first name text box with value is " + firstName);
        userRegisterPage.inputToTextboxById(driver, "FirstName", firstName);

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 02: Input to last name text box with value is " + lastName);
        userRegisterPage.inputToTextboxById(driver, "LastName", lastName);

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 03: Input to email text box with value is " + validEmail);
        userRegisterPage.inputToTextboxById(driver, "Email", validEmail);

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 04: Input to password text box with value is " + validPassword);
        userRegisterPage.inputToTextboxById(driver, "Password", validPassword);

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 05: Input to confirm password text box with value is " + validPassword);
        userRegisterPage.inputToTextboxById(driver, "ConfirmPassword", validPassword);

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 06: Click to Register button");
        userRegisterPage.clickToButtonById(driver, "register-button");

        ExtentTestManager.getTest().log(Status.INFO,"Register - Step 07: Verify that the error message displayed with value is Your registration completed");
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
