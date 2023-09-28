package pageObjects.user;

import commons.BaseElement;
import org.openqa.selenium.WebDriver;
import pageUIs.user.UserBaseElementUI;
import pageUIs.user.UserHomePageUI;

public class UserHomePageObject extends BaseElement {
    private WebDriver driver;
    public UserHomePageObject(WebDriver driver) {
        this.driver = driver;
    }

    public UserProductDetailPageObject clickToTheProductByTitle(WebDriver driver, String title) {
        waitForElementClickable(driver, UserHomePageUI.DYNAMIC_VALUE_BY_TITLE, title);
        clickToElement(driver, UserHomePageUI.DYNAMIC_VALUE_BY_TITLE, title);
        return UserPageGeneratorManager.getUserProductDetailPage(driver);
    }
}
