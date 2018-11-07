package com.jiangnan;

import utils.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class TestLog4j {
	WebDriver driver;

  
@Test
  public void f() {
	  driver.get("http://baidu.com");
	  Log.info("Baidu page is loaded");
	  try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		Log.info("Catched InterruptedException");
		e.printStackTrace();
	}
  }


 
@BeforeMethod
  public void beforeMethod() {
	  Log.info(" Try to Open browser");
	  System.setProperty("webdriver.firefox.bin",
		"F:\\Program Files\\Fire\\firefox.exe");
	  driver = new FirefoxDriver();
	  Log.info("Browser is opened");
  }


@AfterMethod
  public void afterMethod() {
	  Log.info("Try to close browser");
	  driver.quit();
	  Log.info("Browser is closed");
  }

}
