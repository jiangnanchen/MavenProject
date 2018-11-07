package ecshop.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import utils.Log;
import utils.Utils;
import static org.testng.Assert.*;

public class SearchResultPage extends BasePage {
//	WebDriver driver;
	
	//搜索结果个数
	@FindBy(how = How.TAG_NAME,using="b")
	@CacheLookup
	WebElement count;
	
	
	public SearchResultPage(WebDriver driver){
		super(driver);
//		PageFactory.initElements(driver, this);
	}
	
	/**
	 * 等待变成预期标题的搜索结果页
	 * @param driver
	 * @param title
	 */
	public SearchResultPage(WebDriver driver,String title){
		super(driver,title);

	}
	//获得搜索结果个数
	public String getCount(){
		String result = count.getText();
		return result;

	}
}
