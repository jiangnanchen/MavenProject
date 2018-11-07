package com.jiangnan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import utils.Utils;

public class TestSelect {
	WebDriver driver;
  @Test
  public void testSelectA() {
	  driver.get("file:///E:/Selenium/example/select.html");
	  WebElement option1 = driver.findElement(By.id("brand"));
	  
	  
	  //����Ʒ�������򲻿ɶ�ѡ
	  WebElement brand = driver.findElement(By.id("brand"));
	  Utils.assertNotMultiple(brand);
	  //�����б����Զ�ѡ
	  WebElement mn = driver.findElement(By.id("menus_navlist"));
	  Utils.assertMultiple(mn);
	  
	  //���Ե�ǰĬ��ֵ  ѡ����ڡ�����Ʒ�ơ�
	  Utils.assertSelectedOption(option1, "����Ʒ��");
	  Utils.sleep(3000);
	  Utils.assertOptionContains(option1, "����","����");
  }
  
  @Test
  public void testSelectB() {
	  driver.get("file:///E:/Selenium/example/select.html");
	  //�����б����Զ�ѡ
	  WebElement mn = driver.findElement(By.id("menus_navlist"));
	  Utils.assertMultiple(mn);
	  Utils.selectDropDown(mn, "byindex", "1");
	  Utils.selectDropDown(mn, "byindex", "3");
	  Utils.selectDropDown(mn, "byindex", "4");
	  Utils.sleep(1000);
	  //���������б�ѡ����� ��Ա�б� �Ͷ���
	  Utils.assertSelectContains(mn, "��Ա�б�","����");
	  //�����б������ѡ��ĸ�����5
	  Utils.assertOptionsCount(mn, 5);
	  //�����б��ѡ�и�����ȷ3
	  Utils.assertSelectOptionsCount(mn, 3);
	  Utils.sleep(1000);
  }
  @BeforeMethod
  public void beforeMethod() {
	  driver = Utils.openBrowser("firefox");
  }

  @AfterMethod
  public void afterMethod() {
	  driver.quit();
  }

}
