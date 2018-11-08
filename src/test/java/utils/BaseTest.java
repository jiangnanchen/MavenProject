package utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import ecshop.page.AdvancedSearchpage;

public class BaseTest {
	  public WebDriver driver;
	  /**
	   * ≤‚ ‘Ã◊º˛”√
	   */
	  sysotem.print
	 /* @BeforeMethod
	  @Parameters({"browser"})
	  public void beforeMethod(String b) {

		  driver = Utils.openBrowser(b);
		  
	  }*/
	  
	  @BeforeMethod
	  @Parameters({"browser"})
	  public void beforeMethod() {

		  driver = Utils.openBrowser("chrome");
		  
	  }


	  @AfterMethod
	  public void afterMethod() {
		  driver.quit();
	  }

}
