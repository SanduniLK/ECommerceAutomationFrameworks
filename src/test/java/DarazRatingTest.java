import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class DarazRatingTest extends BaseTest {

    @Test
    public void testFourStarFilter() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://www.daraz.lk/smartphones/");

        try {
            By fourStarLocator = By.xpath("(//div[contains(@class, 'dumOn')])[2]");

            WebElement ratingFilter = wait.until(ExpectedConditions.presenceOfElementLocated(fourStarLocator));

            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", ratingFilter);
            Thread.sleep(2000);

            js.executeScript("arguments[0].click();", ratingFilter);

            wait.until(ExpectedConditions.urlContains("rating=4"));

            Assert.assertTrue(driver.getCurrentUrl().contains("rating=4"));

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}