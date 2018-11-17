package utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import ecshop.page.AdvancedSearchpage;

public class BaseTest {
	  public WebDriver driver;
	  /**
	   * testsuit
	   */
	  
	 /* @BeforeMethod
	  @Parameters({"browser"})
	  public void beforeMethod(String b) {

		  driver = Utils.openBrowser(b);
		  
	  }*/
	  
	  @BeforeMethod
	  @Parameters({"browser"})
	  public void beforeMethod(String b) {

		  driver = Utils.openBrowser(b);
		  
	  }


	  @AfterMethod
	  public void afterMethod() {
		  driver.quit();
	  }

}
