package com.sx.fun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.sx.entity.Book;
import com.sx.util.DbTitle;
import com.sx.util.ExcelTitle;

/**
 * ����롢��������
 * 
 * @author sunjie
 *
 */
public class ExcelOp {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelOp.class);

	/*
	 * ʹ��poi��ȡexcel�������ݿ�
	 */
	public static void importExcel(File file) {
		// poi��ȡexcel
		// ����Ҫ������ļ���������
		InputStream inp = null;
		try {
			inp = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
			String errorMsg = String.format("File %s not found!",
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
			LOGGER.error("Create workbook error!!!");
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
		}
		// �ѹ�����ת����booklist
		List<Book> bookList = assemble(wb);

		try {
			BookOp.insertBooks(bookList);
		} catch (Exception e) {
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
		JOptionPane.showMessageDialog(null, "����ɹ�", "�ɹ�",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/*
	 * ʹexcel���е����ֺ��е���Ŷ�Ӧ������ʹ��map�洢
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
	 * ��excel���е�����������װ��booklist
	 */
	private static List<Book> assemble(Workbook wb) {
		List<Book> bookList = new ArrayList<>();
		// �õ���һҳ sheet
		// ҳSheet�Ǵ�0��ʼ������
		Sheet sheet = wb.getSheetAt(0);
		Map<String, Integer> map = getMapByColumnName(sheet);

		int totalRowNum = sheet.getLastRowNum();
		Row row = null;
		// ����ѭ�� ����sheet�е�������,����һ������
		for (int i = 1; i <= totalRowNum; i++) {
			Book book = new Book();
			// ����row�е����з���
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
	 * ����booktable���ݿ����ݵ�excel
	 */
	// ʹ��POI����excel������
	public static void createWorkBook(String fileName) throws IOException {
		// ����excel������
		Workbook wb = new HSSFWorkbook();
		// ������һ��sheet��ҳ��������Ϊ new sheet
		Sheet sheet = wb.createSheet();
		// Row ��
		// Cell ����
		// Row �� Cell ���Ǵ�0��ʼ������

		// ����һ�У���ҳsheet��
		Row row = sheet.createRow((short) 0);
		// ��row���ϴ���һ������
		Cell cell = row.createCell(0);
		// ���÷������ʾ
		cell.setCellValue(1);

		// Or do it on one line.
		row.createCell(1).setCellValue(1.2);
		row.createCell(2).setCellValue("This is a string �ٶȷ�������");
		row.createCell(3).setCellValue(true);

		// ����һ���ļ� ����Ϊworkbook.xls
		FileOutputStream fileOut = new FileOutputStream(fileName);
		// �����洴���Ĺ�����������ļ���
		wb.write(fileOut);
		// �ر������
		fileOut.close();
	}

	private static Row setHeader(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue(ExcelTitle.BOOKNAME);

		return row;
	}

}
