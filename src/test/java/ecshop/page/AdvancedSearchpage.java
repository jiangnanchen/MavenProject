package ecshop.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import utils.Constants;


/**
 * 高级搜索网页
 * @author Administrator
 *
 */
		
public class AdvancedSearchpage extends BasePage{
//	WebDriver driver; 父类有该变量

	
	@FindBy(how = How.ID,using="keywords")
	@CacheLookup
	WebElement keywords;
	
	@FindBy(how = How.ID,using="select")
	@CacheLookup
	WebElement select;
	
	@FindBy(how = How.ID,using="brand")
	@CacheLookup
	WebElement brand;
	
	@FindBy(how = How.ID,using="min_price")
	@CacheLookup
	WebElement min_price;

	@FindBy(how = How.ID,using="max_price")
	@CacheLookup
	WebElement max_price;
	
	@FindBy(how = How.NAME,using="goods_type")
	@CacheLookup
	WebElement goods_type;
	
	@FindBy(how = How.NAME,using="attr[172]")
	@CacheLookup
	WebElement attr172;
	
	@FindBy(how = How.NAME,using="attr[185]")
	@CacheLookup
	WebElement attr185;
	
	
	@FindBy(how = How.NAME,using="Submit")
	@CacheLookup
	WebElement Submit;
	
	public AdvancedSearchpage(WebDriver driver){
		super(driver);
		super.url = Constants.ECSHOP_ADVANCED_SEARCH_URL;
	}
	
	public AdvancedSearchpage(WebDriver driver,String title){
		super(driver,title);
		super.url = Constants.ECSHOP_ADVANCED_SEARCH_URL;
	}
	
//	public void get(){
//		driver.get(url);
//	}
	/**
	 * 高级搜索
	 * @throws InterruptedException 
	 */
	public SearchResultPage advancedSearch(
			String kw, //关键字
			String cg,
			String bd,
			String minp,
			String maxp,
			String ext,
			String dt,
			String cl
			) throws InterruptedException{
		keywords.clear();
		keywords.sendKeys(kw);
		new Select(select).selectByVisibleText(cg);
		new Select(brand).selectByVisibleText(bd);
		min_price.clear();
		min_price.sendKeys(minp);
		max_price.clear();
		max_price.sendKeys(maxp);
		new Select(goods_type).selectByVisibleText(ext);
		if(ext.equals("精品手机")){
			Thread.sleep(3000);
			new Select(attr172).selectByVisibleText(dt);
			new Select(attr185).selectByVisibleText(cl);
		}
		Submit.click();
		Thread.sleep(2000);
		return new SearchResultPage(driver);
	}
}

