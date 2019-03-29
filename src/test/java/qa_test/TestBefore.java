package qa_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestBefore {
    public static WebDriver driver;
    public static Screen screen;

    public WebElement findWithTakeScreen(WebDriver d, By by) {
        WebElement element = d.findElement(by);
        DateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        screen.saveAllureScreenshot(element, formatForDateNow.format(new Date()));
        return element;
    }

    public WebElement findWithTakeScreen(WebElement d, By by) {
        WebElement element = d.findElement(by);
        DateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        screen.saveAllureScreenshot(element, formatForDateNow.format(new Date()));
        return element;
    }


    @BeforeTest
    public void preparation(ITestContext testContext) {
        //Указываем путь к драйверу
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("start-fullscreen");
        /*options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("disable-extensions"); // disabling extensions
        options.addArguments("disable-gpu"); // applicable to windows os only
        options.addArguments("disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("no-sandbox"); // Bypass OS security model
        options.setExperimentalOption("useAutomationExtension", false);*/
        driver = new ChromeDriver(options);
        screen = new Screen(driver, "C:\\Users\\milen\\IdeaProjects\\QA_Project");
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.get("https://beru.ru");
        WebElement element = findWithTakeScreen(driver, By.cssSelector("[class*='_1ZYDKa22GJ']"));
        element.click();
    }

    @AfterTest
    public void clear() {
        driver.quit();
    }



}

