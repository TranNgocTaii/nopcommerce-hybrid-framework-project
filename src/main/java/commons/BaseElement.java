package commons;

import org.openqa.selenium.WebDriver;
import pageObjects.user.UserPageGeneratorManager;
import pageUIs.user.UserBaseElementUI;

public class BaseElement extends BasePage {

    public static BaseElement getBaseLementOject() {
        return new BaseElement();
    }

    public void selectToDropDownByName(WebDriver driver, String dropdownAttributeName, String itemValue) {
        waitForElementClickable(driver, UserBaseElementUI.DYNAMIC_DROPDOWN_BY_NAME, dropdownAttributeName);
        selectItemInDefaultDropdown(driver, UserBaseElementUI.DYNAMIC_DROPDOWN_BY_NAME, itemValue, dropdownAttributeName);
    }

    public void inputToTextboxById(WebDriver driver, String attributeID, String value){
        waitForElementVisible(driver, UserBaseElementUI.DYNAMIC_TEXTBOX_BY_ID, attributeID);
        sendKeysToElement(driver, UserBaseElementUI.DYNAMIC_TEXTBOX_BY_ID, value, attributeID);
    }

    public void clickToButtonById(WebDriver driver, String attributeID){
        waitForElementVisible(driver, UserBaseElementUI.DYNAMIC_BUTTON_BY_ID, attributeID);
        clickToElement(driver, UserBaseElementUI.DYNAMIC_BUTTON_BY_ID, attributeID);
    }

    public void clickToButtonByText(WebDriver driver, String textValue) {
        waitForElementVisible(driver, UserBaseElementUI.DYNAMIC_BUTTON_BY_TEXT, textValue);
        clickToElement(driver, UserBaseElementUI.DYNAMIC_BUTTON_BY_TEXT, textValue);
    }

    public String getErrorMessageById(WebDriver driver, String attributeID) {
        waitForElementVisible(driver, UserBaseElementUI.DYNAMIC_ERROR_MESSAGE_BY_ID, attributeID);
        return getElementText(driver, UserBaseElementUI.DYNAMIC_ERROR_MESSAGE_BY_ID, attributeID);
    }



    public String getMessageByClass(WebDriver driver, String attributeClass) {
        waitForElementVisible(driver, UserBaseElementUI.DYNAMIC_MESSAGE_BY_CLASS, attributeClass);
        return getElementText(driver, UserBaseElementUI.DYNAMIC_MESSAGE_BY_CLASS, attributeClass);
    }

    public void selectToRadioButtonById(WebDriver driver, String attributeID) {
        waitForElementClickable(driver, UserBaseElementUI.DYNAMIC_RADIO_BY_ID, attributeID);
        checkToDefaultCheckboxRadio(driver, UserBaseElementUI.DYNAMIC_RADIO_BY_ID, attributeID);
    }

    public String getDynamicValueById(WebDriver driver, String attributeName,String textboxID) {
        waitForElementVisible(driver, UserBaseElementUI.DYNAMIC_TEXTBOX_BY_ID, textboxID);
        return getElementAttribute(driver, UserBaseElementUI.DYNAMIC_TEXTBOX_BY_ID, attributeName, textboxID);
    }

    public String getDynamicValueByClass(WebDriver driver, String textboxClass) {
        waitForElementVisible(driver, UserBaseElementUI.DYNAMIC_TEXTBOX_BY_CLASS, textboxClass);
        return getElementText(driver, UserBaseElementUI.DYNAMIC_TEXTBOX_BY_CLASS, textboxClass);
    }

    public void clickToCloseIcon(WebDriver driver) {
        waitForElementClickable(driver, UserBaseElementUI.CLOSE_ICON);
        clickToElement(driver, UserBaseElementUI.CLOSE_ICON);
    }

    public Object clickToLinkByClass(WebDriver driver, String attributeClass) {
        waitForElementClickable(driver, UserBaseElementUI.DYNAMIC_LINK_BY_CLASS, attributeClass);
        clickToElement(driver, UserBaseElementUI.DYNAMIC_LINK_BY_CLASS, attributeClass);
        switch (attributeClass) {
            case "ico-register":
                return UserPageGeneratorManager.getUserRegisterPage(driver);
            case "ico-login":
                return UserPageGeneratorManager.getUserLoginPage(driver);
            case "ico-account":
                return UserPageGeneratorManager.getUserMyAccountPage(driver);
            case "ico-logout":
                return UserPageGeneratorManager.getUserHomePage(driver);
            default:
                throw new RuntimeException("Invalid page name at link");
        }
    }

    public Object openPagesAtMyAccountByText(WebDriver driver, String pageName){
        waitForElementClickable(driver, UserBaseElementUI.DYNAMIC_PAGE_MY_ACCOUNT_AREA, pageName);
        clickToElement(driver, UserBaseElementUI.DYNAMIC_PAGE_MY_ACCOUNT_AREA, pageName);
        switch(pageName){
            case "Addresses":
                return UserPageGeneratorManager.getUserAddressesPage(driver);
            case "Change password":
                return UserPageGeneratorManager.getUserChangePasswordPage(driver);
            default:
                throw new RuntimeException("Invalid page name at My account area");
        }
    }

    public void hoverMoveToTopMenu(WebDriver driver, String tabName){
        waitForElementVisible(driver, UserBaseElementUI.DYNAMIC_TOP_MENU_BY_TEXT, tabName);
        switch(tabName){
            case"Computers":
                hoverMouseToElement(driver, UserBaseElementUI.DYNAMIC_TOP_MENU_BY_TEXT, tabName);
                break;
            default:
                throw new RuntimeException("Can not hoverover with invalid value");
        }
    }

    public Object clickToSecondMenuInTopMenu(WebDriver driver, String tabName, String secondTabName){
        waitForElementClickable(driver, UserBaseElementUI.DYNAMIC_SECOND_MENU_BY_TEXT, tabName, secondTabName);
        clickToElement(driver, UserBaseElementUI.DYNAMIC_SECOND_MENU_BY_TEXT, tabName, secondTabName);
        switch(secondTabName){
            case"Desktops":
            case"Notebooks":
                return UserPageGeneratorManager.getUserHomePage(driver);
            default:
                throw new RuntimeException("Can not click on second tab");
        }
    }



}