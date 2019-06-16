package com.web2data.engine.crawler.browser.impl.a;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ElementPresenceCondition implements ExpectedCondition<WebElement> {
	
	private static final Logger logger = Logger.getLogger(ElementPresenceCondition.class);
	
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
					logger.error(e);
					continue;
				}
				if (element != null) {
					break;
				}
			}
		}

		return element;
	}
}
