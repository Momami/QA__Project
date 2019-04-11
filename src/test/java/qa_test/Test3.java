package qa_test;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class Test3 extends TestBefore{

    @Test
    public void test3() {
        // Находим электрические зубные щетки и переходим на страницу
        WebElement fieldForSearch = findWithTakeScreen(driver, By.id("header-search"));
        fieldForSearch.click();
        fieldForSearch.sendKeys("Электрические зубные щетки");
        fieldForSearch.sendKeys(Keys.ENTER);

        // Ищем на странице поля для фильтрации цен
        WebElement fieldPriceFrom = findWithTakeScreen(driver, By.id("glpricefrom")),
                fieldPriceTo = findWithTakeScreen(driver, By.id("glpriceto"));

        // Вводим границы цен
        fieldPriceFrom.click();
        fieldPriceFrom.sendKeys("999");
        // Ожидание появления подсказки, сколько элементов найдено
        // Она гарантирует то, что страница прогрузилась и можно вводить второе поле
        WebElement wind = findWithTakeScreen(driver, By.cssSelector("[class*='_1PQIIOelRL']"));
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(wind));
        // Вводим цену во второе поле
        fieldPriceTo.click();
        fieldPriceTo.sendKeys("1999");
        fieldPriceTo.sendKeys(Keys.ENTER);

        // Ожидание появления подсказки
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(wind));

        // Пока есть кнопка "Показать еще", нажимаем её, тем самым откроем на одной сранице все щетки
        while(true) {
            try {
                WebElement showNewElement = findWithTakeScreen(driver, By.cssSelector(".n-pager-more__button"));
                showNewElement.click();
            } catch (Exception e) {
                break;
            }
        }

        // Находим фразу, которая содержит кол-во найденных щеток для считывания всех щеток в список
        final int countElement = Integer.parseInt(
                findWithTakeScreen(driver, By.cssSelector(".n-search-preciser__results-count"))
                .getAttribute("textContent").split(" ")[1]);

        // Ждем появления всех щеток на странице
        (new WebDriverWait(driver,10)).until(new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver driver) {
                return driver.findElements(By
                        .cssSelector("[class*='grid-snippet_react']")).size() == countElement;
            }
        });

        // Считываем щетки в список и проверяем, что их не 0
        final List<WebElement> elements = driver.findElements(By.cssSelector("[class*='grid-snippet_react']"));
        Assert.assertTrue(elements.size() != 0);

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
                if (price  < 999 || price > 1999) {
                    flag = false;
                    break;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

        Assert.assertTrue(flag);

        // Находим последнюю щетку и жмем на "добавить в корзину"
        findWithTakeScreen(elements.get(elements.size() - 2), By.cssSelector("[class*='_2w0qPDYwej']")).click();

        // Ждем, пока щетка добавится в корзину
        (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By
                        .cssSelector("[class*='_1sjxYfIabK _26mXJDBxtH']")));

        // Кликаем еще раз, чтобы перейти в корзину
        findWithTakeScreen(elements.get(elements.size() - 2), By.cssSelector("[class*='_2w0qPDYwej']")).click();

        // Ждем перехода
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class*='_3AlSA6AOKL']")));

        // Проверка на то, что доставка пока ещё не бесплатна
        WebElement freeCome = findWithTakeScreen(driver, By.cssSelector("[class *= '_3EX9adn_xp']"));
        Assert.assertTrue(freeCome.getAttribute("textContent").contains("бесплатной доставки осталось"));

        // Добавляем элементы в список для проверки корректности текущей цены
        List<WebElement> checkPrice = driver.findElements(By.cssSelector("[class *= '_1Q9ASvPbPN']"));

        String priceStr = checkPrice.get(0).findElement(By.cssSelector("[data-auto*='value']"))
                .getAttribute("textContent").replace(" ", "");
        // Вытаскиваем стоимость продукта и доставки
        int productPrice = Integer.parseInt(priceStr.substring(0, priceStr.length() - 1));
        priceStr = checkPrice.get(1).findElement(By.cssSelector("[data-auto*='value']"))
                .getAttribute("textContent").replace(" ", "");
        int comePrice = Integer.parseInt(priceStr.substring(0, priceStr.length() - 1)), sale = 0;

        int index = 2;
        // Если есть скидка, то достаем и ее
        if (checkPrice.size() == 4) {
            priceStr = checkPrice.get(2).findElement(
                    By.cssSelector("[class*='_3l-uEDOaBN _180wuXkZuy _3HJsMt3YC_']"))
                    .getAttribute("textContent").replace(" ", "");
            sale = Integer.parseInt(priceStr.substring(1, priceStr.length() - 1));
            index = 3;
        }
        priceStr = checkPrice.get(index).findElement(By.cssSelector("[class*='_1oBlNqVHPq']"))
                .getAttribute("textContent").replace(" ", "");
        int allPrice = Integer.parseInt(priceStr.substring(0, priceStr.length() - 1));

        Assert.assertEquals(productPrice + comePrice - sale, allPrice);

        //div[@data-auto='CartOfferPrice']/span/span/span

        // Добавляем кол-во щеток, пока цена не станет выше 2999
        priceStr = findWithTakeScreen
                (driver, By.xpath("//div[@data-auto='CartOfferPrice']/span/span/span"))
                .getAttribute("textContent").replace(" ", "");
        int priceProduct = Integer.parseInt(priceStr);
        while(priceProduct < 2999){
            System.out.println(priceProduct);
            findWithTakeScreen(driver,
                    By.xpath("//button//span[text()='+']")).click();
            priceStr = findWithTakeScreen
                    (driver, By.xpath("//div[@data-auto='CartOfferPrice']/span/span/span"))
                    .getAttribute("textContent").replace(" ", "");
            priceProduct = Integer.parseInt(priceStr);
        }

        // Ожидание изменения надписи о бесплатной доставке
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.attributeContains(
                        By.cssSelector("[class*='_3EX9adn_xp']"), "textContent", "Поздравляем!"));
        freeCome = findWithTakeScreen(driver, By.cssSelector("[class*='_3EX9adn_xp']"));
        Assert.assertTrue(freeCome.getAttribute("textContent").contains("Поздравляем!"));

        // Снова аналогичная проверка на корректность стоимости товара
        checkPrice = driver.findElements(By.cssSelector("[class *= '_1Q9ASvPbPN']"));

        String priceStrTwo = checkPrice.get(0).findElement(By.cssSelector("[data-auto*='value']"))
                .getAttribute("textContent").replace(" ", "");

        productPrice = Integer.parseInt(priceStrTwo.substring(0, priceStrTwo.length() - 1));
        priceStrTwo = checkPrice.get(1).findElement(By.cssSelector("[data-auto*='value']"))
                .getAttribute("textContent");
        Assert.assertTrue(priceStrTwo.contains("бесплатно"));

        sale = 0;
        index = 2;

        if (checkPrice.size() == 4) {
            priceStrTwo = checkPrice.get(2).findElement(
                    By.cssSelector("[class*='_3l-uEDOaBN _180wuXkZuy _3HJsMt3YC_']"))
                    .getAttribute("textContent").replace(" ", "");
            sale = Integer.parseInt(priceStrTwo.substring(1, priceStrTwo.length() - 1));
            index = 3;
        }
        priceStrTwo = checkPrice.get(index).findElement(By.cssSelector("[class*='_1oBlNqVHPq']"))
                .getAttribute("textContent").replace(" ", "");
        allPrice = Integer.parseInt(priceStrTwo.substring(0, priceStrTwo.length() - 1));

        Assert.assertEquals(productPrice - sale, allPrice);
    }
}
