package pageObjects.user;

import commons.BaseElement;
import org.openqa.selenium.WebDriver;
import pageUIs.user.UserProductReviewPageUI;

public class UserProductReviewPageObject extends BaseElement {
    private WebDriver driver;
    public UserProductReviewPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void inputToReviewText(String textValue) {
        waitForElementVisible(UserProductReviewPageUI.REVIEW_TEXT_AREA);
        sendKeysToElement(UserProductReviewPageUI.REVIEW_TEXT_AREA, textValue);
    }
}
