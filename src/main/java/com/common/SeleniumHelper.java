package com.common;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.*;

public class SeleniumHelper {
	public static Integer waitSecond = 30; 
	public static Integer longWaitSecond = 600; 
	protected WebDriver driver;
	protected WebDriverWait defaultWait;
	protected WebDriverWait longWait;
	
	public WebDriverWait getWait(){
		return new WebDriverWait(driver, waitSecond);
	}
	
	public WebDriverWait getWait(Integer myWaitSecond){
		return new WebDriverWait(driver, myWaitSecond);
	}

	public SeleniumHelper(WebDriver driver) {
		this.driver = driver;
	}

	public void clickUtilClickable(By clickedBy, WebDriverWait wait) {
		wait.until(ExpectedConditions.presenceOfElementLocated(clickedBy));
		wait.until(ExpectedConditions.elementToBeClickable(clickedBy));
		WebElement element = driver.findElement(clickedBy);
		element.click();
	}
	
	public void clickUtilClickable(WebElement clickElement, WebDriverWait wait) {
		wait.until(ExpectedConditions.elementToBeClickable(clickElement));
		clickElement.click();
	}

	public void clickUtilClickable(By clickedBy) {
		clickUtilClickable(clickedBy, new WebDriverWait(driver, waitSecond));
	}
	
	public void click(By clickedBy) {
		WebElement element = driver.findElement(clickedBy);
		element.click();
	}
	
	public WebElement parent(By clickedBy){
		WebElement element = driver.findElement(clickedBy);
		return element.findElement(By.xpath(".."));
	}
	

	public void selectByValueUtilSelectable(By selectBy, String value) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.presenceOfElementLocated(selectBy));
		WebElement element = driver.findElement(selectBy);

		// wait.until(ExpectedConditions.elementToBeSelected(selectBy));
		new Select(element).selectByValue(value);
	}

	public void waitForInput(WebElement waitInputElement) {
		waitInputElement.clear();
		String regex = "\\d{6}";
		getWait(longWaitSecond).until(ExpectedConditions.textToBeMatchInElementValue(waitInputElement, regex));
	}

	public void waitForInput(By waitInputBy) {
		WebElement waitInputElement = driver.findElement(waitInputBy);
		waitForInput(waitInputElement);
	}

	public boolean switchWindow(String basicurl) {
		String currentWindow = driver.getWindowHandle();
		Set<String> availableWindows = driver.getWindowHandles();
		if (!availableWindows.isEmpty()) {
			for (String windowId : availableWindows) {
				String currentURL = driver.switchTo().window(windowId).getCurrentUrl();
				if (currentURL.contains(basicurl)) {
					return true;
				} else {
					driver.switchTo().window(currentWindow);
				}
			}
		}
		return false;
	}

	public boolean switichTab() {
		String currentWindow = driver.getWindowHandle();
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "\t");
		driver.switchTo().window(currentWindow);
		return true;
	}

	public void sendkeyUtilPresence(By inputBy, String inputContent) {
		WebDriverWait wait = getWait();
		wait.until(ExpectedConditions.visibilityOfElementLocated(inputBy));
		WebElement element = driver.findElement(inputBy);
		element.clear();
		element.sendKeys(inputContent);
	}

	public void openInTabUtilCliable(By clickedBy) {
		openInTabUtilCliable(clickedBy, getWait());
	}

	public String openInTabUtilCliable(By clickedBy, WebDriverWait wait) {
		wait.until(ExpectedConditions.presenceOfElementLocated(clickedBy));
		wait.until(ExpectedConditions.elementToBeClickable(clickedBy));
		List<WebElement> elements = driver.findElements(clickedBy);
		WebElement element = elements.get(elements.size() - 1);
		element.sendKeys(Keys.CONTROL, Keys.RETURN);
		return element.getAttribute("href");
	}
	
	public void switchFrameUtilLoaded(By by){
		switchFrameUtilLoaded(by, getWait());
	}
	
	public void switchFrameUtilLoaded(By by, WebDriverWait wait) {
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		driver.switchTo().frame(driver.findElement(by));		
	}

	public String openNewBlankWindow() {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "n");
		return driver.getWindowHandle();
	}

	public void waitUtilPresenceOfElementLocated(By by) {
		WebDriverWait wait = getWait();
		waitUtilPresenceOfElementLocated(by, wait);
	}
	
	public void waitUtilPresenceOfElementLocated(By by, WebDriverWait wait) {
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public void waitUtilPresenceOfElementLocated(By... bys) {
		WebDriverWait wait = getWait();
		waitUtilPresenceOfElementLocated(wait, bys);
	}
	
	public void waitUtilPresenceOfElementLocated(WebDriverWait wait, By... bys) {
		for (By by : bys) {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
		}
	}
	
	public void confirm(){
//		String currentWindow = driver.getWindowHandle();
		Alert alert = driver.switchTo().alert();
		alert.accept();
//		driver.switchTo().window(currentWindow);		
	}
}
