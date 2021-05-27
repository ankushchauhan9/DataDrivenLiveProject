package com.w2a.base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger logger = Logger.getLogger("devpinoyLogger");
    public static ExcelReader excelReader = new ExcelReader(System.getProperty("user.dir") + "/src/test/resources/excel/addCustData.xlsx");
    public static WebDriverWait wait;
    public ExtentReports report = ExtentManager.getInstance();
    public static ExtentTest test;

    @BeforeSuite
    public void setUp() throws IOException {

        Date d = new Date();
        String date = d.toString().replace(":","_").replace(" ", "_");
        System.setProperty("current.date",date);
        PropertyConfigurator.configure("src/test/java/log4j.properties");
        //BasicConfigurator.configure();
        if(driver == null) {
            fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\config.properties");
            config.load(fis);
            logger.debug("Config file is loaded!!");

            fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
            OR.load(fis);
            logger.debug("OR file is loaded!!");

            if(config.getProperty("browser").equals("firefox")){
                System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"//src//test//resources//executables//geckodriver.exe");
                driver = new FirefoxDriver();
            }else if(config.getProperty("browser").equals("chrome")){
                System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
                driver = new ChromeDriver();
                logger.debug("Chrome driver is launched!!");
            }else if(config.getProperty("browser").equals("edge")){
                System.setProperty("webdriver.edge.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\IEDriverServer.exe");
                driver = new EdgeDriver();
            }

            driver.get(config.getProperty("testsiteurl"));
            logger.debug("Navigated to "+ config.getProperty("testsiteurl"));
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, 5);

        }

    }


    public boolean isElementPresent(By by){

        try{
            driver.findElement(by);
            return true;
        }catch(NoSuchElementException e){
            return false;
        }

    }


    public void click(String locator){
        driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        test.log(LogStatus.INFO, "clicking on "+locator);
    }

    public void type(String locator, String value){
        driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        test.log(LogStatus.INFO, "typing on "+locator + " entered value "+ value);
    }

    static WebElement dropdown;
    public void select(String locator, String value){

        dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        dropdown.click();
        Select sel = new Select(dropdown);
        sel.selectByVisibleText(value);
        test.log(LogStatus.INFO, "Selecting from dropdown "+ locator + " entered value as "+ value);
    }



    @AfterSuite
    public void tearDown(){
            driver.quit();
        logger.debug("Test Execution completed");
    }


}
