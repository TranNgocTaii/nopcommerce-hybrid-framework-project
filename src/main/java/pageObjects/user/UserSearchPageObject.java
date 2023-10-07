package pageObjects.user;

import commons.BaseElement;
import commons.BasePage;
import org.openqa.selenium.WebDriver;
import pageUIs.user.UserSearchPageUI;

public class UserSearchPageObject extends BaseElement {
    private WebDriver driver;

    public UserSearchPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void clickToSearchButton() {
        waitForElementClickable(UserSearchPageUI.SEARCH_BUTTON);
        clickToElement(UserSearchPageUI.SEARCH_BUTTON);
    }

    public String getWarningMessageAtSearchKeyword() {
        waitForElementVisible(UserSearchPageUI.SEARCH_KEYWORD_WARNING_MESSAGE);
        return getElementText(UserSearchPageUI.SEARCH_KEYWORD_WARNING_MESSAGE);
    }

    public String getNoResultMessageAtSearchKeyword() {
        waitForElementVisible(UserSearchPageUI.SEARCH_KEYWORD_NO_RESULT_MESSAGE);
        return getElementText(UserSearchPageUI.SEARCH_KEYWORD_NO_RESULT_MESSAGE);
    }
}
