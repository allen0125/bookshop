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

import com.sx.util.FileUtil;
import com.sx.fun.ExcelOp;

public class MainFrame extends JFrame {
	BookManage bookManage = new BookManage(1000);
	ReaderManage readerManage = new ReaderManage(1000);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tool = getToolkit();
		Dimension dim = tool.getScreenSize(); // 得到屏幕长宽
		setBounds((int) ((dim.getWidth() - 1000) / 2), 15, 1000, 700);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("\u7CFB\u7EDF\u8BBE\u7F6E");
		menuBar.add(menu);

		JMenuItem mntmImportExcel = new JMenuItem(
				"\u5BFC\u5165\u56FE\u4E66excel...");
		mntmImportExcel.addActionListener(new ActionListener() { // 导入excel
					public void actionPerformed(ActionEvent e) {
						File file = FileUtil.openFile();
						if (file != null) {
							try {
								ExcelOp.importExcel(file);
							} catch (Exception e1) {
								System.err.println(e1.getMessage());
							}
						}
					}
				});
		menu.add(mntmImportExcel);

		JMenuItem mntmExportExcel = new JMenuItem("\u5BFC\u51FAexcel...");
		menu.add(mntmExportExcel);

		JButton btnBookManage = new JButton("\u4E66\u7C4D\u7BA1\u7406");
		btnBookManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Dimension frameDim = getSize(); // 为了获取frame的宽度
				setContentPane(bookManage);
				validate();
			}
		});
		menuBar.add(btnBookManage);

		JMenu menuReaderManage = new JMenu("\u8BFB\u8005\u7BA1\u7406");
		menuBar.add(menuReaderManage);
		
		JButton button = new JButton("\u8BFB\u8005\u7BA1\u7406");//读者管理
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setContentPane(readerManage);
				validate();
			}
		});
		menuReaderManage.add(button);

		JMenu menuBorrowBook = new JMenu("\u501F\u4E66");
		menuBar.add(menuBorrowBook);
		setVisible(true);
	}

}
