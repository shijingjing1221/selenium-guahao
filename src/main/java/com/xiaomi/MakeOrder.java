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

	@Override
	public void run() {
		System.out.println("Thread- Started" + Thread.currentThread().getName());
		preselect();
		waitForStart();
		clickBuy();
	}
	
	public void preselect(){
//		By choosePrice = By.xpath("//li.J_stepItem[contains(@innerText,'高配')]");
//		By chooseColor = By.xpath("//li.J_stepItem[contains(@innerText,'白色')]");
		By choosePrice = By.xpath("//li[contains(@title,'"+ConfProperty.price+"')]");
		By chooseColor = By.xpath("//li[contains(@title,'"+ConfProperty.color+"')]");
		
		WebElement nextButton = driver.findElement(choosePrice);
		seleniumHelper.clickUtilClickable(nextButton, secondWait);
		WebElement buyButton = driver.findElement(chooseColor);
		seleniumHelper.clickUtilClickable(buyButton, secondWait);
	}

	public void waitForStart() {
		Boolean isOrderStarted = false;

		while (!isOrderStarted) {
			try {
				By waitBy = By.cssSelector("li.J_packageItem:last-child");
				WebElement nextButton = driver.findElement(waitBy);
				seleniumHelper.clickUtilClickable(nextButton, secondWait);
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

	public void clickBuy() {
		Boolean isBuy = false;
		while (!isBuy) {
			try {
				WebElement buyButton = driver.findElement(By.linkText("立即购买"));
				seleniumHelper.clickUtilClickable(buyButton, secondWait);
				isBuy = true;
			} catch (TimeoutException ex) {
				System.out.println("Timeout Message: " + ex.getMessage());
				driver.navigate().refresh();
				// Following refresh code will cause 059 error
				// driver.navigate().to(driver.getCurrentUrl());
				isBuy = false;
			}
		}

	}


}
