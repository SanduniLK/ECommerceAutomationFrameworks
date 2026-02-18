import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class FreeShippingFilterTest extends BaseTest {

    @Test
    public void testFreeShippingFilter() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;


        driver.get("https://www.daraz.lk/");
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("q")));
        searchBox.sendKeys("Mobile Phone");
        searchBox.submit();


        js.executeScript("window.scrollBy(0, 500)");
        By freeShippingCheckbox = By.xpath("//span[contains(text(), 'Free Shipping')]//preceding-sibling::span/input");


        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(freeShippingCheckbox));
        js.executeScript("arguments[0].click();", checkbox);
        System.out.println("✅ Free Shipping filter applied");


        Thread.sleep(5000);


        List<WebElement> shippingLabels = driver.findElements(By.cssSelector(".shipping--ms_S3"));

        if (!shippingLabels.isEmpty()) {
            for (int i = 0; i < Math.min(shippingLabels.size(), 3); i++) {
                String labelText = shippingLabels.get(i).getText();
                System.out.println("📦 Product " + (i+1) + " Shipping info: " + labelText);


                Assert.assertTrue(labelText.toLowerCase().contains("free shipping"),
                        "❌ Error: Product " + (i+1) + " does not have Free Shipping even after filter applied!");
            }
            System.out.println("✅ Success: Free Shipping filter is working correctly!");
        } else {
            Assert.fail("❌ No products found with Free Shipping label.");
        }
    }
}