import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DarazPerformanceTest extends BaseTest { // Assumes driver setup in BaseTest

    @Test
    public void checkDetailedPerformance() {
        driver.get("https://www.daraz.lk/");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Get metrics from the browser's performance window
        long loadEventEnd = (Long) js.executeScript("return window.performance.timing.loadEventEnd;");
        long navigationStart = (Long) js.executeScript("return window.performance.timing.navigationStart;");
        long responseStart = (Long) js.executeScript("return window.performance.timing.responseStart;"); // TTFB
        long domComplete = (Long) js.executeScript("return window.performance.timing.domComplete;");

        System.out.println("--- Daraz Performance Breakdown ---");
        System.out.println("Time to First Byte (TTFB): " + (responseStart - navigationStart) + "ms");
        System.out.println("DOM Interactive Time: " + (domComplete - navigationStart) + "ms");
        System.out.println("Total Load Time: " + (loadEventEnd - navigationStart) + "ms");

        Assert.assertTrue((loadEventEnd - navigationStart) < 10000, "Critical slowdown detected!");
    }
}