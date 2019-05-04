package qa_test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestBefore {
    public static WebDriver driver;
    public static Screen screen;
    public static final String PATH_FOR_SCREEN = new File("screen").getAbsolutePath();

    public String screenDate() {
        Date date = new Date();
        DateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        screen.saveAllureScreenshot(formatForDateNow.format(date));
        return formatForDateNow.format(date);
    }

    @BeforeMethod
    public void preparation(ITestContext testContext) {
        //Указываем путь к драйверу
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("start-fullscreen");
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("disable-extensions"); // disabling extensions
        options.addArguments("disable-gpu"); // applicable to windows os only
        options.addArguments("disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("no-sandbox"); // Bypass OS security model
        options.setExperimentalOption("useAutomationExtension", false);
        driver = new ChromeDriver(options);
        screen = new Screen(driver, PATH_FOR_SCREEN);
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.get("https://beru.ru");
    }

    @AfterMethod
    public void quit() {
        driver.get("https://beru.ru/logout?retpath=https%3A%2F%2Fberu.ru%2F%3Fncrnd%3D4545");
        driver.quit();
    }
}

