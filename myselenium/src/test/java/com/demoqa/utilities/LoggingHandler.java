package com.demoqa.utilities;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class LoggingHandler extends AppenderBase<ILoggingEvent> implements ITestListener, IInvokedMethodListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getMethod().getMethodName());
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            System.out.println("Failure Reason: " + throwable.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getMethod().getMethodName());
    }

    // Other ITestListener methods can be overridden as needed

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("Starting method: " + method.getTestMethod().getMethodName());
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("Finished method: " + method.getTestMethod().getMethodName());
    }

    @Override
    protected void append(ILoggingEvent event) {
        String message = event.getFormattedMessage();

        // Optionally include log level, thread, etc.
        String fullMessage = String.format("[%s] %s", event.getLevel(), message);

        // true = also send to System.out
        Reporter.log(fullMessage, true);
    }

}
