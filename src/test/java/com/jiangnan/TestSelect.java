package com.jiangnan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import utils.Utils;

public class TestSelect {
	WebDriver driver;
  @Test
  public void testSelectA() {
	  driver.get("file:///E:/Selenium/example/select.html");
	  WebElement option1 = driver.findElement(By.id("brand"));
	  
	  
	  //断言品牌下拉框不可多选
	  WebElement brand = driver.findElement(By.id("brand"));
	  Utils.assertNotMultiple(brand);
	  //断言列表框可以多选
	  WebElement mn = driver.findElement(By.id("menus_navlist"));
	  Utils.assertMultiple(mn);
	  
	  //断言当前默认值  选项等于“所有品牌”
	  Utils.assertSelectedOption(option1, "所有品牌");
	  Utils.sleep(3000);
	  Utils.assertOptionContains(option1, "联想","三星");
  }
  
  @Test
  public void testSelectB() {
	  driver.get("file:///E:/Selenium/example/select.html");
	  //断言列表框可以多选
	  WebElement mn = driver.findElement(By.id("menus_navlist"));
	  Utils.assertMultiple(mn);
	  Utils.selectDropDown(mn, "byindex", "1");
	  Utils.selectDropDown(mn, "byindex", "3");
	  Utils.selectDropDown(mn, "byindex", "4");
	  Utils.sleep(1000);
	  //断言下拉列表选项包含 会员列表 和订单
	  Utils.assertSelectContains(mn, "会员列表","订单");
	  //断言列表框所有选项的个数是5
	  Utils.assertOptionsCount(mn, 5);
	  //断言列表框选中个数正确3
	  Utils.assertSelectOptionsCount(mn, 3);
	  Utils.sleep(1000);
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
