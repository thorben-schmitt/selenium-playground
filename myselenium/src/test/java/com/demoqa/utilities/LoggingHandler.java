package com.demoqa.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.demoqa.pages.Base;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class LoggingHandler implements ITestListener, IInvokedMethodListener {
    private static final Logger logger = LoggerFactory.getLogger(Base.class);
    protected static ExtentReports extent;
    protected static ExtentTest test;

    public static void setupExtent() {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/test-output/ExtentReport.html");
        htmlReporter.config().setDocumentTitle("Automation Report");
        htmlReporter.config().setReportName("Selenium Test Results");
        htmlReporter.config().setTheme(Theme.STANDARD);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    public static void log(String message) {
        log(message, false);
    }

    public static void log(String message, boolean onlyTerminal) {
        if (!onlyTerminal) {
            logger.info(message);
            Reporter.log(message);
        } else {
            logger.info(message);
        }
    }

    public static void logStep(String message) {
        test.info(message);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getName());
        test.pass("Result as expected");
        // test.log(Status.PASS, "Result as expected");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getName());
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            test.fail(throwable.getMessage());
            // test.log(Status.FAIL, throwable.getMessage());
        }

        WebDriver driver = Base.getWebDriver();

        try {
            Properties properties = new PropertiesHandler().loadEnvironment();
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String screenshotName = result.getName() + "_" + timestamp + ".png";

            String destPath = properties.getProperty("testResultsOutput") + "/media/" + screenshotName;
            FileUtils.copyFile(screenshot, new File(destPath));

            test.addScreenCaptureFromPath("media/" + screenshotName);
        } catch (IOException e) {
            test.warning("Could not attach screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getName());
        test.skip("Skipped test");
        // test.log(Status.SKIP, "Skipped test");
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        log("Starting " + method.getTestMethod().getMethodName());
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        log("Finished " + method.getTestMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    /*
     * @Override
     * protected void append(ILoggingEvent event) {
     * String message = event.getFormattedMessage();
     * 
     * String fullMessage = String.format("[%s] %s", event.getLevel(), message);
     * 
     * Reporter.log(fullMessage, true);
     * }
     */
    public static ExtentReports getExtent() {
        return extent;
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void setTest(ExtentTest test) {
        LoggingHandler.test = test;
    }

}
