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
    private String firstName, lastName, email, companyName, emailAddress;
    private String name, phoneNumber, faxNumber, addressCompany, address1, addressCity, country, addressZipPostalCode, addressFirstName, addressLastName;
    private String oldPassword, newPassword, confirmPassword;
    private String day, month, year;
    @Parameters({"browser", "url"})
    @BeforeClass
    public void beforeClass(String browserName, String appURL) {
        driver = getBrowserDriver(browserName, appURL);

        userHomePage = UserPageGeneratorManager.getUserHomePage(driver);
        driver.manage().window().maximize();

        firstName = "DEMO";
        lastName = "NOPCOMMERCE";
        email = "demonopcommerce"+generateFakeNumber()+"@gmail.com";
        emailAddress = "demonopcommerce"+generateFakeNumber()+"@gmail.com";
        companyName = "nopcommerce company";

        addressFirstName = "demo";
        addressLastName ="nopcommerce";
        name = addressFirstName + " " + addressLastName;
        phoneNumber = "0123456789";
        faxNumber = "0987654321";
        addressCompany = "demonopcommerce company";
        address1 = "100 Su Van Hanh, Quan 10";
        addressCity = "Ho Chi Minh";
        addressZipPostalCode = "550000";
        country = "Viet Nam";

        oldPassword = "123456demo";
        newPassword = "123456nopcommerce";
        confirmPassword = "123456nopcommerce";

        day = "21";
        month = "June";
        year = "2001";

        Log.info("Pre_Condition - Step 01: Navigate to 'Login' Page");
        userLoginPage = (UserLoginPageObject) userHomePage.clickToLinkByClass(driver, "ico-login");

        Log.info("Pre_Condition - Step 02: Input to email text box with value is " + UserRegisterTest.validEmail);
        userLoginPage.inputToTextboxById(driver, "Email", UserRegisterTest.validEmail);

        Log.info("Pre_Condition - Step 03: Input to password text box with value is " + UserRegisterTest.validPassword);
        userLoginPage.inputToTextboxById(driver, "Password", UserRegisterTest.validPassword);

        Log.info("Pre_Condition - Step 04: Click to Login button");
        userHomePage = userLoginPage.clickToLoginButton();
    }

    @Test
    public void TC_12_Verify_Customer_Info(Method method) {
        ExtentTestManager.startTest(method.getName(),"My_Account_Function");
        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 01: Navigate to 'My Account' Page");
        userMyAccountPage = (UserMyAccountPageObject) userHomePage.clickToLinkByClass(driver, "ico-account");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 02: Select Gender is male");
        userMyAccountPage.selectToRadioButtonById(driver, "gender-male");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 03: Input to first name text box with value is " + firstName);
        userMyAccountPage.inputToTextboxById(driver, "FirstName", firstName);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 04: Input to last name text box with value is " + lastName);
        userMyAccountPage.inputToTextboxById(driver, "LastName", lastName);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 05: Select day with value is " + day);
        userMyAccountPage.selectToDropDownByName(driver, "DateOfBirthDay", day);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 06: Select month with value is " + month);
        userMyAccountPage.selectToDropDownByName(driver, "DateOfBirthMonth", month);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 07: Select year with value is " + year);
        userMyAccountPage.selectToDropDownByName(driver, "DateOfBirthYear", year);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 08: Input to email text box with value is " + email);
        userMyAccountPage.inputToTextboxById(driver, "Email", email);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 09: Input to company text box with value is " + companyName);
        userMyAccountPage.inputToTextboxById(driver, "Company", companyName);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 10: Click to save button");
        userMyAccountPage.clickToButtonById(driver, "save-info-button");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 11: Verify that the message displayed with value is The customer info has been updated successfully.");
        Assert.assertEquals(userMyAccountPage.getMessageByClass(driver, "content"), "The customer info has been updated successfully.");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 12: Verify that the gender is Male");
        userMyAccountPage.isElementSelected(driver, UserBaseElementUI.DYNAMIC_TEXTBOX_BY_ID, "gender-male");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 13: Verify that first name is " + firstName);
        Assert.assertEquals(userMyAccountPage.getDynamicValueById(driver, "value", "FirstName"), firstName);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 14: Verify that last name is " + lastName);
        Assert.assertEquals(userMyAccountPage.getDynamicValueById(driver, "value", "LastName"), lastName);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 15: Verify that email is " + email);
        Assert.assertEquals(userMyAccountPage.getDynamicValueById(driver, "value", "Email"), email);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 16: Verify that company name is " + companyName);
        Assert.assertEquals(userMyAccountPage.getDynamicValueById(driver, "value", "Company"), companyName);
    }

    @Test
    public void TC_13_Verify_Address(Method method) {
        ExtentTestManager.startTest(method.getName(),"My_Account_Function");
        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 01: Navigate to 'Addresses' Page");
        userAddressesPage = (UserAddressesPageObject) userMyAccountPage.openPagesAtMyAccountByText(driver, "Addresses");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 02: Verify that the header displayed with value is My account - Addresses");
        Assert.assertTrue(userAddressesPage.isElementDisplayed(driver, UserBaseElementUI.DYNAMIC_HEADER_BY_TEXT, "My account - Addresses"));

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 03: Click to 'Add new' button");
        userAddressesPage.clickToButtonByText(driver, "Add new");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 04: Input to first name text box with value is " + addressFirstName);
        userAddressesPage.inputToTextboxById(driver, "Address_FirstName", addressFirstName);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 05: Input to last name text box with value is " + addressLastName);
        userAddressesPage.inputToTextboxById(driver, "Address_LastName", addressLastName);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 06: Input to email text box with value is " + emailAddress);
        userAddressesPage.inputToTextboxById(driver, "Address_Email", emailAddress);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 07: Input to company text box with value is " + addressCompany);
        userAddressesPage.inputToTextboxById(driver, "Address_Company", addressCompany);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 08: Select country with value is " + country);
        userAddressesPage.selectToDropDownByName(driver, "Address.CountryId", country);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 09: Input to address city text box with value is " + addressCity);
        userAddressesPage.inputToTextboxById(driver, "Address_City", addressCity);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 10: Input to address1 text box with value is " + address1);
        userAddressesPage.inputToTextboxById(driver, "Address_Address1", address1);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 11: Input to Zip postal code text box with value is " + addressZipPostalCode);
        userAddressesPage.inputToTextboxById(driver, "Address_ZipPostalCode", addressZipPostalCode);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 12: Input to address phone number text box with value is " + phoneNumber);
        userAddressesPage.inputToTextboxById(driver, "Address_PhoneNumber", phoneNumber);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 13: Input to address fax number text box with value is " + faxNumber);
        userAddressesPage.inputToTextboxById(driver, "Address_FaxNumber", faxNumber);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 14: Click to Save button");
        userAddressesPage.clickToButtonByText(driver, "Save");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 15: Verify that the title displayed with value is demo nopcommerce");
        Assert.assertEquals(userAddressesPage.getTitleAtAddressesPage(),"demo nopcommerce");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 16: Verify that the name displayed with value is " + name);
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass(driver,"name"), name);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 17: Verify that the email displayed with value is " + emailAddress);
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass(driver,"email"), "Email: "+ emailAddress);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 18: Verify that the phone number displayed with value is " + phoneNumber);
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass(driver,"phone"), "Phone number: " + phoneNumber);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 19: Verify that the fax number displayed with value is " + faxNumber);
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass(driver,"fax"),"Fax number: " + faxNumber);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 20: Verify that the address company displayed with value is " + addressCompany);
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass(driver,"company"),addressCompany);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 21: Verify that the address1 displayed with value is " + address1);
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass(driver,"address1"),address1);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 22: Verify that the city-state-zip displayed with value is " + addressCity + ", " + addressZipPostalCode);
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass(driver,"city-state-zip"),addressCity + ", " + addressZipPostalCode);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 23: Verify that the country displayed with value is " + country);
        Assert.assertEquals(userAddressesPage.getDynamicValueByClass(driver,"country"),country);
    }

    @Test
    public void TC_14_Change_Password(Method method){
        ExtentTestManager.startTest(method.getName(),"My_Account_Function");
        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 01: Navigate to 'Change password' Page");
        userChangePasswordPage = (UserChangePasswordPageObject) userAddressesPage.openPagesAtMyAccountByText(driver, "Change password");


        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 02: Verify that the header displayed with value is My account - Change password");
        Assert.assertTrue(userChangePasswordPage.isElementDisplayed(driver, UserBaseElementUI.DYNAMIC_HEADER_BY_TEXT, "My account - Change password"));

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 03: Input to old password text box with value is " + oldPassword);
        userChangePasswordPage.inputToTextboxById(driver, "OldPassword", oldPassword);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 04: Input to new password text box with value is " + newPassword);
        userChangePasswordPage.inputToTextboxById(driver, "NewPassword", newPassword);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 05: Input to confirm password text box with value is " + confirmPassword);
        userChangePasswordPage.inputToTextboxById(driver, "ConfirmNewPassword", confirmPassword);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 06: Click to change password button");
        userChangePasswordPage.clickToButtonByText(driver, "Change password");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 07: Verify that the message displayed with value is Password was changed");
        Assert.assertEquals(userChangePasswordPage.getMessageByClass(driver, "content"), "Password was changed");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 08: Click to close icon");
        userChangePasswordPage.clickToCloseIcon(driver);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 09: Navigate to 'Home page' Page");
        userHomePage = (UserHomePageObject) userChangePasswordPage.clickToLinkByClass(driver, "ico-logout");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 10: Navigate to 'Log in' Page");
        userLoginPage = (UserLoginPageObject) userHomePage.clickToLinkByClass(driver, "ico-login");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 11: Input to email text box with value is " + email);
        userLoginPage.inputToTextboxById(driver, "Email", email);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 12: Input to old password text box with value is " + oldPassword);
        userLoginPage.inputToTextboxById(driver, "Password", oldPassword);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 13: Click to login button");
        userLoginPage.clickToLoginButton();

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 14: Input to new password text box with value is " + newPassword);
        userLoginPage.inputToTextboxById(driver, "Password", newPassword);

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 15: Click to login button");
        userHomePage = userLoginPage.clickToLoginButton();
    }

    @Test
    public void TC_15_My_Product_Review(Method method){
        ExtentTestManager.startTest(method.getName(),"My_Account_Function");
        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 01: Hover move to top menu");
        userChangePasswordPage.hoverMoveToTopMenu(driver, "Computers");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 02: Click to second menu in top menu");
        userHomePage = (UserHomePageObject) userChangePasswordPage.clickToSecondMenuInTopMenu(driver, "Computers", "Desktops");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 03: Navigate to 'Product detail' page");
        userProductDetailPage = userHomePage.clickToTheProductByTitle(driver, "Show details for Build your own computer");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 04: Click to add your review link");
        userProductReviewPage = userProductDetailPage.clickToAddYourReview();

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 05: Input to review title text box with value is Build your own computer");
        userProductReviewPage.inputToTextboxById(driver, "AddProductReview_Title", "Build your own computer");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 06: Input to review text text box with value is This computer is good");
        userProductReviewPage.inputToReviewText("This computer is good");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 07: Click to submit review button");
        userProductReviewPage.clickToButtonByText(driver, "Submit review");

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 08: Verify that the review title displayed with value is Build your own computer");
        Assert.assertTrue(userProductReviewPage.isElementDisplayed(driver, UserProductReviewPageUI.REVIEW_TITLE, "Build your own computer"));

        ExtentTestManager.getTest().log(Status.INFO,"My_Account - Step 09: Verify that the review text displayed with value is This computer is good");
        Assert.assertTrue(userProductReviewPage.isElementDisplayed(driver, UserProductReviewPageUI.REVIEW_TEXT, "This computer is good"));
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
