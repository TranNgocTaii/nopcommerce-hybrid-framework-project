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
import reportConfigs.ExtentTestManager;

import java.lang.reflect.Method;
import java.util.Random;


public class UserSortDisplayPagingTest extends BaseTest {
    private WebDriver driver;
    private UserHomePageObject userHomePage;

    @Parameters({"browser", "environment"})
    @BeforeClass
    public void beforeClass(String browserName, String environmentName) {
        driver = getBrowserDriver(browserName, environmentName);

        userHomePage = UserPageGeneratorManager.getUserHomePage(driver);

        driver.manage().window().maximize();

        userHomePage.hoverMoveToTopMenu("Computers");

        userHomePage.clickToSecondMenuInTopMenu("Computers", "Notebooks");
    }

    @Test
    public void TC_24_Sort_With_Name_A_To_Z(Method method){
        ExtentTestManager.startTest(method.getName(),"Sort_Display_Paging_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Sort with value is A to Z");
        userHomePage.selectToDropDownByName("products-orderby", "Name: A to Z");
        userHomePage.sleepInSecond(3);

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Verify that the product name is sort by ascending");
        Assert.assertTrue(userHomePage.isProductNameSortByAscending());
    }

    @Test
    public void TC_25_Sort_With_Name_Z_To_A(Method method){
        ExtentTestManager.startTest(method.getName(),"Sort_Display_Paging_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Sort with value is Z to A");
        userHomePage.selectToDropDownByName("products-orderby", "Name: Z to A");
        userHomePage.sleepInSecond(3);

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Verify that the product name is sort by descending");
        Assert.assertTrue(userHomePage.isProductNameSortByDescending());
    }

    @Test
    public void TC_26_Sort_With_Price_Low_To_High(Method method){
        ExtentTestManager.startTest(method.getName(),"Sort_Display_Paging_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Sort with value is Low to High");
        userHomePage.selectToDropDownByName("products-orderby", "Price: Low to High");
        userHomePage.sleepInSecond(3);

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Verify that the product price is sort by ascending");
        Assert.assertTrue(userHomePage.isProductPriceSortByAscending());
    }

    @Test
    public void TC_27_Sort_With_Price_High_To_Low(Method method){
        ExtentTestManager.startTest(method.getName(),"Sort_Display_Paging_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Sort with value is High to Low");
        userHomePage.selectToDropDownByName("products-orderby", "Price: High to Low");
        userHomePage.sleepInSecond(3);

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Verify that the product price is sort by descending");
        Assert.assertTrue(userHomePage.isProductPriceSortByDescending());
    }

    @Test
    public void TC_28_Display_With_3_Product(Method method){
        ExtentTestManager.startTest(method.getName(),"Sort_Display_Paging_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Select page size with value is 3");
        userHomePage.selectToDropDownByName("products-pagesize", "3");
        userHomePage.sleepInSecond(3);

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Verify that the product number is displayed less than or equal 3");
        Assert.assertTrue(userHomePage.isProductNumberLessThanOrEqualDisplayed(3));

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Verify that the next icon is displayed");
        Assert.assertTrue(userHomePage.isNextIconDisplayed());

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 03: Click to second page");
        userHomePage.clickToSecondPage();
        userHomePage.sleepInSecond(3);

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 04: Verify that the previous icon is displayed");
        Assert.assertTrue(userHomePage.isPreviousIconDisplayed());
    }

    @Test
    public void TC_29_Display_With_6_Product(Method method){
        ExtentTestManager.startTest(method.getName(),"Sort_Display_Paging_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Select page size with value is 6");
        userHomePage.selectToDropDownByName("products-pagesize", "6");
        userHomePage.sleepInSecond(3);

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Verify that the product number is displayed less than or equal 6");
        Assert.assertTrue(userHomePage.isProductNumberLessThanOrEqualDisplayed(6));

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 03: Verify that the paging is undisplayed");
        Assert.assertTrue(userHomePage.isPagingUndisplayed());
    }

    @Test
    public void TC_30_Display_With_9_Product(Method method){
        ExtentTestManager.startTest(method.getName(),"Sort_Display_Paging_Function");
        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 01: Select page size with value is 9");
        userHomePage.selectToDropDownByName("products-pagesize", "9");
        userHomePage.sleepInSecond(3);

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 02: Verify that the product number is displayed less than or equal 9");
        Assert.assertTrue(userHomePage.isProductNumberLessThanOrEqualDisplayed(9));

        ExtentTestManager.getTest().log(Status.INFO,"Search - Step 03: Verify that the paging is undisplayed");
        Assert.assertTrue(userHomePage.isPagingUndisplayed());
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
