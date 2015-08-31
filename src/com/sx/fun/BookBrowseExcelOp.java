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
	 * ʹ��poi��ȡbook-excel�������ݿ�
	 */
	public static void importBookBrowseExcel(File file) throws ParseException {
		boolean flag = true; // ִ���Ƿ�ɹ��ı�־λ
		// poi��ȡexcel
		// ����Ҫ������ļ���������
		InputStream inp = null;
		try {
			inp = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			flag = false;
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
			String errorMsg = String.format(
					"\u6587\u4ef6 %s \u4e0d\u5b58\u5728!", // �ļ�������
					file.getAbsoluteFile());
			LOGGER.error(errorMsg);
			JOptionPane.showMessageDialog(null, errorMsg, "����",
					JOptionPane.ERROR_MESSAGE);
		}

		// �������������������� ��������������
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(inp);
		} catch (InvalidFormatException | IOException e) {
			flag = false;
			LOGGER.error("Create workbook error!!!");
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
		}
		// �ѹ�����ת����booklist
		List<BookBrowser> bookBorrowList = assemble(wb);

		try {
			BookBrowseOp.insertBookBrowsers(bookBorrowList);
		} catch (Exception e) {
			flag = false;
			JOptionPane.showMessageDialog(null, "�������ݿ����", "����",
					JOptionPane.ERROR_MESSAGE);
			LOGGER.error("Insert into booktable error!!!");
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
			return;
		} finally {
			// �ر�������
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
			JOptionPane.showMessageDialog(null, "����ɹ�", "�ɹ�",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/*
	 * ʹexcel���е����ֺ��е���Ŷ�Ӧ������ʹ��map�洢
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
	 * ��excel���е�����������װbookBrowserlist
	 */
	private static List<BookBrowser> assemble(Workbook wb) throws ParseException {
		List<BookBrowser> bookBrowserList = new ArrayList<>();
		// �õ���һҳ sheet
		// ҳSheet�Ǵ�0��ʼ������
		Sheet sheet = wb.getSheetAt(0);
		Map<String, Integer> map = getMapByColumnName(sheet);

		int totalRowNum = sheet.getLastRowNum();
		Row row = null;
		// ����ѭ�� ����sheet�е�������,����һ������
		for (int i = 1; i <= totalRowNum; i++) {
			BookBrowser bookBrowser = new BookBrowser();
			// ����row�е����з���
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
