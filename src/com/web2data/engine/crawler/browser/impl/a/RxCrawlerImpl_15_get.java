package com.web2data.engine.crawler.browser.impl.a;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.rkylin.crawler.engine.flood.util.StringUtil;
import com.web2data.engine.crawler.browser.node.impl.RxNodeImpl;
import com.web2data.open.RxCrawlerException;
import com.web2data.open.RxNode;

public class RxCrawlerImpl_15_get {
	
    // -------------------------------------------------------
    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_15_get.class);


	public static String getPageSource( WebDriver webDriver ) throws RxCrawlerException {
		
		//checkInterrupted();

		String pageSource = "NA";
		try {
			pageSource = StringUtil.removeSpecialChar( webDriver.getPageSource() );
		} catch (Throwable t) {
			logger.error("getPageSource异常！", t);
			throw new RxCrawlerException(999, t.getMessage());
		}
    	
		return pageSource;
	}


	public static String getTitle( WebDriver webDriver ) throws RxCrawlerException {
		
		//checkInterrupted();

		String pageTitle = "NA";
		try {
			pageTitle = StringUtil.removeSpecialChar( webDriver.getTitle() );
		} catch (Throwable t) {
			logger.error("getTitle异常！", t);
			throw new RxCrawlerException(999, t.getMessage());
		}
    	
		return pageTitle;
	}
	

    public static String getCurrentUrl( WebDriver webDriver ) {
		return webDriver.getCurrentUrl();
	}
    

	public static RxNode getNodeByXpath(String xpath, WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();

		try {
			WebElement element = null;
			webDriver.switchTo().defaultContent();
			List<WebElement> webElementList = webDriver.findElements(By.xpath(xpath));
			if (webElementList != null && webElementList.size() > 0) {
				for (WebElement webElement : webElementList) {
					element = webElement;
					break;

				}
			}
			if (element == null) {
				List<WebElement> iframes = webDriver.findElements(By.tagName("iframe"));
				if (iframes == null || iframes.size() == 0) {
					return null;
				}
				for (WebElement iframe : iframes) {
					try {
						webDriver.switchTo().defaultContent();
						webDriver.switchTo().frame(iframe);
						List<WebElement> elementList = webDriver.findElements(By.xpath(xpath));
						if (elementList != null && elementList.size() > 0) {
							for (WebElement webElement : elementList) {
								element = webElement;
								break;
							}
						} else {
							List<WebElement> subIframes = webDriver.findElements(By.tagName("iframe"));
							if (subIframes == null || subIframes.size() == 0) {
								continue;
							}
							for (WebElement subIframe : subIframes) {
								try {
									webDriver.switchTo().frame(subIframe);
									elementList = webDriver.findElements(By.xpath(xpath));
									if (elementList != null && elementList.size() > 0) {
										for (WebElement webElement : elementList) {
											element = webElement;
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

			if (element == null) {
				return null;
			} else {
				return new RxNodeImpl(webDriver, element, xpath);
			}
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
	}
	

	public static List<RxNode> getNodeListByXpath(String xpath, TabWindow newTabWindow, WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();

		List<RxNode> elements = new ArrayList<>();
		try {
			webDriver.switchTo().defaultContent();
			List<WebElement> webElementList = webDriver.findElements(By.xpath(xpath));
			if (webElementList != null && webElementList.size() > 0) {

				for (WebElement webElement : webElementList) {

					RxNode rxNode = new RxNodeImpl(webDriver, webElement, xpath );
					elements.add(rxNode);
				}
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

						for (WebElement webElement : elementList) {
							elements.add(new RxNodeImpl(webDriver, webElement, xpath ));
						}
						break;
					} else {
						List<WebElement> subIframes = webDriver.findElements(By.tagName("iframe"));
						for (WebElement subIframe : subIframes) {
							try {
								webDriver.switchTo().frame(subIframe);
								elementList = webDriver.findElements(By.xpath(xpath));
								if (elementList != null && elementList.size() > 0) {
									for (WebElement webElement : elementList) {
										elements.add(new RxNodeImpl(webDriver, webElement, xpath ));
									}
									break;
								}
							} catch (Exception ex) {
								logger.error(ex,ex);
								continue;
							}
						}
					}
				} catch (Exception e) {
					logger.error(e,e);
					continue;
				}
			}
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
		return elements;
	}
	

	public static List<RxNode> getNodeListByXpath(String xpath, int timeoutSeconds, TabWindow newTabWindow, WebDriver webDriver) throws RxCrawlerException {
		
		//checkInterrupted();
	
		List<RxNode> elements = new ArrayList<>();
		try {
			webDriver.switchTo().defaultContent();
//			List<WebElement> webElementList = webDriver.findElements(By.xpath(xpath));
			List<WebElement> webElementList = findElements(webDriver, By.xpath(xpath), timeoutSeconds);
			
			if (webElementList != null && webElementList.size() > 0) {
	
				for (WebElement webElement : webElementList) {
	
					RxNode rxNode = new RxNodeImpl(webDriver, webElement, xpath );
					elements.add(rxNode);
				}
				return elements;
			}
	
			List<WebElement> iframes = webDriver.findElements(By.tagName("iframe"));
//			List<WebElement> iframes = findElements(webDriver, By.tagName("iframe"), timeoutSeconds);
			if (iframes == null || iframes.size() == 0) {
				return elements;
			}
			
			for (WebElement iframe : iframes) {
				try {
					webDriver.switchTo().defaultContent();
					webDriver.switchTo().frame(iframe);
					
//					List<WebElement> elementList = webDriver.findElements(By.xpath(xpath));
					List<WebElement> elementList = findElements(webDriver, By.xpath(xpath), timeoutSeconds);
					if (elementList != null && elementList.size() > 0) {
	
						for (WebElement webElement : elementList) {
							elements.add(new RxNodeImpl(webDriver, webElement, xpath ));
						}
						break;
					} else {
						List<WebElement> subIframes = webDriver.findElements(By.tagName("iframe"));
//						List<WebElement> subIframes = findElements(webDriver, By.tagName("iframe"), timeoutSeconds);
						for (WebElement subIframe : subIframes) {
							try {
								webDriver.switchTo().frame(subIframe);
//								elementList = webDriver.findElements(By.xpath(xpath));
								elementList = findElements(webDriver, By.xpath(xpath), timeoutSeconds);
								if (elementList != null && elementList.size() > 0) {
									for (WebElement webElement : elementList) {
										elements.add(new RxNodeImpl(webDriver, webElement, xpath ));
									}
									break;
								}
							} catch (Exception ex) {
								logger.error(ex,ex);
								continue;
							}
						}
					}
				} catch (Exception e) {
					logger.error(e,e);
					continue;
				}
			}
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
		return elements;
	}
    

	private static List<WebElement> findElements(final WebDriver driver, final By by, int timeoutSeconds) {
		
		List<WebElement> webElementList = (new WebDriverWait(driver, timeoutSeconds)).until(new ExpectedCondition<List<WebElement>>() {
			@Override
			public List<WebElement> apply(WebDriver d) {
				return driver.findElements(by);
			}
		});
		
		return webElementList;
	}

	
//	public static RxSelectNode getSelectNodeByXpath(String xpath, WebDriver webDriver) throws RxCrawlerException {
//
//		//checkInterrupted();
//
//		try {
//			WebElement element = null;
//			webDriver.switchTo().defaultContent();
//
//			List<WebElement> webElementList = webDriver.findElements(By.xpath(xpath));
//			if (webElementList != null && webElementList.size() > 0) {
//				for (WebElement webElement : webElementList) {
//					element = webElement;
//					break;
//
//				}
//			}
//			if (element == null) {
//				List<WebElement> iframes = webDriver.findElements(By.tagName("iframe"));
//				if (iframes == null || iframes.size() == 0) {
//					return null;
//				}
//				for (WebElement iframe : iframes) {
//					try {
//						webDriver.switchTo().defaultContent();
//						webDriver.switchTo().frame(iframe);
//						List<WebElement> elementList = webDriver.findElements(By.xpath(xpath));
//						if (elementList != null && elementList.size() > 0) {
//							for (WebElement webElement : elementList) {
//								element = webElement;
//								break;
//							}
//						} else {
//							List<WebElement> subIframes = webDriver.findElements(By.tagName("iframe"));
//							if (subIframes == null || subIframes.size() == 0) {
//								continue;
//							}
//							for (WebElement subIframe : subIframes) {
//								try {
//									webDriver.switchTo().frame(subIframe);
//									elementList = webDriver.findElements(By.xpath(xpath));
//									if (elementList != null && elementList.size() > 0) {
//										for (WebElement webElement : elementList) {
//											element = webElement;
//										}
//									}
//								} catch (Exception ex) {
//									continue;
//								}
//							}
//						}
//					} catch (Exception e) {
//						continue;
//					}
//					if (element != null) {
//						break;
//					}
//				}
//			}
//
//			if (element == null) {
//				return null;
//			} else {
//				Select select = new Select(webDriver.findElement(ById.xpath(xpath)));
//	    		return new RxSelectNodeImpl(select);
//	    		
//			}
//		} catch (Throwable t) {
//			logger.error(t.getMessage(), t);
//			throw new RxCrawlerException(999, t.getMessage());
//		}
//		
//	}
}
