package com.sx.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ExcelFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		if (f.getName().endsWith(".xls")) {
			return true;
		}

		return false;
	}

	@Override
	public String getDescription() {
		return "ExcelÎÄ¼þ£¨*.xls£©";
	}

}
