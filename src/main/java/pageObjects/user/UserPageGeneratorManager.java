package pageObjects.user;

import org.openqa.selenium.WebDriver;

public class UserPageGeneratorManager {
    public static UserHomePageObject getUserHomePage(WebDriver driver){
        return new UserHomePageObject(driver);
    }

    public static UserLoginPageObject getUserLoginPage(WebDriver driver){
        return new UserLoginPageObject(driver);
    }

    public static UserRegisterPageObject getUserRegisterPage(WebDriver driver){
        return new UserRegisterPageObject(driver);
    }

    public static UserAddressesPageObject getUserAddressesPage(WebDriver driver) {
        return new UserAddressesPageObject(driver);
    }

    public static UserMyAccountPageObject getUserMyAccountPage(WebDriver driver) {
        return new UserMyAccountPageObject(driver);
    }

    public static UserSearchPageObject getUserSearchPage(WebDriver driver) {
        return new UserSearchPageObject(driver);
    }

    public static UserChangePasswordPageObject getUserChangePasswordPage(WebDriver driver) {
        return new UserChangePasswordPageObject(driver);
    }

    public static UserProductDetailPageObject getUserProductDetailPage(WebDriver driver) {
        return new UserProductDetailPageObject(driver);
    }

    public static UserProductReviewPageObject getUserProductReviewPage(WebDriver driver) {
        return new UserProductReviewPageObject(driver);
    }
}
