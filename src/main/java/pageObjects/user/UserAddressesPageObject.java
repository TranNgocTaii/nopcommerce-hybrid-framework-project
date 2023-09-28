package pageObjects.user;

import commons.BaseElement;
import org.openqa.selenium.WebDriver;
import pageUIs.user.UserAddressesPageUI;

public class UserAddressesPageObject extends BaseElement {
    private WebDriver driver;

    public UserAddressesPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitleAtAddressesPage() {
        waitForElementVisible(driver, UserAddressesPageUI.TITLE_TEXT);
        return getElementText(driver, UserAddressesPageUI.TITLE_TEXT);
    }
}
