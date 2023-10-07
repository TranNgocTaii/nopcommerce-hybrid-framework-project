package nopcommerce.user;

import com.aventstack.extentreports.Status;
import commons.BaseElement;
import commons.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.user.*;
import pageUIs.user.UserBaseElementUI;
import reportConfigs.ExtentTestManager;

import java.lang.reflect.Method;
import java.util.Random;

public class UserSearchTest extends BaseTest {
    private WebDriver driver;
    private UserHomePageObject userHomePage;
    private UserLoginPageObject userLoginPage;
    private UserSearchPageObject userSearchPage;

    @Parameters({"browser", "environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName) {
        driver = getBrowserDriver(browserName, environmentName);

        userHomePage = UserPageGeneratorManager.getUserHomePage(driver);

        driver.manage().window().maximize();

        userLoginPage = (UserLoginPageObject) userHomePage.clickToLinkByClass("ico-login");

        userLoginPage.inputToTextboxById("Email", UserRegisterTest.validEmail);

        userLoginPage.inputToTextboxById("Password", UserMyAccountTest.newPassword);

        userHomePage = userLoginPage.clickToLoginButton();
        userSearchPage = (UserSearchPageObject) userHomePage.selectOptionAtCustomerServiceFooter("Search");
    }

    @Test
    public void TC_16_Search_With_Empty_Data(Method method) {
        ExtentTestManager.startTest(method.getName(),"Search_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Verify that Search header is displayed");
        Assert.assertTrue(userSearchPage.isElementDisplayed(UserBaseElementUI.DYNAMIC_HEADER_BY_TEXT, "Search"));

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Click to search button");
        userSearchPage.clickToSearchButton();

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 03: Verify that the warning message displayed with value is Search term minimum length is 3 characters");
        Assert.assertEquals(userSearchPage.getWarningMessageAtSearchKeyword(), "Search term minimum length is 3 characters");
    }

    @Test
    public void TC_17_Search_With_Data_Not_Exist(Method method) {
        ExtentTestManager.startTest(method.getName(),"Search_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Search with value is Macbook Pro 250");
        userSearchPage.inputToTextboxById("q", "Macbook Pro 250");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Click to search button");
        userSearchPage.clickToSearchButton();

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 03: Verify that the no result message displayed with value is No products were found that matched your criteria.");
        Assert.assertEquals(userSearchPage.getNoResultMessageAtSearchKeyword(), "No products were found that matched your criteria.");
    }

    @Test
    public void TC_18_Search_With_Product_Name_Is_Lenovo(Method method) {
        ExtentTestManager.startTest(method.getName(),"Search_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Search with value is Lenovo");
        userSearchPage.inputToTextboxById("q", "Lenovo");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Click to search button");
        userSearchPage.clickToSearchButton();

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 03: Verify that the product is displayed with value is Show details for Lenovo IdeaCentre 600 All-in-One PC");
        Assert.assertTrue(userSearchPage.isTheProductDislayedByTitle("Show details for Lenovo IdeaCentre 600 All-in-One PC"));

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 04: Verify that the product is displayed with value is Show details for Lenovo Thinkpad X1 Carbon Laptop");
        Assert.assertTrue(userSearchPage.isTheProductDislayedByTitle("Show details for Lenovo Thinkpad X1 Carbon Laptop"));
    }

    @Test
    public void TC_19_Search_With_Product_Name_Is_Lenovo_Thinkpad_X1_Carbon_Laptop(Method method) {
        ExtentTestManager.startTest(method.getName(),"Search_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Search with value is Lenovo Thinkpad x1 Carbon Laptop");
        userSearchPage.inputToTextboxById("q", "Lenovo Thinkpad x1 Carbon Laptop");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Click to search button");
        userSearchPage.clickToSearchButton();

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 03: Verify that the product is displayed with value is Show details for Lenovo Thinkpad X1 Carbon Laptop");
        Assert.assertTrue(userSearchPage.isTheProductDislayedByTitle("Show details for Lenovo Thinkpad X1 Carbon Laptop"));
    }

    @Test
    public void TC_20_Advanced_Search_With_Parent_Categories(Method method) {
        ExtentTestManager.startTest(method.getName(),"Search_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Search with value is Apple Macbook Pro");
        userSearchPage.inputToTextboxById("q", "Apple Macbook Pro");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Check to Advanced search");
        userSearchPage.checkToDefaultCheckboxRadio(UserBaseElementUI.ADVANCED_SEARCH_CHECKBOX);

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 03: Verify that Advanced search is selected");
        Assert.assertTrue(userSearchPage.isElementSelected(UserBaseElementUI.ADVANCED_SEARCH_CHECKBOX));

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 04: Select Category with value is Computers");
        userSearchPage.selectToDropDownByName("cid", "Computers");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 05: Verify that the selected item is displayed");
        Assert.assertEquals(userSearchPage.getFirstSelectedItem(UserBaseElementUI.DYNAMIC_DROPDOWN_BY_NAME, "cid"), "Computers");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 06: Check Automatically search sub categories checkbox is unselected");
        Assert.assertFalse(userSearchPage.isElementSelected(UserBaseElementUI.AUTOMATICALLY_SEARCH_SUB_CATEGORIES_CHECKBOX));

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 07: Click to search button");
        userSearchPage.clickToSearchButton();

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 08: Verify that the no result message displayed with value is No products were found that matched your criteria.");
        Assert.assertEquals(userSearchPage.getNoResultMessageAtSearchKeyword(), "No products were found that matched your criteria.");
    }

    @Test
    public void TC_21_Advanced_Search_With_Sub_Categories(Method method) {
        ExtentTestManager.startTest(method.getName(),"Search_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Search with value is Apple Macbook Pro");
        userSearchPage.inputToTextboxById("q", "Apple Macbook Pro");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Verify that Advanced search is selected");
        Assert.assertTrue(userSearchPage.isElementSelected(UserBaseElementUI.ADVANCED_SEARCH_CHECKBOX));

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 03: Verify that the selected item is displayed");
        Assert.assertEquals(userSearchPage.getFirstSelectedItem(UserBaseElementUI.DYNAMIC_DROPDOWN_BY_NAME, "cid"), "Computers");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 04: Check to Automatically search sub categories");
        userSearchPage.checkToDefaultCheckboxRadio(UserBaseElementUI.AUTOMATICALLY_SEARCH_SUB_CATEGORIES_CHECKBOX);

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 05: Click to search button");
        userSearchPage.clickToSearchButton();

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 08: Verify that the product displayed with value is Show details for Apple MacBook Pro 13-inch");
        Assert.assertTrue(userSearchPage.isTheProductDislayedByTitle("Show details for Apple MacBook Pro 13-inch"));
    }

    @Test
    public void TC_22_Advanced_Search_With_Incorrect_Manufacturer(Method method) {
        ExtentTestManager.startTest(method.getName(),"Search_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Search with value is Apple Macbook Pro");
        userSearchPage.inputToTextboxById("q", "Apple Macbook Pro");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Verify that Advanced search is selected");
        Assert.assertTrue(userSearchPage.isElementSelected(UserBaseElementUI.ADVANCED_SEARCH_CHECKBOX));

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 03: Verify that the selected item is displayed");
        Assert.assertEquals(userSearchPage.getFirstSelectedItem(UserBaseElementUI.DYNAMIC_DROPDOWN_BY_NAME, "cid"), "Computers");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 04: Verify that Automatically search sub categories at is selected");
        Assert.assertTrue(userSearchPage.isElementSelected(UserBaseElementUI.AUTOMATICALLY_SEARCH_SUB_CATEGORIES_CHECKBOX));

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 05: Select Manufacturer with value is HP");
        userSearchPage.selectToDropDownByName("mid", "HP");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 06: Click to search button");
        userSearchPage.clickToSearchButton();

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 07: Verify that the no result message displayed with value is No products were found that matched your criteria.");
        Assert.assertEquals(userSearchPage.getNoResultMessageAtSearchKeyword(), "No products were found that matched your criteria.");
    }

    @Test
    public void TC_23_Advanced_Search_With_Correct_Manufacturer(Method method) {
        ExtentTestManager.startTest(method.getName(),"Search_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Search with value is Apple Macbook Pro");
        userSearchPage.inputToTextboxById("q", "Apple Macbook Pro");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Verify that Advanced search is selected");
        Assert.assertTrue(userSearchPage.isElementSelected(UserBaseElementUI.ADVANCED_SEARCH_CHECKBOX));

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 03: Verify that the selected item is displayed");
        Assert.assertEquals(userSearchPage.getFirstSelectedItem(UserBaseElementUI.DYNAMIC_DROPDOWN_BY_NAME, "cid"), "Computers");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 04: Verify that Automatically search sub categories at is selected");
        Assert.assertTrue(userSearchPage.isElementSelected(UserBaseElementUI.AUTOMATICALLY_SEARCH_SUB_CATEGORIES_CHECKBOX));

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 05: Select Manufacturer with value is Apple");
        userSearchPage.selectToDropDownByName("mid", "Apple");

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 06: Click to search button");
        userSearchPage.clickToSearchButton();

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 07: Verify that the product displayed with value is Show details for Apple MacBook Pro 13-inch");
        Assert.assertTrue(userSearchPage.isTheProductDislayedByTitle("Show details for Apple MacBook Pro 13-inch"));
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
