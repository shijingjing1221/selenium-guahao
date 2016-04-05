package com.guahao;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.common.PropertyHelper;
import com.common.SeleniumUtil;

public class GuahaoV1 {

	public static String trueName = PropertyHelper.getKeyValue("trueName");
	public static String persionId = PropertyHelper.getKeyValue("persionId");
	public static String orderHospital = PropertyHelper.getKeyValue("orderHospital");
	public static String orderDepartment = PropertyHelper.getKeyValue("orderDepartment");
	public static String orderDate = PropertyHelper.getKeyValue("orderDate");

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	public WebDriverWait wait;
	public WebDriverWait longWait;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		// System.setProperty("webdriver.chrome.driver",
		// "/opt/chromium-browser/chromedriver");
		// driver = new ChromeDriver();
		baseUrl = "http://www.bjguahao.gov.cn/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 30);
		longWait = new WebDriverWait(driver, 300);
		SeleniumUtil.driver = driver;
	}


	public void findHospitalPage() throws Exception {
		driver.get(baseUrl);
		WebElement searchHospital = driver.findElement(By.id("words"));
		searchHospital.sendKeys(orderHospital);
		searchHospital.sendKeys(Keys.ENTER);
		SeleniumUtil.clickUtilClickable(By.linkText(orderHospital));
	}

	@Test
	public void testGuahaoTest2() throws Exception {
		// driver.get(baseUrl + "comm/yyks.php?hpid=142");
		findHospitalPage();
		SeleniumUtil.clickUtilClickable(By.id("denglu"));
		SeleniumUtil.sendkeyUtilPresence(By.id("truename"), trueName);
		SeleniumUtil.sendkeyUtilPresence(By.id("sfzhm"), persionId);

		SeleniumUtil.waitForInput(By.id("yzm"));
		driver.findElement(By.xpath("//img[contains(@src,'http://images.guahao114.com/images/v2_logindl.gif')]"))
				.click();

		SeleniumUtil.clickUtilClickable(By.linkText(orderDepartment));

		Boolean isOrderStarted = false;
		while (!isOrderStarted) {
			try {
				SeleniumUtil.clickUtilClickable(By.xpath("//a[contains(@href, 'date1=" + orderDate + "')]"),
						new WebDriverWait(driver, 5));
				isOrderStarted = true;
			} catch (TimeoutException ex) {
				System.out.println("Timeout Message: " + ex.getMessage());
				driver.navigate().refresh();
				// Following refresh code will cause 059 error
				// driver.navigate().to(driver.getCurrentUrl());
				isOrderStarted = false;
			}
		}
		driver.switchTo().frame(driver.findElement(By.className("cboxIframe")));

		SeleniumUtil.openInTabUtailCliable(By.linkText("预约挂号"));
		System.out.println("The iFrame of clicked element is:" + driver.switchTo().defaultContent().getTitle());

		SeleniumUtil.switichTab();
        
		SeleniumUtil.clickUtilClickable(By.xpath("//input[contains(@value,'点击获取')]"));
		SeleniumUtil.selectByValueUtilSelectable(By.id("baoxiao"), "1");
		SeleniumUtil.waitForInput(By.id("dxcode1"));

		driver.findElement(By.xpath("//img[contains(@src,'../images/v2_queren.gif')]")).click();
	}

	@After
	public void tearDown() throws Exception {
		// driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
