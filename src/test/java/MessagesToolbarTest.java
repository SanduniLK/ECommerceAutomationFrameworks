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

        // 1. Daraz Search Page එකට හෝ Home Page එකට යෑම
        driver.get("https://www.daraz.lk/");

        // 2. Messages Toolbar එක දිස්වනවාදැයි බැලීම (Using your provided class)
        By toolbarLocator = By.cssSelector(".session-toolbar");
        WebElement messagesToolbar = wait.until(ExpectedConditions.visibilityOfElementLocated(toolbarLocator));

        // 3. Toolbar එකේ මාතෘකාව "Messages" දැයි පරීක්ෂා කිරීම
        WebElement toolbarTitle = messagesToolbar.findElement(By.className("session-toolbar__title"));
        String actualTitle = toolbarTitle.getText();

        System.out.println("💬 Toolbar Title found: " + actualTitle);
        Assert.assertEquals(actualTitle, "Messages", "❌ Error: Toolbar title mismatch!");

        // 4. Toolbar එක click කර විවෘත වේදැයි බැලීම
        messagesToolbar.click();
        System.out.println("✅ Messages toolbar clicked!");
    }
}