package pageObjects.user;

import commons.BaseElement;
import org.openqa.selenium.WebDriver;

public class UserChangePasswordPageObject extends BaseElement {
    private WebDriver driver;
    public UserChangePasswordPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }
}
