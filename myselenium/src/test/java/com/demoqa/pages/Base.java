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

import com.demoqa.utilities.PropertiesHandler;

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

    public static Logger getLogger() {
        return logger;
    }

    public void initPage() {
        initDriver();
        initBaseURL();
        configureImplicitWait();
        load();
        isLoaded();
    }

    private static void initDriver() {
        logger.info("Starting WebDriver initialization");
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
            logger.info("Successfully initialized WebDriver with: " + browser);
        } catch (IOException e) {
            logger.error("Issues initializing WebDriver", e);
            return;
        }

        webDriver.manage().window().maximize();
        initBaseURL();
    }

    private static void initBaseURL() {
        logger.info("Starting BaseURL initialization");
        try {
            Properties properties = new PropertiesHandler().loadEnvironment();
            String baseURL = properties.getProperty("baseURL");

            if (baseURL.equals(null)) {
                logger.error("baseURL not set in properties file");
            } else {
                Base.baseURL = baseURL;
            }
            logger.info("Successfully initialized BaseURL: " + Base.baseURL);
        } catch (IOException e) {
            logger.error("Issues initializing BaseURL", e);
            return;
        }
    }

    private static void configureImplicitWait() {
        logger.info("Starting ImplicitWait initialization");
        try {
            Properties properties = new PropertiesHandler().loadEnvironment();
            int timeout = Integer.parseInt(properties.getProperty("implicitWait"));

            if (baseURL.equals(null)) {
                logger.error("ImplicitWait not set in properties file");
            } else {
                webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
            }
            logger.info("Successfully initialized BaseURL");
        } catch (IOException e) {
            logger.error("Issues initializing BaseURL", e);
            return;
        }
    }

    @Override
    protected void load() {
        Base.logger.info("Loading baseURL page");
        Base.webDriver.get(baseURL);
    }

    @Override
    protected void isLoaded() throws Error {
        String url = Base.webDriver.getCurrentUrl();

        Assert.assertTrue(url.endsWith("login"));
        Base.logger.info("Successfully navigated to baseURL");
    }

    public static void cleanup() {
        logger.info("Cleaning up environment");
        webDriver.close();
        logger.info("Successfully cleaned up");
    }
}
