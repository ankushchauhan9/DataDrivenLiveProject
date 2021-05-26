package com.w2a.testcases;

import com.w2a.base.TestBase;
import com.w2a.com.w2a.listeners.TestUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class AddCustomerTest extends TestBase {

        @Test(dataProviderClass = TestUtils.class, dataProvider = "dp")
        public void addCustomerTest(Hashtable<String, String> data){

            if(!data.get("Runmode").equalsIgnoreCase("Y")){
               throw new SkipException("Test data Skipped as RunMode is No");
            }

            click("addCustBtn");
            type("custFirstName", data.get("firstname"));
            type("custLastName", data.get("lastname"));
            type("custPostCode", data.get("postcode"));
            click("addBtnForCust");

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
            alert.accept();
        }


}
