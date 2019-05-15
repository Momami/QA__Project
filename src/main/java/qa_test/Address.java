package qa_test;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Address extends TestBefore {
    private WebDriver driver;
    private WebElement regionName;
    private WebElement settingsRegion;

    public Address(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Поиск элемента для ввода города")
    public void findCityInner() {
        regionName = driver.findElement(By.cssSelector("[class*='__region'] [class*='__inner']"));
    }

    @Step("Поиск адреса доставки")
    public void findDeliveryAddress() {
        settingsRegion = driver.findElement(By.cssSelector("[class*='settings-list_type_region'] [class*='__inner']"));
    }

    @Step("Сравнение текущего региона и адреса доставки")
    public void checkAdresses() {
        Assert.assertEquals(settingsRegion.getAttribute("textContent"), regionName.getAttribute("textContent"));
    }
}
