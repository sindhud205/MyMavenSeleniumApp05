package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class App {

    public static void main(String[] args) {

        // Automatically download and configure GeckoDriver
        WebDriverManager.firefoxdriver().setup();

        // Configure Firefox options
        FirefoxOptions options = new FirefoxOptions();

        // Enable headless mode when running in Jenkins
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            options.addArguments("-headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }

        // If Firefox is installed via Snap (common in Ubuntu), specify the binary
        String firefoxBinary = System.getenv("FIREFOX_BIN");
        if (firefoxBinary != null && !firefoxBinary.isEmpty()) {
            options.setBinary(firefoxBinary);
        }

        // Launch Firefox
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().window().maximize();

        // Explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // ===============================
            // 1. SauceDemo Login
            // ===============================
            driver.get("https://www.saucedemo.com/");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
                    .sendKeys("standard_user");

            driver.findElement(By.id("password"))
                    .sendKeys("secret_sauce");

            driver.findElement(By.id("login-button"))
                    .click();

            wait.until(ExpectedConditions.urlContains("inventory"));
            System.out.println("SauceDemo login successful.");

            // ===============================
            // 2. Practice Test Automation Login
            // ===============================
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get("https://practicetestautomation.com/practice-test-login/");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")))
                    .sendKeys("student");

            driver.findElement(By.id("password"))
                    .sendKeys("Password123");

            driver.findElement(By.id("submit"))
                    .click();

            wait.until(ExpectedConditions.urlContains("logged-in-successfully"));
            System.out.println("Practice Test Automation login successful.");

            // ===============================
            // 3. Automation Exercise – Add to Cart
            // ===============================
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get("https://automationexercise.com");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            // Locate first product's Add to Cart button
            WebElement addToCart = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("(//a[@data-product-id='1'])[1]")
                    )
            );

            // Scroll to the element
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    addToCart
            );

            // Hide advertisement iframes to prevent click interception
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('iframe').forEach(el => el.style.display='none');"
            );

            // Click using JavaScript
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();",
                    addToCart
            );

            // Click Continue Shopping
            WebElement continueBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(text(),'Continue Shopping')]")
                    )
            );
            continueBtn.click();

            System.out.println("Automation Exercise: Item added to cart successfully.");
            System.out.println("\nAll automations completed successfully.");

        } catch (Exception e) {
            System.err.println("Error occurred during automation:");
            e.printStackTrace();
        } finally {
            // Close browser
            driver.quit();
        }
    }
}
