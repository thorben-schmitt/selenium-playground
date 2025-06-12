package com.demoqa.poc;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProofOfConcept {
    static WebDriver driver = new ChromeDriver();
    static WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));

    public static void main(String[] args) {
        driver.get("https://demoqa.com/books");
        driver.manage().window().maximize();

        System.out.println(driver.getTitle());
        WebElement login = driver.findElement(By.id("login"));

        login.click();

        try {
            WebElement userNameField = webDriverWait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("userName")));

            WebElement passwordField = webDriverWait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("password")));

            userNameField.sendKeys("HelloWorld");
            passwordField.sendKeys("HelloPassword");
        } catch (Exception e) {
            System.out.println("Element not visible within the timeout");
        }
    }
}
