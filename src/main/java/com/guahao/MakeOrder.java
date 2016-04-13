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
import com.guahao.ConfProperty;

public class MakeOrder extends Thread {
	private WebDriver driver;
	private String baseUrl;
	private SeleniumHelper seleniumHelper;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	public WebDriverWait secondWait;
	public WebDriverWait minuteWait;
	public WebDriverWait quickRefreshWait;

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
		driver = ChooseBrowser.myBrowser();
		seleniumHelper = new SeleniumHelper(driver);

		baseUrl = ConfProperty.baseUrl;
		secondWait = new WebDriverWait(driver, 5, 1);
		minuteWait = new WebDriverWait(driver, 60, 1);
		quickRefreshWait = new WebDriverWait(driver, ConfProperty.waitBeforeStartTimeSecond, 1);
		driver.get(baseUrl);

	}

	public void findHospitalPage() {

		WebElement searchHospital = driver.findElement(By.id("words"));
		searchHospital.clear();
		searchHospital.sendKeys(ConfProperty.orderHospital);
		searchHospital.sendKeys(Keys.ENTER);
		keepRefreshing(By.linkText(ConfProperty.orderHospital));
//		seleniumHelper.clickUtilClickable(By.linkText(ConfProperty.orderHospital));
	}

	public void waitForBegin() {
//		seleniumHelper.clickUtilClickable(By.linkText(ConfProperty.orderDepartment));
		keepRefreshing(By.linkText(ConfProperty.orderDepartment));

		Boolean isPageLoaded, isThisWeek;

		isThisWeek = false;
		isPageLoaded = false;
		while (!isPageLoaded || !isThisWeek) {
			try {
				By theLastDay = By.cssSelector("#ksorder_time .ksorder_cen_l_t_c table tr:first-child th:last-child p");
				By nextButton = By.cssSelector("#ksorder_time .ksorder_cen_l_r>a.ksorder_btn_right");
				seleniumHelper.waitUtilPresenceOfElementLocated(theLastDay, secondWait);
				isPageLoaded = true;
				String getLastDay = driver.findElement(theLastDay).getText();
				if (getLastDay.compareTo(ConfProperty.orderDate) < 0) {
					seleniumHelper.clickUtilClickable(nextButton, secondWait);
				} else {
					isThisWeek = true;
				}
			} catch (TimeoutException ex) {
				System.out.println("Timeout Message: " + ex.getMessage());
				driver.navigate().refresh();
				isPageLoaded = false;
			}
		}

		Boolean isOrderStarted = false;
		while (!isOrderStarted) {
			try {
//				By orderHideInput = By
//						.cssSelector("#ksorder_time .ksorder_cen_l_t_c table tr td.ksorder_kyy input[value*=\""
//								+ ConfProperty.orderDate + "\"]");
//				WebElement orderButton = seleniumHelper.parent(orderHideInput);
//				seleniumHelper.clickUtilClickable(orderButton, secondWait);
				
				By orderHideInput = By.xpath("//td[contains(@class,'ksorder_kyy')]/input[contains(@value,'"
								+ ConfProperty.orderDate + "')]/..");
				seleniumHelper.clickUtilClickable(orderHideInput, secondWait);
				
				isOrderStarted = true;
			} catch (TimeoutException ex) {
				System.out.println("Timeout Message: " + ex.getMessage());
				driver.navigate().refresh();
				isOrderStarted = false;
			}
		}
	}

	public void makeTheOrder() {

		seleniumHelper.openInTabUtilCliable(By.xpath("//div[@id='ksorder_djgh_doctor']/div[last()]//a[text()='预约挂号']"));//Click 预约挂号

		seleniumHelper.switichTab();

		seleniumHelper.clickUtilClickable(By.id("btnSendCodeOrder"));
		seleniumHelper.confirm();
		seleniumHelper.selectByValueUtilSelectable(By.id("Rese_db_dl_idselect"), "1");
		seleniumHelper.waitForInput(By.id("Rese_db_dl_dxyzid"));

		driver.findElement(By.id("Rese_db_qryy_btn")).click();
	}

	public void keepClicking(By waitBy) {
		seleniumHelper.clickUtilClickable(waitBy, quickRefreshWait);
	}
	
	public void keepRefreshing(By waitBy){
		Boolean isStarted = false;
		while (!isStarted) {
			try {
				seleniumHelper.clickUtilClickable(waitBy, secondWait);
				isStarted = true;
			} catch (TimeoutException ex) {
				System.out.println("Timeout Message: " + ex.getMessage());
				driver.navigate().refresh();
				isStarted = false;
			}
		}
	}

}
