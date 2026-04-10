package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class App {

    public static void main(String[] args) {

        WebDriver driver = null;

        try {
            // Setup GeckoDriver automatically
            WebDriverManager.firefoxdriver().setup();

            // Configure Firefox options
            FirefoxOptions options = new FirefoxOptions();

            // Enable headless mode if specified (for Jenkins)
            if (System.getProperty("headless") != null) {
                options.addArguments("--headless");
            }

            options.addArguments("--width=1920");
            options.addArguments("--height=1080");

            // Initialize WebDriver
            driver = new FirefoxDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // =====================
            // SauceDemo Login
            // =====================
            driver.get("https://www.saucedemo.com/");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
                    .sendKeys("standard_user");

            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            // =====================
            // Practice Test Automation Login
            // =====================
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get("https://practicetestautomation.com/practice-test-login/");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
                    .sendKeys("student");

            driver.findElement(By.id("password")).sendKeys("Password123");
            driver.findElement(By.id("submit")).click();

            // =====================
            // Automation Exercise
            // =====================
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get("https://automationexercise.com");

            // Wait for Add to Cart button
            WebElement addToCart = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("(//a[@data-product-id='1'])[1]"))
            );

            // Scroll to the element
            js.executeScript("arguments[0].scrollIntoView(true);", addToCart);

            // Hide iframe ads to avoid click interception
            js.executeScript(
                    "document.querySelectorAll('iframe').forEach(el => el.style.display='none');"
            );

            // Click using JavaScript
            js.executeScript("arguments[0].click();", addToCart);

            // Click Continue Shopping
            WebElement continueBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(text(),'Continue Shopping')]"))
            );
            continueBtn.click();

            System.out.println("All automations completed successfully.");

        } catch (Exception e) {
            System.err.println("An error occurred during execution:");
            e.printStackTrace();
        } finally {
            // Close browser safely
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
