package com.xiaomi;

import org.openqa.selenium.WebDriver;

import com.common.Browser;

public class ChooseBrowser {
	
	public static WebDriver myBrowser(){
		return Browser.getChrome();
//		return Browser.getFirefox();
	}

}
