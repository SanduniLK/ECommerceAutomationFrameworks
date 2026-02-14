import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductSearch extends BaseTest {

    @Test(priority = 1)
    public void searchProduct() {
      driver.get("https://www.daraz.lk/#?");

        WebElement searchBar = driver.findElement(By.id("q"));
        searchBar.sendKeys("Mobile");
        searchBar.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.titleContains("Mobile"));

        String actualTitle = driver.getTitle();
        Assert.assertTrue(actualTitle.toLowerCase().contains("mobile"),
                "Search failed. Title: " + actualTitle);

        System.out.println("Search functionality verified successfully.");
    }
    @Test(dependsOnMethods = "searchProduct")
    public void sortlist(){
        WebElement sortOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(text(),'Price low to high')]")
                )
        );

        sortOption.click();
        System.out.println("Clicked 'Price low to high' sorting option");
    }
}
