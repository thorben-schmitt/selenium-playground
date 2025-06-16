package com.demoqa.tests;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.demoqa.pages.Base;
import com.demoqa.pages.elements.ElementsBase;

@Listeners(com.demoqa.utilities.LoggingHandler.class)
public class DemoQATest {
    ElementsBase elementsBase;

    @BeforeClass
    public void populate(ITestContext context) {
        elementsBase = (ElementsBase) context.getSuite().getAttribute("elementsBaseClass");
        Base.log("ElementsBase class successfully initialized");
    }

    @Test
    public void openElements() {
        elementsBase.ToggleElementsDropdown();
        elementsBase.validateOpenDropdown();
        Base.log("Test");
    }
}
