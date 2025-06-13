package com.demoqa.pages;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demoqa.utilities.PropertiesHandler;

public class Base {
    private static final Logger logger = LoggerFactory.getLogger(Base.class);
    protected static WebDriver webDriver;

    public Base() {
        
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public static WebDriver initDriver() {
        try {
            Properties properties = new PropertiesHandler().loadEnvironment();

            switch (properties.getProperty("browserType")) {
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
        }
        catch(IOException e) {
            logger.error(null, e);
        }

        webDriver.manage().window().maximize();
        return webDriver;
    }
}
