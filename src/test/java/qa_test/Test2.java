package qa_test;


import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class Test2 extends TestBefore {
    @Parameters("cityName")
    @Test
    public void test2(String cityName) {
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
