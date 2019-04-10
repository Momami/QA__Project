package qa_test;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class Test2 extends TestBefore {

    @Parameters("cityName")
    @Test
    public void test(String cityName) {

        WebElement regionElement = findWithTakeScreen(driver,
                By.cssSelector("[class*='__region'] [class*='__inner']"));
        regionElement.click();

        WebElement CityForm = (new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class*='region-select-form']"))));

        findWithTakeScreen(driver, By.cssSelector("[class*='region-select-form']"));

        WebElement enterCity = findWithTakeScreen(CityForm,
                By.cssSelector("[class*='region-suggest'] [class*='input__control']"));
        enterCity.click();
        enterCity.sendKeys(cityName);

        //(new WebDriverWait(driver, 10))
        //        .until(ExpectedConditions.elementToBeClickable(enterCity));

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(enterCity
                .findElement(By.xpath("//strong[text()[contains(., \'" + cityName + "\')]]"))));

        enterCity.sendKeys(Keys.ENTER);

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.invisibilityOf(driver
                        .findElement(By.cssSelector("[class*='suggestick-list']"))));

        enterCity.sendKeys(Keys.ENTER);

        driver.navigate().refresh();

        WebElement regionElementCheck = findWithTakeScreen(driver,
                By.cssSelector("[class*='__region'] [class*='__inner']"));

        Assert.assertEquals(regionElementCheck.getAttribute("textContent"), cityName);


        // Вход в аккаунт
        //=================================================================================================
        WebElement buttonAccount = findWithTakeScreen(driver, By.className("header2-nav__user"));
        buttonAccount.click();
        WebElement logInForm = (new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.className("passp-login-form"))));

        WebElement logInEnter = findWithTakeScreen(logInForm, By.name("login"));

        logInEnter.click();
        logInEnter.sendKeys("wewantGoT@yandex.ru");
        logInEnter.sendKeys(Keys.ENTER);

        WebElement PasswordForm = (new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.className("passp-password-form"))));

        WebElement passwordEnter = findWithTakeScreen(PasswordForm, By.name("passwd"));
        passwordEnter.sendKeys("Aria_one_love");
        passwordEnter.sendKeys(Keys.ENTER);

        //==================================================================================================


        WebElement navigateAccount =  findWithTakeScreen(driver, By.className("header2-nav__user"));

        (new Actions(driver)).moveToElement(navigateAccount).build().perform();

        WebElement adresses = findWithTakeScreen(driver, By.cssSelector("[class*='item_type_addresses']"));
        adresses.click();

        WebElement settingsRegion = findWithTakeScreen(driver,
                By.cssSelector("[class*='settings-list_type_region'] [class*='__inner']"));

        WebElement regionName = findWithTakeScreen(driver,
                By.cssSelector("[class*='__region'] [class*='__inner']"));

        Assert.assertEquals(settingsRegion.getAttribute("textContent"), regionName.getAttribute("textContent"));
    }
}
