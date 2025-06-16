package com.demoqa.setup;

import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;

import com.demoqa.pages.Base;
import com.demoqa.pages.elements.ElementsBase;

public class TestSetup {
    @BeforeTest
    public void setupSuite(ITestContext context) {
        Base base = new Base();
        base.initPage();

        context.getSuite().setAttribute("elementsBaseClass", new ElementsBase(Base.getWebDriver()));
        Base.getLogger().info("Starting test execution");
    }
}
