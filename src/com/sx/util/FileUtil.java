package com.sx.util;

import java.io.File;

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
		fileChooser.setDialogTitle("���ļ���");
		fileChooser.setFileFilter(new ExcelFilter());
		File file = null;
		int ret = fileChooser.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			LOGGER.debug("Open File: "
					+ fileChooser.getSelectedFile().getAbsolutePath());
		} else if (ret == JFileChooser.CANCEL_OPTION) {
//			fileChooser.cancelSelection();
		} else {
			JOptionPane.showMessageDialog(null, "ѡ���ļ����������ԣ�", "����",
					JOptionPane.ERROR_MESSAGE);
		}
		return file;
	}
}
