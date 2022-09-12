package lib;

import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class CoreTestCase {

    protected AndroidDriver<WebElement> driver;
    private static final String APPIUM_URL = "http://localhost:4723/wd/hub";

    @Before
    public void setUp() throws MalformedURLException {
        URL URL = new URL(APPIUM_URL);
        this.driver = new AndroidDriver<>(URL, this.getAndroidDesiredCapabilities());
    }

    private DesiredCapabilities getAndroidDesiredCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("avd","ver10");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","10.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","\\Users\\Masha\\IdeaProjects\\QAJA_MOBILE\\apps\\org.wikipedia.apk");
        return capabilities;
    }
}
