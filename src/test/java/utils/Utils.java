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
	// �Ƿ���ܵ����е�Ҫ��Ĭ��ֵ��true������ܣ����ǵ����ȷ����
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

				// ����Firefox
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
			// Maximize �����������
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
	 * ��ȡ��ǰ��ͼ
	 * 
	 * @param sTestCaseName
	 */
	public static void takeScreenshot(String sTestCaseName) {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh_mm_ss");
		Date date = new Date(); // ��ǰ��ϵͳ����ʱ��
		// ��ͼ���� �����ǽ�ͼ
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Ŀ���µ��ļ�·��
		File destFile = new File(Constants.SCREENSHOT + sTestCaseName + "\\"
				+ sTestCaseName + " #" + dateformat.format(date) + ".png");
		// ��ͼ�ļ����Ƶ�Ŀ��·������
		try {
			FileUtils.copyFile(file, destFile);
		} catch (IOException e) {
			e.printStackTrace();
			// ͬһ�����µľ�̬�෽��������ֱ�ӵ���
			Log.error(e.getMessage());
			fail(e.getMessage());
		}

	}

	/**
	 * ȫ����ͼ�ķ���
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public static void takeFullScreenShot() throws InterruptedException,
			AWTException {
		/*
		 * //׼���ļ���·�� String currentPath = System.getProperty("user.dir");
		 * //ƴ�ӽ�ͼ�ļ�·�� String filePath = currentPath + "\\test-output\\snap";
		 */

		// ���ɽ�ͼ�ļ����ƣ�ʹ��ϵͳ���ں�ʱ����Ϊ�ļ����֣�
		String filename = DateFormat.getDateTimeInstance().format(
				System.currentTimeMillis()).replace(":", "-")
				+ ".png";
		try {
			Thread.sleep(3000);
			// ȫ����ͼ
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
	 * ִ��JS�ķ���
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
	 * �ȴ��̶�������
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
	 * �ȴ�ҳ��������
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
	 * ��30���ڵȴ���ҳ�������
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
	 * ��ʾ�ȴ��� �ȴ���ҳ�������Ԥ���ַ���
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
	 * ��ȡҳ��Ԫ�ص�״̬
	 * 
	 * @param element
	 * @return boolean: true����ɼ����ã�False�����ɼ��򲻿���
	 */
	public static boolean getElementStatus(WebElement element) {
		if (!element.isDisplayed()) {
			Log.error("The element is not displayed:" + element.toString());
			takeScreenshot("Utils-getElementStatus");

		} else if (!element.isEnabled()) {// disabled ����Ч��
			Log.error("The element is disabled:" + element.toString());
			takeScreenshot("Utils-getElementStatus");
			fail("The element is disabled:" + element.toString());
		}
		return element.isDisplayed() && element.isEnabled();
	}

	/**
	 * ѡ���������ѡ��
	 * 
	 * @param element
	 * @param flag
	 *            ��byvalue:����ֵ byindex:ͨ�����ѡ�� byvisibletext:ͨ���ı�ѡ��
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
	 * ���ҳ��Ԫ��
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
	 * ���ҳ��Ԫ�أ����ҵȴ�����ҳ�ļ������
	 * 
	 * @param element
	 */
	public static void clickAndWait(WebElement element) {
		// ���Ԫ��
		click(element);
		waitForPageLoad(); // �ȴ�ҳ�������ɷ���
		Log.info("The Page is complete.");
	}

	/**
	 * ��ָ����ҳ��Ԫ���������ݵķ���
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
			Log.error("element not such bading������");
			Log.error(e.getMessage());
			fail(e.getMessage());
		}
	}

	/**
	 * �л���Ĭ�ϵ���ҳҳ��
	 */
	public static void switchToDefault() {
		// �л���Ĭ�ϵ���ҳҳ��
		driver.switchTo().defaultContent();
		Log.info("Switched to default content.");
	}

	/**
	 * ʹ��id��name�������л�frame
	 * 
	 * @param idOrName
	 */
	public static void switchFrame(String idOrName) {
		try {
			// �������ϵ�Ĭ����ҳҳ��
			switchToDefault();
			// �л����µ���ҳҳ��
			driver.switchTo().frame(idOrName);
			Log.info("Switched to frame,idOrName:" + idOrName);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
			Log.error("No frame to switch.");
			fail(e.getMessage());
		}
	}

	/**
	 * ͨ�����index�л�frame frame ���԰���˳����и�frame���򣬵�һ��frame��indexΪ0���ڶ���Ϊ 2��...�������ơ�
	 * 
	 * @param index
	 */
	public static void switchFrame(int index) {
		try {
			// �������ϵ�Ĭ����ҳҳ��
			switchToDefault();
			// �л����µ���ҳҳ��
			driver.switchTo().frame(index);
			Log.info("Switched to frame,index:" + index);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
			Log.error("No frame to switch.");
			fail(e.getMessage());
		}
	}

	/**
	 * ͨ������������λFrame,���л�frame(��λԪ�ط���)
	 * 
	 * @param frameElement
	 */
	public static void switchFrame(WebElement frameElement) {
		try {
			if (getElementStatus(frameElement)) {
				// �������ϵ�Ĭ����ҳҳ��
				switchToDefault();
				// �л����µ���ҳҳ��
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
	 * ͨ����ҳ���ݰ���ָ���ַ����� �л�Frame
	 * 
	 * @param pageSource
	 */
	public static void switchFrameByPageSource(String pageSource) {
		// //�л���Ĭ����ҳҳ�棬�������ҳ���ǩ��frame��
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
	 * ͨ���������ƻ������л�����
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
	 * �ʺϵ�ǰ��2�����ڣ� �л�����һ�����ڡ�
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
	 * �л�������ָ���������´���
	 * 
	 * @param type
	 *            1��ByTitle������Ϣ 2��ByURL������Ϣ 3��ByPageSource������Ϣ
	 * @param value
	 *            Ҫ��������Ϣ
	 */
	public static void switchWindow(int type, String value) {
		String org = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		if (allWindows.size() > 1) {

			boolean flag = false; // Ŀǰδ�л���Ŀ�괰��
			Iterator<String> it = allWindows.iterator();
			while (it.hasNext()) {
				String currentWindow = it.next();
				if (!currentWindow.equals(org)) {
					driver.switchTo().window(currentWindow);
					String currentValue;

					// �ж��л��Ĵ��ں�Ԥ�ڵĴ����Ƿ�һ�¡�
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
						flag = true; // �ҵ���Ŀ��Ĵ��ڣ��˳�ѭ��

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
	 * �ж�ҳ��Ԫ���Ƿ����
	 * 
	 * @param by
	 *            ����Ԫ�ض���
	 * @return 1��true����Ԫ�ش��� 2��false��������
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
	 * ����ҳ��Ԫ�ش��ڣ����֣�
	 * 
	 * @param by
	 */
	public static void assertElementPresent(By by) {
		assertTrue(isElementPresent(by));
	}

	/**
	 * ����ҳ��Ԫ�ز�����|������|��ʧ
	 * 
	 * @param by
	 */
	public static void assertElementNotPresent(By by) {
		assertFalse(isElementPresent(by));
	}

	/**
	 * �ж��Ƿ���ֵ���
	 * 
	 * @return true:������� false������δ���ֻ���ʧ
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
	 * ���Ե������|����
	 */
	public static void assertAlertPresent() {
		assertTrue(isAlertPresent());
	}

	/**
	 * ���Ե��򲻳���|������|��ʧ
	 */
	public static void assertAlertNotPresent() {
		assertFalse(isAlertPresent());
	}

	/**
	 * ��õ������ݣ����ҹرյ��� Ĭ�ϵ����ȷ������ ���ǡ� ������ ȡ��|�� ���ȸ�acceptNextAlert��ֵfalse,�ڵ��÷���
	 * 
	 * @return �������ı�����
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
	 * ���Ե����ı�����Ԥ��ֵ������ͨ����� ȷ��|�� ���رյ���
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
	 * ���Ե����ı�����Ԥ��ֵ������ͨ�� ȡ��|�� ���رյ���
	 * 
	 * @param expTxt
	 */

	public static void assertAlertTextAndDismiss(String expTxt) {
		// TODO Auto-generated method stub
		acceptNextAlert = false;
		assertAlertText(expTxt);
	}

	/**
	 * ���Ե�����ı��а���Ԥ�ڵ���Щ�ı��ַ��� ��� ȷ��|�� ���رյ�����
	 * 
	 * @param expTexts
	 *            �磺Utils.assertAlertContainsText("Wel","come","!");
	 *            Utils.assertAlertContainsText("�û�������Ϊ��","��¼���벻��Ϊ��");
	 */
	public static void assertAlertContainsText(String... expTexts) {
		// ���ʵ�ʵ����ı� �ҹرյ���
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
	 * ���Ե��������ݰ���Ԥ���ı������ҵ�� ȡ��|�� ���رյ�����
	 * 
	 * @param expTexts
	 *            �磺Utils.assertAlertContainsText("Wel","come","!");
	 */
	public static void assertAlertContainsTextAndDismiss(String... expTexts) {
		acceptNextAlert = false;
		assertAlertContainsText(expTexts);
	}

	/**
	 * ����Ԫ���е��ı�����Ԥ��ֵ
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
	 * ����Ԫ�ص��ı�����ָ����Ԥ���ı��ַ���
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
	 * ����value����ֵ����Ԥ��ֵ �ı�������=Ԥ��ֵ
	 * 
	 * @param element
	 * @param expValue
	 */
	public static void assertValue(WebElement element, String expValue) {
		assertAttribute(element, "value", expValue);
	}

	/**
	 * ����ָ������ֵ����Ԥ��ֵ
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
	 * �����ı������� ����ָ����һЩԤ���ַ���
	 * 
	 * @param element
	 * @param expValues
	 */
	public static void assertContainsValue(WebElement element,
			String... expValues) {
		assertContainsAttribute(element, "value", expValues);
	}

	/**
	 * ����ָ������ֵ����ָ����һЩԤ���ַ���
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
	 * ���Ը�ѡ�� �� ��ѡ��ť��ѡ��
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
	 * ���Ը�ѡ�� �� ��ѡ��ť δ��ѡ��
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
	 * ���������� �� �б��ĵ�ǰѡ�����Ԥ��ѡ�� һ��ֻ�ʺ� ֻ��һ��ѡ�ѡ�е����
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
	 * ���������� �� �б���е�����ѡ���а���Ԥ���ı�
	 * 
	 * @param element
	 * @param exp
	 */
	public static void assertOptionContains(WebElement element,
			String... expOptions) {
		if (getElementStatus(element)) {
			// ����ÿ��Ҫ����Ԥ��ѡ���ı�
			for (String expOption : expOptions) {
				// Ĭ�ϱ��Ϊ û���ҵ�
				boolean flag = false;
				// ��������ѡ��
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
	 * ������������б����Զ�ѡ
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
	 * ������������б�� �����Զ�ѡ
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
			// �������б�ѡ��ѡ��
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
	 * ���������б�������ѡ�������ȷ
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
	 * ���������б������е�ǰѡ��ѡ�������ȷ
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
