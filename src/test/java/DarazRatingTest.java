import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class RatingFilterTest extends BaseTest {

    @Test
    public void testRatingFilterSelection() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. භාණ්ඩයක් සෙවීම
        driver.get("https://www.daraz.lk/");
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("q")));
        searchBox.sendKeys("Headphones");
        searchBox.submit();
        System.out.println("✅ Search completed");

        WebElement fedd=driver.findElement(By.id(""))


    }
}