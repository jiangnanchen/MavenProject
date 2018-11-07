package ecshop.tc;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import ecshop.page.AdvancedSearchpage;
import ecshop.page.SearchResultPage;

import utils.BaseTest;
import utils.Constants;
import utils.Log;
import utils.ReadFile;
import utils.Utils;
import static org.testng.Assert.*;
public class TestAdvancedSearch extends BaseTest {
//	WebDriver driver;
	AdvancedSearchpage asp;
	SearchResultPage srp;
	
  @Test(dataProvider = "dp")
  public void f(
		    String kw, //关键字
			String cg,
			String bd,
			String minp,
			String maxp,
			String ext,
			String dt,
			String cl,
			String expCount //预期结果个数
		  ) throws InterruptedException {
	  asp = new AdvancedSearchpage(driver);
	  asp.get();
	  srp = asp.advancedSearch(kw, cg, bd, minp, maxp, ext, dt, cl);
	  String actCount = srp.getCount();
	  try{
	  assertEquals(actCount,expCount);
	  
	  		}catch(Exception e){
			  	e.printStackTrace();
				//同一个包下的静态类方法，可以直接调用
				Log.error(e.getMessage());
				Log.error("waring:江南-断言失败，failse! ActResult:"+actCount);
				String sTestCaseName=this.getClass().getName();
				Utils.takeScreenshot(sTestCaseName);
		  }
  }

//  @BeforeMethod
//  @Parameters({"browser"})
//  public void beforeMethod() {
//
//	  driver = Utils.openBrowser("chrome");
//  }
  //以下是测试套件专用设置启动
//  @BeforeMethod
//  @Parameters({"browser"})
//  public void beforeMethod(String b) {
//
//	  driver = Utils.openBrowser(b);
//	  asp = new AdvancedSearchpage(driver);
//  }

//  @AfterMethod
//  public void afterMethod() {
//	  driver.quit();
//  }

  @DataProvider
  public Object[][] dp() {
//    return ReadFile.getTestDataFromExcel("E:\\Selenium\\code\\", "数据_ECShop.xlsx", "高级搜索");
	  return ReadFile.getTestDataFromCSVFile(Constants.DATA_PATH, "数据_ECShop.csv");
  }

}
