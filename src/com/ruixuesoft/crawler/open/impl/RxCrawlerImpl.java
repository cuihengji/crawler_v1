package com.ruixuesoft.crawler.open.impl;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Screen;

import com.Rop.api.ApiException;
import com.Rop.api.DefaultRopClient;
import com.Rop.api.request.PsPictureStreamUploadRequest;
import com.Rop.api.response.PsPictureStreamUploadResponse;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.rkylin.crawler.engine.flood.common.Constant;
import com.rkylin.crawler.engine.flood.common.IpProxyResult;
import com.rkylin.crawler.engine.flood.exception.WebDriverGetException;
import com.rkylin.crawler.engine.flood.model.MessageObject;
import com.rkylin.crawler.engine.flood.proxy.client.ProxyFacade;
import com.rkylin.crawler.engine.flood.util.HTTP;
import com.rkylin.crawler.engine.flood.util.ImageMerger;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
import com.rkylin.crawler.engine.flood.util.StringUtil;
import com.rkylin.crawler.engine.flood.webdriver.WebDriverManager;
import com.rkylin.crawler.engine.flood.websocket.ProxyAddressProducer;
import com.ruixuesoft.crawler.open.RxCrawler;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxHttpContext;
import com.ruixuesoft.crawler.open.RxHttpNetwork;
import com.ruixuesoft.crawler.open.RxHttpNode;
import com.ruixuesoft.crawler.open.RxHttpRequest;
import com.ruixuesoft.crawler.open.RxHttpResponse;
import com.ruixuesoft.crawler.open.RxNode;
import com.ruixuesoft.crawler.open.RxSelectNode;
import com.ruixuesoft.crawler.open.RxSimpleCrawler;
import com.ruixuesoft.crawler.open.Website;
import com.ruixuesoft.crawler.open.geetest.GeeTestCracker;
import com.ruixuesoft.crawler.open.thirdparty.VerifyCodeHelper;
import com.web2data.system.app.AppFacade;
//import com.ruixuesoft.crawler.open.uurecognition.TelPictureRecognition;
import com.youtu.TencentRecognition;

import net.sourceforge.htmlunit.corejs.javascript.EvaluatorException;

public class RxCrawlerImpl implements RxCrawler {

	public static final Logger logger = Logger.getLogger(RxCrawlerImpl.class);
    
	protected WebDriver webDriver;
   	// Tab页处理用变量
	protected TabWindow newTabWindow = new TabWindow();
	protected Accounting accounting;
    
	private int sessionId;
	
	private RxHttpRequest rxHttpRequest;
	private boolean isUsingProxy;
	
	private CloseableHttpClient httpClient;
	private WebClient webClient;
	//Document节点用来存储httpGet或者httpPost返回的page Source,已经被解析的节点,
	//用来做下一步的查询
	private Document httpDocument;
	private String pageSource;
	
	//生成图片名字的时候使用
    private int crawlerid;
    
   	private int ruleVersion;
    private MessageObject messageObject;


	public RxCrawlerImpl() {
    	
    }
    
	public RxCrawlerImpl(WebDriver webDriver, Accounting accounting) {
		super();
		this.webDriver = webDriver;
		this.accounting = accounting;
	}
	

	@Override
	public void open(String url) throws RxCrawlerException {
		open(url, "-1");
	}
	    
	@Override
	public void open(String url, int waitSeconds) throws RxCrawlerException {
		open(url, "-1", waitSeconds);
	}

	@Override
	public void open(String url, String targetKeyword) throws RxCrawlerException {
		open(url, targetKeyword, -1);
	}
	
	@Override
	public void open(String url, String targetKeyword, int waitSeconds) throws RxCrawlerException {
		
		checkInterrupted();
		//打开网页http://www.baidu.com, 在开发平台控制台显示打开的URL
		pushAppTaskLog(url);

		if ((url == null) || (url.equalsIgnoreCase(""))) {
			throw new RxCrawlerException("输入的URL为空或者等于null!");
		}
		
		try {
			WebDriverManager.getIns().get(this.webDriver, url, targetKeyword, waitSeconds);
		} catch (WebDriverGetException e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getCode(), "不能打开指定的URL");
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
		
		accounting.setPages(accounting.getPages() + 1);
	}

	@Override
	public RxNode getNodeByXpath(String xpath) throws RxCrawlerException {
		
		checkInterrupted();

		try {
			WebElement element = null;
			this.webDriver.switchTo().defaultContent();
			List<WebElement> webElementList = this.webDriver.findElements(By.xpath(xpath));
			if (webElementList != null && webElementList.size() > 0) {
				for (WebElement webElement : webElementList) {
					element = webElement;
					break;

				}
			}
			if (element == null) {
				List<WebElement> iframes = this.webDriver.findElements(By.tagName("iframe"));
				if (iframes == null || iframes.size() == 0) {
					return null;
				}
				for (WebElement iframe : iframes) {
					try {
						this.webDriver.switchTo().defaultContent();
						this.webDriver.switchTo().frame(iframe);
						List<WebElement> elementList = this.webDriver.findElements(By.xpath(xpath));
						if (elementList != null && elementList.size() > 0) {
							for (WebElement webElement : elementList) {
								element = webElement;
								break;
							}
						} else {
							List<WebElement> subIframes = this.webDriver.findElements(By.tagName("iframe"));
							if (subIframes == null || subIframes.size() == 0) {
								continue;
							}
							for (WebElement subIframe : subIframes) {
								try {
									this.webDriver.switchTo().frame(subIframe);
									elementList = this.webDriver.findElements(By.xpath(xpath));
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
				return new RxNodeImpl(this.webDriver, element, xpath, accounting, newTabWindow);
			}
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
	}


	@Override
	public List<RxNode> getNodeListByXpath(String xpath) throws RxCrawlerException {
		
		checkInterrupted();

		List<RxNode> elements = new ArrayList<>();
		try {
			webDriver.switchTo().defaultContent();
			List<WebElement> webElementList = webDriver.findElements(By.xpath(xpath));
			if (webElementList != null && webElementList.size() > 0) {

				for (WebElement webElement : webElementList) {

					RxNode rxNode = new RxNodeImpl(this.webDriver, webElement, xpath, accounting, newTabWindow);
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
							elements.add(new RxNodeImpl(this.webDriver, webElement, xpath, accounting, newTabWindow));
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
										elements.add(new RxNodeImpl(this.webDriver, webElement, xpath, accounting, newTabWindow));
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
	
    @Override
	public List<RxNode> getNodeListByXpath(String xpath, int timeoutSeconds) throws RxCrawlerException {
		
		checkInterrupted();
	
		List<RxNode> elements = new ArrayList<>();
		try {
			webDriver.switchTo().defaultContent();
//			List<WebElement> webElementList = webDriver.findElements(By.xpath(xpath));
			List<WebElement> webElementList = findElements(webDriver, By.xpath(xpath), timeoutSeconds);
			
			if (webElementList != null && webElementList.size() > 0) {
	
				for (WebElement webElement : webElementList) {
	
					RxNode rxNode = new RxNodeImpl(this.webDriver, webElement, xpath, accounting, newTabWindow);
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
							elements.add(new RxNodeImpl(this.webDriver, webElement, xpath, accounting, newTabWindow));
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
										elements.add(new RxNodeImpl(this.webDriver, webElement, xpath, accounting, newTabWindow));
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


	private List<WebElement> findElements(final WebDriver driver, final By by, int timeoutSeconds) {
		
		List<WebElement> webElementList = (new WebDriverWait(driver, timeoutSeconds)).until(new ExpectedCondition<List<WebElement>>() {
			@Override
			public List<WebElement> apply(WebDriver d) {
				return driver.findElements(by);
			}
		});
		
		return webElementList;
	}

	
	@Override
	public RxSelectNode getSelectNodeByXpath(String xpath) throws RxCrawlerException {

		checkInterrupted();

		try {
			WebElement element = null;
			this.webDriver.switchTo().defaultContent();

			List<WebElement> webElementList = this.webDriver.findElements(By.xpath(xpath));
			if (webElementList != null && webElementList.size() > 0) {
				for (WebElement webElement : webElementList) {
					element = webElement;
					break;

				}
			}
			if (element == null) {
				List<WebElement> iframes = this.webDriver.findElements(By.tagName("iframe"));
				if (iframes == null || iframes.size() == 0) {
					return null;
				}
				for (WebElement iframe : iframes) {
					try {
						this.webDriver.switchTo().defaultContent();
						this.webDriver.switchTo().frame(iframe);
						List<WebElement> elementList = this.webDriver.findElements(By.xpath(xpath));
						if (elementList != null && elementList.size() > 0) {
							for (WebElement webElement : elementList) {
								element = webElement;
								break;
							}
						} else {
							List<WebElement> subIframes = this.webDriver.findElements(By.tagName("iframe"));
							if (subIframes == null || subIframes.size() == 0) {
								continue;
							}
							for (WebElement subIframe : subIframes) {
								try {
									this.webDriver.switchTo().frame(subIframe);
									elementList = this.webDriver.findElements(By.xpath(xpath));
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
				Select select = new Select(webDriver.findElement(ById.xpath(xpath)));
	    		return new RxSelectNodeImpl(select);
	    		
			}
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
		
	}
	
	@Override
	public void scrollToTop() throws RxCrawlerException {
		
		checkInterrupted();

		try {
			((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, 0)");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(t.getMessage());
		}
	}

	@Override
	public void scrollToBottom() throws RxCrawlerException {

		checkInterrupted();

		try {
			((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(t.getMessage());
		}
	}

	@Override
	public void scroll(int pixels) throws RxCrawlerException {
		
		checkInterrupted();

		try {
			String javaScripit = "window.scrollTo(0, " + pixels + ")";
			((JavascriptExecutor) webDriver).executeScript(javaScripit);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(t.getMessage());
		}
	}

	@Override
	public void scrollTo(RxNode node) throws RxCrawlerException {
		
		checkInterrupted();

		try {
			WebElement webElement = ((RxNodeImpl) node).getElement();

			Coordinates coordinate = ((Locatable) webElement).getCoordinates();
			coordinate.onPage();
			coordinate.inViewPort();

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(t.getMessage());
		}
	}
	
//	@Override
//	public void inputVerifyCode(String verifyImageXpath, String verifyInputTextXpath, String iframeXpath)  throws RxCrawlerException{
//		try {
//			int x = 0, y = 0;
//			
//			if(iframeXpath != null && iframeXpath.trim().length() > 0)
//			{
//				RxNodeImpl iframeNode = (RxNodeImpl) getNodeByXpath(iframeXpath);
//				x = iframeNode.getLocationX();
//				y = iframeNode.getLocationY();
//
//			}
//			
//			logger.info("iframeNode.LocationX:" + x);
//			
//			logger.info("iframeNode.LocationY:" + y);
//			
//			VerifyCodeHelper.inputVerifyCode(webDriver, verifyImageXpath, verifyInputTextXpath, x, y);
//		} catch (Throwable t) {
//			logger.error("selectVerifyCode异常！", t);
//			throw new RxCrawlerException(t.getMessage());
//		}
//	}
	
	@Override
	public void inputVerifyCode(String verifyImageXpath, String verifyInputTextXpath)  throws RxCrawlerException{
		
		checkInterrupted();

		try {
			
			int x = 0, y = 0;
			
			Point iframeLocation = getIframeLocationByVerifyImageXpath(verifyImageXpath);
			if( iframeLocation != null )
			{
				x = iframeLocation.getX();
				y = iframeLocation.getY();
			}
			
			logger.info("inputVerifyCode-iframeNode.LocationX:" + x);
			logger.info("inputVerifyCode-iframeNode.LocationY:" + y);
			
			VerifyCodeHelper.inputVerifyCode(webDriver, verifyImageXpath, verifyInputTextXpath, x, y);
			
			accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);

		} catch (Throwable t) {
			logger.error("selectVerifyCode异常！", t);
			throw new RxCrawlerException(t.getMessage());
		}
	}
	
//	@Override
//	public void selectVerifyCode(String verifyImageXpath, String verifyButtonXpath, String iframeXpath)  throws RxCrawlerException {
//		try {
//			int x = 0, y = 0;
//			
//			if(iframeXpath != null && iframeXpath.trim().length() > 0)
//			{
//				RxNodeImpl iframeNode = (RxNodeImpl) getNodeByXpath(iframeXpath);
//				x = iframeNode.getLocationX();
//				y = iframeNode.getLocationY();
//			}
//			
//			VerifyCodeHelper.selectVerifyCode(webDriver, verifyImageXpath, verifyButtonXpath, x, y);
//		} catch (Throwable t) {
//			logger.error("selectVerifyCode异常！", t);
//			throw new RxCrawlerException(t.getMessage());
//		}
//	}
	
	@Override
	public void selectVerifyCode(String verifyImageXpath, String verifyButtonXpath)  throws RxCrawlerException{
		
		checkInterrupted();

		try {
			
			int x = 0, y = 0;
			
			Point iframeLocation = getIframeLocationByVerifyImageXpath(verifyImageXpath);
			if( iframeLocation != null )
			{
				x = iframeLocation.getX();
				y = iframeLocation.getY();
			}
			
			logger.info("iframeNode.LocationX:" + x);
			logger.info("iframeNode.LocationY:" + y);
			
			VerifyCodeHelper.selectVerifyCode(webDriver, verifyImageXpath, verifyButtonXpath, x, y);
			
			accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);

		} catch (Throwable t) {
			logger.error("selectVerifyCode异常！", t);
			throw new RxCrawlerException(t.getMessage());
		}
		
	}
	
	public WebDriver getWebDriver() {
		return webDriver;
	}

	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}


	@Override
	public void input(String xpath, String text) throws RxCrawlerException {
		
		checkInterrupted();

		try {
			RxNode rxnode = getNodeByXpath(xpath);
			rxnode.input(text);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
	}


	@Override
	public void clear(String xpath) throws RxCrawlerException {
		
		checkInterrupted();

		try {
			RxNode rxnode = getNodeByXpath(xpath);
			rxnode.clear();
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
	}


	@Override
	public void closeAlert() throws RxCrawlerException {

		checkInterrupted();

		//TODO dismiss or accept方法? 测试看看效果
		try {
			//Alert alert = this.webDriver.switchTo().alert();
			Thread.sleep(2000);
			//alert.dismiss();
			this.webDriver.switchTo().alert().accept();
			this.webDriver.switchTo().defaultContent();

		} catch (Throwable t) {
			logger.error("closeAlert异常！", t);
			throw new RxCrawlerException(t.getMessage());
		}

	}

	@Override
	public void closeAlert(int waitSeconds) throws RxCrawlerException {
		
		checkInterrupted();

		try {
			//Alert alert = this.webDriver.switchTo().alert();
			Thread.sleep(waitSeconds*1000);
			//alert.dismiss();
			this.webDriver.switchTo().alert().accept();
			this.webDriver.switchTo().defaultContent();
			
		} catch (Throwable t) {
			logger.error("closeAlert异常！", t);
			throw new RxCrawlerException(t.getMessage());
		}
	}
	
	@Override
	public boolean isAlertDisplayed() throws RxCrawlerException {
		
		checkInterrupted();

		boolean isAlertDisplayed = false;
		
		try {
			Thread.sleep(1*1000);
			this.webDriver.getTitle();
		} catch (UnhandledAlertException e) {
			isAlertDisplayed = true;
			logger.error("UnhandledAlertException异常! ,alert window is displayed.", e);
		} catch (InterruptedException e) {
			logger.error("InterruptedException异常! ,alert window is displayed.", e);
		}
		
		return isAlertDisplayed;
	}

	@Override
	public String getAlertText() throws RxCrawlerException {
		
		checkInterrupted();

		String message = "NA";
		
		try {
			Alert alert = webDriver.switchTo().alert();
			message = alert.getText();
		} catch (Throwable t) {
			logger.error("getAlertText异常！", t);
			throw new RxCrawlerException(t.getMessage());
		}
		return message;
	}
	

	@Override
	public void sleepSeconds( int seconds ) {

		checkInterrupted();

		try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
	

	@Override
	public Object executeJsScript(String jsScript) throws RxCrawlerException {

		checkInterrupted();
		
		try {
    			Object o = ((JavascriptExecutor) webDriver).executeScript(jsScript);	
	    		Thread.sleep(1000);
	    		return o;
			} catch (Throwable t) {
				logger.error(t.getMessage(), t);
				throw new RxCrawlerException(t.getMessage());
			}
	}
	
	@Override
	public boolean isNewTabOpened() throws RxCrawlerException {
		
		checkInterrupted();

		try {
			if (newTabWindow.getNewTabWindowHandleIndex() > 0) {
				return true;
			}
			return false;
		} catch (Throwable t) {
			logger.error("isNewTabOpened异常！", t);
			throw new RxCrawlerException(999, t.getMessage());
		}
	}

	@Override
	public void switchToNewTab() throws RxCrawlerException {
		
		checkInterrupted();

		try {
			int newTabWindowHandleIndex = newTabWindow.getNewTabWindowHandleIndex();

			if (newTabWindowHandleIndex > 0) {
				this.webDriver.switchTo().window(newTabWindow.getTabWindowHandleList().get(newTabWindowHandleIndex));
			}
		} catch (Throwable t) {
			logger.error("switchToNewTab异常！", t);
			throw new RxCrawlerException(999, t.getMessage());
		}
		
	}

	@Override
	public void closeNewTab() throws RxCrawlerException {
		
		checkInterrupted();

		try {
			int newTabWindowHandleIndex = newTabWindow.getNewTabWindowHandleIndex();

			if (newTabWindowHandleIndex > 0) {

				WebDriver tab = this.webDriver.switchTo().window(newTabWindow.getTabWindowHandleList().get(newTabWindowHandleIndex));
				tab.close();

				// 回到打开该tab的tab页
				this.webDriver.switchTo().window(newTabWindow.getTabWindowHandleList().get(newTabWindowHandleIndex - 1));

				newTabWindow.getTabWindowHandleList().remove(newTabWindowHandleIndex);
				newTabWindow.setNewTabWindowHandleIndex(newTabWindow.getNewTabWindowHandleIndex() - 1);
			}
		} catch (Throwable t) {
			logger.error("closeNewTab异常！", t);
			throw new RxCrawlerException(999, t.getMessage());
		}
	}


	@Override
	public String getPageSource() throws RxCrawlerException {
		
		checkInterrupted();

		String pageSource = "NA";
		try {
			pageSource = StringUtil.removeSpecialChar(this.webDriver.getPageSource());
		} catch (Throwable t) {
			logger.error("getPageSource异常！", t);
			throw new RxCrawlerException(999, t.getMessage());
		}
    	
		return pageSource;
	}

	@Override
	public String getTitle() throws RxCrawlerException {
		
		checkInterrupted();

		String pageTitle = "NA";
		try {
			pageTitle = StringUtil.removeSpecialChar(this.webDriver.getTitle());
		} catch (Throwable t) {
			logger.error("getTitle异常！", t);
			throw new RxCrawlerException(999, t.getMessage());
		}
    	
		return pageTitle;
	}
	
    
	private Point getIframeLocationByVerifyImageXpath(String verifyImageXpath) {
		
		checkInterrupted();

        boolean isFindElement = false;
        
		try {
			Point iframeLocation = null;
			this.webDriver.switchTo().defaultContent();
			
			List<WebElement> iframes = this.webDriver.findElements(By.tagName("iframe"));
			// 没有iframe
			if (iframes == null || iframes.size() == 0) {
				iframeLocation = new Point(0, 0);
				return iframeLocation;
			}
			
			for (WebElement iframe : iframes) {
				
				this.webDriver.switchTo().defaultContent();
				iframeLocation = iframe.getLocation();
				
				logger.info("getIframeLocationByVerifyImageXpath-iframeNode.LocationX:" + iframeLocation.getX());
				
				logger.info("getIframeLocationByVerifyImageXpath-iframeNode.LocationY:" + iframeLocation.getY());
				
				try {
	                // 在当前frame查找验证码图片
					this.webDriver.switchTo().frame(iframe);
					List<WebElement> elementList = this.webDriver.findElements(By.xpath(verifyImageXpath));
					if (elementList != null && elementList.size() > 0) {
						for (WebElement webElement : elementList) {
							if (webElement.isDisplayed()) {
								isFindElement = true;
								break;
							}
						}
					} else {
						List<WebElement> subIframes = this.webDriver.findElements(By.tagName("iframe"));
						if (subIframes == null || subIframes.size() == 0) {
							continue;
						}
						for (WebElement subIframe : subIframes) {
							
							this.webDriver.switchTo().defaultContent();
							iframeLocation = iframe.getLocation();
							
							logger.info("getIframeLocationByVerifyImageXpath-subIframes-iframeNode.LocationX:" + iframeLocation.getX());
							
							logger.info("getIframeLocationByVerifyImageXpath-subIframes-iframeNode.LocationY:" + iframeLocation.getY());
							
							
							try {
								this.webDriver.switchTo().frame(subIframe);
								elementList = this.webDriver.findElements(By.xpath(verifyImageXpath));
								if (elementList != null && elementList.size() > 0) {
									for (WebElement webElement : elementList) {
										if (webElement.isDisplayed()) {
											isFindElement = true;
											break;
										}
									}
									
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
				
				if (iframeLocation != null) {
					break;
				}
			}
			
			if (!isFindElement) {
				iframeLocation = new Point(0, 0);
			}
			
			return iframeLocation;
				
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new RxCrawlerException(999, t.getMessage());
		}
	}


	@Override
	public String imageRecognize(String imageXpath) throws RxCrawlerException {
		
		checkInterrupted();

		String imageString = "NA";
		
		try {
			imageString = TencentRecognition.imageRecognize(webDriver, imageXpath, 0, 0);
		} catch (Exception e) {
			logger.error("tencentRecogize", e);
		}
		
//		accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);
		return imageString;
	}
	
	
	@Override
	public String imageRecogize(String imageXpath) throws RxCrawlerException {
		return imageRecognize(imageXpath);
	}
	
	
	@Override
	public String phoneImageRecognize(String imageXpath) throws RxCrawlerException {
		
		checkInterrupted();

		String imageString = "NA";
		
		try {
			imageString = TencentRecognition.phoneImageRecognize(webDriver, imageXpath, 0, 0);
		} catch (Exception e) {
			logger.error("phoneImageRecogize:", e);
		}
		
//		accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);
		
		return imageString;
	}
	
	@Override
	public String phoneImageRecogize(String imageXpath) throws RxCrawlerException {
		return phoneImageRecognize(imageXpath);
	}
	
	@Override
	public String phoneImageRecognize(String imageXpath, Website website) throws RxCrawlerException {
		
		checkInterrupted();

		String imageString = "NA";
		
		try {
			imageString = TencentRecognition.phoneImageRecognize(webDriver, imageXpath, website);
		} catch (Exception e) {
			logger.error("phoneImageRecogize", e);
		}
		
//		accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);
		
		return imageString;
	}
	
	@Override
	public String phoneImageRecogize(String imageXpath, Website website) throws RxCrawlerException {
		return phoneImageRecognize(imageXpath, website);
	}
	
	//目前UU打码已经迁移到自己研发的顺企网图片识别模块
	@Override
	public String uuPictureRecognition(String imageSrc) throws RxCrawlerException {

		checkInterrupted();
		
		String imageString = "NA";
		
		try {
			imageString = TencentRecognition.phoneImageRecognize(webDriver, imageSrc, Website.ShunQi);
		} catch (Exception e) {
			logger.error("uuPictureRecognition异常", e);
			throw new RxCrawlerException(999, e.getMessage());
		}
		
		return imageString;
	}

	@Override
	public boolean verifyGeeTest() {
		
		checkInterrupted();

		boolean verifyResult = false;
		try {
			verifyResult = GeeTestCracker.verifyGeeTest(webDriver);
		} catch (Exception e) {
			logger.error("verifyGeeTest Exception 异常！", e);
		}
		
//		accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);
		
		return verifyResult;
	}
	
	@Override
	public boolean verifyGeeTest2() {
		
		checkInterrupted();

		boolean verifyResult = false;
		try {
			verifyResult = GeeTestCracker.verifyQixinGeeTest(webDriver);
		} catch (Exception e) {
			logger.error("verifyGeeTest Exception 异常！", e);
		}

//		accounting.setVerifyCodeTimes(accounting.getVerifyCodeTimes() + 1);
		
		return verifyResult;
	}
//	
//	@Override
//	public String takeScreenshot(int appSeq, int x, int y, int w, int h) throws RxCrawlerException {
//
//		String fileName = "";
//
//		byte[] screenshotBytes = null;
//		try {
//			screenshotBytes = VerifyCodeHelper.takeScreenshot( this.webDriver );
//		} catch (Exception e) {
//			
//			logger.error("截图发生异常，", e);
//			new RxCrawlerException(999, e.getMessage());
//		}
//
//		BufferedImage screenshotImage = null;
//		try {
//			screenshotImage = ImageIO.read( new ByteArrayInputStream( screenshotBytes ) );
//		} catch (Exception e) {
//			
//			logger.error("截图读入发生异常，", e);
//			
//			new RxCrawlerException(999, e.getMessage());
//		}
//
//		BufferedImage subScreenshotImage = screenshotImage.getSubimage( x, y, w, h );
//		String[] fileNameArr = null;
//		try {
//			fileNameArr = writeScreenshotImageToLocal( appSeq, subScreenshotImage );
//		} catch (Exception e) {
//			
//			logger.error("截图写入发生异常，", e);
//			
//			new RxCrawlerException(999, e.getMessage());
//		}
//
//		// 调用php写图片文件到taskhost
//		try {
//			AppFacade.getInstance().uploadImageFile( appSeq, fileNameArr[0] );
//		} catch (Exception e) {
//			logger.error("截图上传发生异常，", e);
//			new RxCrawlerException(999, e.getMessage());
//		}
//
//		fileName = fileNameArr[1];
//		
//		return fileName;
//	}
	
	@Override
    public String takeScreenshot ( int appSeq, boolean isWatermark ) {
		
		checkInterrupted();

//		滑动码验证的时候导致截图的尺寸变小,临时的补丁让窗口最大化.
		webDriver.manage().window().maximize();
		org.openqa.selenium.Dimension myDimension = webDriver.manage().window().getSize();
		logger.info("-------------verifyGeeTest takeScreenshot Width:" + myDimension.getWidth() + "<--> Height: " + myDimension.getHeight());
		
		String fileName = "";
		byte[] imgBytes = null;

		byte[] screenshotBytes = null;
		try {
			screenshotBytes = VerifyCodeHelper.takeScreenshot( this.webDriver );
		} catch (Exception e) {
			
			logger.error("截图发生异常，", e);
			new RxCrawlerException(999, e.getMessage());
		}

		
        StringBuffer filePathBuf = new StringBuffer ( );
		String curTimeStr = new SimpleDateFormat ( "yyyyMMddHHmmssSSS" ).format ( new Date () );
		
		if ( isWatermark ) {

	        InputStream in = new ByteArrayInputStream( screenshotBytes );
	        imgBytes = markImageByIcon ( in , "Arial", 30, Color.RED );
			
	        filePathBuf.append( "app" ).append( appSeq ).append( "_"+crawlerid).append( "_mark" ).append ( "_img_" ).append ( curTimeStr ).append ( ".jpg" );

		} else {
			
			filePathBuf.append( "app" ).append( appSeq ).append( "_"+crawlerid).append( "_img_" ).append ( curTimeStr ).append ( ".jpg" );
			imgBytes = screenshotBytes;
		}
		
		fileName = filePathBuf.toString();
		logger.info("takeScreenshot: " + fileName);
		
//		// 调用php写图片文件到taskhost
//		try {
//			hostDownloadUrl = AppFacade.getInstance().uploadImageFile(appSeq, imgBytes, fileName);
//			if (hostDownloadUrl == null) {
//				throw new RuntimeException("不能到获取TaskHost Server");
//			}
//		} catch (Exception e) {
//			logger.error("截图上传发生异常，", e);
//			new RxCrawlerException(999, e.getMessage());
//		}
		
		return uploadToRop(fileName, imgBytes);

		//		return hostDownloadUrl + "/" + fileName;
    }


	@Override
	public String takeFullScreenshot (boolean isWatermark, List<String> divIds) {
		
		checkInterrupted();
		
		Long windowHeight = (Long) executeJsScript("return document.documentElement.clientHeight;");
		logger.info("windowHeight:" + windowHeight);

		Long totalScrollHeight = (Long) executeJsScript("return document.body.scrollHeight;");
		logger.info("totalScrollHeight:" + totalScrollHeight);
		int windowHeightInt = windowHeight.intValue();
		int totalScrollHeightInt = totalScrollHeight.intValue();
		
		String fileName = "";
		byte[] imgBytes = null;
		
		StringBuffer filePathBuf = new StringBuffer ( );
		String curTimeStr = new SimpleDateFormat ( "yyyyMMddHHmmssSSS" ).format ( new Date () );

		filePathBuf.append( "_img_" ).append ( curTimeStr ).append ( ".png" );
	
		fileName = filePathBuf.toString();
		logger.info("takeFullScreenshot: " + fileName);
		
		int lastPageHeight = totalScrollHeightInt%windowHeightInt;
		int finalTotalHeight = totalScrollHeightInt;

		 // 读取待合并的文件 
        BufferedImage bufferImage = null; 
        
        // 调用mergeImage方法获得合并后的图像 
        BufferedImage targetImage = null; 
        

		totalScrollHeightInt = totalScrollHeightInt - windowHeightInt;
		targetImage = getScreenBufferedImage(true);
		
		//先滚动到底部,预防客户外部操作的影响
		scrollToTop();


		//先让屏幕滚动一遍,使所有的图片显示
		int dryRunTotalScrollHeightInt	= totalScrollHeightInt;
		int dryRunFinalTotalHeight = finalTotalHeight;
		int dryRunScrollTimes = 1;
		while (dryRunTotalScrollHeightInt >= 0) {

			if (dryRunTotalScrollHeightInt >= windowHeightInt) {
				scroll(windowHeightInt * dryRunScrollTimes);
			}else{
				scroll(dryRunFinalTotalHeight);

			}
			
			dryRunTotalScrollHeightInt = dryRunTotalScrollHeightInt - windowHeightInt;
			dryRunScrollTimes++;
		}
		
		scrollToTop();
		sleepSeconds(1);
		
		if(divIds!=null){
			for (String id : divIds) {
				sleepSeconds(1);
				executeJsScript("document.getElementById('" +id.trim() + "').style.display='none'; return 0;");
			}
		}
		
		int scrollTimes = 1;

		while (totalScrollHeightInt >= 0) {

			if (totalScrollHeightInt >= windowHeightInt) {
				scroll(windowHeightInt * scrollTimes);
			}else{
				scroll(finalTotalHeight);

			}
			sleepSeconds(1);
			bufferImage = getScreenBufferedImage(true);

			// 调用mergeImage方法获得合并后的图像
			try {

				if (totalScrollHeightInt >= windowHeightInt) {
					targetImage = ImageMerger.mergeImage(targetImage, bufferImage, false, windowHeightInt);					
				}
				else
				{
					targetImage = ImageMerger.mergeImage(targetImage, bufferImage, false, lastPageHeight);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			logger.info("scrollTimes:  " + scrollTimes);
			logger.info("totalScrollHeightInt:  " + totalScrollHeightInt);
			
			totalScrollHeightInt = totalScrollHeightInt - windowHeightInt;
			scrollTimes++;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			boolean flag = ImageIO.write(targetImage, "png", out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imgBytes = out.toByteArray();

		return uploadToRop(fileName, imgBytes);
		

	}
	
    private BufferedImage getScreenBufferedImage (boolean isWatermark ) {
		
		checkInterrupted();

		byte[] imgBytes = null;

		byte[] screenshotBytes = null;
		try {
			screenshotBytes = VerifyCodeHelper.takeScreenshot( this.webDriver );
		} catch (Exception e) {
			
			logger.error("截图发生异常，", e);
			new RxCrawlerException(999, e.getMessage());
		}
		
		if ( isWatermark ) {
	        InputStream in = new ByteArrayInputStream( screenshotBytes );
	        imgBytes = markImageByIcon ( in , "Arial", 30, Color.RED );
		} else {
			imgBytes = screenshotBytes;
		}
		
		BufferedImage screenshotImage = null;
		try {
			screenshotImage = ImageIO.read(new ByteArrayInputStream( imgBytes ) );
		} catch (Exception e) {
			logger.error("截图读入发生异常，", e);
			new RxCrawlerException(999, e.getMessage());
		}
		
		return screenshotImage;
    }

	
	private String uploadToRop(String fileName, byte[] imgBytes) {
		//新需求存储文件到ROP平台
		DefaultRopClient ropClient = DefaultRopClientFacade.getInstance();
		PsPictureStreamUploadRequest request = new PsPictureStreamUploadRequest();
		request.setExtension("jpg");
		request.setPicturefilename(fileName);
		request.setPicturestream(compressPicture(imgBytes));
		
		String fileurl = "NA";
		try {
			PsPictureStreamUploadResponse response = ropClient.execute(request);
			
			String strError = null;
			if (response != null) {
			
				fileurl = response.getFileurl();
				logger.info("takeScreenshot fileUrl: " + fileurl);
				if (response.isSuccess() != true) {
					if (response.getSubMsg() != null && response.getSubMsg() != "") {
						strError = response.getSubMsg();
					} else {
						strError = response.getMsg();
					}
				}
				if (strError != null) {
					throw new RxCrawlerException(999,"调用ROP平台发生错误: " + strError);
				}
			}
		} catch (ApiException e) {
			throw new RxCrawlerException(999,"调用ROP平台发生错误");
		}
		return fileurl;
	}
	
	@Override
	public String takeScreenshotByJava ( int appSeq, boolean isWatermark ) {
		
		checkInterrupted();

		String fileName = "";
		byte[] imgBytes = null;

		byte[] screenshotBytes = null;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);

		try {
			
	        Robot robot = new Robot();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        
	        BufferedImage image = robot.createScreenCapture(screenRectangle);
			ImageIO.write(image,"jpg", out);
			
	        screenshotBytes = out.toByteArray();
	        
		} catch (IOException e) {
			
			logger.error("截图发生异常，", e);
			new RxCrawlerException(999, "截图发生异常，" + e.getMessage());
			
		} catch (AWTException e) {
			
			logger.error("截图发生异常，", e);
			new RxCrawlerException(999, "截图发生异常，" +  e.getMessage());
			
		}

		
        StringBuffer filePathBuf = new StringBuffer ( );
		String curTimeStr = new SimpleDateFormat ( "yyyyMMddHHmmssSSS" ).format ( new Date () );
		
		if ( isWatermark ) {

	        InputStream in = new ByteArrayInputStream( screenshotBytes );
	        imgBytes = markImageByIcon ( in , "Arial", 30, Color.RED );
			
	        filePathBuf.append( "app" ).append( appSeq ).append( "_mark" ).append ( "_img_" ).append ( curTimeStr ).append ( ".jpg" );

		} else {
			
			filePathBuf.append( "app" ).append( appSeq ).append( "_img_" ).append ( curTimeStr ).append ( ".jpg" );
			imgBytes = screenshotBytes;
		}
		
		fileName = filePathBuf.toString();
		
		// 调用php写图片文件到taskhost
		try {
			AppFacade.getInstance().uploadImageFile( appSeq, imgBytes, fileName);
		} catch (Exception e) {
			logger.error("截图上传发生异常，", e);
			new RxCrawlerException(999, e.getMessage());
		}
		
		return fileName;
		
	}
	
	@Override
	public String getImageByUrl(int appSeq, String url) {
		checkInterrupted();

		String fileName = "";
		byte[] imgBytes = null;

        StringBuffer filePathBuf = new StringBuffer ( );
		String curTimeStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		filePathBuf.append("app").append(appSeq).append("_" + crawlerid).append("_img_").append(curTimeStr);
		imgBytes = getImageData(url);
		
		if (imgBytes == null) {
			throw new RxCrawlerException(999, "不能得到指定URL的图片信息");
		}
		
		fileName = filePathBuf.toString();
		
//		// 调用php写图片文件到taskhost
//		try {
//			hostDownloadUrl = AppFacade.getInstance().uploadImageFile(appSeq, imgBytes, fileName);
//			if (hostDownloadUrl == null) {
//				throw new RxCrawlerException(999, "不能到获取TaskHost Server");
//			}
//		} catch (Exception e) {
//			logger.error("截图上传发生异常，", e);
//			new RxCrawlerException(999, e.getMessage());
//
//		}
		
		return uploadToRop(fileName, imgBytes);
	}
	
	
	
	public static ByteArrayInputStream compressPicture(byte[] imgBytes){
        
        BufferedImage src = null;
        ByteArrayOutputStream out = null;
        ImageWriteParam imgWriteParams;
 
        // 指定写图片的方式为 jpg
        ImageWriter imgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        
        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
        imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        // 这里指定压缩的程度，参数qality是取值0~1范围内，
        imgWriteParams.setCompressionQuality((float) 0.5);
        imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
        
        ColorModel colorModel = ColorModel.getRGBdefault();
        // 指定压缩时使用的色彩模式
        imgWriteParams.setDestinationType(new ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));
 
        try {
        	//将imgBytes作为输入流；
        	ByteArrayInputStream inputImageStream = new ByteArrayInputStream(imgBytes);    
        	//将InputStream作为输入流，读取图片存入image中
        	src = ImageIO.read(inputImageStream);     
        	
			out = new ByteArrayOutputStream();
			imgWriter.reset();
			// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
			// OutputStream构造
			imgWriter.setOutput(ImageIO.createImageOutputStream(out));
			// 调用write方法，就可以向输入流写图片
			imgWriter.write(null, new IIOImage(src, null, null), imgWriteParams);

			out.flush();
			out.close();
            
        } catch (Exception e) {
        	throw new RxCrawlerException(999,"压缩图片出现错误:");
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
	
	
    public String logScreen( int appSeq) {
		
		checkInterrupted();
		String hostDownloadUrl = null;

		String fileName = "";
		byte[] imgBytes = null;

		byte[] screenshotBytes = null;
		try {
			screenshotBytes = VerifyCodeHelper.takeScreenshot( this.webDriver );
		} catch (Exception e) {
			
			logger.error("截图发生异常，", e);
			new RxCrawlerException(999, e.getMessage());
		}

		StringBuffer filePathBuf = new StringBuffer();
		String curTimeStr = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		filePathBuf.append("img_").append(curTimeStr).append(".jpg");
		imgBytes = screenshotBytes;
		
		
		fileName = filePathBuf.toString();
		
		// 调用php写图片文件到taskhost
		try {
			hostDownloadUrl = AppFacade.getInstance().uploadImageFile(appSeq, imgBytes, fileName);
			if (hostDownloadUrl == null) {
				throw new RuntimeException("不能到获取TaskHost Server");
			}
		} catch (Exception e) {
			logger.error("截图上传发生异常，", e);
			throw new RxCrawlerException(999, e.getMessage());
		}
		
		int downloadIndex = hostDownloadUrl.indexOf("download_images");
		String pureHostUrl = hostDownloadUrl.substring(0, downloadIndex);

		//http://taskhost/App713_Download_App_LogScreen.php?userUuid={userUuid}&app=111&imageName=20180227101021321.png
		return pureHostUrl + "app/APP713_Download_App_LogScreen.php?userUuid={userUuid}&appSeq=" + appSeq + "&imageFileName="+fileName;
    }

//	private String [] writeScreenshotImageToLocal( int appSeq, BufferedImage subScreenshotImage ) throws IOException {
//		
//		String [] fileNameArr = new String [2];
//		
//        StringBuffer filePathBuf = new StringBuffer ( FILE_PATH );
//        String curTimeStr = new SimpleDateFormat ( "yyyyMMddHHmmssSSS" ).format ( new Date () );
//        filePathBuf.append( "app" ).append( appSeq ).append ( "_img_" ).append ( curTimeStr ).append ( ".jpg" );
//        String fileName = filePathBuf.toString ();
//        fileNameArr[0] = fileName;
//        
//        File file = new File ( fileName );
//        String filePath = file.getParent ();
//        File fileParent = new File ( filePath );
//        if ( ! fileParent.exists () ) {
//            fileParent.mkdirs ();
//        }
//        
//		ImageIO.write ( subScreenshotImage , "jpg" , file );
//        
//        fileNameArr[1] = file.getName();
//        
//        return fileNameArr;
//
//	}
	
	private byte [] markImageByIcon ( InputStream srcImgInputStream, String fontType, int fontSize, Color fontColor ) {
      
		checkInterrupted();

		ByteArrayOutputStream os = null;
       
		try {
			
            Image srcImg = ImageIO.read(srcImgInputStream);
            
            int srcImgWidth = srcImg.getWidth ( null );
            int srcImgHeight = srcImg.getHeight ( null );

            BufferedImage buffImg = new BufferedImage ( srcImgWidth , srcImgHeight , BufferedImage.TYPE_INT_RGB );
            // 得到画笔对象
            Graphics2D g = buffImg.createGraphics ();

            // 设置对线段的锯齿状边缘处理
            g.setRenderingHint ( RenderingHints.KEY_INTERPOLATION , RenderingHints.VALUE_INTERPOLATION_BILINEAR );

            g.drawImage ( srcImg.getScaledInstance ( srcImg.getWidth ( null ) , srcImg.getHeight ( null ) , Image.SCALE_SMOOTH ) , 0 , 0 , null );

            float alpha = 1f; // 透明度
            g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_ATOP , alpha ) );

            g.setColor ( fontColor);

            Font font = new Font ( fontType , Font.PLAIN , fontSize );
            g.setFont ( font );

            // 表示水印图片的位置
            SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
            java.util.Date date = new java.util.Date ();

            g.drawString ( sdf.format ( date ) , (int)Math.floor( srcImgWidth * 0.8 ), (int)Math.floor( srcImgHeight * 0.8 ) );

            g.setComposite ( AlphaComposite.getInstance ( AlphaComposite.SRC_OVER ) );

            g.dispose ();

            os = new ByteArrayOutputStream();
            
            // 生成图片
            ImageIO.write ( buffImg , "JPG" , os );
            
        } catch ( Exception e ) {
        	
            e.printStackTrace ();
            
        } finally {
        	
            try {
                if ( null != os ) os.close ();
            } catch ( Exception e ) {
                e.printStackTrace ();
            }
            
        }
        
        return os.toByteArray();
    }
	
	
//	private byte[] getImageData(String src) {
//		
//		byte[] data = null;
//
//		for (int i = 0; i < 5; i++) {
//
//			try {
//				URL url = new URL(src);
//				URLConnection urlConn = url.openConnection();
//				urlConn.setConnectTimeout(600 * 1000);
//				InputStream is = urlConn.getInputStream();
//				data = streamToByte(is);
//			} catch (IOException e) {
//				logger.error("不能得到指定URL的图片信息");
//			}
//			
//			if ((data != null) && (data.length >= 0)) {
//				break;
//			}
//		}
//
//		return data;
//	}
	
	private byte[] getImageData(String src) {
		
		byte[] data = null;

		for (int i = 0; i < 5; i++) {
			CloseableHttpResponse response = null;
			try {
				CloseableHttpClient httpclient = this.getHttpClient();
				HttpGet get = new HttpGet(src);
				response = httpclient.execute(get);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				data = streamToByte(is);

			} catch (IOException e) {
				logger.error("不能得到指定URL的图片信息:" + e.getMessage());
			} finally {
				if (response != null) {
					try {
						response.close();
					} catch (IOException e) {
						logger.error("关闭CloseableHttpResponse出现异常:" + e.getMessage());
					}
				}
			}

			if ((data != null) && (data.length >= 0)) {
				break;
			}
		}

		return data;
	}
	

	private byte[] streamToByte(InputStream is) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		int ch;
		while ((ch = is.read()) != -1) {
			stream.write(ch);
		}
		byte imgData[] = stream.toByteArray();
		stream.close();

		return imgData;
	}
	 
	    
	@Override
	public void refresh() {
		
		try {
			this.webDriver.navigate().refresh();
			} catch (Exception e) {
			logger.error("webDriver.navigate().refresh()！", e);
		}
		
	}
	
	@Override
	public void back() {
		
		try {
			this.webDriver.navigate().back();
			} catch (Exception e) {
			logger.error("webDriver.navigate().back()！", e);
		}
		
	}
	
	public void checkInterrupted() {

		if (Thread.currentThread().isInterrupted()) {
			throw new RxCrawlerException("该任务已被用户请求强制终止！");
		}
	}

	
	@Override
	public Screen getScreenInstance() throws RxCrawlerException {
        Screen screen = new Screen();
        return screen;
	}

	
	
	@Override
	public void performJSAction(String actionName, String actionValue, String selector) {
		
		JSAction jsAction = new JSAction();
		jsAction.setActionName(actionName);
		jsAction.setActionValue(actionValue);
		jsAction.setSelector(selector);
		
		String javaScriptMessage = "";
		try {
			javaScriptMessage = JsonUtil.convertObj2JsonStr(jsAction);
			//chrome frontend extension websocket Id like 10001, 10002, etc... 
			ProxyAddressProducer.sendMessage(String.valueOf(10000+this.getSessionId()), javaScriptMessage);
		} catch (Exception e) {
			logger.error("performJSAction", e);
		}
		
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	
    public int getCurTabs() {
    	
    	int tabs = 1;
    	List<String> tabWindowHandleList = new ArrayList<String> (this.webDriver.getWindowHandles());
    	
    	if ( tabWindowHandleList != null ) {
        	tabs = tabWindowHandleList.size();	
    	}
    	
    	return tabs;
    }

	@Override
	public JSONObject getAllCookies() throws RxCrawlerException {
		
		JSONObject jsonObject = new JSONObject();
		
		Set<Cookie> cookies = this.webDriver.manage().getCookies();
		try {

			for (Cookie c : cookies) {
				
				jsonObject.put(c.getName(), c.getValue());
				
			}
			
		} catch (JSONException e) {
			
			new RxCrawlerException(999, "json转换异常，" + e.getMessage());
			
		}
		return jsonObject;
	}

	@Override
	public String getCookieByName(String name) {
		
		Set<Cookie> cookies = this.webDriver.manage().getCookies();
		for ( Cookie c : cookies ) {
			
			if ( name.equalsIgnoreCase(c.getName()) ) {
				
				return c.getValue();

			}
		}
		
		return null;
	}

	@Override
	public void deleteCookieByName(String name) {
		
		this.webDriver.manage().deleteCookieNamed(name);
		
	}

	@Override
	public void deleteAllCookies() {
		
		this.webDriver.manage().deleteAllCookies();
		
	}
	
	@Override
	public RxHttpNetwork getHttpNetworkByURL(String url) throws RxCrawlerException {
		// TODO 清除Log open前和open后
		LogEntries logEntries = null;

		LogEntry requestLog = null;
		LogEntry responseLog = null;

		int loopTime = 60;
		for (int i = 0; i < loopTime; i++) {

			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				logger.error("TimeUnit.SECONDS", e);
			}
			try {
				logEntries = this.webDriver.manage().logs().get(LogType.PERFORMANCE);
			} catch (Exception e) {
				throw new RxCrawlerException("请联系瑞雪采集云调整爬虫机参数");
			}
			for (LogEntry logEntry : logEntries) {
				String logMessage = logEntry.getMessage();

				if ((requestLog == null) && logMessage.contains("XHR") && logMessage.contains("Network.requestWillBeSent") && logMessage.contains(url)) {
					requestLog = logEntry;
					logger.info("Network.requestWillBeSent" + " : " + logEntry);
				}

				if ((responseLog == null) && logMessage.contains("XHR") && logMessage.contains("Network.responseReceived") && logMessage.contains(url)) {
					responseLog = logEntry;
					logger.info("Network.responseReceived" + " : " + logEntry);
				}
			}
			
			logger.info(new Date() + " --- requestLog: "+ requestLog + "|" + "responseLog: " + responseLog);
			if ((requestLog != null) && (responseLog != null)) {
				return new RxHttpNetworkImpl(requestLog, responseLog);
			}
		}
		return null;
	}

	
	@Override
	public RxHttpRequest copyRxHttpRequest(RxHttpRequest rxHttpRequest) {
		this.rxHttpRequest = rxHttpRequest;
		return this.rxHttpRequest;
	}

	@Override
	public RxHttpResponse send(RxHttpRequest rxHttpRequest) {

		RxHttpResponseImpl rxHttpResponseImpl = null;
		String responseText = "NA";

		if (rxHttpRequest != null) {
			
			String url = rxHttpRequest.getURL();
			JSONObject params = rxHttpRequest.getAllParameters();
			String method = rxHttpRequest.getMethod();

			JSONObject headers = rxHttpRequest.getAllHeaders();
			HashMap headersHashmap = jsonToHashmap(headers);
			
			JSONObject cookies = rxHttpRequest.getAllCookies();
			HashMap cookiesHashmap = jsonToHashmap(cookies);
			
			IpProxyResult ipProxyInfo = null;
			if (isUsingProxy) {
				ipProxyInfo = ProxyFacade.getInstance().fetchIpProxy(1, 1);
			}

			if (method.equalsIgnoreCase("GET")) {
				try {
					if(isUsingProxy){
						responseText = HTTP.doGet(url, ipProxyInfo.getIp(), ipProxyInfo.getPort(), ipProxyInfo.getUser_name(), ipProxyInfo.getPass_word(), headersHashmap, cookiesHashmap);
					}
					else{
						responseText = HTTP.doGet(url, headersHashmap, cookiesHashmap);
					}
				} catch (Exception e) {
					logger.error("send HTTP.doGet:", e);
				}
			} else {
				try {
					if(isUsingProxy){
						responseText = HTTP.doPost(url, ipProxyInfo.getIp(), ipProxyInfo.getPort(), ipProxyInfo.getUser_name(), ipProxyInfo.getPass_word(),jsonToHashmap(params), headersHashmap, cookiesHashmap);
					}else{
						responseText = HTTP.doPost(url, jsonToHashmap(params), headersHashmap, cookiesHashmap);
					}
				} catch (Exception e) {
					logger.error("send HTTP.doPost:", e);
				}
			}

			rxHttpResponseImpl = new RxHttpResponseImpl(responseText);
		}

		return rxHttpResponseImpl;
	}

	private HashMap jsonToHashmap(JSONObject params){
		HashMap paramHasMap = new HashMap();
		if (params != null) {
			for (Iterator<String> keys = params.keys(); keys.hasNext();) {
				String paramKey = keys.next();
				String paramValue = "";
				try {
					paramValue = params.getString(paramKey);
				} catch (JSONException e) {
					logger.error("jsonToHashmap:", e);
				}
				paramHasMap.put(paramKey, paramValue);
			}
		}
		return paramHasMap;
	}

	public boolean isUsingProxy() {
		return isUsingProxy;
	}

	public void setUsingProxy(boolean isUsingProxy) {
		this.isUsingProxy = isUsingProxy;
	}
	
    public CloseableHttpClient getHttpClient(){
    	
    	if ( this.httpClient == null) {
    		
    		HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {

    			public boolean retryRequest(IOException exception,
    					int executionCount, HttpContext context) {
    				// 重试三次,网络连接失败
    				if (executionCount > 3) {
    					return false;
    				}

    				try {
    					Thread.sleep(2000);
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
    				return true;
    			}
    		};
      
        	
        	if ( this.isUsingProxy ) {
        		
        		IpProxyResult ipProxyInfo = ProxyFacade.getInstance().fetchIpProxy(1, 1);
    			CredentialsProvider credsProvider = new BasicCredentialsProvider();
    			credsProvider.setCredentials(new AuthScope(ipProxyInfo.getIp(), ipProxyInfo.getPort()), new UsernamePasswordCredentials(ipProxyInfo.getUser_name(), ipProxyInfo.getPass_word()));
    			this.httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).setRetryHandler(retryHandler).build();
        	
        	}

        	this.httpClient = HttpClients.custom().setRetryHandler(retryHandler).build();
        	
    	}

    	return this.httpClient;
    }
    
    public WebClient getHtmlUnitWebClient() {
    	
    	if ( this.webClient == null ) {
    		
        	this.webClient = new WebClient();
    		
        	if ( this.isUsingProxy ) {
        		
        		IpProxyResult ipProxyInfo = ProxyFacade.getInstance().fetchIpProxy(1, 1);
            	ProxyConfig proxyConfig = new ProxyConfig(ipProxyInfo.getIp(), ipProxyInfo.getPort());
            	final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
            	credentialsProvider.addCredentials(ipProxyInfo.getUser_name(), ipProxyInfo.getPass_word());
            	this.webClient.getOptions().setProxyConfig(proxyConfig);
        	}
        	
        	// 支持js
        	this.webClient.getOptions().setJavaScriptEnabled(true);
        	this.webClient.getOptions().setThrowExceptionOnScriptError(false);
        	this.webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        	
        	// 非200code
        	this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        	
        	// 屏蔽css
        	this.webClient.getOptions().setCssEnabled(true);
        	
        	// 支持https
        	this.webClient.getOptions().setUseInsecureSSL(true);
        	
        	// 设置超时时间
        	this.webClient.getOptions().setTimeout(30000);
        	
        	// 支持cookie
        	this.webClient.getCookieManager().setCookiesEnabled(true);
        	
    	}

    	
    	return this.webClient;
    }

	@Override
	public boolean verifyQichacha() {
		
		boolean verifyResult = false;
		
		WebElement sliderElement = this.webDriver.findElement(By.xpath("//*[@id=\"nc_1_n1z\"]"));
		WebElement widthElement = this.webDriver.findElement(By.xpath("//*[@id=\"nc_1__scale_text\"]/span"));

		int slideDistance =  widthElement.getSize().getWidth()-40;

		Actions actions = new Actions(this.webDriver);
		new Actions(this.webDriver).clickAndHold(sliderElement).perform();

		int movedDistance = 0;
		
		while ((movedDistance - slideDistance) <= 0) {
			
			int step = 0;
			if (movedDistance > slideDistance * 0.9) {
				step = getRandom(1, 10);
			} else {
				step = getRandom(1, 20);
			}
			
			movedDistance += step;
			actions.moveByOffset(step, getRandom(1, 3)).perform();
	       
			try {
				Thread.sleep(new Random().nextInt(11));
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
		actions.release(sliderElement).perform();
		
		this.sleepSeconds(1);
		
		try {
			WebElement textElement = this.webDriver.findElement(By.xpath("//*[@id=\"nc_1__scale_text\"]"));
			logger.info("input text: -----------" + textElement.getText());

			//有可能一次通过,没有后续的选择
			if(textElement.getText().contains("验证通过")){
				verifyResult = true;
				return verifyResult;
			}
			
			// 选择某个字
			if (textElement.getText().contains("请点击")) {
			
				for (int i = 0; i < 5; i++) {

					logger.info("select the text.........");

//					this.selectVerifyCode("//*[@id=\"nc_1_clickCaptcha\"]/div[2]", "//*[@id=\"nc_1__scale_text\"]");
					this.selectVerifyCode("//*[@id=\"normalLoginPanel\"]", "//*[@id=\"nc_1__scale_text\"]");
					
					try {
						
						WebElement errorElement = this.webDriver.findElement(By.xpath("//*[@id=\"nc_1__captcha_text\"]/span"));
						logger.info("error Message:" + errorElement.getText());
						// 第二次或者以后的验证通过,textElement没有错误的提示信息
						if (errorElement.getText().trim().equals("")) {
							verifyResult = true;
							break;
						}
					} catch (NoSuchElementException e) {
						verifyResult = true;
						break;
					}
					this.sleepSeconds(1);
				}
				
				
				
			} else {// 输入字符

				for (int i = 0; i < 5; i++) {

					logger.info("input the text.........");

					this.inputVerifyCode("//*[@id=\"nc_1_imgCaptcha\"]", "//*[@id=\"nc_1_captcha_input\"]");
					this.sleepSeconds(2);

					WebElement submitElement = this.webDriver.findElement(By.xpath("//*[@id=\"nc_1_scale_submit\"]/span"));
					submitElement.click();
					this.sleepSeconds(1);

					try {
						WebElement errorElement = this.webDriver.findElement(By.xpath("//*[@id=\"nc_1__captcha_img_text\"]/span"));
						logger.info("error Message:" + errorElement.getText());
						// 第二次或者以后的验证通过,存在errorElement但是没有错误的提示信息
						if (errorElement.getText().trim().equals("")) {
							verifyResult = true;
							break;
						}
					} catch (NoSuchElementException e) {
						verifyResult = true;
						break;
					}
				}

			}
			
		} catch (NoSuchElementException e) {//提示信息不出现,直接通过

			verifyResult = true;
		}
		
		return verifyResult;
	}
    
	private static int getRandom(int min, int max)
	{
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	@Override
	public RxHttpNode doHttpGet(String url) {
		
		return doHttpGet(url, null, null);
	}


	@Override
	public RxHttpNode doHttpGet(String url, Map<String, String> header, Map<String, String> cookie) {
		
		RxHttpNodeImpl rxHttpNode = null;
		
		CloseableHttpClient httpclient = this.getHttpClient();
		HttpGet get = new HttpGet(url);
		logger.info("doHttpGet: " + url);

		if ( header != null && header.size() > 0 ) {
            for (Entry<String, String> entry : header.entrySet()) {
            	get.addHeader(entry.getKey(), entry.getValue());
            }
        }
        
		if (cookie != null && cookie.size() > 0) {
	        StringBuffer cookies = new StringBuffer();
	        for (Entry<String, String> entry : cookie.entrySet()) {
	        	cookies.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
	        }
	        get.addHeader("Cookie", cookies.toString());
		}
		
		try (CloseableHttpResponse response = httpclient.execute(get)) {
			String res = EntityUtils.toString(response.getEntity(), "utf-8");
			response.close();

			this.pageSource = res;
			// 解析网页 得到文档对象
			this.httpDocument = Jsoup.parse(res);
			
			rxHttpNode = new RxHttpNodeImpl(httpDocument);
			rxHttpNode.setPageSource(res);
			
		} catch (Exception e) {
			logger.error("doHttpGet", e);
		}
		
		return rxHttpNode;
	}

	
	@Override
	public RxHttpNode doHttpPost(String url, Map<String, String> params) {

		return doHttpPost(url, params, null, null);
	}
	
	
	@Override
	public RxHttpNode doHttpPost(String url, Map<String, String> params, Map<String, String> header, Map<String, String> cookie) {
		RxHttpNodeImpl rxHttpNode = null;

		CloseableHttpClient httpclient = this.getHttpClient();
		HttpPost post = new HttpPost(url);
		logger.info("doHttpPost: " + url);
		
		// 建立一个NameValuePair数组，用于存储欲传送的参数
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		// 遍历参数 map,添加参数
		for (String key : params.keySet()) {
			paramsList.add(new BasicNameValuePair(key, params.get(key)));
		}

		 if ( header != null && header.size() > 0 ) {
             for (Entry<String, String> entry : header.entrySet()) {
             	post.addHeader(entry.getKey(), entry.getValue());
             }
         }
         
 		if (cookie != null && cookie.size() > 0) {
 	        StringBuffer cookies = new StringBuffer();
 	        for (Entry<String, String> entry : cookie.entrySet()) {
 	        	cookies.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
 	        }
 	        post.addHeader("Cookie", cookies.toString());
 		}
		
		try {
			post.setEntity(new UrlEncodedFormEntity(paramsList, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			logger.error("UrlEncodedFormEntity", e1);
		}

		try (CloseableHttpResponse response = httpclient.execute(post)) {

			int code = response.getStatusLine().getStatusCode();
			if (code >= 200 && code <= 299) {
				
				String res = EntityUtils.toString(response.getEntity(), "utf-8");
				this.httpDocument = Jsoup.parse(res);
				rxHttpNode = new RxHttpNodeImpl(httpDocument);
			}
			throw new Exception("Post Error in HTTP POST because StatusCodecode <= 200 || code >= 299!");

		} catch (Exception e) {
			logger.error("Post Error in HTTP POST! URL: " + url, e);
		}

		return rxHttpNode;
	}
	
	
	@Override
	public List<RxHttpNode> getRxHttpNodeBySelector(String selector) {

		List<RxHttpNode> rxHttpNode = new ArrayList<>();

		if (this.httpDocument != null) {
			Elements elementList = this.httpDocument.select(selector);

			for (Element element : elementList) {
				rxHttpNode.add(new RxHttpNodeImpl(element));
			}
		}
		return rxHttpNode;
	}

	@Override
	public RxHttpContext getRxHttpContext(String url, String ajaxUrl) {

		WebClient webClient = getHtmlUnitWebClient();
		RxHttpContextImpl httpConext = new RxHttpContextImpl();
		Map<String, String> cookiesMap = new HashMap<>();
		try {
			final URL ajaxURL = new URL(ajaxUrl);
			
			webClient.setWebConnection(new WebConnectionWrapper(webClient) {
				
				public WebResponse getResponse(WebRequest request) throws IOException {

					WebResponse response = super.getResponse(request);
					if (request.getUrl().getPath().contains(ajaxURL.getPath())) {
						logger.info("request-url===" + request.getUrl());
						logger.info("request-headers===" + request.getAdditionalHeaders());
						logger.info("request-httpmethod===" + request.getHttpMethod());
						logger.info("request-parameters===" + request.getRequestParameters());
						logger.info("request-body===" + request.getRequestBody());
						
						httpConext.setHeader(request.getAdditionalHeaders());
						
						if (request.getHttpMethod().toString().equalsIgnoreCase("POST")) {
							httpConext.setParameters(request.getRequestBody());
						} else {// GET方法
							httpConext.setParameters(request.getRequestParameters().toString());
						}
					}

					//保留Response的部分,以防后续有些数据在Response中获得	
//					if (request.getUrl().getPath().contains(ajaxURL.getPath())) {
//						System.out.println("======================= response ======================");
//						System.out.println("response===" + response.toString());
//						System.out.println("response-contents===" + response.getContentAsString());
//						System.out.println("response-ontentType===" + response.getContentType());
//						System.out.println("response-statusCode===" + response.getStatusCode());
//						System.out.println("response-statusMessage===" + response.getStatusMessage());
//						System.out.println("response-Headers===" + response.getResponseHeaders());
//						System.out.println("response-request===" + response.getWebRequest());
//
//					}

					return response;
				}
			});


			webClient.getPage(url);
			
		
			Set<com.gargoylesoftware.htmlunit.util.Cookie> htmlUnitCookies = webClient.getCookies(ajaxURL);
			for (Object cookie : htmlUnitCookies) {
				com.gargoylesoftware.htmlunit.util.Cookie c = (com.gargoylesoftware.htmlunit.util.Cookie) cookie;
				cookiesMap.put(c.getName(), c.getValue());
			}
			
			httpConext.setCookie(cookiesMap);
			
		} catch (MalformedURLException e1) {
			logger.error("getRxHttpContext:", e1);
			throw new RxCrawlerException(999, "请输入正确的AJAX请求URL");
		} catch (FailingHttpStatusCodeException | IOException e) {
			logger.error("getRxHttpContext", e);
		} catch (EvaluatorException e1) {
			logger.error("getRxHttpContext EvaluatorException:", e1);
		}

		return httpConext;
	}

	public int getCrawlerid() {
		return crawlerid;
	}

	public void setCrawlerid(int crawlerid) {
		this.crawlerid = crawlerid;
	}

	public int getRuleVersion() {
		return ruleVersion;
	}

	public void setRuleVersion(int ruleVersion) {
		this.ruleVersion = ruleVersion;
	}

	public MessageObject getMessageObject() {
		return messageObject;
	}

	public void setMessageObject(MessageObject messageObject) {
		this.messageObject = messageObject;
	}
	
	private void pushAppTaskLog(String logContent) {
		
		logger.info("pushAppTaskLog:" + logContent);
		
		if ("TEST".equals(this.messageObject.getScheduledType())) {
			// 开发平台log记入redis
			String log = Constant.APPTASK_LOG_KEY.APP_LOG_STATE_RUNNING + "|" + new SimpleDateFormat("HH:mm:ss").format(new Date())
					             + "|" +  "打开网页 "+ logContent;
			try {
				AppFacade.getInstance().pushAppTaskLog(this.messageObject.getAppSeq(),
						                               this.messageObject.getScenarioIndex(),
						                               this.messageObject.getRuleIndex(),
						                               ruleVersion,
						                               this.messageObject.getScheduledType(),
						                               log);	
			} catch (Exception e) {
				logger.error("pushAppTaskLog异常", e);
			}
		}
		
	}
	
	
    public Map<String, ArrayList<ArrayList<String>>> readExcelDataByUrl(String url) {
    	FormulaEvaluator evaluator;
    	Map<String, ArrayList<ArrayList<String>>> excelData = new HashMap<String, ArrayList<ArrayList<String>>>();

		String url_decode = "";
		try {
			url_decode = URLDecoder.decode(url, "UTF-8");
		} catch (Exception e) {
			logger.error("readExcelDataByUrl异常:url_decode=>", e);
		}

		Workbook workbook = null;
		Sheet sheet = null;
		try {
			workbook = WorkbookFactory.create(downloadExcel(url_decode));
		} catch (EncryptedDocumentException e1) {
			logger.error("readExcelDataByUrl异常:EncryptedDocumentException=>", e1);
		} catch (InvalidFormatException e2) {
			logger.error("readExcelDataByUrl异常:InvalidFormatException=>", e2);
		} catch (IOException e3) {
			logger.error("readExcelDataByUrl异常:IOException=>", e3);
		}
		int sheetNumbers = workbook.getNumberOfSheets();
		evaluator=workbook.getCreationHelper().createFormulaEvaluator();
		for (int i = 0; i < sheetNumbers; i++) {

			sheet = workbook.getSheetAt(i);

			ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();

			for (Row row : sheet) {
				StringBuffer rowContent = new StringBuffer("");
				ArrayList<String> cells = new ArrayList<String>();

				for (Cell cell : row) {

					// 获取值并自己格式化
					switch (cell.getCellTypeEnum()) {
					case STRING:// 字符串型
//						cells.add(cell.getRichStringCellValue().getString());
//						rowContent.append(cell.getRichStringCellValue().getString());
						cells.add(cell.getStringCellValue());
						rowContent.append(cell.getStringCellValue());
						break;
					case NUMERIC:// 数值型
						if (DateUtil.isCellDateFormatted(cell)) { // 如果是date类型则

							cells.add(cell.getDateCellValue() + "");
							rowContent.append(cell.getDateCellValue() + "");
							
						} else {// 纯数字
							
							DecimalFormat df = new DecimalFormat("0.0000");  
							cells.add(df.format(cell.getNumericCellValue()) + "");
							rowContent.append(cell.getNumericCellValue() + "");
						}
						break;
					case BOOLEAN:// 布尔
						cells.add(cell.getNumericCellValue() + "");
						rowContent.append(cell.getNumericCellValue() + "");
						break;
					case FORMULA:// 公式型
						CellValue cellValue = null;
						try {
							
							cellValue = evaluator.evaluate(cell);
							
						} catch (RuntimeException e) {
							logger.error("readExcelDataByUrl异常:RuntimeException=>", e);
							
							cells.add("");
							break;
						}

	                    switch (cellValue.getCellTypeEnum()) {
							case STRING:// 字符串型
								cells.add(cell.getRichStringCellValue().getString());
								rowContent.append(cell.getRichStringCellValue().getString());
								break;
							case NUMERIC:// 数值型
								if (DateUtil.isCellDateFormatted(cell)) { // 如果是date类型则
	
									cells.add(cell.getDateCellValue() + "");
									rowContent.append(cell.getDateCellValue() + "");
									
								} else {// 纯数字
									
									DecimalFormat df = new DecimalFormat("0.0000");  
									cells.add(df.format(cell.getNumericCellValue()) + "");
									rowContent.append(cell.getNumericCellValue() + "");
								}
								break;
							case BOOLEAN:// 布尔
								cells.add(cell.getNumericCellValue() + "");
								rowContent.append(cell.getNumericCellValue() + "");
								break;
							case BLANK:// 空值
								cells.add("");
								break;
							case ERROR: // 故障
								cells.add("");
								break;
							case _NONE:
								cells.add("");
								break;
							default:
								cells.add("");
	                    }
	                    break;
					case BLANK:// 空值
						cells.add("");
						break;
					case ERROR: // 故障
						cells.add("");
						break;
					case _NONE:
						cells.add("");
						break;
					default:
						cells.add("");
					}
					
//                  旧版本的写法
//					// 获取值并自己格式化
//					switch (cell.getCellType()) {
//					case Cell.CELL_TYPE_STRING:// 字符串型
//						cells.add(cell.getRichStringCellValue().getString());
//						rowContent.append(cell.getRichStringCellValue().getString());
//						break;
//					case Cell.CELL_TYPE_NUMERIC:// 数值型
//						if (DateUtil.isCellDateFormatted(cell)) { // 如果是date类型则
//
//							cells.add(cell.getDateCellValue() + "");
//							rowContent.append(cell.getDateCellValue() + "");
//						} else {// 纯数字
//							DecimalFormat df = new DecimalFormat("0.0000");  
//							cells.add(df.format(cell.getNumericCellValue()) + "");
//							rowContent.append(cell.getNumericCellValue() + "");
//						}
//						
//						break;
//					case Cell.CELL_TYPE_BOOLEAN:// 布尔
//						cells.add(cell.getNumericCellValue() + "");
//						rowContent.append(cell.getNumericCellValue() + "");
//						break;
//					case Cell.CELL_TYPE_FORMULA:// 公式型
//						cells.add(evaluator.evaluate(cell) + "");
//						rowContent.append(cell.getCellFormula() + "");
//						break;
//					case Cell.CELL_TYPE_BLANK:// 空值
//						cells.add("");
//						rowContent.append("");
//						break;
//					case Cell.CELL_TYPE_ERROR: // 故障
//						cells.add("");
//						break;
//					default:
//						cells.add("");
//					}

				}
				
				if (rowContent.toString().trim().equals("")) {
					continue;
				}

				if (cells.size() > 0) {
					rows.add(cells);
				}

			}

			String sheetName = workbook.getSheetName(i);

			excelData.put(sheetName, rows);
		}
		
		return excelData;
    }
	
    private InputStream downloadExcel(String url) {
    	
    	InputStream is = null;
    	
		try {
			
//			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet(url);
			HttpResponse response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			
		} catch (Exception e) {
			logger.error("downloadExcel异常:Exception=>", e);
		}
		
		return is;
	}
    
    
    public void pressKeys(CharSequence... keys) {
    	
    	Actions action=new Actions(this.webDriver);

		action.sendKeys(keys).perform();
		
    }
    
    public void pressKeys(int times, CharSequence... keys) {
    	
    	Actions action=new Actions(this.webDriver);

    	for (int i=0; i < times; i++ ) {
    		
    		action.sendKeys(keys).perform();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}

    }

	@Override
	public void addCookie(String name, String value) {
		Cookie cookie = new Cookie(name, value, "/", null);
		this.webDriver.manage().addCookie(cookie);
	}

	@Override
	public void addCookie(String name, String value, String domainName, String path, Date expireDate) {
		Cookie cookie = new Cookie(name, value, domainName, path, expireDate);
		this.webDriver.manage().addCookie(cookie);
		
//		Set<Cookie> cookies = this.webDriver.manage().getCookies();
//		for (Cookie cookieInSet : cookies ) {  
//		      System.out.println(cookieInSet);  
//		}  
		
	}
	
	@Override
    public String getCurrentUrl() {
		
		return this.webDriver.getCurrentUrl();

	}

	@Override
	public RxSimpleCrawler getSimpleCrawler() throws RxCrawlerException {
		
		if ( this.webClient == null ) {
    		
        	this.webClient = new WebClient();
    		
        	if ( this.isUsingProxy ) {
        		
        		IpProxyResult ipProxyInfo = ProxyFacade.getInstance().fetchIpProxy(1, 1);
            	ProxyConfig proxyConfig = new ProxyConfig(ipProxyInfo.getIp(), ipProxyInfo.getPort());
            	final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
            	credentialsProvider.addCredentials(ipProxyInfo.getUser_name(), ipProxyInfo.getPass_word());
            	this.webClient.getOptions().setProxyConfig(proxyConfig);
        	}
        	
        	// 支持js
        	this.webClient.getOptions().setJavaScriptEnabled(true);
        	this.webClient.getOptions().setThrowExceptionOnScriptError(false);
        	this.webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        	
        	// 非200code
        	this.webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        	
        	// 屏蔽css
        	this.webClient.getOptions().setCssEnabled(true);
        	
        	// 支持https
        	this.webClient.getOptions().setUseInsecureSSL(true);
        	
        	// 设置超时时间
        	this.webClient.getOptions().setTimeout(30000);
        	
        	// 支持cookie
        	this.webClient.getCookieManager().setCookiesEnabled(true);
        	
    	}

    	
    	return new RxSimpleCrawlerImpl(this.webClient);
	}

}