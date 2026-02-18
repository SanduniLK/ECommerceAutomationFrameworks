import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class DarazCartToCheckout extends BaseTest {




    @Test
    public void testCartToCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://cart.daraz.lk/cart");

        try {
            By itemCheckbox = By.xpath("//div[@class='cart-item-inner']//label[contains(@class, 'next-checkbox')]");
            WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(itemCheckbox));

            if (!checkbox.getAttribute("class").contains("checked")) {
                js.executeScript("arguments[0].click();", checkbox);
            }

            By checkoutBtnLocator = By.xpath("//button[contains(text(), 'PROCEED TO CHECKOUT')] | //div[contains(@class, 'checkout-order-total')]//button");
            WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtnLocator));
            js.executeScript("arguments[0].click();", checkoutBtn);

            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("checkout"),
                    ExpectedConditions.urlContains("login")
            ));

            Assert.assertTrue(driver.getCurrentUrl().contains("checkout") || driver.getCurrentUrl().contains("login"));

        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}