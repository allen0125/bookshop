package com.sx.fun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import com.sx.entity.Reader;
import com.sx.util.DbTitle;
import com.sx.util.ExcelTitle;

/**
 * 这个类是为了导入读者表到数据库专门写的，从程序设计的角度，它可以和ExcelOp合并，并且合并是更合理的设计方式
 * 
 * @author sunjie
 *
 */

public class ReaderExcelOp {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReaderExcelOp.class);

	/*
	 * 使用poi读取book-excel导入数据库
	 */
	public static void importReaderExcel(File file) {
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
		List<Reader> readerList = assemble(wb);

		try {
			ReaderOp.insertReaders(readerList);
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
			if (cell.toString().equals(ExcelTitle.READERID)) {
				map.put(DbTitle.READERID, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.READERNAME)) {
				map.put(DbTitle.READERNAME, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.READERGRADE)) {
				map.put(DbTitle.READERGRADE, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.READERSEX)) {
				map.put(DbTitle.READERSEX, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.HISTORYCOUNT)) {
				map.put(DbTitle.HISTORYCOUNT, cell.getColumnIndex());
			} else if (cell.toString().equals(ExcelTitle.LIMITCOUNT)) {
				map.put(DbTitle.LIMITCOUNT, cell.getColumnIndex());
			}
		}
		return map;
	}

	/*
	 * 把excel表中的内容重新组装成readerlist
	 */
	private static List<Reader> assemble(Workbook wb) {
		List<Reader> readerList = new ArrayList<>();
		// 得到第一页 sheet
		// 页Sheet是从0开始索引的
		Sheet sheet = wb.getSheetAt(0);
		Map<String, Integer> map = getMapByColumnName(sheet);

		int totalRowNum = sheet.getLastRowNum();
		Row row = null;
		// 利用循环 遍历sheet中的所有行,除第一行以外
		for (int i = 1; i <= totalRowNum; i++) {
			Reader reader = new Reader();
			// 遍历row中的所有方格
			row = sheet.getRow(i);

			Cell cell = row.getCell(map.get(DbTitle.READERID));
			try {
				reader.setUID((int) Double.parseDouble(cell.toString()));
			} catch (NumberFormatException e) {
				LOGGER.error("Number Format Exception!!! Error row num: " + i);
				LOGGER.error(e.getMessage());
				LOGGER.error(e.getStackTrace().toString());
			}

			cell = row.getCell(map.get(DbTitle.READERNAME));
			reader.setName(cell.toString().trim());
			
			cell = row.getCell(map.get(DbTitle.READERSEX));
			reader.setSex(cell.toString().trim());

			cell = row.getCell(map.get(DbTitle.READERGRADE));
			reader.setUserGrade(cell.toString().trim());
			
			cell = row.getCell(map.get(DbTitle.HISTORYCOUNT));
			int a = Integer.parseInt(cell.toString().trim());
			reader.setHistoryCount(a);
			
			cell = row.getCell(map.get(DbTitle.LIMITCOUNT));
			int b = Integer.parseInt(cell.toString().trim());
			reader.setLimitCount(b);

			readerList.add(reader);
		}

		return readerList;
	}
}
