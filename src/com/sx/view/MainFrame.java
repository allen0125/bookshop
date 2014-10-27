package com.sx.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.sx.fun.ExcelOp;
import com.sx.util.Constant;
import com.sx.util.FileUtil;

public class MainFrame extends JFrame {
	BookManage bookManage = new BookManage();
	ReaderManage readerManage = new ReaderManage(1000);
	BookBrowseManage bookBrowseManage = new BookBrowseManage();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("\u56FE\u4E66\u7BA1\u7406\u7CFB\u7EDF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tool = getToolkit();
		Dimension dim = tool.getScreenSize(); // 得到屏幕长宽
		setBounds((int) ((dim.getWidth() - 1000) / 2), 15, 1030, 700);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("\u7CFB\u7EDF\u8BBE\u7F6E");
		menuBar.add(menu);

		/*
		 * 导入book-excel
		 */
		JMenuItem mntmImportBookExcel = new JMenuItem(
				"\u5BFC\u5165\u56FE\u4E66excel...");
		mntmImportBookExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = FileUtil.openFile();
				if (file != null) {
					try {
						ExcelOp.importBookExcel(file);
					} catch (Exception e1) {
						System.err.println(e1.getMessage());
					}
				}
			}
		});
		menu.add(mntmImportBookExcel);

		/*
		 * 导出book-Excel表
		 */
		JMenuItem mntmExportBookExcel = new JMenuItem(
				"\u5BFC\u51FA\u56FE\u4E66excel");
		mntmExportBookExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = FileUtil.genExcelFileName(Constant.BookTable);
				ExcelOp.exportExcel(fileName, Constant.BookTable);
			}
		});
		/*
		 * 导入读者excel
		 */
		JMenuItem mntmImportReaderExcel = new JMenuItem(
				"\u5BFC\u5165\u8BFB\u8005excel...");
		mntmImportReaderExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = FileUtil.openFile();
				if (file != null) {
					try {
						// ExcelOp.importExcel(file);
					} catch (Exception e1) {
						System.err.println(e1.getMessage());
					}
				}
			}
		});
		menu.add(mntmImportReaderExcel);
		menu.add(mntmExportBookExcel);

		/*
		 * 导出读者表到excel
		 */
		JMenuItem mntmexcel = new JMenuItem("\u5BFC\u51FA\u8BFB\u8005excel");
		mntmexcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fileName = FileUtil.genExcelFileName(Constant.UserTable);
				ExcelOp.exportExcel(fileName, Constant.UserTable);
			}
		});
		menu.add(mntmexcel);

		JButton btnBookManage = new JButton("\u4E66\u7C4D\u7BA1\u7406");
		btnBookManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Dimension frameDim = getSize(); // 为了获取frame的宽度
				setContentPane(bookManage);
				validate();
			}
		});
		menuBar.add(btnBookManage);

		/*
		 * 借书管理
		 */
		JButton buttonBorrowBook = new JButton("\u501F\u4E66\u7BA1\u7406");
		buttonBorrowBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setContentPane(bookBrowseManage);
				validate();
			}
		});
		
				JButton button = new JButton("\u8BFB\u8005\u7BA1\u7406");// 读者管理
				menuBar.add(button);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setContentPane(readerManage);
						validate();
					}
				});
		menuBar.add(buttonBorrowBook);
		
		JButton button_1 = new JButton("\u8FD8\u4E66\u7BA1\u7406");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReaderLogin readerLogin = new ReaderLogin();
				readerLogin.setVisible(true);
			}
		});
		menuBar.add(button_1);
		setVisible(true);
	}
}
