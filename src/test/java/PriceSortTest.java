import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class PriceSortTest extends BaseTest {

    @Test
    public void testPriceSortLowToHigh() throws InterruptedException {
        driver.get("https://www.daraz.lk/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Search for item
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("q")));
        searchBox.sendKeys("Bluetooth Headphones");
        searchBox.submit();
        System.out.println("✅ Search submitted");

        js.executeScript("window.scrollBy(0, 350)");
        By sortBox = By.cssSelector("div[data-spm-click*='locaid=dinit']");
        WebElement sortDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(sortBox));

        // Force JS click because of the 'opacity: 0' and 'readonly' attributes in the HTML
        js.executeScript("arguments[0].click();", sortDropdown);
        System.out.println("✅ Sort dropdown opened using dinit");



        // 4. Select 'Price low to high'
        // 'd2' is the standard Daraz ID for Price Ascending
        By lowToHighLocator = By.cssSelector("div[data-spm-click*='locaid=d2']");
        WebElement lowToHighOption = wait.until(ExpectedConditions.visibilityOfElementLocated(lowToHighLocator));
        js.executeScript("arguments[0].click();", lowToHighOption);
        System.out.println("✅ 'Price low to high' selected");

    }
}