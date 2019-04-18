package qa_test;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;


public class Address {
    private WebDriver driver;
    private WebElement regionName;
    private WebElement settingsRegion;

    public Address(WebDriver driver) {
        this.driver = driver;
    }

    public void findCityInner() {
        settingsRegion = driver
                .findElement(By.cssSelector("[class*='settings-list_type_region'] [class*='__inner']"));
    }

    public void findDeliveryAddress() {
        regionName = driver.findElement(By.cssSelector("[class*='__region'] [class*='__inner']"));
    }

    public void checkAdresses() {
        Assert.assertEquals(settingsRegion.getAttribute("textContent"), regionName.getAttribute("textContent"));
    }
}
