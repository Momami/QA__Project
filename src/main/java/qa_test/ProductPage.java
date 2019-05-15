package qa_test;

import io.qameta.allure.Step;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class ProductPage extends TestBefore {
    private WebDriver driver;
    private List<WebElement> elements = null;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ввод левой границы ценового диапазона")
    public void enterFirstPrice(int price) {
        WebElement fieldPriceFrom = driver.findElement(By.id("glpricefrom"));
        // Вводим границы цен
        fieldPriceFrom.click();
        String priceStr = Integer.toString(price);
        fieldPriceFrom.sendKeys(priceStr);
        // Ожидание появления подсказки, сколько элементов найдено
        WebElement wind = driver.findElement(By.cssSelector("[class*='_1PQIIOelRL']"));
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(wind));
    }

    @Step("Ввод правой границы ценового диапазона")
    public void enterSecondPrice(int price) {
        WebElement fieldPriceTo = driver.findElement(By.id("glpriceto"));
        // Вводим цену во второе поле
        fieldPriceTo.click();
        String priceStr = Integer.toString(price);
        fieldPriceTo.sendKeys(priceStr);
        WebElement wind = driver.findElement(By.cssSelector("[class*='_1PQIIOelRL']"));
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.visibilityOf(wind));
    }

    @Step("Нажатие на кнопку \"Показать еще\"")
    public void showYet() {
        while (true) {
            try {
                WebElement showNewElement = driver.findElement(By.cssSelector(".n-pager-more__button"));
                showNewElement.click();
            } catch (Exception e) {
                break;
            }
        }
    }

    @Step("Проверка количества элементов на странице")
    public void checkCountItem() {
        // Находим фразу, которая содержит кол-во найденных щеток для считывания всех щеток в список
        final int countElement = Integer.parseInt(
                driver.findElement(By.cssSelector(".n-search-preciser__results-count"))
                        .getAttribute("textContent").split(" ")[1]);
        // Ждем появления всех щеток на странице
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.findElements(By
                        .cssSelector("[class*='grid-snippet_react']")).size() == countElement;
            }
        });
        elements = driver.findElements(By.cssSelector("[class*='grid-snippet_react']"));
    }

    @Step("Проверка того, что список элементов не пуст")
    public void checkEmptyListItem() {
        Assert.assertNotNull(elements);
        Assert.assertTrue(elements.size() != 0);
    }

    @Step("Проверка ценового диапазона")
    public void checkPriceInRange(int priceOne, int priceTwo) {
        // Проверка цен щеток: они должны быть в промежутке от 999 до 1999
        boolean flag = true;
        JSONParser parser = new JSONParser();
        for (WebElement element : elements) {
            try {
                // Вытаскиваем элемент data-bem, содержащий информацию о цене
                JSONObject jsonObject = (JSONObject) parser.parse(element.getAttribute("data-bem"));
                // Вытаскиваем из него цену
                int price = Integer.parseInt((((JSONObject)
                        ((JSONObject) jsonObject.get("b-zone")).get("data")).get("price")).toString());
                if (price < priceOne || price > priceTwo) {
                    flag = false;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Assert.assertTrue(flag);
    }

    @Step("Добавление элемента в корзину")
    public void addToBasket() {
        // Находим последнюю щетку и жмем на "добавить в корзину"
        elements.get(elements.size() - 2).findElement(By.cssSelector("[class*='_2w0qPDYwej']")).click();
        // Ждем, пока щетка добавится в корзину
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By
                        .cssSelector("[class*='_1sjxYfIabK _26mXJDBxtH']")));
    }

    @Step("Переход в корзину")
    public void goToBasket() {
        // Кликаем еще раз, чтобы перейти в корзину
        elements.get(elements.size() - 2).findElement(By.cssSelector("[class*='_2w0qPDYwej']")).click();
        // Ждем перехода
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class*='_3AlSA6AOKL']")));
    }
}