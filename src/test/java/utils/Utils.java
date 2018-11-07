package utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.javascript.TimeoutError;

import static org.testng.Assert.*;

public class Utils {
	public static WebDriver driver;
	// 是否接受弹框中的要求，默认值是true代表接受，就是点击“确定”
	public static boolean acceptNextAlert = true;

	public static WebDriver openBrowser(String browser) {

		try {
			if (browser.equalsIgnoreCase("firefox")) {
				FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference(
						"security.mixed_content.block_active_content", false);
				profile.setPreference(
						"security.mixed_content.block_display_content", true);
				profile.setPreference(
						"browser.download.manager.showWhenStarting", false);
				profile.setPreference(
								"browser.helperApps.neverAsk.saveToDisk",
								"application/octet-stream,"
										+ "application/vnd.ms-excel,text/csv,application/zip");
				profile.setPreference("browser.download.folderList", "2");
				profile.setPreference("browser.download.dir",
						Constants.DOWNLOAD_PATH);

				// 启动Firefox
				System.setProperty("webdriver.firefox.bin",
						"F:\\Program Files\\Fire\\firefox.exe");
				driver = new FirefoxDriver(profile);

			} else if (browser.equalsIgnoreCase("ie")) {
				DesiredCapabilities ieCapabilities = DesiredCapabilities
						.internetExplorer();
				ieCapabilities.setCapability("nativeEvents", true);
				ieCapabilities.setCapability("unexpectedAlertBehaviour",
						"accept");
				ieCapabilities.setCapability("ignoreProtectedModeSettings",
						true);
				ieCapabilities.setCapability("disable-popup-blocking", true);
				ieCapabilities
						.setCapability(
								InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
								true);
				ieCapabilities.setCapability("requireWindowFocus", false);
				ieCapabilities.setCapability("enablePersistentHover", false);

				System.setProperty("webdriver.id.driver", Constants.IE_DRIVER);
				driver = new InternetExplorerDriver();
			} else if (browser.equalsIgnoreCase("chrome")) {
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability("chrome.switches", Arrays
						.asList("--incognito"));
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--test-type");
				options.addArguments("enable-automation");
				options.addArguments("--disable-infobars");

				capabilities.setCapability(ChromeOptions.CAPABILITY, options);

				System.setProperty("webdriver.chrome.driver",
						Constants.CHROME_DRIVER);
				driver = new ChromeDriver(capabilities);
			} else {
				Log.error("Invalid browser bype: " + browser);
			}
			Log.info("Browser is opened,Type is: " + browser);
			// Maximize 公共部分最大化
			driver.manage().window().maximize();
			// set Wait Time
			driver.manage().timeouts().implicitlyWait(Constants.WAIT_TIME,
					TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(Constants.WAIT_TIME,
					TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(Constants.WAIT_TIME,
					TimeUnit.SECONDS);
			return driver;
		} catch (Exception e) {
			Log.error("Unable to open browser.");
			Log.error(e.getMessage());
			fail(e.getMessage());
			return null;
		}
	}

	/**
	 * 获取当前截图
	 * 
	 * @param sTestCaseName
	 */
	public static void takeScreenshot(String sTestCaseName) {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh_mm_ss");
		Date date = new Date(); // 当前的系统日期时间
		// 截图步骤 以下是截图
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// 目标新的文件路径
		File destFile = new File(Constants.SCREENSHOT + sTestCaseName + "\\"
				+ sTestCaseName + " #" + dateformat.format(date) + ".png");
		// 截图文件复制到目标路径下面
		try {
			FileUtils.copyFile(file, destFile);
		} catch (IOException e) {
			e.printStackTrace();
			// 同一个包下的静态类方法，可以直接调用
			Log.error(e.getMessage());
			fail(e.getMessage());
		}

	}

	/**
	 * 全屏截图的方法
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public static void takeFullScreenShot() throws InterruptedException,
			AWTException {
		/*
		 * //准备文件的路径 String currentPath = System.getProperty("user.dir");
		 * //拼接截图文件路径 String filePath = currentPath + "\\test-output\\snap";
		 */

		// 生成截图文件名称（使用系统日期和时间作为文件名字）
		String filename = DateFormat.getDateTimeInstance().format(
				System.currentTimeMillis()).replace(":", "-")
				+ ".png";
		try {
			Thread.sleep(3000);
			// 全屏截图
			Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit()
					.getScreenSize());
			BufferedImage image = new Robot().createScreenCapture(rec);
			ImageIO.write(image, "png", new File(Constants.SCREENSHOT
					+ filename));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Can not save screentshot!");
		} finally {
			System.out.println("output Success!");
		}
	}

	/**
	 * 执行JS的方法
	 * 
	 * @param js
	 * @param arg1
	 * @return
	 */
	public static Object executeJS(String js, Object... arg1) {
		try {
			Log.info("Execute JS:" + js);
			Log.info("JS arg1:" + arg1);
			return ((JavascriptExecutor) driver).executeScript(js, arg1);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			takeScreenshot("executeJS");
			fail(e.getMessage());
			return null;
		}

	}

	/**
	 * 等待固定毫秒数
	 * 
	 * @param time
	 */
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Log.error("The sleep thread is interrupted.");
			fail(e.getMessage());
		}
	}

	/**
	 * 等待页面加载完成
	 */
	public static void waitForPageLoad() {
		String js = "return document.readyState;";
		while (!"complete".equals((String) executeJS(js))) {
			Log.info("Page is loading......");
			sleep(1000);
		}
		Log.info("The page is loaded.");
	}

	/**
	 * 在30秒内等待网页加载完毕
	 */
	public static void waitForPageLoad30() {
		String js = "return document.readyState;";

		boolean flag = false;
		for (int i = 1; i <= 30; i++) {
			sleep(1000);
			if ("complete".equals((String) executeJS(js))) {
				flag = true;
				Log.info("Page is loaded in " + i + " seconds");
				break;
			}

		}
		if (!flag) {
			fail();
			Log.error("Page is not loaded in 30 seconds.");
			fail("Page is not loaded in 30 seconds.");
		}
	}

	/**
	 * 显示等待， 等待网页标题包含预期字符串
	 * 
	 * @param title
	 * 
	 */
	public static void explictWaitTitle(final String title) {
		WebDriverWait wait = new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
		try {
			wait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driverx) {
					return driverx.getTitle().toLowerCase().contains(
							title.toLowerCase());
				}
			});
		} catch (TimeoutException e) {
			Log.error("Title is not correct.");
			throw new IllegalStateException(driver.getTitle());
		}
	}

	/**
	 * 获取页面元素的状态
	 * 
	 * @param element
	 * @return boolean: true代表可见可用，False代表不可见或不可用
	 */
	public static boolean getElementStatus(WebElement element) {
		if (!element.isDisplayed()) {
			Log.error("The element is not displayed:" + element.toString());
			takeScreenshot("Utils-getElementStatus");

		} else if (!element.isEnabled()) {// disabled 是无效的
			Log.error("The element is disabled:" + element.toString());
			takeScreenshot("Utils-getElementStatus");
			fail("The element is disabled:" + element.toString());
		}
		return element.isDisplayed() && element.isEnabled();
	}

	/**
	 * 选择下拉框的选项
	 * 
	 * @param element
	 * @param flag
	 *            （byvalue:属性值 byindex:通过编号选择 byvisibletext:通过文本选择）
	 * @param data
	 */
	public static void selectDropDown(WebElement element, String flag,
			String data) {
		Select select = new Select(element);
		if (getElementStatus(element)) {
			Log.info("nannan-Select opetion in drop down list " + flag);
			if (flag.equalsIgnoreCase("byvalue")) {
				select.selectByValue(data);
			} else if (flag.equalsIgnoreCase("byindex")) {
				select.selectByIndex(Integer.parseInt(data));
			} else {
				select.selectByVisibleText(data);
			}

			Log.info(data + "nan--is select in drop down list:"
					+ element.toString());
		} else {
			Log.error("nan-The drop down list is not displayed or disabled.");
			fail("nan-The drop down list is not displayed or disabled.");
		}
	}

	/**
	 * 点击页面元素
	 * 
	 * @param element
	 */
	public static void click(WebElement element) {
		try {
			if (getElementStatus(element)) {
				element.click();
				Log.info("The element is clicked.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.error("Method[click]" + e.getMessage());
			fail(e.getMessage());
		}

	}

	/**
	 * 点击页面元素，并且等待新网页的加载完成
	 * 
	 * @param element
	 */
	public static void clickAndWait(WebElement element) {
		// 点击元素
		click(element);
		waitForPageLoad(); // 等待页面加载完成方法
		Log.info("The Page is complete.");
	}

	/**
	 * 向指定的页面元素输入数据的方法
	 * 
	 * @param element
	 * @param value
	 */
	public static void inpuValue(WebElement element, String value) {

		try {
			if (getElementStatus(element)) {
				if (value != null) {
					element.clear();
					element.sendKeys(value);
					Log.info(value + " is input to element.");
				} else {
					Log.error("The value is null.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("element not such bading。。。");
			Log.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * 切换到默认的网页页面
	 */
	public static void switchToDefault() {
		// 切换到默认的网页页面
		driver.switchTo().defaultContent();
		Log.info("Switched to default content.");
	}

	/**
	 * 使用id或name属性来切换frame
	 * 
	 * @param idOrName
	 */
	public static void switchFrame(String idOrName) {
		try {
			// 调用以上的默认网页页面
			switchToDefault();
			// 切换到新的网页页面
			driver.switchTo().frame(idOrName);
			Log.info("Switched to frame,idOrName:" + idOrName);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
			Log.error("No frame to switch.");
			fail(e.getMessage());
		}
	}

	/**
	 * 通过编号index切换frame frame 可以按照顺序进行给frame排序，第一个frame的index为0，第二个为 2，...依次类推。
	 * 
	 * @param index
	 */
	public static void switchFrame(int index) {
		try {
			// 调用以上的默认网页页面
			switchToDefault();
			// 切换到新的网页页面
			driver.switchTo().frame(index);
			Log.info("Switched to frame,index:" + index);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
			Log.error("No frame to switch.");
			fail(e.getMessage());
		}
	}

	/**
	 * 通过其他方法定位Frame,再切换frame(定位元素方法)
	 * 
	 * @param frameElement
	 */
	public static void switchFrame(WebElement frameElement) {
		try {
			if (getElementStatus(frameElement)) {
				// 调用以上的默认网页页面
				switchToDefault();
				// 切换到新的网页页面
				driver.switchTo().frame(frameElement);
				Log.info("Switched to frame.");
			}
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
			Log.error("No frame to switch.");
			fail("No frame to switch.");
		}
	}

	/**
	 * 通过网页内容包含指定字符串来 切换Frame
	 * 
	 * @param pageSource
	 */
	public static void switchFrameByPageSource(String pageSource) {
		// //切换到默认网页页面，方便查找页面标签“frame”
		switchToDefault();
		List<WebElement> frames = driver.findElements(By.tagName("frame"));
		if (frames.size() > 0) {
			boolean flag = false;
			for (int i = 0; i < frames.size(); i++) {
				switchFrame(i);
				if (driver.getPageSource().contains(pageSource)) {
					flag = true;
					break;
				}
			}

			if (flag) {
				Log.info("Switch to frame contains:" + pageSource);
			} else {
				Log.error("No frame contains:" + pageSource);
				fail("No frame contains:" + pageSource);
			}
		} else {
			Log.error("No frame to switch.");
			fail("No frame to switch.");
		}

	}

	/**
	 * 通过窗口名称或句柄来切换窗口
	 * 
	 * @param nameOrHdl
	 */
	public static void switchWindow(String nameOrHdl) {

		try {
			driver.switchTo().window(nameOrHdl);
		} catch (NoSuchWindowException e) {
			e.printStackTrace();
			Log.error("The window cannot be found:" + nameOrHdl);
			fail(e.getMessage());
		}
	}

	/**
	 * 适合当前有2个窗口； 切换到另一个窗口。
	 */
	public static void switchWindow() {
		String org = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		if (allWindows.size() == 2) {
			Iterator<String> it = allWindows.iterator();
			while (it.hasNext()) {
				String currentWindow = it.next();
				if (!currentWindow.equalsIgnoreCase(org)) {
					driver.switchTo().window(currentWindow);
				}
			}
			Log.info("Switched to new window.");
		} else {
			Log.error("There are not two windows.");
			fail("There are not two windows.");
		}

	}

	/**
	 * 切换到符合指定条件的新窗口
	 * 
	 * @param type
	 *            1：ByTitle包含信息 2：ByURL包含信息 3：ByPageSource包含信息
	 * @param value
	 *            要包含的信息
	 */
	public static void switchWindow(int type, String value) {
		String org = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		if (allWindows.size() > 1) {

			boolean flag = false; // 目前未切换到目标窗口
			Iterator<String> it = allWindows.iterator();
			while (it.hasNext()) {
				String currentWindow = it.next();
				if (!currentWindow.equals(org)) {
					driver.switchTo().window(currentWindow);
					String currentValue;

					// 判断切换的窗口和预期的窗口是否一致。
					switch (type) {
					case 1:
						currentValue = driver.getTitle();
						Log.info("Switch window by title value");
						break;
					case 2:
						currentValue = driver.getCurrentUrl();
						Log.info("Switch window by url value");
						break;
					default:
						currentValue = driver.getPageSource();
						Log.info("Switch window by page source value");
						break;
					}
					if (currentValue.contains(value)) {
						flag = true; // 找到了目标的窗口，退出循环

						break;
					}
				}
			}

			if (flag) {
				Log.info("Switch to target window.");
			} else {
				Log.error("Can not find targe window contains:" + value);
				fail("Can not find targe window contains:" + value);
			}

		} else {
			Log.info("Only one window,no other windows.");
			fail("Only one window,no other windows.");
		}
	}

	/**
	 * 判断页面元素是否存在
	 * 
	 * @param by
	 *            条件元素对象
	 * @return 1：true代表元素存在 2：false代表不存在
	 */
	public static boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			Log.info("Can find the element.");
			return true;
		} catch (NoSuchElementException e) {
			// e.printStackTrace();
			Log.error("Can not find the element.");
			return false;

		}

	}

	/**
	 * 断言页面元素存在（出现）
	 * 
	 * @param by
	 */
	public static void assertElementPresent(By by) {
		assertTrue(isElementPresent(by));
	}

	/**
	 * 断言页面元素不存在|不出现|消失
	 * 
	 * @param by
	 */
	public static void assertElementNotPresent(By by) {
		assertFalse(isElementPresent(by));
	}

	/**
	 * 判断是否出现弹框
	 * 
	 * @return true:代表出现 false：代表未出现或消失
	 */
	public static boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			Log.info("Can find alert.");
			return true;
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
			Log.error("Can not fid alert.");
			return false;
		}

	}

	/**
	 * 断言弹框出现|存在
	 */
	public static void assertAlertPresent() {
		assertTrue(isAlertPresent());
	}

	/**
	 * 断言弹框不出现|不存在|消失
	 */
	public static void assertAlertNotPresent() {
		assertFalse(isAlertPresent());
	}

	/**
	 * 获得弹框内容，并且关闭弹框 默认点击“确定”或 “是” 如果点击 取消|否 ，先给acceptNextAlert赋值false,在调用方法
	 * 
	 * @return 弹框中文本内容
	 */
	public static String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				Log.info("Accept the alert.");
				alert.accept();
			} else {
				Log.info("Dismiss the alert.");
				alert.dismiss();
			}

			Log.info("The text in the alert:" + alertText);
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	/**
	 * 断言弹框文本等于预期值，并且通过点击 确定|是 来关闭弹框
	 * 
	 * @param expText
	 */
	public static void assertAlertText(String expText) {

		try {
			String acText = closeAlertAndGetItsText();
			assertEquals(acText, expText);
		} catch (AssertionError e) {
			e.printStackTrace();
			Log.error("Alert txt is not equals to expected text.");
			fail(e.getMessage());
		}
	}

	/**
	 * 断言弹框文本等于预期值，并且通过 取消|否 来关闭弹框
	 * 
	 * @param expTxt
	 */

	public static void assertAlertTextAndDismiss(String expTxt) {
		// TODO Auto-generated method stub
		acceptNextAlert = false;
		assertAlertText(expTxt);
	}

	/**
	 * 断言弹框的文本中包含预期的那些文本字符串 点击 确定|是 来关闭弹出框
	 * 
	 * @param expTexts
	 *            如：Utils.assertAlertContainsText("Wel","come","!");
	 *            Utils.assertAlertContainsText("用户名不能为空","登录密码不能为空");
	 */
	public static void assertAlertContainsText(String... expTexts) {
		// 获得实际弹框文本 且关闭弹框
		String actText = closeAlertAndGetItsText();
		for (String expText : expTexts) {
			try {
				assertTrue(actText.contains(expText));
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error("Actual alert text:[" + actText
						+ "]does not contains " + "expText:[" + expText + "].");
				fail(e.getMessage());
			}

		}
	}

	/**
	 * 断言弹出框内容包含预期文本，并且点击 取消|否 来关闭弹出框
	 * 
	 * @param expTexts
	 *            如：Utils.assertAlertContainsText("Wel","come","!");
	 */
	public static void assertAlertContainsTextAndDismiss(String... expTexts) {
		acceptNextAlert = false;
		assertAlertContainsText(expTexts);
	}

	/**
	 * 断言元素中的文本等于预期值
	 * 
	 * @param element
	 * @param expText
	 */
	public static void assertText(WebElement element, String expText) {
		if (getElementStatus(element)) {
			if (expText != null) {
				try {
					assertEquals(element.getText(), expText);
				} catch (Exception e) {
					e.printStackTrace();
					Log.error(e.getMessage());
				}

			} else {
				Log.error("Expected text:" + expText + "is null.");
				fail("Expected text:" + expText + "is null.");
			}
		}
	}

	/**
	 * 断言元素的文本包含指定的预期文本字符串
	 * 
	 * @param element
	 * @param expText
	 */
	public static void assertContainsText(WebElement element,
			String... expTexts) {
		if (getElementStatus(element)) {
			for (String expText : expTexts) {
				try {
					assertTrue(element.getText().contains(expText));
				} catch (AssertionError e) {
					e.printStackTrace();
					Log.error("Actual element text:[" + element.getText()
							+ "] does not contains" + " expected text:["
							+ expText + "].");
					fail(e.getMessage());
				}

			}

		}
	}

	/**
	 * 断言value属性值等于预期值 文本框内容=预期值
	 * 
	 * @param element
	 * @param expValue
	 */
	public static void assertValue(WebElement element, String expValue) {
		assertAttribute(element, "value", expValue);
	}

	/**
	 * 断言指定属性值等于预期值
	 * 
	 * @param element
	 * @param attributeName
	 * @param expValue
	 */
	public static void assertAttribute(WebElement element,
			String attributeName, String expValue) {
		if (getElementStatus(element)) {
			if (expValue != null) {
				try {
					assertEquals(element.getAttribute(attributeName), expValue);
				} catch (AssertionError e) {
					// TODO: handle exception
					e.printStackTrace();
					Log.error(e.getMessage());
				}

			} else {
				Log.error("Expected value:[" + expValue + "] is null.");
				fail("Expected value:[" + expValue + "] is null.");

			}
		}
	}

	/**
	 * 断言文本框内容 包含指定的一些预期字符串
	 * 
	 * @param element
	 * @param expValues
	 */
	public static void assertContainsValue(WebElement element,
			String... expValues) {
		assertContainsAttribute(element, "value", expValues);
	}

	/**
	 * 断言指定属性值包含指定的一些预期字符串
	 * 
	 * @param element
	 * @param attributeName
	 * @param expValues
	 */
	public static void assertContainsAttribute(WebElement element,
			String attributeName, String... expValues) {
		if (getElementStatus(element)) {
			for (String expValue : expValues) {
				try {
					assertTrue(element.getAttribute(attributeName).contains(
							expValue));
				} catch (AssertionError e) {
					// TODO: handle exception
					e.printStackTrace();
					Log.error("Actual attribute [" + attributeName
							+ "] value does not conatains expected value:["
							+ expValue + "] .");
					fail(e.getMessage());
				}

			}
		}
	}

	/**
	 * 断言复选框 或 单选按钮被选中
	 * 
	 * @param element
	 */
	public static void assertChecked(WebElement element) {
		if (getElementStatus(element)) {
			try {
				assertTrue(element.isSelected());
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error("The checkbox or radiobutton is not checked.");
				fail(e.getMessage());
			}
		}
	}

	/**
	 * 断言复选框 或 单选按钮 未被选中
	 * 
	 * @param element
	 */
	public static void assertNotChecked(WebElement element) {
		if (getElementStatus(element)) {
			try {
				assertFalse(element.isSelected());
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error("The checkbox or radiobutton is checked.");
				fail(e.getMessage());
			}
		}
	}

	/**
	 * 断言下拉框 或 列表框的当前选项等于预期选项 一般只适合 只有一个选项被选中的情况
	 * 
	 * @param element
	 * @param expOption
	 */
	public static void assertSelectedOption(WebElement element, String expOption) {
		if (getElementStatus(element)) {
			try {
				String actOption = new Select(element).getFirstSelectedOption()
						.getText();
				assertEquals(actOption, expOption);
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error(e.getMessage());
				fail(e.getMessage());
			}

		}
	}

	/**
	 * 断言下拉框 或 列表框中的所有选项中包含预期文本
	 * 
	 * @param element
	 * @param exp
	 */
	public static void assertOptionContains(WebElement element,
			String... expOptions) {
		if (getElementStatus(element)) {
			// 遍历每个要检查的预期选项文本
			for (String expOption : expOptions) {
				// 默认标记为 没有找到
				boolean flag = false;
				// 遍历所有选项
				for (WebElement option : new Select(element).getOptions()) {
					String actOption = option.getText();
					if (actOption.contains(expOption)) {
						flag = true;

						break;
					}
				}

				try {
					assertTrue(flag);
				} catch (AssertionError e) {
					e.printStackTrace();
					Log.error("All options does not contains expected option:"
							+ expOption);
					fail(e.getMessage());
				}
			}
		}
	}

	/**
	 * 短语下拉框或列表框可以多选
	 * 
	 * @param element
	 *            assertNotMultiple
	 */
	public static void assertMultiple(WebElement element) {
		if (getElementStatus(element)) {
			try {
				assertTrue(new Select(element).isMultiple());
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error("The drop wown list is not multiple.");
				fail(e.getMessage());
			}
		}
	}

	/**
	 * 短语下拉框或列表框 不可以多选
	 * 
	 * @param element
	 */
	public static void assertNotMultiple(WebElement element) {
		if (getElementStatus(element)) {
			try {
				assertFalse(new Select(element).isMultiple());
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error("The drop wown list is not multiple.");
				fail(e.getMessage());
			}
		}
	}

	public static void assertSelectContains(WebElement element,
			String... expOptions) {
		for (String expOption : expOptions) {
			boolean flag = false;
			// 遍历所有被选中选项
			for (WebElement option : new Select(element)
					.getAllSelectedOptions()) {
				String actOption = option.getText();

				if (actOption.contains(expOption)) {
					flag = true;
					break;
				}
			}

			try {
				assertTrue(flag);
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error("Select options does not contaions:" + expOption);
				fail("Select options does not contaions:" + expOption);

			}

		}
	}
	/**
	 * 断言下拉列表框的所有选项个数正确
	 * @param element
	 * @param expCount
	 */
	public static void assertOptionsCount(WebElement element,int expCount){
		if(getElementStatus(element)){
			int actCount = new Select(element).getOptions().size();
			try {
				assertEquals(actCount,expCount);
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error(e.getMessage());
				fail("All option count:"+e.getMessage());
			}
			
		}
	}
	/**
	 * 断言下拉列表框的所有当前选中选项个数正确
	 * @param element
	 * @param expCount
	 */
	public static void assertSelectOptionsCount(WebElement element,int expCount){
		if(getElementStatus(element)){
			int actCount = new Select(element).getAllSelectedOptions().size();
			try {
				assertEquals(actCount,expCount);
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error(e.getMessage());
				fail("All Selected option count:"+e.getMessage());
			}
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
