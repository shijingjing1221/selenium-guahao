package com.guahao;

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

public class Start {
	private WebDriver driver;
	private String baseUrl;
	private SeleniumHelper seleniumHelper;

	@Before
	public void setUp() throws Exception {
		driver = ChooseBrowser.myBrowser();
		seleniumHelper = new SeleniumHelper(driver);
		baseUrl = ConfProperty.baseUrl;
		driver.get(baseUrl);
	}
	
	
	public void loginThePage() {
		seleniumHelper.clickUtilClickable(By.linkText("登录"));
		seleniumHelper.clickUtilClickable(By.cssSelector("#djdl_menui li:nth-child(1)"));
		seleniumHelper.sendkeyUtilPresence(By.id("mobileQuickLogin"), ConfProperty.mobileNumber);
		seleniumHelper.sendkeyUtilPresence(By.id("pwQuickLogin"), ConfProperty.password);
		driver.findElement(By.id("quick_login")).click();
	}
	
	
	public Set<Cookie> getSession(){
		seleniumHelper.waitUtilPresenceOfElementLocated(By.linkText("退出"), By.linkText("个人中心"));
		Set<Cookie> allCookies = driver.manage().getCookies();
		for (Cookie loadedCookie : allCookies) {
		    System.out.println(String.format("%s -> %s", loadedCookie.getName(), loadedCookie.getValue()));
		}
		return allCookies;
	}

	@Test
	public void guahao() throws Exception {
		loginThePage();
		Set<Cookie> allCookies = getSession();
		Integer current = 0;
		List<Thread> tArray = new ArrayList<Thread>();
		Thread t;
		while (current < ConfProperty.windowCount) {
			t = new MakeOrder("window " + current, allCookies);
			tArray.add(t);
			++current;
		}
		//Start Each session
		for(Thread th: tArray){
			th.start();
		}
		// Exit the parent processes until all the child are finished
		while (tArray.size() > 0) {
			Integer index = 0;
			while (index < tArray.size()) {
				for (int j = 0; j < tArray.size(); j++) {
					Thread tt = tArray.get(j);
					if (tt.isAlive()) {
						System.out.println("Main thread will be alive till the child thread is live");
						Thread.sleep(18000); //5 minutes
					} else {
						tArray.remove(tt);
					}
				}
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		 driver.quit();
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
