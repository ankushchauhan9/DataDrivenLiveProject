package com.w2a.com.w2a.listeners;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.TestBase;
import org.testng.*;

import java.io.IOException;

public class CustomListners extends TestBase implements ITestListener {


    @Override
    public void onTestStart(ITestResult result) {

        test = report.startTest(result.getName().toUpperCase());
        //checking Run Mode
        if(!TestUtils.isTestRunnable(result.getName(), excelReader)){
            throw new SkipException("Skipping the test "+ result.getName().toUpperCase()+" as Run mode is No");
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.log(LogStatus.PASS, result.getName().toUpperCase()+ " PASS");
        report.endTest(test);
        report.flush();
    }

    @Override
    public void onTestFailure(ITestResult result) {

        System.setProperty("org.uncommons.reportng.escape-output","false");
        try {
            TestUtils.captureScreenshots();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*Reporter.log("Capturing Screenshot");
        Reporter.log("loginAsbankManager() method successfully completed !!!");
        Reporter.log("<a href=\"D:\\vmware_error.png\">Screenshot</a>");*/

        test.log(LogStatus.FAIL, result.getName()+" FAILED with exception "+ result.getThrowable());
        test.log(LogStatus.FAIL, test.addScreencast(TestUtils.screenShotName));
        report.endTest(test);
        report.flush();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.log(LogStatus.SKIP, result.getName().toUpperCase()+ " Skipped");
        report.endTest(test);
        report.flush();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
