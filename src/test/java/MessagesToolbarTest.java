import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class MessagesToolbarTest extends BaseTest {

    @Test
    public void testMessagesToolbarVisibility() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));


        driver.get("https://www.daraz.lk/");


        By toolbarLocator = By.cssSelector(".session-toolbar");
        WebElement messagesToolbar = wait.until(ExpectedConditions.visibilityOfElementLocated(toolbarLocator));


        WebElement toolbarTitle = messagesToolbar.findElement(By.className("session-toolbar__title"));
        String actualTitle = toolbarTitle.getText();

        System.out.println("💬 Toolbar Title found: " + actualTitle);
        Assert.assertEquals(actualTitle, "Messages", "❌ Error: Toolbar title mismatch!");


        messagesToolbar.click();
        System.out.println("✅ Messages toolbar clicked!");
    }
}