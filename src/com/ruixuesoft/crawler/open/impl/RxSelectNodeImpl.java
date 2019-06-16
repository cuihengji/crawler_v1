package com.ruixuesoft.crawler.open.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxSelectNode;

public class RxSelectNodeImpl implements RxSelectNode {
	
	private static final Logger logger = Logger.getLogger(RxSelectNodeImpl.class);
	
	private Select select;
	
	public RxSelectNodeImpl(Select select) {
		super();
		this.select = select;
	}

	@Override
	public void selectByIndex(int index) throws RxCrawlerException {
		
		checkInterrupted();

		try {
			select.selectByIndex(index);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
	}

	@Override
	public void selectByVisibleText(String text) throws RxCrawlerException {
		
		checkInterrupted();
		
		try {
			select.selectByVisibleText(text);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
	}

	@Override
	public void selectByValue(String value) throws RxCrawlerException {
		
		checkInterrupted();
		
		try {
			select.selectByValue(value);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
	}
	
	
	public void checkInterrupted() {

		if (Thread.currentThread().isInterrupted()) {
			throw new RxCrawlerException("该任务已被用户请求强制终止！");
		}
	}

	@Override
	public int getSelectLength() throws RxCrawlerException {
		
		checkInterrupted();
		
		try {
			List<WebElement> options = select.getOptions();
			return options.size();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
		
	}

	@Override
	public String getTextByIndex(int index) throws RxCrawlerException {
		
		checkInterrupted();
		
		String selectValue = "NA"; 
		try {
			select.selectByIndex(index);
			WebElement option = select.getFirstSelectedOption();
			selectValue = option.getText();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new RxCrawlerException(e.getMessage());
		}
		
		return selectValue;
	}

}
