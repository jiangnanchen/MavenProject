package ecshop.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import utils.Constants;
import utils.Utils;

public class BasePage {
	public WebDriver driver;
	String url;
	public BasePage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
//		new AjaxElementLoactorFactory(driver,Constants.EXPLICIT_WAIT,this);
	}
	public BasePage(WebDriver driver,String title){
		this.driver = driver;
		//显式等待，等待网页标题变成当前标题
		Utils.explictWaitTitle(title);
		PageFactory.initElements(driver, this);
	}
	
	
	/**
	 * 打开网页页面的通用方法
	 * 注意 ：在子类网页中如果有直接打开网页的需求，那么需要在构造方法中给url赋值
	 */
	
	public void get(){
		driver.get(url);
		//最大化
//		driver.manage().window().maximize();
	}
	
	/**
	 * 获得网页页面的标题
	 * @return
	 */
	public String getTitle(){
		return driver.getTitle();
	}
	
	/**
	 * 获得网址
	 */
	public String getURL(){
		return driver.getCurrentUrl();
	}
	/**
	 * 获得网页源码
	 * @return
	 */
	public String getPageSource(){
		return driver.getPageSource();
	}
}
