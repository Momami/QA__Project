package qa_test;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;


public class Test1 extends TestBefore {
    @Test
    public void test_1(ITestContext testContext) {
        WebElement userButtonLog = findWithTakeScreen(driver, By.cssSelector(".header2-nav__user"));
        userButtonLog.click();

        WebElement logInForm = (new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".passp-login-form"))));

        WebElement logIn = findWithTakeScreen(logInForm, By.name("login"));
        logIn.click();
        logIn.sendKeys("wewantGoT@yandex.ru");
        logIn.sendKeys(Keys.ENTER);

        WebElement passwordForm = (new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".passp-password-form"))));

        WebElement passwordLine = findWithTakeScreen(passwordForm, By.name("passwd"));
        passwordLine.sendKeys("Aria_one_love" + "\n");

        WebElement userButtonLogin = driver.findElement(By.cssSelector("[class*='header2-nav__user']"));
        WebElement textButtonUser = findWithTakeScreen(userButtonLogin, By.cssSelector("[class*='__text']"));
        Assert.assertEquals(textButtonUser.getAttribute("textContent"), "Мой профиль");

        (new Actions(driver)).moveToElement(userButtonLogin).build().perform();
        WebElement userMenuEmail = findWithTakeScreen(driver, By.cssSelector("[class*='user-menu__email']"));
        Assert.assertEquals(userMenuEmail.getAttribute("textContent"), "wewantGoT@yandex.ru");
    }

}