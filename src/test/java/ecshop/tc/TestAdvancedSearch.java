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
		    String kw, //�ؼ���
			String cg,
			String bd,
			String minp,
			String maxp,
			String ext,
			String dt,
			String cl,
			String expCount //Ԥ�ڽ������
		  ) throws InterruptedException {
	  asp = new AdvancedSearchpage(driver);
	  asp.get();
	  srp = asp.advancedSearch(kw, cg, bd, minp, maxp, ext, dt, cl);
	  String actCount = srp.getCount();
	  try{
	  assertEquals(actCount,expCount);
	  
	  		}catch(Exception e){
			  	e.printStackTrace();
				//ͬһ�����µľ�̬�෽��������ֱ�ӵ���
				Log.error(e.getMessage());
				Log.error("waring:����-����ʧ�ܣ�failse! ActResult:"+actCount);
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
  //�����ǲ����׼�ר����������
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
//    return ReadFile.getTestDataFromExcel("E:\\Selenium\\code\\", "����_ECShop.xlsx", "�߼�����");
	  return ReadFile.getTestDataFromCSVFile(Constants.DATA_PATH, "����_ECShop.csv");
  }

}
