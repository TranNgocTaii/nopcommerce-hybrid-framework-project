package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageUIs.user.UserBaseElementUI;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract class BasePage {
    private WebDriver driver;
    private long longTimeout = GlobalConstants.LONG_TIMEOUT;
    private long shortTimeout = GlobalConstants.SHORT_TIMEOUT;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void openPageURL(String pageURL) {
        driver.get(pageURL);
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public void backToPage() {
        driver.navigate().back();
    }

    public void forwardToPage() {
        driver.navigate().forward();
    }

    public void refreshCurrentPage() {
        driver.navigate().refresh();
    }

    public Set<Cookie> getAllCookies(){
        return driver.manage().getCookies();
    }

    public void setCookies(Set<Cookie> cookies){
        for (Cookie cookie : cookies){
            driver.manage().addCookie(cookie);
        }
        sleepInSecond(3);
    }

    protected Alert waitForAlertPresence() {
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        return explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    protected void acceptAlert() {
        Alert alert = waitForAlertPresence();
        alert.accept();
    }

    protected void cancelAlert(WebDriver driver) {
        waitForAlertPresence().dismiss();
    }

    protected String getAlertText(WebDriver driver) {
        return waitForAlertPresence().getText();
    }

    protected void sendKeysToAlert(String textValue) {
        waitForAlertPresence().sendKeys(textValue);
    }

    protected void switchToWindowById(String windowID) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String id : allWindowIDs) {
            if (!id.contains(windowID)) {
                driver.switchTo().window(id);
                sleepInSecond(2);
            }
        }
    }

    protected void switchToWindowByPageTitle(String expectedPageTitle) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String id : allWindowIDs) {
            driver.switchTo().window(id);
            String actualPageTitle = driver.getTitle();
            if (actualPageTitle.equals(expectedPageTitle)) {
                break;
            }
        }
    }

    protected void closeAllTabWithoutParent(String parentID) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String id : allWindowIDs) {
            if (!id.equals(parentID)) {
                driver.switchTo().window(id);
                driver.close();
            }
            driver.switchTo().window(parentID);
        }
    }

    private By getByLocator(String locatorType) {
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

    private String getDynamicXpath(String locatorType, String... values){
        if(locatorType.startsWith("xpath=") || locatorType.startsWith("XPATH=") || locatorType.startsWith("Xpath=") || locatorType.startsWith("XPath=")) {
            locatorType = String.format(locatorType, (Object[]) values);
        }
        return locatorType;
    }

    private WebElement getWebElement(String locatorType){
        return driver.findElement(getByLocator(locatorType));
    }

    public List<WebElement> getListWebElement(String locatorType){
        return driver.findElements(getByLocator(locatorType));
    }

    public void clickToElement(String locatorType) {
        if (driver.toString().contains("internet explorer")) {
            clickToElementByJS(locatorType);
            sleepInSecond(3);
        } else {
            getWebElement(locatorType).click();
        }
    }

    public void clickToElement(String locatorType, String...dynamicValues) {
        if (driver.toString().contains("internet explorer")) {
            clickToElementByJS(locatorType);
            sleepInSecond(3);
        } else {
            getWebElement(getDynamicXpath(locatorType, dynamicValues)).click();
        }
    }

    public void sendKeysToElement(String locatorType, String textValue){
        WebElement element = getWebElement(locatorType);
        element.clear();
        element.sendKeys(textValue);
    }

    public void sendKeysToElement(String locatorType, String textValue, String...dynamicValues){
        WebElement element = getWebElement(getDynamicXpath(locatorType, dynamicValues));
        element.clear();
        element.sendKeys(textValue);
    }

    public void selectItemInDefaultDropdown(String locatorType, String textItem){
        Select select = new Select(getWebElement(locatorType));
        select.selectByVisibleText(textItem);
    }

    public void selectItemInDefaultDropdown(String locatorType, String textItem, String...dynamicValues){
        Select select = new Select(getWebElement(getDynamicXpath(locatorType, dynamicValues)));
        select.selectByVisibleText(textItem);
    }

    public String getFirstSelectedItem(String locatorType){
        Select select = new Select(getWebElement(locatorType));
        return select.getFirstSelectedOption().getText();
    }

    public String getFirstSelectedItem(String locatorType, String...dynamicValues){
        Select select = new Select(getWebElement(getDynamicXpath(locatorType, dynamicValues)));
        return select.getFirstSelectedOption().getText();
    }

    public String getElementAttribute(String locatorType, String attributeName){
        return getWebElement(locatorType).getAttribute(attributeName);
    }

    public String getElementAttribute(String locatorType, String attributeName, String...dynamicValues){
        return getWebElement(getDynamicXpath(locatorType, dynamicValues)).getAttribute(attributeName);
    }

    public String getElementText(String locatorType){
        return getWebElement(locatorType).getText();
    }

    public String getElementText(String locatorType, String...dynamicValues){
        return getWebElement(getDynamicXpath(locatorType, dynamicValues)).getText();
    }

    public int getElementSize(String locatorType){
        return getListWebElement(locatorType).size();
    }

    public int getElementSize(String locatorType, String...dynamicValues){
        return getListWebElement(getDynamicXpath(locatorType, dynamicValues)).size();
    }

    public void checkToDefaultCheckboxRadio(String locatorType){
        WebElement element = getWebElement(locatorType);
        if (!element.isSelected()){
            element.click();
        }
    }

    public void checkToDefaultCheckboxRadio(String locatorType, String...dynamicValues){
        WebElement element = getWebElement(getDynamicXpath(locatorType, dynamicValues));
        if (!element.isSelected()){
            element.click();
        }
    }

    public void unCheckToDefaultCheckboxRadio(String locatorType){
        WebElement element = getWebElement(locatorType);
        if (element.isSelected()){
            element.click();
        }
    }

    public void uncheckToDefaultCheckboxRadio(String locatorType, String...dynamicValues){
        WebElement element = getWebElement(getDynamicXpath(locatorType, dynamicValues));
        if (!element.isSelected()){
            element.click();
        }
    }

    public boolean isElementDisplayed(String locatorType){
        return getWebElement(locatorType).isDisplayed();
    }

    public boolean isElementDisplayed(String locatorType, String...dynamicValues){
        return getWebElement(getDynamicXpath(locatorType, dynamicValues)).isDisplayed();
    }

    public boolean isElementUndislayed(String locatorType){
        overrideImplicitTimeout(shortTimeout);
        List<WebElement> elements = getListWebElement(locatorType);
        overrideImplicitTimeout(longTimeout);

        if(elements.size()==0){
            return true;
        } else if (elements.size()>0 && !elements.get(0).isDisplayed()){
            return true;
        } else {
            return false;
        }
    }

    public boolean isElementUndislayed(String locatorType, String... dynamicValues){
        overrideImplicitTimeout(shortTimeout);
        List<WebElement> elements = getListWebElement(getDynamicXpath(locatorType, dynamicValues));
        overrideImplicitTimeout(longTimeout);

        if(elements.size()==0){
            return true;
        } else if (elements.size()>0 && !elements.get(0).isDisplayed()){
            return true;
        } else {
            return false;
        }
    }

    public boolean isElementEnabled(String locatorType){
        return getWebElement(locatorType).isEnabled();
    }

    public boolean isElementEnabled(String locatorType, String... dynamicValues){
        return getWebElement(getDynamicXpath(locatorType, dynamicValues)).isEnabled();
    }

    public boolean isElementSelected(String locatorType){
        return getWebElement(locatorType).isSelected();
    }

    public boolean isElementSelected(String locatorType, String... dynamicValues){
        return getWebElement(getDynamicXpath(locatorType, dynamicValues)).isSelected();
    }

    public void clearValueInElementByDeleteKey(String locatorType){
        WebElement element = getWebElement(locatorType);
        if (GlobalConstants.OS_NAME.equals("Windows")) {
            element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        } else if (GlobalConstants.OS_NAME.equals("Mac")) {
            element.sendKeys(Keys.chord(Keys.COMMAND, "a", Keys.DELETE));
        }
    }

    public void clearValueInElementByDeleteKey(String locatorType, String...dynamicValues){
        WebElement element = getWebElement(getDynamicXpath(locatorType, dynamicValues));
        if (GlobalConstants.OS_NAME.equals("Windows")) {
            element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        } else if (GlobalConstants.OS_NAME.equals("Mac")) {
            element.sendKeys(Keys.chord(Keys.COMMAND, "a", Keys.DELETE));
        }
    }

    public void hoverMouseToElement(String locatorType){
        Actions action = new Actions(driver);
        action.moveToElement(getWebElement(locatorType)).perform();
    }

    public void hoverMouseToElement(String locatorType, String dynamicValues){
        Actions action = new Actions(driver);
        action.moveToElement(getWebElement(getDynamicXpath(locatorType, dynamicValues))).perform();
    }

    public void pressKeyToElement(String locatorType, Keys key){
        Actions action = new Actions(driver);
        action.sendKeys(getWebElement(locatorType), key).perform();
    }

    public void pressKeyToElement(String locatorType, Keys key, String... dynamicValues){
        Actions action = new Actions(driver);
        action.sendKeys(getWebElement(getDynamicXpath(locatorType, dynamicValues)),key).perform();
    }

    public void doubleClickToElement(String locatorType){
        Actions action = new Actions(driver);
        action.doubleClick(getWebElement(locatorType)).perform();
    }

    public void doubleClickToElement(String locatorType, String...dynamicValues){
        Actions action = new Actions(driver);
        action.doubleClick(getWebElement(getDynamicXpath(locatorType, dynamicValues))).perform();
    }

    public void rightClickToElement(String locatorType){
        Actions action = new Actions(driver);
        action.contextClick(getWebElement(locatorType)).perform();
    }

    public void rightClickToElement(String locatorType, String...dynamicValues){
        Actions action = new Actions(driver);
        action.contextClick(getWebElement(getDynamicXpath(locatorType, dynamicValues))).perform();
    }

    public void highlightElement(String locatorType) {
        WebElement element = getWebElement(locatorType);
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
                "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    public void highlightElement(String locatorType, String...dynamicValues) {
        WebElement element = getWebElement(getDynamicXpath(locatorType, dynamicValues));
        String originalStyle = element.getAttribute("style");
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
                "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    public boolean isImageLoaded(String locatorType) {
        boolean status = (boolean) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                getWebElement(locatorType));
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isImageLoaded(String locatorType, String...dynamicValues) {
        boolean status = (boolean) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                getWebElement(getDynamicXpath(locatorType, dynamicValues)));
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    public void scrollToBottomPage() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void switchToDefaultContent(){
        driver.switchTo().defaultContent();
    }

    public void clickToElementByJS(String locatorType) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", getWebElement(locatorType));
    }

    public void scrollToElementByJS(String locatorType) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", getWebElement(locatorType));
    }

    public String getElementValueByJsXpath(String xpathLocator) {
        xpathLocator = xpathLocator.replace("xpath=","");
        return (String) ((JavascriptExecutor) driver).executeScript("$(document.evaluate(\"" + xpathLocator + "\", document, null, xPathResult.FIRST_ORDERER_NODE_TYPE, null).singleNodeValue).val");
    }

    public void removeAttributeInDOMByJS(String locatorType, String attributeRemove) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(locatorType));
    }

    public boolean areJQueryAndJSLoadedSuccess() {
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

    public String getElementValidationMessageByJS(String locatorType) {
        return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", getWebElement(locatorType));
    }

    public void dragAndDrop(String sourcelocatorType, String targetlocatorType){
        Actions action = new Actions(driver);
        action.dragAndDrop(getWebElement(sourcelocatorType), getWebElement(targetlocatorType)).perform();
    }

    public void sendKeysBoardToElement(String locatorType, Keys key){
        WebElement element = getWebElement(locatorType);
        Actions action = new Actions(driver);
        action.sendKeys(element, key).perform();
    }

    public boolean isDropdownMultiple(String locatorType) {
        Select select = new Select(getWebElement(locatorType));
        return select.isMultiple();
    }

    public void selectItemInCustomDropDown(String parentXpath, String childXpath, String allItemCss, String expectedTextItem){
        getWebElement(parentXpath).click();
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

    public String getElementCss(String locatorType, String propertyName){
        return getWebElement(locatorType).getCssValue(propertyName);
    }

    public String getHexaColorFromRGBA(String rgbaValue){
        return Color.fromString(rgbaValue).asHex();
    }

    public void switchToFrameIframe(String locatorType){
        driver.switchTo().frame(getWebElement(locatorType));
    }

    protected void waitForElementVisible(String locatorType){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locatorType)));
    }

    protected void waitForElementVisible(String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitForAllElementsVisible(String locatorType){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(locatorType)));
    }

    protected void waitForAllElementsVisible(String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitForElementInvisible(String locatorType){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
    }

    protected void waitForElementInvisible(String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
        overrideImplicitTimeout(shortTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
        overrideImplicitTimeout(longTimeout);
    }

    protected void waitForAllElementsInvisible(String locatorType){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(locatorType)));
    }

    protected void waitForAllElementsInvisible(String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getListWebElement(getDynamicXpath(locatorType, dynamicValues))));
    }

    protected void waitForElementUndisplayed(String locatorType){
        WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
        overrideImplicitTimeout(shortTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locatorType)));
        overrideImplicitTimeout(longTimeout);
    }

    protected void waitForElementUndisplayed(String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, shortTimeout);
        overrideImplicitTimeout(shortTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
        overrideImplicitTimeout(longTimeout);
    }

    protected void waitForElementClickable(String locatorType){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(locatorType)));
    }

    protected void waitForElementClickable(String locatorType, String...dynamicValues){
        WebDriverWait explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocator(getDynamicXpath(locatorType, dynamicValues))));
    }

    public void overrideImplicitTimeout(long timeOut){
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }

    public void uploadMultipleFiles(String... fileNames){
        String filePath = GlobalConstants.UPLOAD_FILE;
        String fullFileName ="";
        for(String file : fileNames){
            fullFileName = fullFileName + filePath + file + "\n";
        }
        fullFileName = fullFileName.trim();
        getWebElement(UserBaseElementUI.UPLOAD_FILE).sendKeys(fullFileName);
    }

}
