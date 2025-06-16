package com.demoqa.pages.elements;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import junit.framework.Assert;

public class ElementsBase {
    private final WebDriver webDriver;
    private List<String> liTexts = Arrays.asList("Text Box", "Check Box", "Radio Button", "Web Tables", "Buttons",
            "Links", "Broken Links - Images", "Upload and Download", "Dynamic Properties");
    private List<By> dropdownList;

    @FindBy(xpath = "//div/div[contains(text(), 'Elements')]")
    private WebElement elementsDropdown;

    public ElementsBase(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);

        dropdownList = liTexts.stream()
                .map(text -> By.xpath(
                        "//div[contains(@class, 'show')]//ul[contains(@class, 'menu-list')]//*[text()='" + text + "']"))
                .collect(Collectors.toList());
    }

    public void ToggleElementsDropdown() {
        elementsDropdown.click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".element-list.collapse.show")));
    }

    public void validateOpenDropdown() {
        Assert.assertEquals(liTexts.size(), dropdownList.size());

        for (By li : dropdownList) {
            Assert.assertTrue(liTexts.contains(webDriver.findElement(li).getText()));
        }
    }

    public void navigateToSubMenu(String subMenuString) {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

        for (int i = 0; i < liTexts.size(); i++) {
            if (liTexts.get(i).toLowerCase().contains(subMenuString.toLowerCase())) {
                By locator = dropdownList.get(i);
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                element.click();
                return;
            }
        }

        throw new NoSuchElementException("No menu item containing text: " + subMenuString);
    }
}
