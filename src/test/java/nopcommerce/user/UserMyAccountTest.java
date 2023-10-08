package nopcommerce.user;

import com.aventstack.extentreports.Status;
import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.user.*;
import pageUIs.user.UserBaseElementUI;
import pageUIs.user.UserChangePasswordPageUI;
import pageUIs.user.UserProductReviewPageUI;
import reportConfigs.ExtentTestManager;
import testData.UserMyAccountDataMapper;
import utilities.logs.Log;

import java.lang.reflect.Method;
import java.util.Random;
public class UserMyAccountTest extends BaseTest {
    private WebDriver driver;
    private UserHomePageObject userHomePage;
    private UserLoginPageObject userLoginPage;
    private UserMyAccountPageObject userMyAccountPage;
    private UserAddressesPageObject userAddressesPage;
    private UserChangePasswordPageObject userChangePasswordPage;
    private UserProductDetailPageObject userProductDetailPage;
    private UserProductReviewPageObject userProductReviewPage;
    private UserMyAccountDataMapper userMyAccountData;
    private String email, emailAddress;

    @Parameters({"browser", "environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName) {
         driver = getBrowserDriver(browserName, environmentName);

        userHomePage = UserPageGeneratorManager.getUserHomePage(driver);
        userMyAccountData = UserMyAccountDataMapper.getMyAccountUserData();
        driver.manage().window().maximize();

        email = userMyAccountData.getEmail();
        emailAddress = userMyAccountData.getEmailAddress();

        Log.info("Pre_Condition - Step 01: Navigate to 'Login' Page");
        userLoginPage = (UserLoginPageObject) userHomePage.clickToLinkByClass("ico-login");

        Log.info("Pre_Condition - Step 02: Input to email text box with value is " + UserRegisterTest.validEmail);
        userLoginPage.inputToTextboxById("Email", UserRegisterTest.validEmail);

        Log.info("Pre_Condition - Step 03: Input to password text box with value is " + UserRegisterTest.validPassword);
        userLoginPage.inputToTextboxById("Password", UserRegisterTest.validPassword);

        Log.info("Pre_Condition - Step 04: Click to Login button");
        userHomePage = userLoginPage.clickToLoginButton();
    }

    @Test
    public void TC_12_Verify_Customer_Info(Method method) {
        ExtentTestManager.startTest(method.getName(),"My_Account_Function");
        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 01: Navigate to 'My Account' Page");
        userMyAccountPage = (UserMyAccountPageObject) userHomePage.clickToLinkByClass("ico-account");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 02: Select Gender is male");
        userMyAccountPage.selectToRadioButtonById("gender-male");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 03: Input to first name text box with value is '" + userMyAccountData.getFirstName() + "'");
        userMyAccountPage.inputToTextboxById("FirstName", userMyAccountData.getFirstName());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 04: Input to last name text box with value is '" + userMyAccountData.getLastName() + "'");
        userMyAccountPage.inputToTextboxById("LastName", userMyAccountData.getLastName());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 05: Select day with value is '" + userMyAccountData.getDay() + "'");
        userMyAccountPage.selectToDropDownByName("DateOfBirthDay", userMyAccountData.getDay());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 06: Select month with value is '" + userMyAccountData.getMonth() + "'");
        userMyAccountPage.selectToDropDownByName("DateOfBirthMonth", userMyAccountData.getMonth());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 07: Select year with value is '" + userMyAccountData.getYear() + "'");
        userMyAccountPage.selectToDropDownByName("DateOfBirthYear", userMyAccountData.getYear());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 08: Input to email text box with value is '" + email + "'");
        userMyAccountPage.inputToTextboxById("Email", email);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 09: Input to company text box with value is '" + userMyAccountData.getCompanyName() + "'");
        userMyAccountPage.inputToTextboxById("Company", userMyAccountData.getCompanyName());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 10: Click to save button");
        userMyAccountPage.clickToButtonById("save-info-button");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 11: Verify that the message displayed with value is 'The customer info has been updated successfully.'");
        Assert.assertEquals(userMyAccountPage.getMessageByClass("content"), "The customer info has been updated successfully.");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 12: Verify that the gender is 'Male'");
        userMyAccountPage.isElementSelected(UserBaseElementUI.DYNAMIC_TEXTBOX_BY_ID, "gender-male");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 13: Verify that first name is '" + userMyAccountData.getFirstName() + "'");
        Assert.assertEquals(userMyAccountPage.getDynamicValueById("value", "FirstName"), userMyAccountData.getFirstName());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 14: Verify that last name is '" + userMyAccountData.getLastName() + "'");
        Assert.assertEquals(userMyAccountPage.getDynamicValueById("value", "LastName"), userMyAccountData.getLastName());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 15: Verify that email is '" + email + "'");
        Assert.assertEquals(userMyAccountPage.getDynamicValueById("value", "Email"), email);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 16: Verify that company name is '" + userMyAccountData.getCompanyName() + "'");
        Assert.assertEquals(userMyAccountPage.getDynamicValueById("value", "Company"), userMyAccountData.getCompanyName());
    }

    @Test
    public void TC_13_Verify_Address(Method method) {
        ExtentTestManager.startTest(method.getName(),"My_Account_Function");
        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 01: Navigate to 'Addresses' Page");
        userAddressesPage = (UserAddressesPageObject) userMyAccountPage.openPagesAtMyAccountByText("Addresses");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 02: Verify that the header displayed with value is 'My account - Addresses'");
        Assert.assertTrue(userAddressesPage.isElementDisplayed(UserBaseElementUI.DYNAMIC_HEADER_BY_TEXT, "My account - Addresses"));

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 03: Click to 'Add new' button");
        userAddressesPage.clickToButtonByText("Add new");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 04: Input to first name text box with value is '" + userMyAccountData.getAddressFirstName() + "'");
        userAddressesPage.inputToTextboxById("Address_FirstName", userMyAccountData.getAddressFirstName());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 05: Input to last name text box with value is '" + userMyAccountData.getAddressLastName() + "'");
        userAddressesPage.inputToTextboxById("Address_LastName", userMyAccountData.getAddressLastName());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 06: Input to email text box with value is '" + emailAddress + "'");
        userAddressesPage.inputToTextboxById("Address_Email", emailAddress);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 07: Input to company text box with value is '" + userMyAccountData.getAddressCompany() + "'");
        userAddressesPage.inputToTextboxById("Address_Company", userMyAccountData.getAddressCompany());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 08: Select country with value is '" + userMyAccountData.getCountry() + "'");
        userAddressesPage.selectToDropDownByName("Address.CountryId", userMyAccountData.getCountry());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 09: Input to address city text box with value is '" + userMyAccountData.getAddressCity() + "'");
        userAddressesPage.inputToTextboxById("Address_City", userMyAccountData.getAddressCity());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 10: Input to address1 text box with value is '" + userMyAccountData.getAddress1() + "'");
        userAddressesPage.inputToTextboxById("Address_Address1", userMyAccountData.getAddress1());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 11: Input to Zip postal code text box with value is '" + userMyAccountData.getAddressZipPostalCode() + "'");
        userAddressesPage.inputToTextboxById("Address_ZipPostalCode", userMyAccountData.getAddressZipPostalCode());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 12: Input to address phone number text box with value is '" + userMyAccountData.getPhoneNumber() + "'");
        userAddressesPage.inputToTextboxById("Address_PhoneNumber", userMyAccountData.getPhoneNumber());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 13: Input to address fax number text box with value is '" + userMyAccountData.getFaxNumber() + "'");
        userAddressesPage.inputToTextboxById("Address_FaxNumber", userMyAccountData.getFaxNumber());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 14: Click to Save button");
        userAddressesPage.clickToButtonByText("Save");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 15: Verify that the title displayed with value is 'demo nopcommerce'");
        Assert.assertEquals(userAddressesPage.getTitleAtAddressesPage(),"demo nopcommerce");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 16: Verify that the name displayed with value is '" + userMyAccountData.getName() + "'");
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass("name"), userMyAccountData.getName());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 17: Verify that the email displayed with value is '" + emailAddress + "'");
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass("email"), "Email: "+ emailAddress);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 18: Verify that the phone number displayed with value is '" + userMyAccountData.getPhoneNumber() + "'");
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass("phone"), "Phone number: " + userMyAccountData.getPhoneNumber());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 19: Verify that the fax number displayed with value is '" + userMyAccountData.getFaxNumber() + "'");
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass("fax"),"Fax number: " + userMyAccountData.getFaxNumber());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 20: Verify that the address company displayed with value is '" + userMyAccountData.getAddressCompany() + "'");
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass("company"),userMyAccountData.getAddressCompany());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 21: Verify that the address1 displayed with value is '" + userMyAccountData.getAddress1() + "'");
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass("address1"),userMyAccountData.getAddress1());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 22: Verify that the city-state-zip displayed with value is '" + userMyAccountData.getAddressCity() + ", " + userMyAccountData.getAddressZipPostalCode() + "'");
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass("city-state-zip"),userMyAccountData.getAddressCity() + ", " + userMyAccountData.getAddressZipPostalCode());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 23: Verify that the country displayed with value is '" + userMyAccountData.getCountry() + "'");
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass("country"), userMyAccountData.getCountry());
    }

    @Test
    public void TC_14_Change_Password(Method method){
        ExtentTestManager.startTest(method.getName(),"My_Account_Function");
        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 01: Navigate to 'Change password' Page");
        userChangePasswordPage = (UserChangePasswordPageObject) userAddressesPage.openPagesAtMyAccountByText("Change password");


        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 02: Verify that the header displayed with value is 'My account - Change password'");
        Assert.assertTrue(userChangePasswordPage.isElementDisplayed(UserBaseElementUI.DYNAMIC_HEADER_BY_TEXT, "My account - Change password"));

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 03: Input to old password text box with value is '" + userMyAccountData.getOldPassword() + "'");
        userChangePasswordPage.inputToTextboxById("OldPassword", userMyAccountData.getOldPassword());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 04: Input to new password text box with value is '" + userMyAccountData.getNewPassword() + "'");
        userChangePasswordPage.inputToTextboxById("NewPassword", userMyAccountData.getNewPassword());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 05: Input to confirm password text box with value is '" + userMyAccountData.getConfirmPassword() + "'");
        userChangePasswordPage.inputToTextboxById("ConfirmNewPassword", userMyAccountData.getConfirmPassword());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 06: Click to change password button");
        userChangePasswordPage.clickToButtonByText("Change password");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 07: Verify that the message displayed with value is 'Password was changed'");
        Assert.assertEquals(userChangePasswordPage.getMessageByClass( "content"), "Password was changed");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 08: Click to close icon");
        userChangePasswordPage.clickToCloseIcon();

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 09: Navigate to 'Home page' Page");
        userHomePage = (UserHomePageObject) userChangePasswordPage.clickToLinkByClass("ico-logout");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 10: Navigate to 'Log in' Page");
        userLoginPage = (UserLoginPageObject) userHomePage.clickToLinkByClass("ico-login");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 11: Input to email text box with value is '" + email + "'");
        userLoginPage.inputToTextboxById("Email", email);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 12: Input to old password text box with value is '" + userMyAccountData.getOldPassword() + "'");
        userLoginPage.inputToTextboxById("Password", userMyAccountData.getOldPassword());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 13: Click to login button");
        userLoginPage.clickToLoginButton();

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 14: Input to new password text box with value is '" + userMyAccountData.getNewPassword() + "'");
        userLoginPage.inputToTextboxById("Password", userMyAccountData.getNewPassword());

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 15: Click to login button");
        userHomePage = userLoginPage.clickToLoginButton();
    }

    @Test
    public void TC_15_My_Product_Review(Method method){
        ExtentTestManager.startTest(method.getName(),"My_Account_Function");
        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 01: Hover move to top menu");
        userChangePasswordPage.hoverMoveToTopMenu("Computers");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 02: Click to second menu in top menu");
        userHomePage = (UserHomePageObject) userChangePasswordPage.clickToSecondMenuInTopMenu("Computers", "Desktops");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 03: Navigate to 'Product detail' page");
        userProductDetailPage = userHomePage.clickToTheProductByTitle("Show details for Build your own computer");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 04: Click to add your review link");
        userProductReviewPage = userProductDetailPage.clickToAddYourReview();

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 05: Input to review title text box with value is 'Build your own computer'");
        userProductReviewPage.inputToTextboxById("AddProductReview_Title", "Build your own computer");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 06: Input to review text text box with value is 'This computer is good'");
        userProductReviewPage.inputToReviewText("This computer is good");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 07: Click to submit review button");
        userProductReviewPage.clickToButtonByText("Submit review");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 08: Verify that the review title displayed with value is 'Build your own computer'");
        Assert.assertTrue(userProductReviewPage.isElementDisplayed(UserProductReviewPageUI.REVIEW_TITLE, "Build your own computer"));

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 09: Verify that the review text displayed with value is 'This computer is good'");
        Assert.assertTrue(userProductReviewPage.isElementDisplayed(UserProductReviewPageUI.REVIEW_TEXT, "This computer is good"));
    }

    @AfterClass(alwaysRun = true)
    public void afterClass(){
        closeBrowserDriver();
    }

    public int generateFakeNumber() {
        Random rand = new Random();
        return rand.nextInt(99999);
    }
}
