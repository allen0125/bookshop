package com.sx.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileUtil.class);

	/*
	 * �����Ի���ѡ��Ҫ�����excel�ļ�
	 */
	public static File openFile() {
		JFileChooser fileChooser = new JFileChooser(".");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("���ļ�");
		fileChooser.setFileFilter(new ExcelFilter());
		File file = null;
		int ret = fileChooser.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			// ����ļ�������.xls��β��
			if (!file.getName().endsWith(Constant.ExcelSuffix)) {
				JOptionPane.showMessageDialog(null, Constant.fileSuffixError,
						"����", JOptionPane.ERROR_MESSAGE);
				LOGGER.error(Constant.fileSuffixError);
				return null;
			}
			LOGGER.debug("Open File: "
					+ fileChooser.getSelectedFile().getAbsolutePath());
		} else if (ret == JFileChooser.CANCEL_OPTION) {
			// fileChooser.cancelSelection();
		} else {
			JOptionPane.showMessageDialog(null, "ѡ���ļ����������ԣ�", "����",
					JOptionPane.ERROR_MESSAGE);
		}
		return file;
	}

	/*
	 * �����ļ���:����+����
	 */
	public static String genExcelFileName(String prefix) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("-yyyy-MM-dd-HH-mm-ss");
		String ret = prefix + sdf.format(date) + Constant.ExcelSuffix;
		return ret;
	}
}
