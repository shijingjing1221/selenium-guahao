package com.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Browser {
	public static WebDriver getFirefox() {
		return new FirefoxDriver();

	}

	public static WebDriver getChrome() {
		System.setProperty("webdriver.chrome.driver", "./chromedriver");
		return new ChromeDriver();
	}

}
