package com.jiangnan;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import utils.ReadFile;

public class NewTestData {
  @Test(dataProvider = "dp")
  public void f(String name, String age, String height) {
	  System.out.println("姓名:"+name+", 年龄:"+age +", 身高 :"+height);
  }

  @BeforeMethod
  public void beforeMethod() {
  }

  @AfterMethod
  public void afterMethod() {
  }

  @DataProvider
  public Object[][] dp() {
	  
    return ReadFile.getTestDataFromExcel("E:\\Selenium\\code\\", "测试数据.xlsx", "Sheet1");
  }

}
