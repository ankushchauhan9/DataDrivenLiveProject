package com.w2a.com.w2a.listeners;

import com.w2a.base.TestBase;
import com.w2a.utilities.ExcelReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

public class TestUtils extends TestBase {

    public static String screenShotPath;
    public static String screenShotName;
    public static void captureScreenshots() throws IOException {


        Date d = new Date();
        screenShotName = d.toString().replace(":","_").replace(" ","_")+".jpg";
        screenShotPath = System.getProperty("user.dir")+"/src/Screenshots/"+screenShotName;
        File srcfile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcfile,new File(screenShotPath));
    }

    @DataProvider (name = "dp")
    public Object[][] getdata(Method m){

        String sheetName = m.getName();
        int row = excelReader.getRowCount(sheetName);
        int col = excelReader.getColumnCount(sheetName);

        Object data[][] = new Object[row-1][1];
        Hashtable<String, String> table = null;

        for(int r = 2; r<= row; r++){
            table = new Hashtable<String, String>();
            for(int c = 0; c< col; c++){
                table.put(excelReader.getCellData(sheetName, c,1), excelReader.getCellData(sheetName, c,r));
                data[r-2][0] = table;
            }
        }

        return data ;
    }

    public static boolean isTestRunnable(String testName, ExcelReader reader){

        String sheetName = "Test Suite";
        int rows = reader.getRowCount(sheetName);

        for(int i =2; i <= rows; i++){
               String testcase=  reader.getCellData(sheetName, "TCID", i);
               if(testcase.equalsIgnoreCase(testName)){
                   String runMode = reader.getCellData(sheetName, "Runmode", i);
                   if(runMode.equalsIgnoreCase("Y")){
                        return true;
                   }else{
                       return false;
                   }
               }

        }
        return false;
    }



}
