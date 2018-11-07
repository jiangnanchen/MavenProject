package com.jiangnan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import utils.Utils;

public class TestAlert {
	WebDriver driver;
  @Test
  public void testA() {
	  driver.get("file:///E:/Selenium/example/alert.html");
	  Utils.assertAlertNotPresent();
	  driver.findElement(By.id("a1")).click();
	  Utils.assertAlertPresent();
	  
  }
  
  @Test
  public void testAlertA() {
	  driver.get("file:///E:/Selenium/example/alert.html");
	  driver.findElement(By.id("a1")).click();
	  Utils.assertAlertContainsText("Wel","come","!");
	  
  }
 
  @Test
  public void testAlertB() {
	  driver.get("file:///E:/Selenium/example/alert.html");
	  driver.findElement(By.id("c1")).click();
	  Utils.assertAlertContainsTextAndDismiss("Press","a","button");
//	  Utils.assertAlertContainsTextAndDismiss("Press");
	  Utils.sleep(3000);
  }
  
  @Test
  public void testAlertC() {
	  driver.get(" http://localhost/ws/upload/user.php");
	  driver.findElement(By.name("submit")).click();
	  Utils.assertAlertContainsText("用户名不能为空11","登录密码不能为空11");

	  Utils.sleep(3000);
  }
  
  
  @Test
  public void testAlert() {
	  driver.get("file:///E:/Selenium/example/alert.html");
//	  Utils.assertAlertNotPresent();
//	  driver.findElement(By.id("a1")).click();
//	  Utils.assertAlertPresent();
//	  Utils.assertAlertText("Welcome!");
	  driver.findElement(By.id("c1")).click();
	  
	  Utils.assertAlertTextAndDismiss("Press a button");
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
