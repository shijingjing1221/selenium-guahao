package com.jd;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.common.SeleniumHelper;

public class ThreadBaiTiaoV1 extends Thread {
	private WebDriver driver;
	private String baseUrl;
	private SeleniumHelper seleniumHelper;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	public WebDriverWait wait;
	public WebDriverWait longWait;

	public ThreadBaiTiaoV1(String name, Set<Cookie> allCookies) {
		super(name);
		setUp();
		try {
			for (Cookie cookie : allCookies) {
				driver.manage().addCookie(cookie);
			}
		} catch (Exception ex) {
			driver.navigate().refresh();
		}

	}

	@Override
	public void run() {
		System.out.println("Thread- Started" + Thread.currentThread().getName());
		waitForBegin();
		makeTheOrder();
	}

	public void setUp() {
		driver = new FirefoxDriver();
		seleniumHelper = new SeleniumHelper(driver);
		baseUrl = "http://baitiao.jd.com/";
		wait = new WebDriverWait(driver, 30);
		longWait = new WebDriverWait(driver, 300);
		driver.get(baseUrl);
	}

	public void waitForBegin() {
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

	public void makeTheOrder() {

		// driver.switchTo().frame(driver.findElement(By.className("cboxIframe")));
		//
		// seleniumHelper.openInTabUtailCliable(By.linkText("预约挂号"));
		//
		// seleniumHelper.switichTab();
		//
		// seleniumHelper.clickUtilClickable(By.xpath("//input[contains(@value,'点击获取')]"));
		// seleniumHelper.selectByValueUtilSelectable(By.id("baoxiao"), "1");
		// seleniumHelper.waitForInput(By.id("dxcode1"));
		//
		// driver.findElement(By.xpath("//img[contains(@src,'../images/v2_queren.gif')]")).click();
	}

}
