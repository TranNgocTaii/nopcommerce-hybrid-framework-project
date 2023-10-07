package pageObjects.user;

import commons.BaseElement;
import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.user.UserSearchPageUI;

public class UserSearchPageObject extends BaseElement {
    private WebDriver driver;

    public UserSearchPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void clickToSearchButton() {
        waitForElementClickable(driver, UserSearchPageUI.SEARCH_BUTTON);
        clickToElement(driver, UserSearchPageUI.SEARCH_BUTTON);
    }

    public String getWarningMessageAtSearchKeyword() {
        waitForElementVisible(driver, UserSearchPageUI.SEARCH_KEYWORD_WARNING_MESSAGE);
        return getElementText(driver, UserSearchPageUI.SEARCH_KEYWORD_WARNING_MESSAGE);
    }

    public String getNoResultMessageAtSearchKeyword() {
        waitForElementVisible(driver, UserSearchPageUI.SEARCH_KEYWORD_NO_RESULT_MESSAGE);
        return getElementText(driver, UserSearchPageUI.SEARCH_KEYWORD_NO_RESULT_MESSAGE);
    }
}
