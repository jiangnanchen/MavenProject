package com.jiangnan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import utils.Log;
import utils.Utils;

public class TestScreenShotID {
	WebDriver driver;
  @Test
  public void f() {
	  driver.get("file:///E:/Selenium/example/id.html");
	  try{
	  driver.findElement(By.id("343der")).click();
	  } catch(Exception e){
		  	e.printStackTrace();
			//同一个包下的静态类方法，可以直接调用
			Log.error(e.getMessage());
			String sTestCaseName=this.getClass().getName();
			Utils.takeScreenshot(sTestCaseName);
	  }
  }

  @BeforeMethod
  public void beforeMethod() {
	  driver = Utils.openBrowser("firefox");
  }

  @AfterMethod
  public void afterMethod() {
	  driver.quit();
  }

}
