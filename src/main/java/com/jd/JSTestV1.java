package com.jd;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.common.SeleniumHelper;
import com.jd.ConfProperty;

public class JSTestV1 {
	private WebDriver driver;
	private String baseUrl;
	public WebDriverWait wait;
	public WebDriverWait longWait;
	public SeleniumHelper seleniumHelper;
	public static Integer baitiaoWindowCount = 4;
	public List<Thread> tArray;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		seleniumHelper = new SeleniumHelper(driver);
		baseUrl = "http://baitiao.jd.com/";
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 30);
		longWait = new WebDriverWait(driver, 300);
		driver.get(baseUrl);
		tArray = new ArrayList<Thread>();
	}

	public void loginThePage() {
		seleniumHelper.clickUtilClickable(By.className("link-login"));
		seleniumHelper.sendkeyUtilPresence(By.id("loginname"), ConfProperty.trueName);
		seleniumHelper.sendkeyUtilPresence(By.id("nloginpwd"), ConfProperty.persionId);

		driver.findElement(By.id("loginsubmit")).click();
	}

	public Set<Cookie> getSession() {
		seleniumHelper.waitUtilPresenceOfElementLocated(new WebDriverWait(driver, 1800), By.linkText("退出"),
				By.linkText("我的交易单"));
		Set<Cookie> allCookies = driver.manage().getCookies();
		for (Cookie loadedCookie : allCookies) {
			System.out.println(String.format("%s -> %s", loadedCookie.getName(), loadedCookie.getValue()));
		}
		return allCookies;
	}

	@Test
	public void testBaitiaoV1() throws Exception {
		loginThePage();
//		runMultipeThread();

		By waitFor = By.xpath("//a[contains(@class, 'buyNow') and contains(@href,'http') ]");
		Boolean isOrderStarted = false;
		while (!isOrderStarted) {
			try {
				seleniumHelper.waitUtilPresenceOfElementLocated(waitFor, new WebDriverWait(driver, 1));
				seleniumHelper.clickUtilClickable(waitFor);
				isOrderStarted = true;
			} catch (TimeoutException ex) {
				System.out.println("Timeout Message: " + ex.getMessage());
				driver.navigate().refresh();
				// Following refresh code will cause 059 error
				// driver.navigate().to(driver.getCurrentUrl());
				isOrderStarted = false;
			}
		}

	}

	public void runMultipeThread() throws Exception {
		Set<Cookie> allCookies = getSession();
		Integer current = 0;
		List<Thread> tArray = new ArrayList<Thread>();
		Thread t;
		while (current < baitiaoWindowCount) {
			t = new ThreadBaiTiaoV1("window " + current, allCookies);
			tArray.add(t);
			++current;
		}
		// Start Each session
		for (Thread th : tArray) {
			th.start();
		}

	}

	@After
	public void tearDown() throws Exception {
		// Exit the parent processes until all the child are finished
		while (tArray.size() > 0) {
			Integer index = 0;
			while (index < tArray.size()) {
				for (int j = 0; j < tArray.size(); j++) {
					Thread tt = tArray.get(j);
					if (tt.isAlive()) {
						System.out.println("Main thread will be alive till the child thread is live");
						try {
							Thread.sleep(18000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // 5 minutes
					} else {
						tArray.remove(tt);
					}
				}
			}
		}
//		driver.quit();
		// String verificationErrorString = verificationErrors.toString();
		// if (!"".equals(verificationErrorString)) {
		// fail(verificationErrorString);
		// }
	}
	//
	// private boolean isElementPresent(By by) {
	// try {
	// driver.findElement(by);
	// return true;
	// } catch (NoSuchElementException e) {
	// return false;
	// }
	// }
	//
	// private boolean isAlertPresent() {
	// try {
	// driver.switchTo().alert();
	// return true;
	// } catch (NoAlertPresentException e) {
	// return false;
	// }
	// }
	//
	// private String closeAlertAndGetItsText() {
	// try {
	// Alert alert = driver.switchTo().alert();
	// String alertText = alert.getText();
	// if (acceptNextAlert) {
	// alert.accept();
	// } else {
	// alert.dismiss();
	// }
	// return alertText;
	// } finally {
	// acceptNextAlert = true;
	// }
	// }
}
