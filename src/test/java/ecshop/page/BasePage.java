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
		//��ʽ�ȴ����ȴ���ҳ�����ɵ�ǰ����
		Utils.explictWaitTitle(title);
		PageFactory.initElements(driver, this);
	}
	
	
	/**
	 * ����ҳҳ���ͨ�÷���
	 * ע�� ����������ҳ�������ֱ�Ӵ���ҳ��������ô��Ҫ�ڹ��췽���и�url��ֵ
	 */
	
	public void get(){
		driver.get(url);
		//���
//		driver.manage().window().maximize();
	}
	
	/**
	 * �����ҳҳ��ı���
	 * @return
	 */
	public String getTitle(){
		return driver.getTitle();
	}
	
	/**
	 * �����ַ
	 */
	public String getURL(){
		return driver.getCurrentUrl();
	}
	/**
	 * �����ҳԴ��
	 * @return
	 */
	public String getPageSource(){
		return driver.getPageSource();
	}
}
