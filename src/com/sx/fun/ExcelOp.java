package com.sx.fun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sx.db.DBlink;
import com.sx.entity.Book;
import com.sx.util.Constant;
import com.sx.util.DbMetadata;
import com.sx.util.DbTitle;
import com.sx.util.ExcelTitle;

/**
 * 表格导入、导出操作
 * 
 * @author sunjie
 *
 */
public class ExcelOp {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelOp.class);

	/*
	 * 使用poi读取book-excel导入数据库
	 */
	public static void importBookExcel(File file) {
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
		List<Book> bookList = assemble(wb);

		try {
			BookOp.insertBooks(bookList);
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
			if (cell.toString().equals(ExcelTitle.BOOKNAME)) {
				map.put(DbTitle.BOOKNAME, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.AUTHOR)) {
				map.put(DbTitle.AUTHOR, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.PRESS)) {
				map.put(DbTitle.PRESS, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.PRESSDATE)) {
				map.put(DbTitle.PRESSDATE, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.PRICE)) {
				map.put(DbTitle.PRICE, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.COUNT)) {
				map.put(DbTitle.COUNT, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.TOTALRPICE)) {
				map.put(DbTitle.TOTALRPICE, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.ISBN)) {
				map.put(DbTitle.ISBN, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.BOOKCATEGORY)) {
				map.put(DbTitle.BOOKCATEGORY, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.LANGUAGE)) {
				map.put(DbTitle.LANGUAGE, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.SIZE)) {
				map.put(DbTitle.SIZE, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.BINDING)) {
				map.put(DbTitle.BINDING, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.FEATURE)) {
				map.put(DbTitle.FEATURE, cell.getColumnIndex());
			}
		}
		return map;
	}

	/*
	 * 把excel表中的内容重新组装成booklist
	 */
	private static List<Book> assemble(Workbook wb) {
		List<Book> bookList = new ArrayList<>();
		// 得到第一页 sheet
		// 页Sheet是从0开始索引的
		Sheet sheet = wb.getSheetAt(0);
		Map<String, Integer> map = getMapByColumnName(sheet);

		int totalRowNum = sheet.getLastRowNum();
		Row row = null;
		// 利用循环 遍历sheet中的所有行,除第一行以外
		for (int i = 1; i <= totalRowNum; i++) {
			Book book = new Book();
			// 遍历row中的所有方格
			row = sheet.getRow(i);

			Cell cell = row.getCell(map.get(DbTitle.BOOKNAME));
			book.setBookName(cell.toString());

			cell = row.getCell(map.get(DbTitle.AUTHOR));
			book.setAuthor(cell.toString().trim());

			cell = row.getCell(map.get(DbTitle.PRESS));
			book.setPress(cell.toString().trim());

			cell = row.getCell(map.get(DbTitle.PRESSDATE));
			book.setPressDate(cell.toString().trim());
			try {
				cell = row.getCell(map.get(DbTitle.PRICE));
				book.setPrice(Double.parseDouble(cell.toString().trim()));

				cell = row.getCell(map.get(DbTitle.COUNT));
				double count = Double.parseDouble(cell.toString().trim());
				book.setCount((int) count);

				cell = row.getCell(map.get(DbTitle.TOTALRPICE));
				book.setTotalPrice(Double.parseDouble(cell.toString().trim()));
			} catch (NumberFormatException e) {
				LOGGER.error("Number Format Exception!!! Error row num: " + i);
				LOGGER.error(e.getMessage());
				LOGGER.error(e.getStackTrace().toString());
			}

			cell = row.getCell(map.get(DbTitle.ISBN));
			book.setISBN(Long.parseLong(cell.toString().trim()));

			cell = row.getCell(map.get(DbTitle.BOOKCATEGORY));
			book.setBookCategory(cell.toString().trim());

			cell = row.getCell(map.get(DbTitle.LANGUAGE));
			book.setLanguage(cell.toString().trim());

			cell = row.getCell(map.get(DbTitle.SIZE));
			book.setSize(cell.toString().trim());

			cell = row.getCell(map.get(DbTitle.BINDING));
			book.setBinding(cell.toString().trim());

			cell = row.getCell(map.get(DbTitle.FEATURE));
			book.setFeature(cell.toString().trim());

			bookList.add(book);
		}

		return bookList;
	}

	/*
	 * 导出booktable数据库内容到excel param: fileName:导出名称 tableName:要导出的表名
	 */
	// 使用POI创建excel工作簿
	public static void exportExcel(String fileName, String tableName) {
		boolean flag = true; // 判断是否执行成功

		// 创建excel工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），命名为 new sheet
		Sheet sheet = wb.createSheet();
		// Row 行
		// Cell 方格
		// Row 和 Cell 都是从0开始计数的
		// 创建第一行，在页sheet上
		setHeader(sheet, tableName);

		// 根据表名填充数据
		String sqlString = "select * from " + tableName;
		DBlink dBlink = new DBlink();
		ResultSet rs = dBlink.getResult(sqlString);

		int i = 1; // 设置行号
		try {
			while (rs.next()) {
				Row r = sheet.createRow(i);
				int colNum = rs.getMetaData().getColumnCount();
				Cell cell = r.createCell(0);
				cell.setCellValue(i);
				for (int j = 1; j < colNum; j++) {
					cell = r.createCell(j);
					cell.setCellValue(rs.getString(j + 1));
				}
				i++;
			}
		} catch (SQLException e) {
			flag = false;
			LOGGER.error("A SQLException when export data to excel.");
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
		} finally {
			DBlink.closeConnection(rs, null, null); // is there a problem?
		}

		// 创建一个文件 命名为workbook.xls
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			flag = false;
			JOptionPane.showMessageDialog(null, Constant.fileNotFoundError,
					"错误", JOptionPane.ERROR_MESSAGE);
			LOGGER.error("A FileNotFoundException when export data to excel.");
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
		}
		// 把上面创建的工作簿输出到文件中
		try {
			wb.write(fileOut);
		} catch (IOException e) {
			flag = false;
			LOGGER.error("A IOException when export data to excel.");
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
		}
		// 关闭输出流
		try {
			fileOut.close();
		} catch (IOException e) {
			flag = false;
			LOGGER.error("A IOException when export data to excel(close fileoutput stream).");
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
		}

		if (true == flag) {
			JOptionPane.showMessageDialog(null, "导出" + fileName + "成功", "成功",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/*
	 * 设置数据库导出时候的第一行表头内容
	 */
	private static Row setHeader(Sheet sheet, String tableName) {
		Row row = sheet.createRow(0); // 第一行
		row.createCell(0).setCellValue("ID");
		List<String> columnList = DbMetadata.getColumns(tableName);
		String str = "";
		for (int i = 1; i < columnList.size(); i++) {
			str = columnList.get(i);
			row.createCell(i).setCellValue(str);
		}
		return row;
	}
}
