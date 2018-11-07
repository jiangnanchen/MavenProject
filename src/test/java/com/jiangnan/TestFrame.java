package com.jiangnan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import utils.Utils;

public class TestFrame {
	WebDriver driver;
  @Test
  public void f() {
	  driver.get("file:///E:/Selenium/example/main1.html");
	  Utils.switchFrame(0);
	  Utils.inpuValue(driver.findElement(By.id("input1")), "1234785");
//	  Utils.switchToDefault();
	  Utils.switchFrame(1);
	  Utils.inpuValue(driver.findElement(By.id("input2")), "张珊珊");
	  Utils.sleep(3000);
  }

  //通过其他方法定位Frame,再切换frame(定位元素方法)
  @Test
  public void frameElement() {
	  driver.get("file:///E:/Selenium/example/main1.html");
	  WebElement we1 = driver.findElement(By.name("f1"));
	  Utils.switchFrame(we1);
	  Utils.inpuValue(driver.findElement(By.id("input1")), "1234785");
	  Utils.switchToDefault();
	  
	  WebElement we2 = driver.findElement(By.name("f2"));
	  Utils.switchFrame(we2);
	  Utils.inpuValue(driver.findElement(By.id("input2")), "张珊珊");
	  Utils.sleep(3000);
  }
  
//通过网页内容包含指定字符串来 切换Frame
  @Test
  public void testPageSource() {
	  driver.get("file:///E:/Selenium/example/main1.html");
	  Utils.switchFrameByPageSource("input1");
	  Utils.inpuValue(driver.findElement(By.id("input1")), "1234785");

	  Utils.switchFrameByPageSource("input2");
	  Utils.inpuValue(driver.findElement(By.id("input2")), "张珊珊");
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
