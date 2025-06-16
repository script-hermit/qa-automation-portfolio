import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.net.URL;

public class LoginTest {
    AppiumDriver driver;

    @BeforeClass
    public void setup() throws Exception {
       DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("deviceName", "emulator-5554"); // Optional if udid is set
        caps.setCapability("udid", "emulator-5554");
        caps.setCapability("app", System.getProperty("user.dir") + "/ApiDemos-debug.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), caps);
    }

    @Test
    public void testLaunch() {
        System.out.println("App launched successfully.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
