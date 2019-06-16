package com.web2data.engine.crawler.browser.impl.a;

import java.util.Date;
import java.util.List;

import com.ruixuesoft.crawler.open.RxCrawler;
import com.ruixuesoft.crawler.open.RxCrawlerException;
import com.ruixuesoft.crawler.open.RxDatabase;
import com.ruixuesoft.crawler.open.RxNode;
import com.ruixuesoft.crawler.open.RxResult;
import com.ruixuesoft.crawler.open.RxRule;
import com.ruixuesoft.crawler.open.RxTask;

public class Rule_YinJianHui_News implements RxRule {

	@Override
	public RxResult execute(RxTask task, RxCrawler crawler, RxDatabase database)
			throws RxCrawlerException {

		task.log("银监会网站新闻抓取开始");

		// 打开要抓取的网站页面
		crawler.open("http://www.cbrc.gov.cn/chinese/home/docViewPage/110010&current=1");
		task.log("打开页面");

		// 选取感兴趣的网页元素的XPath -- 新闻表格
		RxNode node = crawler.getNodeByXpath("//*[@id=\"testUI\"]");

		// 选择新闻的标题
		List<RxNode> nodeList = node
				.getNodeListByXpath("//table[@id=\"testUI\"]//tr/td[1]");
		task.log("选取抓取节点");

		Date captureDate = new Date();

		// 插入的数据表需要提前创建好
		String sql = "insert into yin_jian_hui_news (newsTitle, captureDate) Values(?,?)";

		for (RxNode rxNode : nodeList) {
			// 插入记录到数据库
			Object[] params = { rxNode.getText(), captureDate };
//			try {
				database.insert(sql, params);
//			} catch (SQLException e) {
//				task.log(e.getMessage());
//			}

		}

		RxResult result = new RxResult();
		// 设置FinishCode为200,表示程序正常结束
		result.setFinishCode(200);

		task.log("银监会网站新闻抓取完成");
		return result;
	}
}