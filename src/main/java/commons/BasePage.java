package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BasePage {
    private long longTimeout = GlobalConstants.LONG_TIMEOUT;
    private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;

    public static BasePage getBasePageOject() {
        return new BasePage();
    }

    public void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void openPageURL(WebDriver driver, String pageURL) {
        driver.get(pageURL);
    }

    protected String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public String getPageUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    protected String getPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    public void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshCurrentPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public Set<Cookie> getAllCookies(WebDriver driver){
        return driver.manage().getCookies();
    }

    public void setCookies(WebDriver driver, Set<Cookie> cookies){
        for (Cookie cookie : cookies){
            driver.manage().addCookie(cookie);
        }
        sleepInSecond(3);
    }

    protected Alert waitForAlertPresence(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    protected void acceptAlert(WebDriver driver) {
        Alert alert = waitForAlertPresence(driver);
        alert.accept();
    }

    protected void cancelAlert(WebDriver driver) {
        waitForAlertPresence(driver).dismiss();
    }

    protected String getAlertText(WebDriver driver) {
        return waitForAlertPresence(driver).getText();
    }

    protected void sendKeysToAlert(WebDriver driver, String textValue) {
        waitForAlertPresence(driver).sendKeys(textValue);
    }



    protected void switchToWindowById(WebDriver driver, String windowID) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String id : allWindowIDs) {
            if (!id.contains(windowID)) {
                driver.switchTo().window(id);
                sleepInSecond(2);
            }
        }
    }

    protected void switchToWindowByPageTitle(WebDriver driver, String expectedPageTitle) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String id : allWindowIDs) {
            driver.switchTo().window(id);
            String actualPageTitle = driver.getTitle();
            if (actualPageTitle.equals(expectedPageTitle)) {
                break;
            }
        }
    }

    protected void closeAllTabWithoutParent(WebDriver driver, String parentID) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String id : allWindowIDs) {
            if (!id.equals(parentID)) {
                driver.switchTo().window(id);
                driver.close();
            }
            driver.switchTo().window(parentID);
        }
    }

    public By getByLocator(String locatorType) {
        By by = null;
        if (locatorType.startsWith("id=") || locatorType.startsWith("ID=") || locatorType.startsWith("Id=")) {
            by = By.id(locatorType.substring(3));
        } else if (locatorType.startsWith("xpath=") || locatorType.startsWith("XPATH=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPath=")) {
            by = By.xpath(locatorType.substring(6));
        } else if (locatorType.startsWith("name=") || locatorType.startsWith("NAME=") || locatorType.startsWith("Name=")) {
            by = By.name(locatorType.substring(5));
        } else if (locatorType.startsWith("css=") || locatorType.startsWith("CSS=") || locatorType.startsWith("Css=")) {
            by = By.cssSelector(locatorType.substring(4));
        } else if (locatorType.startsWith("class=") || locatorType.startsWith("CLASS=") || locatorType.startsWith("Class=")) {
            by = By.className(locatorType.substring(6));
        } else {
            throw new RuntimeException("Locator type is not support");
        }
        return by;
    }

    public String getDynamicXpath(String locatorType, String... values){
        if(locatorType.startsWith("xpath=") || locatorType.startsWith("XPATH=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPath=")) {
            locatorType = String.format(locatorType, (Object[]) values);
        }
        return locatorType;
    }

    public WebElement getWebElement(WebDriver driver, String locatorType){
        return driver.findElement(getByLocator(locatorType));
    }

    public List<WebElement> getListWebElement(WebDriver driver, String locatorType){
        return driver.findElements(getByLocator(locatorType));
    }

    protected void clickToElement(WebDriver driver, String locatorType) {
        if (driver.toString().contains("internet explorer")) {
            clickToElementByJS(driver, locatorType);
            sleepInSecond(3);
        } else {
            getWebElement(driver, locatorType).click();
        }
    }

    protected void clickToElement(WebDriver driver, String locatorType, String...dynamicValues) {
        if (driver.toString().contains("internet explorer")) {
            clickToElementByJS(driver, locatorType);
            sleepInSecond(3);
        } else {
            getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).click();
        }
    }

    public void sendKeysToElement(WebDriver driver, String locatorType, String textValue){
        WebElement element = getWebElement(driver, locatorType);
        element.clear();
        element.sendKeys(textValue);
    }

    public void clearValueInElementByDeleteKey(WebDriver driver, String locatorType){
        WebElement element = getWebElement(driver, locatorType);
        element.sendKeys(Keys.chord(Keys.COMMAND, "a", Keys.DELETE));
    }

    protected void sendKeysToElement(WebDriver driver, String locatorType, String textValue, String...dynamicValues){
        WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
        element.clear();
        element.sendKeys(textValue);
    }

    private void selectItemInDefaultDropdown(WebDriver driver, String locatorType, String textItem){
        Select select = new Select(getWebElement(driver,locatorType));
        select.selectByVisibleText(textItem);
    }

    protected void selectItemInDefaultDropdown(WebDriver driver, String locatorType, String textItem, String...dynamicValues){
        Select select = new Select(getWebElement(driver,getDynamicXpath(locatorType, dynamicValues)));
        select.selectByVisibleText(textItem);
    }

    public String getFirstSelectedItem(WebDriver driver, String locatorType){
        Select select = new Select(getWebElement(driver,locatorType));
        return select.getFirstSelectedOption().getText();
    }

    public String getFirstSelectedItem(WebDriver driver, String locatorType, String...dynamicValues){
        Select select = new Select(getWebElement(driver,getDynamicXpath(locatorType, dynamicValues)));
        return select.getFirstSelectedOption().getText();
    }

    protected boolean isDropdownMultiple(WebDriver driver, String locatorType) {
        Select select = new Select(getWebElement(driver,locatorType));
        return select.isMultiple();
    }

    protected void selectItemInCustomDropDown(WebDriver driver, String parentXpath, String childXpath, String allItemCss, String expectedTextItem){
        getWebElement(driver, parentXpath).click();
        sleepInSecond(1);

        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);

        List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(childXpath)));
        for (WebElement item : allItems){
            if (item.getText().trim().equals(expectedTextItem)){
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                sleepInSecond(1);
                item.click();
                break;
            }
        }
    }

    protected String getElementAttribute(WebDriver driver, String locatorType, String attributeName){
        return getWebElement(driver, locatorType).getAttribute(attributeName);
    }

    protected String getElementAttribute(WebDriver driver, String locatorType, String attributeName, String...dynamicValues){
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getAttribute(attributeName);
    }

    protected String getElementText(WebDriver driver, String locatorType){
        return getWebElement(driver, locatorType).getText();
    }

    protected String getElementText(WebDriver driver, String locatorType, String...dynamicValues){
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).getText();
    }

    protected String getElementCss(WebDriver driver, String locatorType, String propertyName){
        return getWebElement(driver, locatorType).getCssValue(propertyName);
    }

    protected String getHexaColorFromRGBA(String rgbaValue){
        return Color.fromString(rgbaValue).asHex();
    }

    protected int getElementSize(WebDriver driver, String locatorType){
        return getListWebElement(driver, locatorType).size();
    }

    protected int getElementSize(WebDriver driver, String locatorType, String...dynamicValues){
        return getListWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).size();
    }

    public void checkToDefaultCheckboxRadio(WebDriver driver, String locatorType){
        WebElement element = getWebElement(driver, locatorType);
        if (!element.isSelected()){
            element.click();
        }
    }

    public void checkToDefaultCheckboxRadio(WebDriver driver, String locatorType, String...dynamicValues){
        WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
        if (!element.isSelected()){
            element.click();
        }
    }

    public void unCheckToDefaultCheckboxRadio(WebDriver driver, String locatorType){
        WebElement element = getWebElement(driver, locatorType);
        if (element.isSelected()){
            element.click();
        }
    }

    public void uncheckToDefaultCheckboxRadio(WebDriver driver, String locatorType, String...dynamicValues){
        WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
        if (!element.isSelected()){
            element.click();
        }
    }

    public boolean isElementDisplayed(WebDriver driver, String locatorType){
        return getWebElement(driver, locatorType).isDisplayed();
    }

    public boolean isElementDisplayed(WebDriver driver, String locatorType, String...dynamicValues){
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isDisplayed();
    }

    public void overrideImplicitTimeout(WebDriver driver, long timeOut){
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }

    public boolean isElementUndislayed(WebDriver driver, String locatorType){
        overrideImplicitTimeout(driver, shortTimeout);
        List<WebElement> elements = getListWebElement(driver,locatorType);
        overrideImplicitTimeout(driver, longTimeout);

        if(elements.size()==0){
            return true;
        } else if (elements.size()>0 && !elements.get(0).isDisplayed()){
            return true;
        } else {
            return false;
        }
    }

    public boolean isElementUndislayed(WebDriver driver, String locatorType, String... dynamicValues){
        overrideImplicitTimeout(driver, shortTimeout);
        List<WebElement> elements = getListWebElement(driver,getDynamicXpath(locatorType, dynamicValues));
        overrideImplicitTimeout(driver, longTimeout);

        if(elements.size()==0){
            return true;
        } else if (elements.size()>0 && !elements.get(0).isDisplayed()){
            return true;
        } else {
            return false;
        }
    }

    protected boolean isElementEnabled(WebDriver driver, String locatorType){
        return getWebElement(driver, locatorType).isEnabled();
    }

    public boolean isElementSelected(WebDriver driver, String locatorType){
        return getWebElement(driver, locatorType).isSelected();
    }

    public boolean isElementSelected(WebDriver driver, String locatorType, String... dynamicValues){
        return getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)).isSelected();
    }

    protected void switchToFrameIframe(WebDriver driver, String locatorType){
        driver.switchTo().frame(getWebElement(driver, locatorType));
    }

    protected void switchToDefaultContent(WebDriver driver){
        driver.switchTo().defaultContent();
    }

    public void hoverMouseToElement(WebDriver driver, String locatorType){
        Actions action = new Actions(driver);
        action.moveToElement(getWebElement(driver, locatorType)).perform();
    }

    public void hoverMouseToElement(WebDriver driver, String locatorType, String dynamicValues){
        Actions action = new Actions(driver);
        action.moveToElement(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues))).perform();
    }

    protected void pressKeyToElement(WebDriver driver, String locatorType, Keys key){
        Actions action = new Actions(driver);
        action.sendKeys(getWebElement(driver, locatorType), key).perform();
    }

    protected void pressKeyToElement(WebDriver driver, String locatorType, Keys key, String... dynamicValues){
        Actions action = new Actions(driver);
        action.sendKeys(getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)),key).perform();
    }

    protected void doubleClickToElement(WebDriver driver, String locatorType){
        Actions action = new Actions(driver);
        action.doubleClick(getWebElement(driver, locatorType)).perform();
    }

    protected void rightClickToElement(WebDriver driver, String locatorType){
        Actions action = new Actions(driver);
        action.contextClick(getWebElement(driver, locatorType)).perform();
    }

    protected void dragAndDrop(WebDriver driver, String sourcelocatorType, String targetlocatorType){
        Actions action = new Actions(driver);
        action.dragAndDrop(getWebElement(driver, sourcelocatorType), getWebElement(driver, targetlocatorType)).perform();
    }

    protected void sendKeysBoardToElement(WebDriver driver, String locatorType, Keys key){
        WebElement element = getWebElement(driver, locatorType);
        Actions action = new Actions(driver);
        action.sendKeys(element, key).perform();
    }

    protected void scrollToBottomPage(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    protected void highlightElement(WebDriver driver, String locatorType) {
        WebElement element = getWebElement(driver, locatorType);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
                "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    protected void highlightElement(WebDriver driver, String locatorType, String...dynamicValues) {
        WebElement element = getWebElement(driver, getDynamicXpath(locatorType, dynamicValues));
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
                "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    protected void clickToElementByJS(WebDriver driver, String locatorType) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getWebElement(driver, locatorType));
    }

    protected void scrollToElement(WebDriver driver, String locatorType) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, locatorType));
    }

    protected String getElementValueByJsXpath(WebDriver driver, String xpathLocator) {
        xpathLocator = xpathLocator.replace("xpath=","");
        return (String) ((JavascriptExecutor) driver).executeScript("$(document.evaluate(\"" + xpathLocator + "\", document, null, xPathResult.FIRST_ORDERER_NODE_TYPE, null).singleNodeValue).val");
    }

    protected void removeAttributeInDOM(WebDriver driver, String locatorType, String attributeRemove) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, locatorType));
    }

    protected boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
    }

    protected String getElementValidationMessage(WebDriver driver, String locatorType) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", getWebElement(driver, locatorType));
    }

    protected boolean isImageLoaded(WebDriver driver, String locatorType) {
        boolean status = (boolean) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                getWebElement(driver, locatorType));
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isImageLoaded(WebDriver driver, String locatorType, String...dynamicValues) {
        boolean status = (boolean) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                getWebElement(driver, getDynamicXpath(locatorType, dynamicValues)));
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    protected void waitForElementVisible(WebDriver driver, String locatorType){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locatorType)));
    }

    protected void waitForElementVisible(WebDriver driver, String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitForAllElementsVisible(WebDriver driver, String locatorType){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locatorType)));
    }

    protected void waitForAllElementsVisible(WebDriver driver, String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitForElementInvisible(WebDriver driver, String locatorType){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
    }

    protected void waitForElementUndisplayed(WebDriver driver, String locatorType){
        WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
        overrideImplicitTimeout(driver, shortTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
        overrideImplicitTimeout(driver, longTimeout);
    }


    protected void waitForElementInvisible(WebDriver driver, String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
        overrideImplicitTimeout(driver, shortTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
        overrideImplicitTimeout(driver, longTimeout);
    }

    protected void waitForElementUndisplayed(WebDriver driver, String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
        overrideImplicitTimeout(driver, shortTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
        overrideImplicitTimeout(driver, longTimeout);
    }

    protected void waitForAllElementsInvisible(WebDriver driver, String locatorType){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(driver,locatorType)));
    }

    protected void waitForAllElementsInvisible(WebDriver driver, String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(driver,getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitForElementClickable(WebDriver driver, String locatorType){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(locatorType)));
    }

    protected void waitForElementClickable(WebDriver driver, String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

//    public void uploadMultipleFiles(WebDriver driver, String... fileNames) {
//        String filePath = GlobalConstants.UPLOAD_FILE;
//        String fullFileName = "";
//        for (String file : fileNames) {
//            fullFileName = fullFileName + filePath + file + "\n";
//        }
//        fullFileName = fullFileName.trim();
//        getWebElement(driver, .UPLOAD_FILE).sendKeys(fullFileName);
//    }

//    // Toi uu o bai hoc level_09_dynamic_locator
//
//    public Object openPagesAtMyAccountByName(WebDriver driver, String pageName){
//        waitForElementClickable(driver, BasePageUI.DYNAMIC_PAGE_MY_ACCOUNT_AREA, pageName);
//        clickToElement(driver, BasePageUI.DYNAMIC_PAGE_MY_ACCOUNT_AREA, pageName);
//        switch(pageName){
//            case "Addresses":
//                return PageGeneratorManager.getUserAddressPage(driver);
//            case "My product reviews":
//                return PageGeneratorManager.getUserMyProductReviewPage(driver);
//            case "Reward points":
//                return PageGeneratorManager.getUserRewardPointPage(driver);
//            case "Customer info":
//                return PageGeneratorManager.getUserCustomerInfoPage(driver);
//            default:
//                throw new RuntimeException("Invalid page name at My account area");
//        }
//    }

//    public UserRegisterPageObject clickToRegisterLink(WebDriver driver) {
//        waitForElementClickable(driver, BasePageUI.REGISTER_LINK);
//        clickToElement(driver, BasePageUI.REGISTER_LINK);
//        return PageGeneratorManager.getUserRegisterPage(driver);
//    }
//
//    public UserLoginPageObject clickToLoginLink(WebDriver driver) {
//        waitForElementClickable(driver, BasePageUI.LOGIN_LINK);
//        clickToElement(driver, BasePageUI.LOGIN_LINK);
//        return PageGeneratorManager.getUserLoginPage(driver);
//    }


}
