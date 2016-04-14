package com.guahao.singlethread;

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
import com.guahao.ChooseBrowser;
import com.guahao.ConfProperty;

public class Start {
	private WebDriver driver;
	private String baseUrl;
	private SeleniumHelper seleniumHelper;
	public WebDriverWait secondWait;
	public WebDriverWait minuteWait;
	public WebDriverWait quickRefreshWait;

	@Before
	public void setUp() throws Exception {
		driver = ChooseBrowser.myBrowser();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		seleniumHelper = new SeleniumHelper(driver);
		baseUrl = ConfProperty.baseUrl;
		driver.get(baseUrl);

		secondWait = new WebDriverWait(driver, 5, 1);
		minuteWait = new WebDriverWait(driver, 60, 1);
		quickRefreshWait = new WebDriverWait(driver, ConfProperty.waitBeforeStartTimeSecond, 1);
	}

	@Test
	public void guahao() throws Exception {
		loginThePage();
		findHospitalPage();
		waitForBegin();
		makeTheOrder();
		confirmTheInfo();
	}

	public void loginThePage() {
		seleniumHelper.clickUtilClickable(By.linkText("登录"));
		seleniumHelper.clickUtilClickable(By.cssSelector("#djdl_menui li:nth-child(1)"));
		seleniumHelper.sendkeyUtilPresence(By.id("mobileQuickLogin"), ConfProperty.mobileNumber);
		seleniumHelper.sendkeyUtilPresence(By.id("pwQuickLogin"), ConfProperty.password);
		driver.findElement(By.id("quick_login")).click();
	}

	public Set<Cookie> getSession() {
		seleniumHelper.waitUtilPresenceOfElementLocated(By.linkText("退出"), By.linkText("个人中心"));
		Set<Cookie> allCookies = driver.manage().getCookies();
		for (Cookie loadedCookie : allCookies) {
			System.out.println(String.format("%s -> %s", loadedCookie.getName(), loadedCookie.getValue()));
		}
		return allCookies;
	}

	public void findHospitalPage() {

		WebElement searchHospital = driver.findElement(By.id("words"));
		searchHospital.clear();
		searchHospital.sendKeys(ConfProperty.orderHospital);
		searchHospital.sendKeys(Keys.ENTER);
		keepRefreshing(By.linkText(ConfProperty.orderHospital));
		// seleniumHelper.clickUtilClickable(By.linkText(ConfProperty.orderHospital));
	}

	public void waitForBegin() {
		// seleniumHelper.clickUtilClickable(By.linkText(ConfProperty.orderDepartment));
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

		seleniumHelper.openInTabUtilCliable(
				By.xpath("//div[@id='ksorder_djgh_doctor']/descendant::a[text()='预约挂号'][last()]"));// Click 预约挂号
		seleniumHelper.switichTab();

	}

	public void confirmTheInfo() {
		keepRefreshing(By.id("btnSendCodeOrder"));
		try{
		seleniumHelper.confirm();
		}catch(Exception ex){
			
		}
		
			
		Boolean isloaded = false;
		while (!isloaded) {
			try {
				seleniumHelper.selectByValueUtilSelectable(By.id("Rese_db_dl_idselect"), "1");
				seleniumHelper.waitForInput(By.id("Rese_db_dl_dxyzid"));

				driver.findElement(By.id("Rese_db_qryy_btn")).click();
				isloaded = true;
			} catch (TimeoutException ex) {
				System.out.println("Timeout Message: " + ex.getMessage());
				driver.navigate().refresh();
				isloaded = false;
			}
		}
	}

	public void keepClicking(By waitBy) {
		seleniumHelper.clickUtilClickable(waitBy, quickRefreshWait);
	}

	public void keepRefreshing(By waitBy) {
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
	
	
	public void keepRefreshing(By waitBy, Integer secondToWait) {
		WebDriverWait selfDefinedWait = new WebDriverWait(driver, secondToWait);
		Boolean isStarted = false;
		while (!isStarted) {
			try {
				seleniumHelper.clickUtilClickable(waitBy, selfDefinedWait);
				isStarted = true;
			} catch (TimeoutException ex) {
				System.out.println("Timeout Message: " + ex.getMessage());
				driver.navigate().refresh();
				isStarted = false;
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		// driver.quit();
	}

}
