package com.guahao;

import org.openqa.selenium.WebDriver;

import com.common.Browser;

public class ChooseBrowser {
	
	public static WebDriver myBrowser(){
//		WebDriver driver = Browser.getChrome();
		WebDriver driver = Browser.getFirefox(); 
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}

}
