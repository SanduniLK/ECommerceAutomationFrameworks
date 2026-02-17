import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class AutoSuggestTest extends BaseTest {

    @Test
    public void testAutoSuggest() {
        try {
            // 1. Navigate to Daraz home (already logged in)
            driver.get("https://www.daraz.lk/");

            // 2. Locate search box
            WebElement searchBox = driver.findElement(By.id("q"));

            // 3. Type text to trigger auto-suggest
            searchBox.sendKeys("lap");

            // 4. Wait for auto-suggest to appear (already handled by wait in BaseTest)
            wait.until(driver -> driver.findElements(By.cssSelector("div.suggest-common a")).size() > 0);

            // 5. Get all suggestions
            List<WebElement> suggestions = driver.findElements(By.cssSelector("div.suggest-common a"));

            System.out.println("Total suggestions: " + suggestions.size());
            for (WebElement suggestion : suggestions) {
                System.out.println("Suggestion: " + suggestion.getText());
            }

            // 6. Click first suggestion
            if (!suggestions.isEmpty()) {
                suggestions.get(0).click();
                System.out.println("Clicked first suggestion: " + suggestions.get(0).getText());
            }

            // 7. Verify the results page opened
            WebElement resultsHeader = driver.findElement(By.cssSelector("div.page-title"));
            assert resultsHeader.isDisplayed() : "Search results page not loaded";

        } catch (Exception e) {
            takeScreenshot("AutoSuggestTest_Failure");
            e.printStackTrace();
        }
    }
}
