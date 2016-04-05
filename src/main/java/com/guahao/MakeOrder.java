package com.guahao;

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

public class MakeOrder extends Thread {
	private WebDriver driver;
	private String baseUrl;
	private SeleniumHelper seleniumHelper;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	public WebDriverWait secondWait;

	public MakeOrder(String name, Set<Cookie> allCookies) {
		super(name);
		setUp();
		
		for (Cookie cookie : allCookies) {
			driver.manage().addCookie(cookie);
		}
		driver.navigate().refresh();
		
	}

	@Override
	public void run() {
		System.out.println("Thread- Started" + Thread.currentThread().getName());

		findHospitalPage();

		waitForBegin();
		makeTheOrder();
	}

	public void setUp() {
		driver = new FirefoxDriver();
		seleniumHelper = new SeleniumHelper(driver);
		// System.setProperty("webdriver.chrome.driver",
		// "/opt/chromium-browser/chromedriver");
		// driver = new ChromeDriver();
		baseUrl = ConfProperty.baseUrl;
		secondWait = new WebDriverWait(driver, 5);
		driver.get(baseUrl);
		
	}

	public void findHospitalPage() {

		WebElement searchHospital = driver.findElement(By.id("words"));
		searchHospital.clear();
		searchHospital.sendKeys(ConfProperty.orderHospital);
		searchHospital.sendKeys(Keys.ENTER);
		seleniumHelper.clickUtilClickable(By.linkText(ConfProperty.orderHospital));
	}

	public void waitForBegin() {
		seleniumHelper.clickUtilClickable(By.linkText(ConfProperty.orderDepartment));

		Boolean isPageLoaded, isThisWeek;
		
		isThisWeek = false;
		isPageLoaded= false;
		while (!isPageLoaded || !isThisWeek) {
			try {
				By theLastDay = By.cssSelector("#ksorder_time .ksorder_cen_l_t_c table tr:first-child th:last-child p");
				By nextButton = By.cssSelector("#ksorder_time ksorder_cen_l_r>a");
				seleniumHelper.waitUtilPresenceOfElementLocated(theLastDay,
						secondWait);
				isPageLoaded = true;
				String getLastDay = driver.findElement(theLastDay).getText();
				if(getLastDay.compareTo(ConfProperty.orderDate) < 0){
					seleniumHelper.clickUtilClickable(nextButton,
							secondWait);
				}else{
					isThisWeek = true;
				}
			} catch (TimeoutException ex) {
				System.out.println("Timeout Message: " + ex.getMessage());
				driver.navigate().refresh();
				isPageLoaded = false;
			}
		}
		
		isPageLoaded= false;
		while (!isPageLoaded) {
			try {
				
				isPageLoaded = true;
			} catch (TimeoutException ex) {
				System.out.println("Timeout Message: " + ex.getMessage());
				driver.navigate().refresh();
				isPageLoaded = false;
			}
		}
		Boolean isOrderStarted = false;
		while (!isOrderStarted) {
			try {
//				System.out.println("The selector is: " + "#ksorder_time .ksorder_cen_l_t_c table tr td.ksorder_kyy input[value*=\""+ConfProperty.orderDate+"\"]");
				By orderHideInput = By.cssSelector("#ksorder_time .ksorder_cen_l_t_c table tr td.ksorder_kyy input[value*=\""+ConfProperty.orderDate+"\"]");
//				By orderLink = By.cssSelector("input[value~=\""+ConfProperty.orderDate+"\"]");
//				By orderLink = By.xpath("//#ksorder_time/.ksorder_cen_l_t_c/table/tr/td.ksorder_kyy/input[contains(value, '"+ConfProperty.orderDate+"')]");
//				By orderLink = By.xpath("//#ksorder_time/.ksorder_cen_l_t_c/table/tr/td.ksorder_kyy/input[type='hidden']");
//				By orderLink = By.xpath("//input[contains(value, '"+ConfProperty.orderDate+"')]");
//				seleniumHelper.clickUtilClickable(orderLink);
				WebElement orderLink = seleniumHelper.parent(orderHideInput);
				seleniumHelper.clickUtilClickable(orderLink, secondWait);
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

		seleniumHelper.openInTabUtilCliable(By.linkText("预约挂号"));

		seleniumHelper.switichTab();

		seleniumHelper.clickUtilClickable(By.id("btnSendCodeOrder"));
		seleniumHelper.confirm();
		seleniumHelper.selectByValueUtilSelectable(By.id("Rese_db_dl_idselect"), "1");
		seleniumHelper.waitForInput(By.id("Rese_db_dl_dxyzid"));

		driver.findElement(By.id("Rese_db_qryy_btn")).click();
	}
//	public void makeTheOrder() {
//
//		seleniumHelper.switchFrameUtilLoaded(By.className("cboxIframe"));
//		seleniumHelper.openInTabUtilCliable(By.linkText("预约挂号"));
//
//		seleniumHelper.switichTab();
//
//		seleniumHelper.clickUtilClickable(By.xpath("//input[contains(@value,'点击获取')]"));
//		seleniumHelper.selectByValueUtilSelectable(By.id("baoxiao"), "1");
//		seleniumHelper.waitForInput(By.id("dxcode1"));
//
//		driver.findElement(By.xpath("//img[contains(@src,'../images/v2_queren.gif')]")).click();
//	}

}
