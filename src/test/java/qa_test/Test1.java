package qa_test;

import org.testng.annotations.*;

@Listeners(Listener.class)
public class Test1 extends TestBefore {
    @Test
    public void loginTest() {
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

