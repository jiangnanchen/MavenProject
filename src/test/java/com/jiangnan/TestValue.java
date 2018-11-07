package com.jiangnan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import utils.Utils;

public class TestValue {
	WebDriver driver;
  @Test
  public void testValue() {
	  driver.get("file:///E:/Selenium/example/id.html");
	  WebElement un = driver.findElement(By.id("username"));
	  Utils.inpuValue(un, "123");
	  Utils.sleep(3000);
	  Utils.assertValue(un, "123");
	  Utils.sleep(3000);
	  
  }
  
  @Test
  public void testValueB() {
	  driver.get("file:///E:/Selenium/example/xpath.html");
	  WebElement info = driver.findElement(By.id("txt1"));
	  Utils.assertContainsValue(info,"Hello Hello!");
	  Utils.sleep(3000);
	  
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
