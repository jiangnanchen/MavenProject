package com.jiangnan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import utils.Utils;

public class TestSelectDropDown {
	WebDriver driver;
  @Test
  public void f() {
	  driver.get("file:///E:/Selenium/example/select.html");
	  
	  WebElement sweb = driver.findElement(By.id("menus_navlist"));
	  
	  Utils.selectDropDown(sweb, "byindex", "2");
	  Utils.selectDropDown(sweb, "byvalue", "users");
	  Utils.selectDropDown(sweb, "byvisibletext", "…ÃµÍ…Ë÷√");
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
