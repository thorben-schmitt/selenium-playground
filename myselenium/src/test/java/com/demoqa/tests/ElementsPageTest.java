package com.demoqa.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demoqa.pages.elements.ElementsBasePage;
import com.demoqa.pages.elements.ElementsTextBoxPage;
import com.demoqa.setup.TestSetup;
import com.demoqa.utilities.LoggingHandler;

@Listeners(com.demoqa.utilities.LoggingHandler.class)
public class ElementsPageTest extends TestSetup {
    ElementsBasePage elementsBase;

    @BeforeClass
    public void populate() {
        elementsBase = new ElementsBasePage();
        LoggingHandler.log("ElementsBase class successfully initialized");
    }

    @Test
    public void openElementsDropdownAndVerify() {
        elementsBase.ToggleElementsDropdown();
        elementsBase.validateOpenDropdown();
    }

    @Test
    public void validateTextBoxPage() {
        elementsBase.navigateToSubMenu("Text Box");
        String uname = "Jim Bo";
        String email = "jim.bo@cognizant.com";
        String currAddress = "Sample Street 123";
        String permAddress = "Sample Street 123";

        ElementsTextBoxPage textBoxPage = new ElementsTextBoxPage();
        textBoxPage.shouldBeVisible();
        LoggingHandler.logStep("Text Box ");
        textBoxPage.fillForm(uname, email, currAddress, permAddress);
        textBoxPage.submitForm();
        textBoxPage.validateOutput(uname, email, currAddress, permAddress);
    }

    
}
