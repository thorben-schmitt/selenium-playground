package com.demoqa.setup;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import com.demoqa.pages.Base;
import com.demoqa.pages.elements.ElementsBase;

@Listeners(com.demoqa.utilities.LoggingHandler.class)
public class TestSetup {
    @BeforeTest
    public void setupSuite(ITestContext context) {
        new Base().initPage();

        context.getSuite().setAttribute("elementsBaseClass", new ElementsBase());
        Base.log("Starting test execution");
    }

    @AfterSuite
    public void cleanup() {
        Base.cleanup();
    }
}
