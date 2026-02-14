import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        // Automatically manages the chromedriver executable
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

        // Setting up synchronization tools
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        login();
        System.out.println("Browser started and navigated to the base URL.");




    }

    public void login() {
        driver.get("https://member.daraz.lk/user/login");

        driver.findElement(By.xpath("//input[@placeholder='Please enter your Phone Number or Email']"))
                .sendKeys("geeganagesanduni83@gmail.com");

        driver.findElement(By.xpath("//input[@placeholder='Please enter your password']"))
                .sendKeys("Reshika12345@");

        driver.findElement(By.xpath("//button[text()='LOGIN']")).click();

        WebElement accountIcon = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("myAccountTrigger"))
        );

        Assert.assertTrue(accountIcon.isDisplayed(), "Login failed");
        System.out.println("✅ Login successful");
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed successfully.");
        }
    }
    public void takeScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(source, new File("./Screenshots/" + testName + ".png"));
            System.out.println("Screenshot taken for failed test: " + testName);
        } catch (IOException e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }
}
