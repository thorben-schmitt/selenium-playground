package com.demoqa.poc;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.demoqa.pages.Base;

public class DemoQATest {
    WebDriver driver;

    @BeforeSuite
    public void prepareComponents() {
        driver = Base.initDriver();
    }

    @Test
    public void test() {

    }

    @Test
    public void test2() {

    }
}
