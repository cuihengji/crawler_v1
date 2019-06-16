package com.ruixuesoft.crawler.open.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ruixuesoft.crawler.open.RxHttpNode;

public class RxHttpNodeImpl implements RxHttpNode {

	private Document httpDocument;
	private Elements elements;
	private Element element;
	
	private String pageSource;

	
	public RxHttpNodeImpl(Document httpDocument) {
		super();
		this.httpDocument = httpDocument;
	}
	
	public RxHttpNodeImpl(Elements elements) {
		super();
		this.elements = elements;
	}
	
	public RxHttpNodeImpl(Element element) {
		super();
		this.element = element;
	}
	
	@Override
	public RxHttpNode getRxHttpNodeBySelector(String selector) {

		RxHttpNode rxHttpNode = null;
		Elements selectedElements = null;

		if (this.httpDocument != null) {
			selectedElements = httpDocument.select(selector);
		} else if (this.elements != null) {
			selectedElements = this.elements.select(selector);
		} else {
			selectedElements = this.element.select(selector);
		}

		if (selectedElements != null) {
			rxHttpNode = new RxHttpNodeImpl(selectedElements);
		}

		return rxHttpNode;
	}
	
	@Override
	public List<RxHttpNode> getRxHttpNodeListBySelector(String selector) {

		List<RxHttpNode> rxHttpNode = new ArrayList<>();
		Elements selectedElements = null;

		if (this.httpDocument != null) {
			selectedElements = httpDocument.select(selector);
		} else if (this.elements != null) {
			selectedElements = this.elements.select(selector);
		} else {
			selectedElements = this.element.select(selector);
		}
		
		if (selectedElements != null) {
			for (Element element : selectedElements) {
				rxHttpNode.add(new RxHttpNodeImpl(element));
			}
		}

		return rxHttpNode;
	}
	
	
	@Override
	public String getText() {

		String text = "NA";
		
		if (this.httpDocument != null) {
			text = this.httpDocument.text();
		} else if (this.elements != null) {
			text = this.elements.text();
		} else {
			text = this.element.text();
		}
		
		return text;
	}

	@Override
	public String getAttribute(String attibuteKey) {

		String attribute = "NA";
		
		if (this.httpDocument != null) {
			attribute = this.httpDocument.attr(attibuteKey);
		} else if (this.elements != null) {
			attribute = this.elements.attr(attibuteKey);
		} else {
			attribute = this.element.attr(attibuteKey);
		}
		
		return attribute;
	}

	public String getPageSource() {
		return pageSource;
	}
	public void setPageSource(String pageSource) {
		this.pageSource = pageSource;
	}

	
}
