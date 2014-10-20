package com.sx.fun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExcel {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TestExcel.class);

	// ʹ��POI����excel������
	public static void createWorkBook() throws IOException {
		// ����excel������
		Workbook wb = new HSSFWorkbook();
		// ������һ��sheet��ҳ��������Ϊ new sheet
		Sheet sheet = wb.createSheet("new sheet");
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
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		// �����洴���Ĺ�����������ļ���
		wb.write(fileOut);
		// �ر������
		fileOut.close();
	}

	// ʹ��POI����excel�������ļ�
	public void read(String file) throws Exception {
		// poi��ȡexcel
		// ����Ҫ������ļ���������
		InputStream inp = new FileInputStream(file);

		// �������������������� ��������������
		Workbook wb = WorkbookFactory.create(inp);
		// �õ���һҳ sheet
		// ҳSheet�Ǵ�0��ʼ������
		Sheet sheet = wb.getSheetAt(0);
		// ����foreachѭ�� ����sheet�е�������
		for (Row row : sheet) {
			// ����row�е����з���
			for (Cell cell : row) {
				// ��������е����ݣ��Կո���
				System.out.print(cell.toString() + "  ");
			}
			// ÿһ�������֮����
			System.out.println();
		}
		// �ر�������
		inp.close();
	}

	// public void read(String source) {
	// try {
	// File file = new File(source);
	// FileInputStream fint = new FileInputStream(file);
	// POIFSFileSystem poiFileSystem = new POIFSFileSystem(fint);
	// HSSFWorkbook workbook = new HSSFWorkbook(poiFileSystem);
	//
	// HSSFSheet sheet = workbook.getSheetAt(0);
	// for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
	// Row row = rit.next();
	// for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) {
	// Cell cell = cit.next();
	// System.out.print(getCell(cell));
	// }
	// System.out.println();
	// }
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public String getCell(Cell cell) {
	// if (cell == null)
	// return "";
	// switch (cell.getCellType()) {
	// case Cell.CELL_TYPE_NUMERIC:
	// return cell.getNumericCellValue() + "";
	// case Cell.CELL_TYPE_STRING:
	// return cell.getStringCellValue();
	// case Cell.CELL_TYPE_FORMULA:
	// return cell.getCellFormula();
	// case Cell.CELL_TYPE_BLANK:
	// return "";
	// case Cell.CELL_TYPE_BOOLEAN:
	// return cell.getBooleanCellValue() + "";
	// case Cell.CELL_TYPE_ERROR:
	// return cell.getErrorCellValue() + "";
	// }
	// return "";
	// }
}
