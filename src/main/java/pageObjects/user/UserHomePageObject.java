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
        this.driver = driver;
    }

    public UserProductDetailPageObject clickToTheProductByTitle(WebDriver driver, String title) {
        waitForElementClickable(driver, UserHomePageUI.DYNAMIC_VALUE_BY_TITLE, title);
        clickToElement(driver, UserHomePageUI.DYNAMIC_VALUE_BY_TITLE, title);
        return UserPageGeneratorManager.getUserProductDetailPage(driver);
    }

    public boolean isNextIconDisplayed() {
        waitForElementVisible(driver, UserHomePageUI.PAGE_NUMBER, "next-page");
        return isElementDisplayed(driver, UserHomePageUI.PAGE_NUMBER, "next-page");
    }

    public void clickToSecondPage() {
        waitForElementClickable(driver, UserHomePageUI.PAGE_NUMBER, "individual-page");
        clickToElement(driver, UserHomePageUI.PAGE_NUMBER, "individual-page");
    }

    public boolean isPreviousIconDisplayed() {
        waitForElementVisible(driver, UserHomePageUI.PAGE_NUMBER, "previous-page");
        return isElementDisplayed(driver, UserHomePageUI.PAGE_NUMBER, "previous-page");
    }

    public boolean isPagingUndisplayed() {
        List<WebElement> pageNumberList = getListWebElement(driver, UserHomePageUI.PAGE_NUMBER_LIST);
        return isElementUndislayed(driver, UserHomePageUI.PAGE_NUMBER_LIST);
    }
}
