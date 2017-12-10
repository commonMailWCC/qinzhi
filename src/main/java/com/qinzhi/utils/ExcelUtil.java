package com.qinzhi.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qinzhi.domain.Goods;
import com.qinzhi.domain.SysOperator;

/**
 * @author : liwei
 * @Comments : 导入导出Excel工具类
 */

public class ExcelUtil {

	@SuppressWarnings("deprecation")
	public static Map<String, Object> parseExcel(InputStream in, boolean isE2007) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String message = "success";
		List<SysOperator> operators = new ArrayList<SysOperator>();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		Workbook workbook = null;
		try {
			InputStream is = in;// 文件流
			if (isE2007) {
				workbook = new XSSFWorkbook(is);
			} else {
				workbook = new HSSFWorkbook(is);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 3.遍历集合，组装结果
		int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
		// 遍历每个Sheet
		for (int s = 0; s < sheetCount; s++) {
			Sheet sheet = workbook.getSheetAt(s);
			int rowCount = sheet.getPhysicalNumberOfRows(); // 获取总行数
			// 遍历每一行
			for (int r = 1; r < rowCount; r++) {
				SysOperator operator = new SysOperator();
				Row row = sheet.getRow(r);
				int cellCount = row.getPhysicalNumberOfCells(); // 获取总列数
				// 遍历每一列
				for (int c = 0; c < cellCount; c++) {
					Cell cell = row.getCell(c);
					int cellType = cell.getCellType();
					String cellStringValue = null;
					switch (cellType) {
					case Cell.CELL_TYPE_STRING: // 文本
						cellStringValue = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC: // 数字、日期
						if (DateUtil.isCellDateFormatted(cell)) {
							cellStringValue = fmt.format(cell.getDateCellValue()); // 日期型
						} else {
							cellStringValue = String.valueOf(new Double(cell.getNumericCellValue()).longValue()); // 数字
							if (cellStringValue.contains("E")) {
								cellStringValue = String.valueOf(new Double(cell.getNumericCellValue()).longValue()); // 数字
							}
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN: // 布尔型
						cellStringValue = String.valueOf(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_BLANK: // 空白
						cellStringValue = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_ERROR: // 错误
						cellStringValue = "错误";
						break;
					case Cell.CELL_TYPE_FORMULA: // 公式
						cellStringValue = "错误";
						break;
					default:
						cellStringValue = "错误";
					}

					if (cellStringValue.equals("错误")) {
						message = "解析Excel时发生错误，第[" + (s + 1) + "]sheet，第[" + (row.getRowNum() + 1) + "]行，第[" + (c + 1)
								+ "]列解析错误";
						resultMap.put("message", message);
						return resultMap;
					}
					System.out.println(c + "-------" + cellStringValue);
					switch (c) {
					case 0:
						operator.setOperatorLoginName(cellStringValue);
						if (StringUtils.isBlank(cellStringValue)) {
							message = "解析Excel时发生错误，第[" + (row.getRowNum() + 1) + "]行，第[" + (c + 1) + "]列 登录名为空";
							resultMap.put("message", message);
							return resultMap;
						}
						break;
					case 1:
						operator.setOperatorPassword(cellStringValue);
						if (StringUtils.isBlank(cellStringValue)) {
							message = "解析Excel时发生错误，第[" + (row.getRowNum() + 1) + "]行，第[" + (c + 1) + "]列 密码为空";
							resultMap.put("message", message);
							return resultMap;
						}
						break;
					case 2:
						operator.setOperatorName(cellStringValue);
						break;
					case 3:
						operator.setSaleGoods(cellStringValue);
						break;
					case 4:
						operator.setOperatorContact(cellStringValue);
						break;
					case 5:
						operator.setOperatorQq(cellStringValue);
						if (!isNumeric(cellStringValue)) {
							System.out.println(cellStringValue + isNumeric(cellStringValue));
							message = "解析Excel时发生错误，第[" + (row.getRowNum() + 1) + "]行，第[" + (c + 1) + "]列 qq格式不匹配";
							resultMap.put("message", message);
							return resultMap;
						}
						;
						break;
					case 6:
						operator.setOperatorEmail(cellStringValue);
						if (!checkEmail(cellStringValue)) {
							message = "解析Excel时发生错误，第[" + (row.getRowNum() + 1) + "]行，第[" + (c + 1) + "]列 邮箱格式不匹配";
							resultMap.put("message", message);
							return resultMap;
						}
						;
						break;
					case 7:
						operator.setOperatorAddress(cellStringValue);
						break;
					default:
						System.out.println("未定义");
						break;
					}
				}
				operators.add(operator);
			}
		}
		resultMap.put("message", message);
		resultMap.put("operators", operators);
		return resultMap;
	}

	@SuppressWarnings("deprecation") 
	public static Map<String, Object> parseGoodsExcel(InputStream in, boolean isE2007) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String message = "success";
		List<Goods> goodss = new ArrayList<Goods>();

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		Workbook workbook = null;
		try {
			InputStream is = in;// 文件流
			if (isE2007) {
				workbook = new XSSFWorkbook(is);
			} else {
				workbook = new HSSFWorkbook(is);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 3.遍历集合，组装结果
		int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
		// 遍历每个Sheet
		for (int s = 0; s < sheetCount; s++) {
			Sheet sheet = workbook.getSheetAt(s);
			int rowCount = sheet.getPhysicalNumberOfRows(); // 获取总行数
			// 遍历每一行
			for (int r = 1; r < rowCount; r++) {
				Goods goods = new Goods();
				Row row = sheet.getRow(r);
				int cellCount = row.getPhysicalNumberOfCells(); // 获取总列数
				// 遍历每一列
				for (int c = 0; c < cellCount; c++) {
					Cell cell = row.getCell(c);
					if ( null ==cell) {
						continue;
					}
					int cellType = cell.getCellType();
					String cellStringValue = null;
					switch (cellType) {

					case Cell.CELL_TYPE_STRING: // 文本
						cellStringValue = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC: // 数字、日期
						if (DateUtil.isCellDateFormatted(cell)) {
							cellStringValue = fmt.format(cell.getDateCellValue()); // 日期型
						} else {
							cellStringValue = String.valueOf(cell.getNumericCellValue()); // 数字
							if (cellStringValue.contains("E")) {
								cellStringValue = String.valueOf(cell.getNumericCellValue()); // 数字
							}
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN: // 布尔型
						cellStringValue = String.valueOf(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_BLANK: // 空白
						cellStringValue = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_ERROR: // 错误
						cellStringValue = "错误";
						break;
					case Cell.CELL_TYPE_FORMULA: // 公式
						cellStringValue = "错误";
						break;
					default:
						cellStringValue = "错误";
					}

					if (cellStringValue.equals("错误")) {
						message = "解析Excel时发生错误，第[" + (s + 1) + "]sheet，第[" + (row.getRowNum() + 1) + "]行，第[" + (c + 1)
								+ "]列解析错误";
						resultMap.put("message", message);
						return resultMap;
					}
					System.out.println(c + "-------" + cellStringValue);
					switch (c) {
					case 0:
						goods.setGoodsName(cellStringValue);
						goods.setGoodsCode(cellStringValue);
						if (StringUtils.isBlank(cellStringValue)) {
							message = "解析Excel时发生错误，第[" + (row.getRowNum() + 1) + "]行，第[" + (c + 1) + "]列,商品为空";
							resultMap.put("message", message);
							return resultMap;
						}
						break;
					case 1:
						goods.setGoodsPrice(new BigDecimal(cellStringValue));
						break;
					case 2:
						goods.setGoodsBrand(cellStringValue);
						break;
					case 3:
						if (StringUtils.isNotBlank(cellStringValue)) {
							Date date = parseToDate(cellStringValue);
							goods.setGoodsDate(date);
						}
						break;
					case 4:
						goods.setGoodsDesc(cellStringValue);
						break;
					case 5:
						goods.setGoodsImage(cellStringValue);
						break;
					case 6:
						goods.setGoodsAddress(cellStringValue);
						break;	
					default:
						System.out.println("未定义");
						break;
					}
				}
				goodss.add(goods);
			}
		}
		resultMap.put("message", message);
		resultMap.put("goods", goodss);
		return resultMap;
	}

	public static void main(String[] args) {
		System.out.println(checkEmail("q12s@163.com"));
	}

	public static Date parseToDate(String dateString) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(dateString);
			return date;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkEmail(String email) {
		if (StringUtils.isBlank(email)) {
			return true;
		}
		// 验证邮箱的正则表达式
		String format = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
		// p{Alpha}:内容是必选的，和字母字符[\p{Lower}\p{Upper}]等价。如：200896@163.com不是合法的。
		// w{2,15}: 2~15个[a-zA-Z_0-9]字符；w{}内容是必选的。 如：dyh@152.com是合法的。
		// [a-z0-9]{3,}：至少三个[a-z0-9]字符,[]内的是必选的；如：dyh200896@16.com是不合法的。
		// [.]:'.'号时必选的； 如：dyh200896@163com是不合法的。
		// p{Lower}{2,}小写字母，两个以上。如：dyh200896@163.c是不合法的。
		if (email.matches(format)) {
			return true;// 邮箱名合法，返回true
		} else {
			return false;// 邮箱名不合法，返回false
		}
	}
}
