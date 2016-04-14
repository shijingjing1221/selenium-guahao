package com.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Browser {
	public static WebDriver getFirefox() {
		FirefoxProfile fp = new FirefoxProfile();
		// set something on the profile...
		DesiredCapabilities dc = DesiredCapabilities.firefox();
		dc.setCapability(FirefoxDriver.PROFILE, fp);
		WebDriver driver = new FirefoxDriver(dc);
		return driver;

	}

	public static WebDriver getChrome() {
		System.setProperty("webdriver.chrome.driver", "./chromedriver");
		ChromeOptions options = new ChromeOptions();
		// set some options
		DesiredCapabilities dc = DesiredCapabilities.chrome();
		dc.setCapability(ChromeOptions.CAPABILITY, options);
		WebDriver driver = new ChromeDriver(dc);
		return driver;
	}

}
