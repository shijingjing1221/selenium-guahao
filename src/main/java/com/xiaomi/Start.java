package com.xiaomi;

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
		driver = new FirefoxDriver();
		seleniumHelper = new SeleniumHelper(driver);
//		 System.setProperty("webdriver.chrome.driver",
//		 "/opt/chromium-browser/chromedriver");
//		 driver = new ChromeDriver();
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseUrl = ConfProperty.baseUrl;
		driver.get(baseUrl);
	}
	
//	public void loginThePage() {
//		seleniumHelper.clickUtilClickable(By.id("denglu"));
//		seleniumHelper.sendkeyUtilPresence(By.id("truename"), ConfProperty.trueName);
//		seleniumHelper.sendkeyUtilPresence(By.id("sfzhm"), ConfProperty.persionId);
//		seleniumHelper.waitForInput(By.id("yzm"));
//		driver.findElement(By.xpath("//img[contains(@src,'http://images.guahao114.com/images/v2_logindl.gif')]"))
//				.click();
//	}
	
	public void loginThePage() {
		System.out.printf("The username is %s, and the password is %s\n", ConfProperty.username, ConfProperty.password);
		seleniumHelper.clickUtilClickable(By.linkText("登录"));
		seleniumHelper.sendkeyUtilPresence(By.id("username"), ConfProperty.username);
		seleniumHelper.sendkeyUtilPresence(By.id("pwd"), ConfProperty.password);
		driver.findElement(By.id("login-button")).click();
	}
	
	
	public Set<Cookie> getSession(){
		seleniumHelper.waitUtilPresenceOfElementLocated(By.linkText("我的订单"));
		Set<Cookie> allCookies = driver.manage().getCookies();
		for (Cookie loadedCookie : allCookies) {
		    System.out.println(String.format("%s -> %s", loadedCookie.getName(), loadedCookie.getValue()));
		}
		return allCookies;
	}

	@Test
	public void buy() throws Exception {
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
	}

}
