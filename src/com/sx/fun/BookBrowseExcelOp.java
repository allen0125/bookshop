package com.sx.fun;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sx.entity.BookBrowser;
import com.sx.util.DbTitle;
import com.sx.util.ExcelTitle;


public class BookBrowseExcelOp {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BookBrowseExcelOp.class);

	/*
	 * 使用poi读取book-excel导入数据库
	 */
	public static void importBookBrowseExcel(File file) throws ParseException {
		boolean flag = true; // 执行是否成功的标志位
		// poi读取excel
		// 创建要读入的文件的输入流
		InputStream inp = null;
		try {
			inp = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			flag = false;
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
			String errorMsg = String.format(
					"\u6587\u4ef6 %s \u4e0d\u5b58\u5728!", // 文件不存在
					file.getAbsoluteFile());
			LOGGER.error(errorMsg);
			JOptionPane.showMessageDialog(null, errorMsg, "错误",
					JOptionPane.ERROR_MESSAGE);
		}

		// 根据上述创建的输入流 创建工作簿对象
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(inp);
		} catch (InvalidFormatException | IOException e) {
			flag = false;
			LOGGER.error("Create workbook error!!!");
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
		}
		// 把工作簿转化成booklist
		List<BookBrowser> bookBorrowList = assemble(wb);

		try {
			BookBrowseOp.insertBookBrowsers(bookBorrowList);
		} catch (Exception e) {
			flag = false;
			JOptionPane.showMessageDialog(null, "导入数据库错误", "错误",
					JOptionPane.ERROR_MESSAGE);
			LOGGER.error("Insert into booktable error!!!");
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
			return;
		} finally {
			// 关闭输入流
			try {
				inp.close();
				LOGGER.info("InputStream close.");
			} catch (IOException e) {
				LOGGER.error("Close inputstream error!!!");
				LOGGER.error(e.getMessage());
				LOGGER.error(e.getStackTrace().toString());
			}
		}

		if (true == flag) {
			JOptionPane.showMessageDialog(null, "导入成功", "成功",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/*
	 * 使excel的列的名字和列的序号对应起来，使用map存储
	 */
	private static Map<String, Integer> getMapByColumnName(Sheet sheet) {
		Row row = sheet.getRow(0);
		Map<String, Integer> map = new HashMap<>();
		for (Cell cell : row) {
			if (cell.toString().equals(ExcelTitle.BB_BOOKID)) {
				map.put(DbTitle.BB_BOOKID, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.BB_READERID)) {
				map.put(DbTitle.BB_READERID, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.BB_BORROWDATE)) {
				map.put(DbTitle.BB_BORROWDATE, cell.getColumnIndex());
			}else if (cell.toString().equals(ExcelTitle.BB_RETRUNDATE)) {
				map.put(DbTitle.BB_RETRUNDATE, cell.getColumnIndex());
			}else if (cell.toString().equals(ExcelTitle.BB_COMMENT)) {
				map.put(DbTitle.BB_COMMENT, cell.getColumnIndex());
			}
		}
		return map;
	}

	/*
	 * 把excel表中的内容重新组装bookBrowserlist
	 */
	private static List<BookBrowser> assemble(Workbook wb) throws ParseException {
		List<BookBrowser> bookBrowserList = new ArrayList<>();
		// 得到第一页 sheet
		// 页Sheet是从0开始索引的
		Sheet sheet = wb.getSheetAt(0);
		Map<String, Integer> map = getMapByColumnName(sheet);

		int totalRowNum = sheet.getLastRowNum();
		Row row = null;
		// 利用循环 遍历sheet中的所有行,除第一行以外
		for (int i = 1; i <= totalRowNum; i++) {
			BookBrowser bookBrowser = new BookBrowser();
			// 遍历row中的所有方格
			row = sheet.getRow(i);

			Cell cell = row.getCell(map.get(DbTitle.BB_BOOKID));
			try {
				bookBrowser.setBID((int) Double.parseDouble(cell.toString()));
			} catch (NumberFormatException e) {
				LOGGER.error("Number Format Exception!!! Error row num: " + i);
				LOGGER.error(e.getMessage());
				LOGGER.error(e.getStackTrace().toString());
			}

			cell = row.getCell(map.get(DbTitle.BB_READERID));
			bookBrowser.setUID((int) Double.parseDouble(cell.toString()));
			
			SimpleDateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");

			cell = row.getCell(map.get(DbTitle.BB_BORROWDATE));
			try {
				java.util.Date date1 = fmt.parse(cell.toString().trim());
				bookBrowser.setBorrowDate(date1);
			} catch (Exception e) {
				// TODO: handle exception
			}

			cell = row.getCell(map.get(DbTitle.BB_RETRUNDATE));
			try {
				java.util.Date date2 = fmt.parse(cell.toString().trim());
				bookBrowser.setReturnDate(date2);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			cell = row.getCell(map.get(DbTitle.BB_COMMENT));
			bookBrowser.setComment(cell.toString().trim());

			bookBrowserList.add(bookBrowser);
		}

		return bookBrowserList;
	}

	
}
