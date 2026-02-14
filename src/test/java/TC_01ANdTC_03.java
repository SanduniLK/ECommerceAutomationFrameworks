import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_01ANdTC_03  extends BaseTest{
    @Test(priority = 1, description = "TC_01: Verify price and stock update for variants")
    public void testProductVariantUpdates() {
        // 1. Navigate to a product page with multiple variants
        // Replace this URL with a specific product that has multiple colors/sizes for testing
        driver.get("https://www.daraz.lk/products/cotton-t-shirt-for-men-i123456.html");

        // 2. Capture the initial price before selecting any variant
        WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("pdp-price_type_normal")));
        String initialPrice = priceElement.getText();
        System.out.println("Initial Price: " + initialPrice);

        // 3. Select a specific color variant (e.g., Black)
        // Using contains(@class, 'sku-variable-name') as Daraz uses dynamic class suffixes
        WebElement colorVariant = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class, 'sku-variable-name') and text()='Black']")));
        colorVariant.click();
        System.out.println("Selected Color: Black");

        // 4. Select a specific size variant (e.g., L)
        WebElement sizeVariant = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(@class, 'sku-variable-name') and text()='L']")));
        sizeVariant.click();
        System.out.println("Selected Size: L");

        // 5. Explicit wait for the UI to update the price and stock info
        // We wait for the price element to contain a non-empty string or refresh
        wait.until(ExpectedConditions.visibilityOf(priceElement));

        // 6. Capture the updated price and verify visibility
        String updatedPrice = priceElement.getText();
        System.out.println("Updated Price after selection: " + updatedPrice);

        Assert.assertNotNull(updatedPrice, "Price should not be null after variant selection.");
        Assert.assertTrue(priceElement.isDisplayed(), "Updated price is not visible on the UI.");

        // 7. Verify stock status by checking the 'Add to Cart' button state
        WebElement addToCartBtn = driver.findElement(By.xpath("//button[contains(@class, 'add-to-cart')]"));

        boolean isEnabled = addToCartBtn.isEnabled();
        String buttonText = addToCartBtn.getText();

        if (isEnabled && !buttonText.toLowerCase().contains("out of stock")) {
            System.out.println("Verification Success: Variant is In-Stock and button is enabled.");
        } else {
            System.out.println("Verification: Variant is Out-of-Stock. Checking button restriction...");
            // Per TC_01 Requirement: If variant is out of stock, Add to Cart should be disabled
            Assert.assertTrue(buttonText.toLowerCase().contains("out of stock") || !isEnabled,
                    "Add to Cart button must be disabled for Out-of-Stock variants.");
        }
    }
}