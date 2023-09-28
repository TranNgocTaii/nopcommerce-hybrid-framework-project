package pageObjects.user;

import commons.BaseElement;
import org.openqa.selenium.WebDriver;
import pageUIs.user.UserLoginPageUI;

public class UserLoginPageObject extends BaseElement {
    private WebDriver driver;
    public UserLoginPageObject (WebDriver driver) {
        this.driver = driver;
    }

    public UserHomePageObject clickToLoginButton() {
        waitForElementClickable(driver, UserLoginPageUI.LOGIN_BUTTON);
        clickToElement(driver, UserLoginPageUI.LOGIN_BUTTON);
        return UserPageGeneratorManager.getUserHomePage(driver);
    }

    public String getErrorMessageAtLoginPage(WebDriver driver) {
        waitForElementVisible(driver, UserLoginPageUI.ERROR_MESSAGE);
        return getElementText(driver, UserLoginPageUI.ERROR_MESSAGE);
    }
}
