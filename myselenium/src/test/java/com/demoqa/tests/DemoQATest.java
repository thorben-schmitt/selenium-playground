package com.demoqa.tests;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.demoqa.pages.Base;
import com.demoqa.pages.elements.ElementsBase;

public class DemoQATest {
    ElementsBase elementsBase;

    @BeforeClass
    public void populate(ITestContext context) {
        if(elementsBase == null) {
            elementsBase = (ElementsBase) context.getSuite().getAttribute("elementsBaseClass");
        }
    }
    
    @Test
    public void openElements() {
        elementsBase.ToggleElementsDropdown();
        elementsBase.validateOpenDropdown();
    }

    @AfterTest
    public void cleanup() {
        Base.cleanup();
    }
}
