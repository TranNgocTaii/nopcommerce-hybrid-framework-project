package pageObjects.user;

import commons.BaseElement;
import org.openqa.selenium.WebDriver;
import pageUIs.user.UserRegisterPageUI;

public class UserRegisterPageObject extends BaseElement {
    private WebDriver driver;
    public UserRegisterPageObject (WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public String getRegisterMessageSusscessfully() {
        waitForElementVisible(UserRegisterPageUI.SUCCESS_REGISTER_MESSAGE);
        return getElementText(UserRegisterPageUI.SUCCESS_REGISTER_MESSAGE);
    }
}
