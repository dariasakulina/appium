package ru.netology.qa;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_ACTIVITY;

public class ChangeTextAppiumTest {

    private AppiumDriver driver;
    private WebDriverWait wait;

    private static final String TEXT_TO_SET = "Netology";

    @BeforeEach
    public void setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PLATFORM_NAME, "Android");
        capabilities.setCapability(DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(APP_PACKAGE, "ru.netology.testing.uiautomator");
        capabilities.setCapability(APP_ACTIVITY, ".MainActivity");
        capabilities.setCapability(AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability("app", "C:\\Users\\user\\appium\\app\\build\\outputs\\apk\\debug\\app-debug.apk");
        capabilities.setCapability("uiautomator2ServerLaunchTimeout", 60000);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testChangeText() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("ru.netology.testing.uiautomator:id/userInput")));
        inputField.sendKeys(TEXT_TO_SET);

        driver.findElement(By.id("ru.netology.testing.uiautomator:id/buttonChange")).click();

        WebElement resultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("ru.netology.testing.uiautomator:id/textToBeChanged")));

        String result = resultElement.getText();
        assertEquals(TEXT_TO_SET, result);
    }

    @Test
    public void testEmptyStringDoesNotChangeText() {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("ru.netology.testing.uiautomator:id/userInput")));
        WebElement changeButton = driver.findElement(By.id("ru.netology.testing.uiautomator:id/buttonChange"));
        WebElement resultTextView = driver.findElement(By.id("ru.netology.testing.uiautomator:id/textToBeChanged"));

        String originalText = resultTextView.getText();

        inputField.clear();
        inputField.sendKeys("   ");
        changeButton.click();

        String newText = resultTextView.getText();
        assertEquals(originalText, newText);
    }

    @Test
    public void testOpenNewActivityWithText() {
        String inputText = "UI Automator Test";

        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("ru.netology.testing.uiautomator:id/userInput")));
        inputField.sendKeys(inputText);

        driver.findElement(By.id("ru.netology.testing.uiautomator:id/buttonActivity")).click();

        WebElement resultTextView = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("ru.netology.testing.uiautomator:id/text")));

        String resultText = resultTextView.getText();
        assertEquals(inputText, resultText);
    }
}
