package pageObjects.user;

import commons.BaseElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageUIs.user.UserBaseElementUI;
import pageUIs.user.UserHomePageUI;

import java.util.List;

public class UserHomePageObject extends BaseElement {
    private WebDriver driver;
    public UserHomePageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public UserProductDetailPageObject clickToTheProductByTitle(String title) {
        waitForElementClickable(UserHomePageUI.DYNAMIC_VALUE_BY_TITLE, title);
        clickToElement(UserHomePageUI.DYNAMIC_VALUE_BY_TITLE, title);
        return UserPageGeneratorManager.getUserProductDetailPage(driver);
    }

    public boolean isNextIconDisplayed() {
        waitForElementVisible(UserHomePageUI.PAGE_NUMBER, "next-page");
        return isElementDisplayed(UserHomePageUI.PAGE_NUMBER, "next-page");
    }

    public void clickToSecondPage() {
        waitForElementClickable(UserHomePageUI.PAGE_NUMBER, "individual-page");
        clickToElement(UserHomePageUI.PAGE_NUMBER, "individual-page");
    }

    public boolean isPreviousIconDisplayed() {
        waitForElementVisible(UserHomePageUI.PAGE_NUMBER, "previous-page");
        return isElementDisplayed(UserHomePageUI.PAGE_NUMBER, "previous-page");
    }

    public boolean isPagingUndisplayed() {
        List<WebElement> pageNumberList = getListWebElement(UserHomePageUI.PAGE_NUMBER_LIST);
        return isElementUndislayed(UserHomePageUI.PAGE_NUMBER_LIST);
    }
}
