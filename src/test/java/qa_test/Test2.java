package qa_test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(Listener.class)
public class Test2 extends TestBefore {
    @DataProvider(name = "cityName")
    public Object[][] dataProviderMethod() {
        return new Object[][]{{"Хвалынск"}, {"Москва"}, {"Красноармейск"}};
    }

    @Test(dataProvider = "cityName")
    public void changeCityTest(String cityName) {
        StartPage page = new StartPage(driver);
        page.clickCityInner();
        page.changeCityName(cityName);
        page.checkCityName();
        page.clickButtonAccount();
        Login loginForm = new Login(driver);
        String email = "wewantGoT@yandex.ru";
        loginForm.enterLogin(email);
        String password = "Aria_one_love";
        loginForm.enterPassword(password);
        page.clickAddresses();
        Address address = new Address(driver);
        address.findCityInner();
        address.findDeliveryAddress();
        address.checkAdresses();
    }
}