package com.demoqa.pages;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import org.testng.Reporter;

import com.demoqa.utilities.LoggingHandler;
import com.demoqa.utilities.PropertiesHandler;

import ch.qos.logback.classic.LoggerContext;

public class Base extends LoadableComponent<Base> {
    private static final Logger logger = LoggerFactory.getLogger(Base.class);
    protected static WebDriver webDriver;
    protected static String baseURL;

    public Base() {
    }

    public static WebDriver getWebDriver() {
        if (webDriver == null) {
            initDriver();
        }
        return webDriver;
    }

    public void initPage() {
        initDriver();
        initBaseURL();
        configureImplicitWait();
        load();
        isLoaded();
    }

    public static void log(String message) {
        logger.info(message);
        Reporter.log(message);
    }

    private static void initDriver() {
        log("Starting WebDriver initialization");
        try {
            Properties properties = new PropertiesHandler().loadEnvironment();
            String browser = properties.getProperty("browserType");

            switch (browser) {
                case "chrome": {
                    webDriver = new ChromeDriver();
                    break;
                }
                case "firefox": {
                    webDriver = new FirefoxDriver();
                    break;
                }
                case "edge": {
                    webDriver = new EdgeDriver();
                    break;
                }
                case "safari": {
                    webDriver = new SafariDriver();
                    break;
                }
                default: {
                    webDriver = new ChromeDriver();
                    break;
                }
            }
            log("Successfully initialized WebDriver with: " + browser);
        } catch (IOException e) {
            log("Issues initializing WebDriver");
            return;
        }

        webDriver.manage().window().maximize();
    }

    private static void initBaseURL() {
        log("Starting BaseURL initialization");
        try {
            Properties properties = new PropertiesHandler().loadEnvironment();
            String baseURL = properties.getProperty("baseURL");

            if (baseURL.equals(null)) {
                log("baseURL not set in properties file");
            } else {
                Base.baseURL = baseURL;
            }
            log("Successfully initialized BaseURL: " + Base.baseURL);
        } catch (IOException e) {
            log("Issues initializing BaseURL");
            return;
        }
    }

    private static void configureImplicitWait() {
        log("Starting ImplicitWait initialization");
        try {
            Properties properties = new PropertiesHandler().loadEnvironment();
            int timeout = Integer.parseInt(properties.getProperty("implicitWait"));

            if (baseURL.equals(null)) {
                log("ImplicitWait not set in properties file");
            } else {
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
            }
            log("Successfully initialized BaseURL");
        } catch (IOException e) {
            log("Issues initializing BaseURL");
            return;
        }
    }

    @Override
    protected void load() {
        Base.log("Loading baseURL page");
        Base.webDriver.get(baseURL);
    }

    @Override
    protected void isLoaded() throws Error {
        String url = Base.webDriver.getCurrentUrl();

        Assert.assertTrue(url.endsWith("login"));
        Base.log("Successfully navigated to baseURL");
    }

    public static void cleanup() {
        log("Cleaning up environment");
        webDriver.close();
        log("Successfully cleaned up");
    }
}
