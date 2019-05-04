package qa_test;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Listener.class)
public class Test3 extends TestBefore {
    @Test
    public void test3() {
        StartPage page = new StartPage(driver);
        String itemForFinding = "Электрические зубные щетки";
        page.findItem(itemForFinding);
        ProductPage prodPage = new ProductPage(driver);
        int priceOne = 999;
        int priceTwo = 1999;
        prodPage.enterFirstPrice(priceOne);
        prodPage.enterSecondPrice(priceTwo);
        prodPage.showYet();
        prodPage.checkCountItem();
        prodPage.checkEmptyListItem();
        prodPage.checkPriceInRange(priceOne, priceTwo);
        prodPage.addToBasket();
        prodPage.goToBasket();
        int limit = 2999;
        String title = "Поздравляем!";
        String textDelivery = "бесплатной доставки осталось";
        BasketPage basketPage = new BasketPage(driver);
        basketPage.checkDelivery(textDelivery);
        basketPage.checkPriceCorrect();
        basketPage.addCountProduct(limit);
        basketPage.checkFreeDeliveryTitle(title);
        basketPage.checkFreeDelivery();
        basketPage.checkPriceCorrect();
    }
}