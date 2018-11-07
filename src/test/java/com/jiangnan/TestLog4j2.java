package com.jiangnan;

import utils.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestLog4j2 {
	WebDriver driver;

	@Before
	public void setUp() throws Exception {

		Log.info(" Try to Open browser");
		
		System.setProperty("webdriver.firefox.bin",
				"F:\\Program Files\\Fire\\firefox.exe");
		driver = new FirefoxDriver();
		Log.info("Browser is opened");
	}

	@After
	public void tearDown() throws Exception {
		Log.info("Try to close browser");
		driver.quit();
		Log.info("Browser is closed");
	}

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
}
