package ecshop;

/**
 * ������
 */
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import static org.testng.Assert.*;
public class testECShopSearch {
	WebDriver driver;
  @Test
  public void f() throws InterruptedException {
	  driver.get("http://localhost/ws/upload/index.php");
	  driver.findElement(By.id("keyword")).sendKeys("�ƶ�");
	  driver.findElement(By.name("imageField")).click();
	  Thread.sleep(3000);
	  String c = driver.findElement(By.xpath(".//*[@id='pager']/span/b")).getText();
	  assertEquals(c,"3");
	  
	  driver.findElement(By.id("keyword")).clear();
	  driver.findElement(By.id("keyword")).sendKeys("�ƶ�����");
	  driver.findElement(By.name("imageField")).click();
	  Thread.sleep(3000);
	  String c1 = driver.findElement(By.xpath(".//*[@id='pager']/span/b")).getText();
	  assertEquals(c1,"0");
	  String c2 = driver.findElement(By.xpath("//*[contains(text(),'�ҵ���Ʒ')]")).getText();
	  assertEquals(c2,"�޷���������Ҫ�ҵ���Ʒ��");
	  
  }

  @BeforeMethod
  public void beforeMethod() {
	  System.setProperty("webdriver.firefox.bin",
		"F:\\Program Files\\Fire\\firefox.exe");
	  driver = new FirefoxDriver();
	  
  }

  @AfterMethod
  public void afterMethod() throws InterruptedException {
	  Thread.sleep(1000);
	  driver.quit();
  }

}
