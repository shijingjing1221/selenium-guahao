package com.xiaomi;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
	public WebDriverWait quickRefreshWait;

	public MakeOrder(String name, Set<Cookie> allCookies) {
		super(name);
		setUp();

		for (Cookie cookie : allCookies) {
			driver.manage().addCookie(cookie);
		}
		driver.navigate().refresh();

	}

	public void setUp() {
		driver = ChooseBrowser.myBrowser();
		seleniumHelper = new SeleniumHelper(driver);
		baseUrl = ConfProperty.baseUrl;
		secondWait = new WebDriverWait(driver, 5);//Timeout within 5 second, and retry every 500 MilliSecond
		quickRefreshWait =  new WebDriverWait(driver, ConfProperty.waitBeforeStartTimeSecond  , 1);//Timeout within 1 hour second, and retry every 1 MilliSecond
		driver.get(baseUrl);

	}

	@Override
	public void run() {
		System.out.println("Thread- Started" + Thread.currentThread().getName());
		preselect();
		waitForStart();
		clickBuy();
		keepClicking(By.id("nextBtn"));
		clickOut();
		chooseAddress();
		clickOut();
		
	}
	
	public void preselect(){
		By choosePrice = By.xpath("//li[contains(@title,'"+ConfProperty.price+"')]");
		By chooseColor = By.xpath("//li[contains(@title,'"+ConfProperty.color+"')]");
		
		seleniumHelper.clickUtilClickable(choosePrice, secondWait);
		seleniumHelper.clickUtilClickable(chooseColor, secondWait);
	}
	

	public void keepClicking(By waitBy){
		seleniumHelper.clickUtilClickable(waitBy, quickRefreshWait);		
}

	
//	public void keepClicking(By waitBy){
//		Boolean isStarted = false;
//
//		while (!isStarted) {
//			try {
//				seleniumHelper.clickUtilClickable(waitBy, quickRefreshWait);
//				isStarted = true;
//			} catch (Exception ex) {
//				isStarted = false;
//			}
//		}		
//	}
//	
	public void waitForStart(){
		keepClicking(By.cssSelector("li.J_packageItem:last-child"));		
	}
	
	public void clickBuy(){
		keepClicking(By.linkText("立即购买"));
	}
	
	public void clickOut(){
		keepClicking(By.linkText("去结算"));		
	}
	
	public void chooseAddress(){
		keepClicking(By.cssSelector("#J_addressList .address-item.J_addressItem:first-child"));
	}


}
