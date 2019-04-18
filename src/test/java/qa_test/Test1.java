package qa_test;


import org.testng.annotations.*;


public class Test1 extends TestBefore {
    @Test
    public void test1() {
        StartPage topPanel = new StartPage(driver);
        topPanel.clickButtonAccount();
        Login loginForm = new Login(driver);
        String email = "wewantGoT@yandex.ru";
        loginForm.enterLogin(email);
        String password = "Aria_one_love";
        loginForm.enterPassword(password);
        topPanel.checkTextButtonAccount();
        topPanel.checkUserMenuEmail();
    }
}

