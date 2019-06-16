package com.web2data.engine.crawler.browser.impl.a;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
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

import com.web2data.engine.crawler.httpclient.RxCrawlerImpl_95_HttpClient;

public class RxCrawlerImpl_97_Download {

	
//    // -------------------------------------------------------
//    private static final transient Logger logger = Logger.getLogger(RxCrawlerImpl_97_Download.class);
//   
//	
//	
//    public static Map<String, ArrayList<ArrayList<String>>> readExcelDataByUrl(String url) {
//    	FormulaEvaluator evaluator;
//    	Map<String, ArrayList<ArrayList<String>>> excelData = new HashMap<String, ArrayList<ArrayList<String>>>();
//
//		String url_decode = "";
//		try {
//			url_decode = URLDecoder.decode(url, "UTF-8");
//		} catch (Exception e) {
//			logger.error("readExcelDataByUrl异常:url_decode=>", e);
//		}
//
//		Workbook workbook = null;
//		Sheet sheet = null;
//		try {
//			workbook = WorkbookFactory.create(downloadExcel(url_decode));
//		} catch (EncryptedDocumentException e1) {
//			logger.error("readExcelDataByUrl异常:EncryptedDocumentException=>", e1);
//		} catch (InvalidFormatException e2) {
//			logger.error("readExcelDataByUrl异常:InvalidFormatException=>", e2);
//		} catch (IOException e3) {
//			logger.error("readExcelDataByUrl异常:IOException=>", e3);
//		}
//		int sheetNumbers = workbook.getNumberOfSheets();
//		evaluator=workbook.getCreationHelper().createFormulaEvaluator();
//		for (int i = 0; i < sheetNumbers; i++) {
//
//			sheet = workbook.getSheetAt(i);
//
//			ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
//
//			for (Row row : sheet) {
//				StringBuffer rowContent = new StringBuffer("");
//				ArrayList<String> cells = new ArrayList<String>();
//
//				for (Cell cell : row) {
//
//					// 获取值并自己格式化
//					switch (cell.getCellTypeEnum()) {
//					case STRING:// 字符串型
////						cells.add(cell.getRichStringCellValue().getString());
////						rowContent.append(cell.getRichStringCellValue().getString());
//						cells.add(cell.getStringCellValue());
//						rowContent.append(cell.getStringCellValue());
//						break;
//					case NUMERIC:// 数值型
//						if (DateUtil.isCellDateFormatted(cell)) { // 如果是date类型则
//
//							cells.add(cell.getDateCellValue() + "");
//							rowContent.append(cell.getDateCellValue() + "");
//							
//						} else {// 纯数字
//							
//							DecimalFormat df = new DecimalFormat("0.0000");  
//							cells.add(df.format(cell.getNumericCellValue()) + "");
//							rowContent.append(cell.getNumericCellValue() + "");
//						}
//						break;
//					case BOOLEAN:// 布尔
//						cells.add(cell.getNumericCellValue() + "");
//						rowContent.append(cell.getNumericCellValue() + "");
//						break;
//					case FORMULA:// 公式型
//						CellValue cellValue = null;
//						try {
//							
//							cellValue = evaluator.evaluate(cell);
//							
//						} catch (RuntimeException e) {
//							logger.error("readExcelDataByUrl异常:RuntimeException=>", e);
//							
//							cells.add("");
//							break;
//						}
//
//	                    switch (cellValue.getCellTypeEnum()) {
//							case STRING:// 字符串型
//								cells.add(cell.getRichStringCellValue().getString());
//								rowContent.append(cell.getRichStringCellValue().getString());
//								break;
//							case NUMERIC:// 数值型
//								if (DateUtil.isCellDateFormatted(cell)) { // 如果是date类型则
//	
//									cells.add(cell.getDateCellValue() + "");
//									rowContent.append(cell.getDateCellValue() + "");
//									
//								} else {// 纯数字
//									
//									DecimalFormat df = new DecimalFormat("0.0000");  
//									cells.add(df.format(cell.getNumericCellValue()) + "");
//									rowContent.append(cell.getNumericCellValue() + "");
//								}
//								break;
//							case BOOLEAN:// 布尔
//								cells.add(cell.getNumericCellValue() + "");
//								rowContent.append(cell.getNumericCellValue() + "");
//								break;
//							case BLANK:// 空值
//								cells.add("");
//								break;
//							case ERROR: // 故障
//								cells.add("");
//								break;
//							case _NONE:
//								cells.add("");
//								break;
//							default:
//								cells.add("");
//	                    }
//	                    break;
//					case BLANK:// 空值
//						cells.add("");
//						break;
//					case ERROR: // 故障
//						cells.add("");
//						break;
//					case _NONE:
//						cells.add("");
//						break;
//					default:
//						cells.add("");
//					}
//					
////                  旧版本的写法
////					// 获取值并自己格式化
////					switch (cell.getCellType()) {
////					case Cell.CELL_TYPE_STRING:// 字符串型
////						cells.add(cell.getRichStringCellValue().getString());
////						rowContent.append(cell.getRichStringCellValue().getString());
////						break;
////					case Cell.CELL_TYPE_NUMERIC:// 数值型
////						if (DateUtil.isCellDateFormatted(cell)) { // 如果是date类型则
////
////							cells.add(cell.getDateCellValue() + "");
////							rowContent.append(cell.getDateCellValue() + "");
////						} else {// 纯数字
////							DecimalFormat df = new DecimalFormat("0.0000");  
////							cells.add(df.format(cell.getNumericCellValue()) + "");
////							rowContent.append(cell.getNumericCellValue() + "");
////						}
////						
////						break;
////					case Cell.CELL_TYPE_BOOLEAN:// 布尔
////						cells.add(cell.getNumericCellValue() + "");
////						rowContent.append(cell.getNumericCellValue() + "");
////						break;
////					case Cell.CELL_TYPE_FORMULA:// 公式型
////						cells.add(evaluator.evaluate(cell) + "");
////						rowContent.append(cell.getCellFormula() + "");
////						break;
////					case Cell.CELL_TYPE_BLANK:// 空值
////						cells.add("");
////						rowContent.append("");
////						break;
////					case Cell.CELL_TYPE_ERROR: // 故障
////						cells.add("");
////						break;
////					default:
////						cells.add("");
////					}
//
//				}
//				
//				if (rowContent.toString().trim().equals("")) {
//					continue;
//				}
//
//				if (cells.size() > 0) {
//					rows.add(cells);
//				}
//
//			}
//
//			String sheetName = workbook.getSheetName(i);
//
//			excelData.put(sheetName, rows);
//		}
//		
//		return excelData;
//    }
//	
//    private static InputStream downloadExcel(String url) {
//    	
//    	InputStream is = null;
//    	
//		try {
//			
////			HttpClient client = HttpClientBuilder.create().build();
//			HttpGet get = new HttpGet(url);
//			HttpResponse response = RxCrawlerImpl_95_HttpClient.getHttpClient().execute(get);
//			HttpEntity entity = response.getEntity();
//			is = entity.getContent();
//			
//		} catch (Exception e) {
//			logger.error("downloadExcel异常:Exception=>", e);
//		}
//		
//		return is;
//	}
    
}
