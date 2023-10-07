package pageUIs.user;

public class UserBaseElementUI {
    public static final String ADVANCED_SEARCH_CHECKBOX = "xpath=//input[@id='advs']";
    public static final String DYNAMIC_VALUE_BY_TITLE = "xpath=//div[@class='product-item']//div[@class='picture']//a[@title='%s']";
    public static final String DYNAMIC_DROPDOWN_BY_NAME = "xpath=//select[@name='%s']";
    public static final String DYNAMIC_LINK_BY_CLASS = "xpath=//div[@class='header-links']//a[@class='%s']";
    public static final String DYNAMIC_TEXTBOX_BY_ID = "xpath=//input[@id='%s']";
    public static final String DYNAMIC_BUTTON_BY_ID = "xpath=//button[@id='%s']";
    public static final String DYNAMIC_BUTTON_BY_TEXT = "xpath=//button[text()='%s']";
    public static final String DYNAMIC_ERROR_MESSAGE_BY_ID = "xpath=//span[@id='%s']";
    public static final String DYNAMIC_MESSAGE_BY_CLASS = "xpath=//p[@class='%s']";
    public static final String DYNAMIC_RADIO_BY_ID = "xpath=//input[@id='%s']";
    public static final String DYNAMIC_PAGE_MY_ACCOUNT_AREA = "xpath=//div[@class='listbox']//a[text()='%s']";
    public static final String DYNAMIC_HEADER_BY_TEXT = "xpath=//div[@class='page-title']/h1[text()='%s']";
    public static final String DYNAMIC_TEXTBOX_BY_CLASS = "xpath=//ul[@class='info']//li[@class='%s']";
    public static final String CLOSE_ICON = "xpath=//span[@title='Close']";
    public static final String DYNAMIC_TOP_MENU_BY_TEXT = "xpath=//ul[@class='top-menu notmobile']//a[contains(text(),'%s')]";
    public static final String DYNAMIC_SECOND_MENU_BY_TEXT = "xpath=//ul[@class='top-menu notmobile']//a[contains(text(),'%s')]//following-sibling::ul//a[contains(text(),'%s')]";
    public static final String DYNAMIC_VALUE_BY_TEXT = "xpath=//div[@class='footer-block customer-service']//a[text()='%s']";
    public static final String AUTOMATICALLY_SEARCH_SUB_CATEGORIES_CHECKBOX = "xpath=//input[@id='isc']";
    public static final String PRODUCT_NAME_TEXT = "xpath=//div[@class='products-container']//h2//a";
    public static final String PRODUCT_PRICE_TEXT = "xpath=//div[@class='products-container']//span";

}
