package com.jiangnan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Utils;

public class TestJS {
	WebDriver driver;
	@BeforeMethod
	  public void beforeMethod() {
		  driver = Utils.openBrowser("firefox");
	  };


	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

	@Test
	public void testA() throws Exception {
		driver.get("file:///E:/Selenium/example/id.html");
		String js = "arguments[0].setAttribute(arguments[1],arguments[2]);";
		WebElement we1 = driver.findElement(By.id("username"));
		if(Utils.getElementStatus(we1)){
		Utils.executeJS(js, we1, "value", "987654321");
		}
		Utils.sleep(3000);

	}

}
