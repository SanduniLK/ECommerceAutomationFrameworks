import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class CategoriesIconScrollTest extends BaseTest {
    @Test
    public void scrollToCategoriesIcon() {
        driver.get("https://www.daraz.lk/");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 1. Click Categories Icon
            By categoriesIconLocator = By.cssSelector("i.lazada-ic-Categories");
            WebElement navItem = wait.until(ExpectedConditions.elementToBeClickable(categoriesIconLocator));

            // Scroll and use JS click to be safe against overlays
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", navItem);
            js.executeScript("arguments[0].click();", navItem);
            System.out.println("✅ Categories icon clicked");

            // 2. Wait for the menu to be visible
            // We target the link (a) that contains the text for better reliability
            By specificCategory = By.xpath("//*[@id=\"10001796\"]/div[2]");

            // Alternative if the above is too complex, try just finding the link by partial text:
            // By specificCategory = By.partialLinkText("Electronic Devices");

            WebElement cat = wait.until(ExpectedConditions.visibilityOfElementLocated(specificCategory));

            // Move to element before clicking to trigger any hover effects
            new Actions(driver).moveToElement(cat).perform();
            cat.click();

            System.out.println("✅ Category item clicked");

        } catch (Exception e) {
            // Take a screenshot here if you have a method for it!
            Assert.fail("❌ Test failed: " + e.getMessage());
        }
    }
}
