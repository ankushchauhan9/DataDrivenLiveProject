package com.w2a.testcases;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.TestBase;
import com.w2a.com.w2a.listeners.TestUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class OpenAccountTest extends TestBase {

        @Test(dataProviderClass = TestUtils.class, dataProvider = "dp")
        public void openAccountTest(Hashtable<String, String> data){

                click("openAccountBtn");
                select("custDropDown", data.get("Customer"));
                select("curncyDropdown", data.get("Currency"));
                click("processBtn");

                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
                alert.accept();
                test.log(LogStatus.PASS, "Account created successfully !!!!");

        }



}
