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
 * �������Ϊ�˵�����߱����ݿ�ר��д�ģ��ӳ�����ƵĽǶȣ������Ժ�ExcelOp�ϲ������Һϲ��Ǹ��������Ʒ�ʽ
 * 
 * @author sunjie
 *
 */

public class ReaderExcelOp {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReaderExcelOp.class);

	/*
	 * ʹ��poi��ȡbook-excel�������ݿ�
	 */
	public static void importReaderExcel(File file) {
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
		List<Reader> readerList = assemble(wb);

		try {
			ReaderOp.insertReaders(readerList);
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
	 * ��excel���е�����������װ��readerlist
	 */
	private static List<Reader> assemble(Workbook wb) {
		List<Reader> readerList = new ArrayList<>();
		// �õ���һҳ sheet
		// ҳSheet�Ǵ�0��ʼ������
		Sheet sheet = wb.getSheetAt(0);
		Map<String, Integer> map = getMapByColumnName(sheet);

		int totalRowNum = sheet.getLastRowNum();
		Row row = null;
		// ����ѭ�� ����sheet�е�������,����һ������
		for (int i = 1; i <= totalRowNum; i++) {
			Reader reader = new Reader();
			// ����row�е����з���
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
