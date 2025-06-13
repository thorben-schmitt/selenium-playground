package com.demoqa.pages.elements;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class ElementsBase {
    private final WebDriver webDriver;
    private List<String> liTexts = Arrays.asList("Text Box", "Check Box", "Radio Button", "Web Tables", "Buttons",
            "Links", "Broken Links - Images", "Upload and Download", "Dynamic Properties");
    private List<By> dropDownList;

    public ElementsBase(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.dropDownList = liTexts.stream()
                .map(text -> By.xpath(
                        "//div[contains(@class, 'show')]//ul[contains(@class, 'menu-list')]//*[text()='" + text + "']"))
                .collect(Collectors.toList());
    }

    public void navigate() {
        webDriver.findElement(By.xpath("//div/div[contains(text(), 'Elements')]")).click();
    }

    public void validateOpenDropdown() {
        List<WebElement> list = webDriver.findElement(By.className(".show .menu-list")).findElements(By.tagName("li"));

        Assert.assertEquals(liTexts.size(), list.size());

        for (WebElement li : list) {
            Assert.assertTrue(liTexts.contains(li.getText()));
        }
    }

    public void navigateToSubMenu(String subMenuString) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        for (int i = 0; i < liTexts.size(); i++) {
            if (liTexts.get(i).toLowerCase().contains(subMenuString.toLowerCase())) {
                By locator = dropDownList.get(i);
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                return;
            }
        }

        throw new NoSuchElementException("No menu item containing text: " + subMenuString);
    }
}
