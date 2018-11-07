package com.jiangnan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import utils.Log;
import utils.Utils;

public class TestNewWindow {
	WebDriver driver;
	
	@Test
	public void testTwoWindow() {
		driver.get("file:///E:/Selenium/example/newWindow.html");
		driver.findElement(By.partialLinkText("2")).click();
		Utils.switchWindow();
		driver.findElement(By.id("username")).sendKeys("jiangnan");
		Log.info("Switched to window:"+driver.getTitle());
		Utils.sleep(1000);
		Utils.switchWindow();
		Log.info("Switched to window:"+driver.getTitle());
		
		Utils.sleep(2000);
		
	}
	
	@Test
	public void testSwitchWindow() {
		driver.get("file:///E:/Selenium/example/newWindow.html");
		driver.findElement(By.partialLinkText("3")).click();
		String hdll = driver.getWindowHandle();
		Utils.switchWindow("windName11");
		driver.findElement(By.name("username")).sendKeys("123456");
		Utils.sleep(2000);
		driver.close();
		Utils.switchWindow(hdll);
		System.out.println(driver.getTitle());
		Utils.sleep(2000);
	}

	@Test
	public void testNewWindow() {
		driver.get("file:///E:/Selenium/example/newWindow.html");
		Utils.sleep(1000);
		driver.findElement(By.partialLinkText("2链接到")).click();
		driver.findElement(By.partialLinkText("3链接到")).click();
		Utils.switchWindow(1, "id");
		driver.findElement(By.id("username")).sendKeys("jack");
		Utils.sleep(3000);
		Utils.switchWindow(3, "3链接到name.html");
		// driver.findElement(By.name("username")).sendKeys("chenjiangnan");
		System.out.println(driver.getTitle());

	}

	@BeforeMethod
	public void beforeMethod() {
		driver = Utils.openBrowser("firefox");
	}

	@AfterMethod
	public void afterMethod() {
		Utils.sleep(5000);
		driver.quit();
	}

}
