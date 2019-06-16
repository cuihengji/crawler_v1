package com.ruixuesoft.crawler.open;

import java.util.List;

public interface RxHttpNode {

	/**
	 * @return得到RxHttpNode的源代码
	 */
	public String getPageSource();
	
	/**
	 * @return得到节点的文本信息
	 */
	public String getText();
	
	/**
	 * @param attibute得到节点上指定的属性信息
	 * @return
	 */
	public String getAttribute(String attibute);
	
	public List<RxHttpNode> getRxHttpNodeListBySelector(String selector);
	
	public RxHttpNode getRxHttpNodeBySelector(String selector);
	
}
