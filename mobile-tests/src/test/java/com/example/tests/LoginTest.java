package com.example.tests;

import org.testng.annotations.*;
import com.example.base.BaseTest;


public class LoginTest extends BaseTest {


    @Test
    public void testLaunch() {
        System.out.println("App launched successfully.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
