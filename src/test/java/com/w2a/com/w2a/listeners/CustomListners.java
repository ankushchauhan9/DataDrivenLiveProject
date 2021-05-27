package com.w2a.com.w2a.listeners;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.TestBase;
import com.w2a.utilities.MonitoringMail;
import com.w2a.utilities.TestConfig;
import org.testng.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CustomListners extends TestBase implements ITestListener, ISuiteListener {


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

    @Override
    public void onStart(ISuite suite) {

    }

    @Override
    public void onFinish(ISuite suite) {

        MonitoringMail mail = new MonitoringMail();
        String msgBody = null;
        try {
            msgBody = "http://"+ InetAddress.getLocalHost().getHostAddress()+":8080/job/DataDrivenLiveProject/Extent_20Report";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, msgBody);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
