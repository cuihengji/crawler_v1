package com.rkylin.crawler.engine.flood.operation;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CrawlerOperation {

	public static void javaScriptClick(WebDriver driver, WebElement element) {
		if (element != null && element.isEnabled()) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		}
	}

	/**
	 * 通过xpath找到显示的网页元素
	 * 
	 * @param driver
	 * @param xpath
	 * @param isDisplayed
	 *            元素是否要求在页面显示
	 * @return
	 */
	public static final WebElement getElementByXpath(WebDriver driver, String xpath, boolean isDisplayed) {
		WebElement element = null;
		driver.switchTo().defaultContent();
		List<WebElement> webElementList = driver.findElements(By.xpath(xpath));
		if (webElementList != null && webElementList.size() > 0) {
			for (WebElement webElement : webElementList) {
				if (!isDisplayed) {
					element = webElement;
					break;
				} else {
					if (webElement.isDisplayed()) {
						element = webElement;
						break;
					}
				}
			}
		}
		if (element == null) {
			List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
			if (iframes == null || iframes.size() == 0) {
				return element;
			}
			for (WebElement iframe : iframes) {
				try {
					driver.switchTo().defaultContent();
					driver.switchTo().frame(iframe);
					List<WebElement> elementList = driver.findElements(By.xpath(xpath));
					if (elementList != null && elementList.size() > 0) {
						for (WebElement webElement : elementList) {
							if (webElement.isDisplayed()) {
								element = webElement;
								break;
							}
						}
					} else {
						List<WebElement> subIframes = driver.findElements(By.tagName("iframe"));
						if (subIframes == null || subIframes.size() == 0) {
							continue;
						}
						for (WebElement subIframe : subIframes) {
							try {
								driver.switchTo().frame(subIframe);
								elementList = driver.findElements(By.xpath(xpath));
								if (elementList != null && elementList.size() > 0) {
									for (WebElement webElement : elementList) {
										if (webElement.isDisplayed()) {
											element = webElement;
											break;
										}
									}
								}
							} catch (Exception ex) {
								continue;
							}
						}
					}
				} catch (Exception e) {
					continue;
				}
				if (element != null) {
					break;
				}
			}
		}

		return element;
	}

	public static final List<WebElement> getElementsByXpath(WebDriver webDriver, String xpath) {

		List<WebElement> elements = null;
		webDriver.switchTo().defaultContent();
		List<WebElement> webElementList = webDriver.findElements(By.xpath(xpath));
		if (webElementList != null && webElementList.size() > 0) {
			elements = webElementList;
			return elements;
		}
		List<WebElement> iframes = webDriver.findElements(By.tagName("iframe"));
		if (iframes == null || iframes.size() == 0) {
			return elements;
		}
		for (WebElement iframe : iframes) {
			try {
				webDriver.switchTo().defaultContent();
				webDriver.switchTo().frame(iframe);
				List<WebElement> elementList = webDriver.findElements(By.xpath(xpath));
				if (elementList != null && elementList.size() > 0) {
					elements = elementList;
					break;
				} else {
					List<WebElement> subIframes = webDriver.findElements(By.tagName("iframe"));
					for (WebElement subIframe : subIframes) {
						try {
							webDriver.switchTo().frame(subIframe);
							elementList = webDriver.findElements(By.xpath(xpath));
							if (elementList != null && elementList.size() > 0) {
								elements = elementList;
								break;
							}
						} catch (Exception ex) {
							continue;
						}
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		return elements;
	}

	public static void input(WebDriver driver, String xpath, String text) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement e = wait.until(new ElementPresenceCondition(xpath, false));
		e.clear();
		e.sendKeys(text);
	}

	public static void click(WebElement element) {
		boolean isClickable = element.isEnabled();
		if (isClickable) {
			element.click();
		}
	}

	public static void scrollTo(WebElement element) {
		Coordinates coordinate = ((Locatable) element).getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();
	}

	public static void scrollToBottom(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void scrollToBottom(WebDriver driver, long milliSeconds) throws Exception {

		int screenHeight = ((int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);
		int scrollHeight = (int) (screenHeight * 0.9);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String js = "return document.body.clientHeight;";
		long q = (long) jse.executeScript(js);

		for (long i = scrollHeight; i <= q; i += scrollHeight) {
			jse.executeScript("document.body.scrollTop=" + i);
			Thread.sleep(milliSeconds);
			q = (long) jse.executeScript("return document.body.clientHeight;");
		}
	}

	public static void scrollToBottom(WebDriver webDriver, long milliSeconds, long scrollHeight) throws Exception {

		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		String js = "return document.body.clientHeight;";
		long q = (long) jse.executeScript(js);

		for (long i = scrollHeight; i <= q; i += scrollHeight) {
			jse.executeScript("document.body.scrollTop=" + i);
			Thread.sleep(milliSeconds);
			q = (long) jse.executeScript("return document.body.clientHeight;");
		}
	}

	/**
	 * This method will helps us to switch to a New window
	 * 
	 * @param driver
	 */
	public static void switchToNewWindow(WebDriver driver) {
		Set<String> s = driver.getWindowHandles();
		Iterator<String> itr = s.iterator();
		itr.next();
		String w = itr.next();
		driver.switchTo().window(w);
	}

	/**
	 * This method will helps us to switch to a Old window
	 * 
	 * @param driver
	 */
	public static void switchToOldWindow(WebDriver driver) {
		Set<String> s = driver.getWindowHandles();
		Iterator<String> itr = s.iterator();
		String w = itr.next();
		driver.switchTo().window(w);
	}

	/**
	 * This method can be used to get page load time.
	 * 
	 * @param driver
	 * @return
	 */
	public static long getPageLoadTime(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		long navigationStart = (Long) js.executeScript("return window.performance.timing.navigationStart;");
		long loadEventEnd = (Long) js.executeScript("return window.performance.timing.loadEventEnd;");
		long loadtime = (loadEventEnd - navigationStart) / 1000;

		return loadtime;
	}

	private static class ElementPresenceCondition implements ExpectedCondition<WebElement> {
		private String xpath;
		private boolean isDisplayed;

		public ElementPresenceCondition(String xpath, boolean isDisplayed) {
			this.xpath = xpath;
			this.isDisplayed = isDisplayed;
		}

		@Override
		public WebElement apply(WebDriver driver) {
			return getElementByXpath(driver, xpath, isDisplayed);
		}
	}
}
