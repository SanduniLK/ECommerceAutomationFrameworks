import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DarazVoucherTest extends BaseTest {

    @Test(description = "Verify that the cart count updates correctly after adding an item and refreshing the page.")
    public void testCartPersistencyAfterRefresh() {
        driver.get("https://www.daraz.lk/");

        
        try {
            WebElement loginLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("anonLogin")));
            loginLink.click();

            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='text' and @placeholder='Please enter your Phone Number or Email']")));
            emailInput.sendKeys("YOUR_EMAIL_HERE");

            WebElement passwordInput = driver.findElement(By.xpath("//input[@type='password']"));
            passwordInput.sendKeys("YOUR_PASSWORD_HERE");

            driver.findElement(By.xpath("//button[text()='LOGIN']")).click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[text()='LOGIN']")));
            System.out.println("Login Successful.");
        } catch (Exception e) {
            System.out.println("Already logged in or login step skipped.");
        }

        
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("q")));
        searchBox.clear();
        searchBox.sendKeys("Water Bottle");
        searchBox.sendKeys(Keys.ENTER);

        WebElement firstProduct = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//div[@data-qa-locator='product-item']//a)[1]")));
        firstProduct.click();

       
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

       
        WebElement cartCountElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("topActionCartNumber")));
        String initialCountText = cartCountElement.getText().trim();
        int initialCount = initialCountText.isEmpty() ? 0 : Integer.parseInt(initialCountText);
        System.out.println("Initial Cart Count: " + initialCount);

       
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'add-to-cart')]//span[text()='Add to Cart'] | //button//span[contains(text(), 'Add to Cart')]")));

        addToCartBtn.click();
        System.out.println("Clicked Add to Cart.");

        
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
        driver.navigate().refresh();
        System.out.println("Page refreshed. Checking updated count...");

      
        wait.until(driver -> {
            try {
                String currentText = driver.findElement(By.id("topActionCartNumber")).getText().trim();
                int currentCount = currentText.isEmpty() ? 0 : Integer.parseInt(currentText);
                return currentCount > initialCount;
            } catch (Exception e) {
                return false;
            }
        });

        String updatedCount = driver.findElement(By.id("topActionCartNumber")).getText().trim();
        System.out.println("Updated Cart Count after Refresh: " + updatedCount);

        
        driver.navigate().refresh();
        String finalCount = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("topActionCartNumber"))).getText().trim();

        Assert.assertEquals(finalCount, updatedCount, "දෝෂයකි: Refresh කළ පසු Cart එකේ ප්‍රමාණය වෙනස් වී ඇත!");
        System.out.println("Success: Cart Persistency Verified! Final Count: " + finalCount);
    }
}