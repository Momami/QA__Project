package qa_test;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class BasketPage extends TestBefore {
    private WebDriver driver;
    private List<WebElement> checkPrice = null;

    public BasketPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Проверка на то, что доставка пока ещё не бесплатна")
    public void checkDelivery(String textDelivery) {
        // Проверка на то, что доставка пока ещё не бесплатна
        WebElement freeCome = driver.findElement(By.cssSelector("[class *= '_3EX9adn_xp']"));
        Assert.assertTrue(freeCome.getAttribute("textContent").contains(textDelivery));
    }

    private int priceProduct() {
        String priceStr = checkPrice.get(0).findElement(By.cssSelector("[data-auto*='value']"))
                .getAttribute("textContent").replace(" ", "");
        return Integer.parseInt(priceStr.substring(0, priceStr.length() - 1));
    }

    private int priceDelivery() {
        String priceStr = checkPrice.get(1).findElement(By.cssSelector("[data-auto*='value']"))
                .getAttribute("textContent").replace(" ", "");
        return priceStr.contains("бесплатно") ? 0 : Integer.parseInt(priceStr.substring(0, priceStr.length() - 1));
    }

    private int priceSale() {
        int sale = 0;
        // Если есть скидка, то достаем и ее
        if (checkPrice.size() == 4) {
            String priceStr = checkPrice.get(2).findElement(
                    By.cssSelector("[class*='_3l-uEDOaBN _180wuXkZuy _3HJsMt3YC_']"))
                    .getAttribute("textContent").replace(" ", "");
            sale = Integer.parseInt(priceStr.substring(1, priceStr.length() - 1));
        }
        return sale;
    }

    private int priceAll(int index) {
        String priceStr = checkPrice.get(index).findElement(By.cssSelector("[class*='_1oBlNqVHPq']"))
                .getAttribute("textContent").replace(" ", "");
        return Integer.parseInt(priceStr.substring(0, priceStr.length() - 1));
    }

    @Step("Проверка корректности цены")
    public void checkPriceCorrect() {
        // Добавляем элементы в список для проверки корректности текущей цены
        checkPrice = driver.findElements(By.cssSelector("[class *= '_1Q9ASvPbPN']"));
        // Вытаскиваем стоимость продукта и доставки
        int productPrice = priceProduct();
        int comePrice = priceDelivery();
        int sale = priceSale();
        int index = sale == 0 ? 2 : 3;
        int allPrice = priceAll(index);
        Assert.assertEquals(productPrice + comePrice - sale, allPrice);
    }

    @Step("Проверка на то, что доставка бесплатна")
    public void checkFreeDelivery() {
        String priceStr = checkPrice.get(1).findElement(By.cssSelector("[data-auto*='value']"))
                .getAttribute("textContent").replace(" ", "");
        Assert.assertTrue(priceStr.contains("бесплатно"));
    }

    @Step("Увеличение количества продукта")
    public void addCountProduct(int priceLimit) {
        // Добавляем кол-во щеток, пока цена не станет выше priceLimit
        String priceStr = driver.findElement(By.xpath("//div[@data-auto='CartOfferPrice']/span/span/span"))
                .getAttribute("textContent").replace(" ", "");
        int priceProduct = Integer.parseInt(priceStr);
        while (priceProduct < priceLimit) {
            System.out.println(priceProduct);
            driver.findElement(By.xpath("//button//span[text()='+']")).click();
            priceStr = driver.findElement(By.xpath("//div[@data-auto='CartOfferPrice']/span/span/span"))
                    .getAttribute("textContent").replace(" ", "");
            priceProduct = Integer.parseInt(priceStr);
        }
    }

    @Step("Проверка на то, что доставка стала бесплатной")
    public void checkFreeDeliveryTitle(String title) {
        // Ожидание изменения надписи о бесплатной доставке
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.attributeContains(
                        By.cssSelector("[class*='_3EX9adn_xp']"), "textContent", title));
        checkDelivery(title);
    }
}
