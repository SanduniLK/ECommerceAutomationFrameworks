import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginInvalidcreditions extends BaseTest{
    @Test
    public void login(){

        driver.get("https://member.daraz.lk/user/login");
        WebElement username = driver.findElement(By.xpath("//input[@placeholder='Please enter your Phone Number or Email']"));
        username.sendKeys("user_test@mail.com");
        WebElement password=driver.findElement(By.xpath("//input[@placeholder='Please enter your password']"));
        password.sendKeys(" Pass123!");
        WebElement loginBtn = driver.findElement(By.xpath("//button[text()='LOGIN']"));
        loginBtn.click();
        WebElement errorMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='next-feedback-content'] | //span[contains(text(), 'Invalid')]")));
        String actualErrorMessage = errorMsgElement.getText();
        String expectedErrorMessage = "Invalid login id or password.";
        Assert.assertTrue(actualErrorMessage.contains("Invalid"),
                "The error message did not contain the expected text! Found: " + actualErrorMessage);

        System.out.println("Test Passed: Error message verified correctly.");


    }

}
