package com.ruixuesoft.crawler.open.impl;

import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxSelectNode;

public class RxSelectNodeSimpleImpl implements RxSelectNode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private HtmlSelect select;
	
	public RxSelectNodeSimpleImpl(HtmlSelect select) {
		super();
		this.select = select;
	}
	
	@Override
	public void selectByIndex(int index) throws RxCrawlerException {
		try {
			this.select.getOption(index).click();
			System.out.println("selectByIndex--SelectedIndex:" + this.select.getSelectedIndex());
		} catch (Exception e) {
			throw new RxCrawlerException("selectByIndex发生异常！" + e.getMessage());
		}
	}

	@Override
	public void selectByVisibleText(String text) throws RxCrawlerException {
		try {
			this.select.getOptionByText(text).click();
			System.out.println("selectByVisibleText--SelectedIndex:" + this.select.getSelectedIndex());
		} catch (Exception e) {
			throw new RxCrawlerException("selectByVisibleText发生异常！" + e.getMessage());
		}
	}

	@Override
	public void selectByValue(String value) throws RxCrawlerException {
		try {
			this.select.getOptionByValue(value).click();
			System.out.println("selectByValue--SelectedIndex:" + this.select.getSelectedIndex());
		} catch (Exception e) {
			throw new RxCrawlerException("selectByValue发生异常！" + e.getMessage());
		}
	}

	@Override
	public int getSelectLength() throws RxCrawlerException {
		try {
			return this.select.getOptionSize();
		} catch (Exception e) {
			throw new RxCrawlerException("selectByValue发生异常！" + e.getMessage());
		}
	}

	@Override
	public String getTextByIndex(int index) throws RxCrawlerException {
		try {
			return this.select.getOption(index).getText();
		} catch (Exception e) {
			throw new RxCrawlerException("selectByValue发生异常！" + e.getMessage());
		}
	}

}
