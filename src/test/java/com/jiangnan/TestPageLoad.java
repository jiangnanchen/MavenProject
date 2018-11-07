package com.jiangnan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import utils.Log;
import utils.Utils;

public class TestPageLoad {
	WebDriver driver;
  @Test
  public void f() {
	  driver.get("file:///E:/Selenium/example/link.html");
	  WebElement web = driver.findElement(By.partialLinkText("id"));
//	  Utils.click(web);
	  Utils.clickAndWait(web);
	  
	
  }
  
  @Test
  public void testId() {
	  driver.get("file:///E:/Selenium/example/link.html");
	  Utils.sleep(1000);
	  WebElement web = driver.findElement(By.partialLinkText("id"));
	  Utils.clickAndWait(web);
	  Utils.sleep(3000);
	  WebElement web1 = driver.findElement(By.id("username"));
	  Utils.inpuValue(web, "ÕÅÉºÉºÒªË¯¾õ¾õÁË");
	  
  }

  @BeforeMethod
  public void beforeMethod() {
	  driver = Utils.openBrowser("firefox");
  }

  @AfterMethod
  public void afterMethod() {
	  Utils.sleep(3000);
	  driver.quit();
  }

}
