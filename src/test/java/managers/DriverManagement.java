package managers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.support.ui.WebDriverWait;
import types.DriverTypes;
import utils.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Objects;

public class DriverManagement {

    private static AppiumDriverLocalService service;
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();

    public static void setDriver(DriverTypes driverType) throws MalformedURLException {
        switch (driverType) {
            case DESKTOP_CHROME:
                WebDriverManager.chromedriver().setup();

                ChromeDriver chromeDriver = new ChromeDriver();
                chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                chromeDriver.manage().window().maximize();
                driver.set(chromeDriver);

                WebDriverWait webDriverWait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
                wait.set(webDriverWait);
                break;
            case MOBILE_ANDROID:
                // AppiumServiceBuilder appiumServiceBuilder = setAndGetAppiumServiceBuilder();
                // service = AppiumDriverLocalService.buildService(appiumServiceBuilder);
                // service.start();

                ConfigReader capabilitiesConfig = new ConfigReader();
                capabilitiesConfig.readConfig("android.properties");
                DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability("platformName", capabilitiesConfig.getProperty("platformName"));
                desiredCapabilities.setCapability("platformVersion", capabilitiesConfig.getProperty("platformVersion"));
                desiredCapabilities.setCapability("deviceName", capabilitiesConfig.getProperty("deviceName"));
                desiredCapabilities.setCapability("udid", capabilitiesConfig.getProperty("udid"));
                desiredCapabilities.setCapability("app", Objects.requireNonNull(Paths.get("src/test/resources/apk_files", capabilitiesConfig.getProperty("app"))).toAbsolutePath().toString());
                desiredCapabilities.setCapability("skipUnlock", capabilitiesConfig.getProperty("skipUnlock"));
                desiredCapabilities.setCapability("noReset", capabilitiesConfig.getProperty("noReset"));

                AndroidDriver appiumDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
                driver.set(appiumDriver);
                break;
        }
        wait.set(new WebDriverWait(driver.get(), Duration.ofSeconds(10)));
    }

    private static AppiumServiceBuilder setAndGetAppiumServiceBuilder() {
        ConfigReader globalConfig = new ConfigReader();
        globalConfig.readConfig("config.properties");
        System.out.println(globalConfig.getProperty("APPIUM_JS_PATH"));

        return new AppiumServiceBuilder()
            .usingPort(4723)
            .usingDriverExecutable(
                    new File(globalConfig.getProperty("NODE_PATH"))
            )
                .withAppiumJS(
                    new File(globalConfig.getProperty("APPIUM_JS_PATH"))
            )
            .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
            .withArgument(GeneralServerFlag.LOG_LEVEL, "error");
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static WebDriverWait getWait() {
        return wait.get();
    }

    public static void teardown(DriverTypes driverType) throws IOException {
        if (driver.get() != null) {
            File screenshot = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("test-output/screenshots/" + driverType + "/" + "screenshot.png"));
        }


        if (driverType == DriverTypes.MOBILE_ANDROID) {
            if (service != null) {
                service.stop();
            }
        }

        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            wait.remove();
        }
    }

}
