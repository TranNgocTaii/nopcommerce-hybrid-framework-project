package pageObjects.user;

import commons.BaseElement;
import org.openqa.selenium.WebDriver;
import pageUIs.user.UserProductDetailPageUI;

public class UserProductDetailPageObject extends BaseElement {
    private WebDriver driver;
    public UserProductDetailPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public UserProductReviewPageObject clickToAddYourReview() {
        waitForElementClickable(driver, UserProductDetailPageUI.ADD_YOUR_REVIEW_LINK);
        clickToElement(driver, UserProductDetailPageUI.ADD_YOUR_REVIEW_LINK);
        return UserPageGeneratorManager.getUserProductReviewPage(driver);
    }
}
