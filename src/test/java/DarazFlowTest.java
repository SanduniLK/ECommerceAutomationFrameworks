import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class DarazFlowTest extends BaseTest {

    @Test
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

    @Test(dependsOnMethods = "login")
    public void searchProduct() {
        driver.get("https://www.daraz.lk/");

        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("q"))
        );
        searchBox.sendKeys("Mobile");
        searchBox.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.titleContains("Mobile"));
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("mobile"));

        System.out.println("✅ Product search successful");
    }

    @Test(dependsOnMethods = "searchProduct")
    public void applyBrandFilter() {
        try {
            By samsungFilter = By.xpath("//span[contains(text(),'Samsung')]/preceding-sibling::input");

            WebElement samsungCheckbox = wait.until(
                    ExpectedConditions.presenceOfElementLocated(samsungFilter)
            );

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", samsungCheckbox);
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", samsungCheckbox);

            wait.until(ExpectedConditions.urlContains("samsung"));

            System.out.println("✅ Samsung filter applied");
        } catch (Exception e) {
            Assert.fail("Filter click failed: " + e.getMessage());
        }
    }

    @Test(dependsOnMethods = "applyBrandFilter")
    public void verifyFilteredResults() {
        List<WebElement> products = driver.findElements(
                By.cssSelector("div[data-qa-locator='product-item']")
        );

        Assert.assertFalse(products.isEmpty(), "No products found after filter");

        boolean samsungFound = false;
        for (int i = 0; i < Math.min(products.size(), 5); i++) {
            String text = products.get(i).getText().toLowerCase();
            if (text.contains("samsung")) {
                samsungFound = true;
                System.out.println("Verified product: " + products.get(i).getText());
                break;
            }
        }

        Assert.assertTrue(samsungFound, "Filtered results are not Samsung products");
        System.out.println("✅ Filter verification successful");
    }
}
