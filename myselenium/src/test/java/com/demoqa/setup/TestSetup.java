package com.demoqa.setup;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.demoqa.pages.Base;
import com.demoqa.utilities.DatabaseHandler;
import com.demoqa.utilities.LoggingHandler;

@Listeners(com.demoqa.utilities.LoggingHandler.class)
public class TestSetup {
    protected WebDriver driver;

    @BeforeSuite
    public void setupSuite() {
        LoggingHandler.setupExtent();
        new Base().initPage();

        if (!DatabaseHandler.verifyConnection()) {
            this.cleanup();
            Assert.fail("Database connection could not be established");
        }
        LoggingHandler.log("Starting test execution");
    }

    @AfterSuite
    public void cleanup() {
        Base.cleanup();
    }
}
