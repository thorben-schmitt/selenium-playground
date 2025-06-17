package com.demoqa.pages.elements;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementsTextBoxPage extends ElementsBasePage {
    private final WebDriver webDriver;
    private final String pageTitleString = "Text Box";

    @FindBy(css = "#app > div > div > div > div.col-12.mt-4.col-md-6 > h1")
    private WebElement pageTitleWebElement;

    private WebElement userName;
    private WebElement userEmail;
    private WebElement currentAddress;
    private WebElement permanentAddress;
    private WebElement submit;

    public ElementsTextBoxPage() {
        super();
        this.webDriver = super.getWebDriver();
        PageFactory.initElements(webDriver, this);
    }

    public void shouldBeVisible() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.textToBePresentInElement(pageTitleWebElement, pageTitleString));
    }

    public void fillForm(String uname, String email, String currAddress, String permAddress) {
        fillUserName(uname);
        fillEmail(email);
        fillCurrentAddress(currAddress);
        fillPermanentAddress(permAddress);
    }

    public void fillUserName(String uname) {
        userName.clear();
        userName.sendKeys(uname);
    }

    public void fillEmail(String email) {
        userEmail.clear();
        userEmail.sendKeys(email);
    }

    public void fillCurrentAddress(String currAddress) {
        currentAddress.clear();
        currentAddress.sendKeys(currAddress);
    }

    public void fillPermanentAddress(String permAddress) {
        permanentAddress.clear();
        permanentAddress.sendKeys(permAddress);
    }

    public void submitForm() {
        scrollIntoViewIfNeeded(submit);
        submit.click();
    }

    public void validateOutput(String expectedUserName, String expectedEmail, String expectedCurrAddress,
            String expectedPermAddress) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        wait.until(driver -> {
            try {
                WebElement output = driver.findElement(By.id("output"));
                scrollIntoViewIfNeeded(output);
                
                WebElement receivedName = output.findElement(By.id("name"));
                WebElement receivedEmail = output.findElement(By.id("email"));
                WebElement receivedCurrAddress = output.findElement(By.id("currentAddress"));
                WebElement receivedPermAddress = output.findElement(By.id("permanentAddress"));
    
                return receivedName.getText().contains(expectedUserName) &&
                       receivedEmail.getText().contains(expectedEmail) &&
                       receivedCurrAddress.getText().contains(expectedCurrAddress) &&
                       receivedPermAddress.getText().contains(expectedPermAddress);
            } catch (Exception e) {
                return false; // retry until timeout
            }
        });
    }
}
