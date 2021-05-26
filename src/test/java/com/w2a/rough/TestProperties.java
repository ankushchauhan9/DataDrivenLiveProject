package com.w2a.rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {


    public static void main(String args[]) throws IOException {

        Properties config = new Properties();
        FileInputStream fi = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\config.properties");
        config.load(fi);
        System.out.println(config.getProperty("browser"));
    }

}


