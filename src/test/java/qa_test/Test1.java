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

        WebElement el = findWithTakeScreen(driver, By.cssSelector(".header2-nav__user"));
        el.click();

        WebElement logInFotm = (new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".passp-login-form"))));

        el = findWithTakeScreen(logInFotm, By.name("login"));


        el.click();
        el.sendKeys("wewantGoT@yandex.ru");
        el.sendKeys(Keys.ENTER);

        WebElement PassForm = (new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".passp-password-form"))));

        el = findWithTakeScreen(PassForm, By.name("passwd"));

        el.sendKeys("Aria_one_love" + "\n");

        WebElement note = driver.findElement(By.cssSelector("[class*='header2-nav__user']"));
        el = findWithTakeScreen(note, By.cssSelector("[class*='__text']"));
        Assert.assertEquals(el.getAttribute("textContent"), "Мой профиль");

        (new Actions(driver)).moveToElement(note).build().perform();
        el = findWithTakeScreen(driver, By.cssSelector("[class*='user-menu__email']"));
        Assert.assertEquals(el.getAttribute("textContent"), "wewantGoT@yandex.ru");
    }

}