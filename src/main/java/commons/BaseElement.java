package commons;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObjects.user.UserPageGeneratorManager;
import pageUIs.user.UserBaseElementUI;
import pageUIs.user.UserHomePageUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseElement extends BasePage {
    private WebDriver driver;
    public BaseElement(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void selectToDropDownByName(String dropdownAttributeName, String itemValue) {
        waitForElementClickable(UserBaseElementUI.DYNAMIC_DROPDOWN_BY_NAME, dropdownAttributeName);
        selectItemInDefaultDropdown(UserBaseElementUI.DYNAMIC_DROPDOWN_BY_NAME, itemValue, dropdownAttributeName);
    }

    public void inputToTextboxById(String attributeID, String value){
        waitForElementVisible(UserBaseElementUI.DYNAMIC_TEXTBOX_BY_ID, attributeID);
        sendKeysToElement(UserBaseElementUI.DYNAMIC_TEXTBOX_BY_ID, value, attributeID);
    }

    public void clickToButtonById(String attributeID){
        waitForElementVisible(UserBaseElementUI.DYNAMIC_BUTTON_BY_ID, attributeID);
        clickToElement(UserBaseElementUI.DYNAMIC_BUTTON_BY_ID, attributeID);
    }

    public void clickToButtonByText(String textValue) {
        waitForElementVisible(UserBaseElementUI.DYNAMIC_BUTTON_BY_TEXT, textValue);
        clickToElement(UserBaseElementUI.DYNAMIC_BUTTON_BY_TEXT, textValue);
    }

    public boolean isTheProductDislayedByTitle(String title) {
        waitForElementClickable(UserBaseElementUI.DYNAMIC_VALUE_BY_TITLE, title);
        return isElementDisplayed(UserBaseElementUI.DYNAMIC_VALUE_BY_TITLE, title);
    }

    public String getErrorMessageById(String attributeID) {
        waitForElementVisible(UserBaseElementUI.DYNAMIC_ERROR_MESSAGE_BY_ID, attributeID);
        return getElementText(UserBaseElementUI.DYNAMIC_ERROR_MESSAGE_BY_ID, attributeID);
    }

    public String getMessageByClass(String attributeClass) {
        waitForElementVisible(UserBaseElementUI.DYNAMIC_MESSAGE_BY_CLASS, attributeClass);
        return getElementText(UserBaseElementUI.DYNAMIC_MESSAGE_BY_CLASS, attributeClass);
    }

    public void selectToRadioButtonById(String attributeID) {
        waitForElementClickable(UserBaseElementUI.DYNAMIC_RADIO_BY_ID, attributeID);
        checkToDefaultCheckboxRadio(UserBaseElementUI.DYNAMIC_RADIO_BY_ID, attributeID);
    }

    public String getDynamicValueById(String attributeName,String textboxID) {
        waitForElementVisible(UserBaseElementUI.DYNAMIC_TEXTBOX_BY_ID, textboxID);
        return getElementAttribute(UserBaseElementUI.DYNAMIC_TEXTBOX_BY_ID, attributeName, textboxID);
    }

    public String getDynamicValueByClass(String textboxClass) {
        waitForElementVisible(UserBaseElementUI.DYNAMIC_TEXTBOX_BY_CLASS, textboxClass);
        return getElementText(UserBaseElementUI.DYNAMIC_TEXTBOX_BY_CLASS, textboxClass);
    }

    public void clickToCloseIcon() {
        waitForElementClickable(UserBaseElementUI.CLOSE_ICON);
        clickToElement(UserBaseElementUI.CLOSE_ICON);
    }

    public Object clickToLinkByClass(String attributeClass) {
        waitForElementClickable(UserBaseElementUI.DYNAMIC_LINK_BY_CLASS, attributeClass);
        clickToElement(UserBaseElementUI.DYNAMIC_LINK_BY_CLASS, attributeClass);
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

    public Object openPagesAtMyAccountByText(String pageName){
        waitForElementClickable(UserBaseElementUI.DYNAMIC_PAGE_MY_ACCOUNT_AREA, pageName);
        clickToElement(UserBaseElementUI.DYNAMIC_PAGE_MY_ACCOUNT_AREA, pageName);
        switch(pageName){
            case "Addresses":
                return UserPageGeneratorManager.getUserAddressesPage(driver);
            case "Change password":
                return UserPageGeneratorManager.getUserChangePasswordPage(driver);
            default:
                throw new RuntimeException("Invalid page name at My account area");
        }
    }

    public void hoverMoveToTopMenu(String tabName){
        waitForElementVisible( UserBaseElementUI.DYNAMIC_TOP_MENU_BY_TEXT, tabName);
        switch(tabName){
            case"Computers":
                hoverMouseToElement(UserBaseElementUI.DYNAMIC_TOP_MENU_BY_TEXT, tabName);
                break;
            default:
                throw new RuntimeException("Can not hoverover with invalid value");
        }
    }

    public Object clickToSecondMenuInTopMenu(String tabName, String secondTabName){
        waitForElementClickable(UserBaseElementUI.DYNAMIC_SECOND_MENU_BY_TEXT, tabName, secondTabName);
        clickToElement(UserBaseElementUI.DYNAMIC_SECOND_MENU_BY_TEXT, tabName, secondTabName);
        switch(secondTabName){
            case"Desktops":
            case"Notebooks":
                return UserPageGeneratorManager.getUserHomePage(driver);
            default:
                throw new RuntimeException("Can not click on second tab");
        }
    }

    public Object selectOptionAtCustomerServiceFooter(String pageName){
        waitForElementClickable(UserBaseElementUI.DYNAMIC_VALUE_BY_TEXT, pageName);
        clickToElement(UserBaseElementUI.DYNAMIC_VALUE_BY_TEXT, pageName);
        switch (pageName){
            case "Search":
                return UserPageGeneratorManager.getUserSearchPage(driver);
            default:
                throw new RuntimeException("Invalid page name at footer");
        }
    }

    public boolean isProductNameSortByAscending() {
        ArrayList<String> productUIList = new ArrayList<String>();

        List<WebElement> productNameText = getListWebElement(UserBaseElementUI.PRODUCT_NAME_TEXT);

        for (WebElement productName : productNameText){
            productUIList.add(productName.getText());
        }

        ArrayList<String> productSortList = new ArrayList<String>();
        for (String product : productUIList){
            productSortList.add(product);
        }

        Collections.sort(productSortList);

        return productSortList.equals(productUIList);
    }

    public boolean isProductNameSortByDescending() {
        ArrayList<String> productUIList = new ArrayList<String>();

        List<WebElement> productNameText = getListWebElement(UserBaseElementUI.PRODUCT_NAME_TEXT);

        for (WebElement productName : productNameText){
            productUIList.add(productName.getText());
        }

        ArrayList<String> productSortList = new ArrayList<String>();
        for (String product : productUIList){
            productSortList.add(product);
        }

        Collections.sort(productSortList);

        Collections.reverse(productSortList);

        return productSortList.equals(productUIList);
    }

    public boolean isProductPriceSortByAscending() {
        ArrayList<String> productUIList = new ArrayList<String>();

        List<WebElement> productPriceText = getListWebElement(UserBaseElementUI.PRODUCT_PRICE_TEXT);

        for (WebElement productPrice : productPriceText){
            productUIList.add(String.valueOf(productPrice.getText().replace("$", "")));
        }

        ArrayList<String> productSortList = new ArrayList<String>();
        for (String product : productUIList){
            productSortList.add(product);
        }

        Collections.sort(productSortList);

        return productSortList.equals(productUIList);

    }

    public boolean isProductPriceSortByDescending() {
        ArrayList<String> productUIList = new ArrayList<String>();

        List<WebElement> productPriceText = getListWebElement(UserBaseElementUI.PRODUCT_PRICE_TEXT);

        for (WebElement productPrice : productPriceText){
            productUIList.add(String.valueOf(productPrice.getText().replace("$", "")));
        }

        ArrayList<String> productSortList = new ArrayList<String>();
        for (String product : productUIList){
            productSortList.add(product);
        }

        Collections.sort(productSortList);

        Collections.reverse(productSortList);

        return productSortList.equals(productUIList);
    }

    public boolean isProductNumberLessThanOrEqualDisplayed(int numberValue) {
        List<WebElement> productNameText = getListWebElement(UserBaseElementUI.PRODUCT_NAME_TEXT);
        waitForAllElementsVisible(UserBaseElementUI.PRODUCT_NAME_TEXT);
        if (productNameText.size()<= numberValue){
            return true;
        } else
            throw new RuntimeException("Product Number is greater than " + numberValue);
    }
}
