package pageObjects.user;

import commons.BaseElement;
import org.openqa.selenium.WebDriver;

public class UserMyAccountPageObject extends BaseElement {
    private WebDriver driver;
    public UserMyAccountPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

}
