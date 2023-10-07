package pageObjects.user;

import commons.BaseElement;
import org.openqa.selenium.WebDriver;
import pageUIs.user.UserLoginPageUI;

public class UserLoginPageObject extends BaseElement {
    private WebDriver driver;
    public UserLoginPageObject (WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public UserHomePageObject clickToLoginButton() {
        waitForElementClickable(UserLoginPageUI.LOGIN_BUTTON);
        clickToElement(UserLoginPageUI.LOGIN_BUTTON);
        return UserPageGeneratorManager.getUserHomePage(driver);
    }

    public String getErrorMessageAtLoginPage(WebDriver driver) {
        waitForElementVisible(UserLoginPageUI.ERROR_MESSAGE);
        return getElementText(UserLoginPageUI.ERROR_MESSAGE);
    }
}
