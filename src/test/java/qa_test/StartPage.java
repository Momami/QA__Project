package qa_test;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;


public class StartPage {
    private WebElement userButtonLog;
    private WebDriver driver;
    private WebElement regionElement;
    private String cityName;

    public StartPage(WebDriver driver) {
        this.driver = driver;
    }

    private void findButtonAccount() {
        userButtonLog = driver.findElement(By.cssSelector(".header2-nav__user"));
    }

    public void clickButtonAccount() {
        findButtonAccount();
        userButtonLog.click();
    }

    public void checkUserMenuEmail() {
        findButtonAccount();
        (new Actions(driver)).moveToElement(userButtonLog).build().perform();
        WebElement userMenuEmail = driver.findElement(By.cssSelector("[class*='user-menu__email']"));
        Assert.assertEquals(userMenuEmail.getAttribute("textContent"), "wewantGoT@yandex.ru");
    }

    public void checkTextButtonAccount() {
        findButtonAccount();
        WebElement textButtonUser = userButtonLog.findElement(By.cssSelector("[class*='__text']"));
        Assert.assertEquals(textButtonUser.getAttribute("textContent"), "Мой профиль");
    }

    public void findCityInner() {
        regionElement = driver.findElement(By.cssSelector("[class*='__region'] [class*='__inner']"));
    }

    public void clickCityInner() {
        findCityInner();
        regionElement.click();
    }

    public void changeCityName(String cityName) {
        this.cityName = cityName;
        WebElement CityForm = (new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class*='region-select-form']"))));

        driver.findElement(By.cssSelector("[class*='region-select-form']"));

        WebElement enterCity = CityForm
                .findElement(By.cssSelector("[class*='region-suggest'] [class*='input__control']"));
        enterCity.click();
        enterCity.sendKeys(cityName);

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(enterCity
                        .findElement(By.xpath("//strong[text()[contains(., \'" + cityName + "\')]]"))));

        enterCity.sendKeys(Keys.ENTER);

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.invisibilityOf(driver
                        .findElement(By.cssSelector("[class*='suggestick-list']"))));

        enterCity.sendKeys(Keys.ENTER);

        driver.navigate().refresh();
    }

    public void checkCityName() {
        findCityInner();
        Assert.assertEquals(regionElement.getAttribute("textContent"), cityName);
    }

    public void clickAddresses() {
        findButtonAccount();
        (new Actions(driver)).moveToElement(userButtonLog).build().perform();

        WebElement addresses = driver.findElement(By.cssSelector("[class*='item_type_addresses']"));
        addresses.click();
    }
}
