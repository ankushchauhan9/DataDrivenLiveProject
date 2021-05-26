package com.w2a.testcases;

import com.w2a.base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class BankManagerLoginTest extends TestBase {

    @Test
    public void bankManagerLoginTest() throws InterruptedException {

        logger.debug("Inside loginAsbankManager() method");
       // driver.findElement(By.cssSelector(OR.getProperty("bmlbtn"))).click();
        click("bmlbtn");

        Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))), "Element not present !!");
        logger.debug("loginAsbankManager() method successfully completed !!!");
    }
}
