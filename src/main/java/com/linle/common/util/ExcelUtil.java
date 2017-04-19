package com.linle.common.util;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @描述 Excel工具类
 * @作者 Guitarist
 * @版本 linle
 * @创建时间：2015年8月11日 下午5:25:55 青春气贯长虹 勇锐盖过怯弱 进取压倒苟安 年岁有加,并非垂老,理想丢弃,方堕暮年
 */
public class ExcelUtil {
	public static final Logger _logger = LoggerFactory.getLogger(ExcelUtil.class);
	/**
	 * 导出Excel文件
	 */
	public static void exportExcel(String[] titles, ServletOutputStream outputStream) {
		System.out.println("================================================");
		// 创建一个workbook 对应一个excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(System.currentTimeMillis() + "");
		XSSFCellStyle headStyle = getHeadStyle(workBook);
		XSSFCellStyle bodyStyle = getBodyStyle(workBook);
		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		XSSFCell headCell = null;
		for (int i = 0; i < 10; i++) {
			headCell = headRow.createCell(i);
			headCell.setCellStyle(headStyle);
			headCell.setCellValue("title");
		}
		// 构建表体数据
		for (int i = 1; i <= 5; i++) {// 行数
			XSSFRow bodyRow = sheet.createRow(i);
			for (int j = 0; j < 10; j++) {// 列数
				XSSFCell bodyCell = bodyRow.createCell(j);
				bodyCell.setCellStyle(bodyStyle);
				bodyCell.setCellValue("行数:" + i + "\t列数" + (j + 1));
			}
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace(); _logger.error("出错了", e);
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace(); _logger.error("出错了", e);
			}
		}

	}

	/**
	 * 设置表头的单元格样式
	 * 
	 * @return
	 */
	private static XSSFCellStyle getHeadStyle(XSSFWorkbook wb) {
		// 创建单元格样式
		XSSFCellStyle cellStyle = wb.createCellStyle();
		// 设置单元格的背景颜色为淡蓝色
		cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		// 设置单元格居中对齐
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置单元格垂直居中对齐
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		// 设置单元格字体样式
		XSSFFont font = wb.createFont();
		// 设置字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		// 设置单元格边框为细线条
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}

	/**
	 * 设置表体的单元格样式
	 * 
	 * @return
	 */
	private static XSSFCellStyle getBodyStyle(XSSFWorkbook wb) {
		// 创建单元格样式
		XSSFCellStyle cellStyle = wb.createCellStyle();
		// 设置单元格居中对齐
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		// 设置单元格垂直居中对齐
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 创建单元格内容显示不下时自动换行
		cellStyle.setWrapText(true);
		// 设置单元格字体样式
		XSSFFont font = wb.createFont();
		// 设置字体加粗
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 200);
		cellStyle.setFont(font);
		// 设置单元格边框为细线条
		cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		return cellStyle;
	}
}