package com.sx.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;

import com.sx.fun.*;
import com.sx.db.BookDb;
import com.sx.db.mapper.BookTableMapper;
import com.sx.entity.Book;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Add extends JFrame {

	 JPanel contentPane;
     JTextField textField_BookName;
	 JTextField textField_BookISBN;
	 JTextField textField_Author;
	 JTextField textField_BookNum;
	 JTextField textField_Price;
	 JLabel label_1;
	 JLabel label_2;
	 JLabel label_3;
	 JLabel label_4;
	 JTextField textField_press;
	 JTextField textField_press_date;
	 JTextField textField_total;
	 JTextField textField_fenlei;
	 JTextField textField_lang;
	 JTextField textField_zhuangzhen;
	 JTextField textField_tezhen;
	 JTextField textField_kaiben;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add frame = new Add();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
//	有BUG！！！！！！！！！！！！！！关闭Add窗口主窗口也会随之关闭
	
	
	
	
	

	/**
	 * Create the frame.
	 */
	public Add() {
		setTitle("\u6DFB\u52A0\u56FE\u4E66");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit tool = getToolkit();
		Dimension dim = tool.getScreenSize(); // 得到屏幕长宽
		
		setBounds((int)((dim.getWidth()-506)/2), 100, 776, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
//		书名输入框
		textField_BookName = new JTextField();
		textField_BookName.setBounds(117, 12, 200, 32);
		contentPane.add(textField_BookName);
		textField_BookName.setColumns(10);
		String bookName = null;
//		bookName = textField_BookName.getText();
		
//		书号输入框
		textField_BookISBN = new JTextField();
		textField_BookISBN.setBounds(467, 12, 200, 32);
		contentPane.add(textField_BookISBN);
		textField_BookISBN.setColumns(10);
		long ISBN = 0;
//		ISBN = Long.parseLong(textField_BookISBN.getText());
		
//		作者输入框
		textField_Author = new JTextField();
		textField_Author.setBounds(117, 70, 200, 32);
		contentPane.add(textField_Author);
		textField_Author.setColumns(10);
		String author = null;
//		author = textField_Author.getText();
		
//		图书数量输入框
		textField_BookNum = new JTextField();
		textField_BookNum.setBounds(467, 70, 200, 32);
		contentPane.add(textField_BookNum);
		textField_BookNum.setColumns(10);
		int bookNum = 0;
//		bookNum = Integer.parseInt(textField_BookNum.getText()); 
		
//		价格输入框
		textField_Price = new JTextField();
		textField_Price.setBounds(117, 138, 200, 32);
		contentPane.add(textField_Price);
		textField_Price.setColumns(10);
//		double price = Double.parseDouble(textField_press.getText());
		
		JButton button = new JButton("\u6DFB\u52A0");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		button.setBounds(302, 443, 164, 50);
		contentPane.add(button);
		
		
		// 标签 无用……
		JLabel label = new JLabel("\u4E66\u540D\uFF1A");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setBounds(57, 10, 50, 32);
		contentPane.add(label);
		
		label_1 = new JLabel("\u4E66\u53F7\uFF1A");
		label_1.setFont(new Font("宋体", Font.PLAIN, 16));
		label_1.setBounds(396, 10, 50, 32);
		contentPane.add(label_1);
		
		label_2 = new JLabel("\u4F5C\u8005\uFF1A");
		label_2.setFont(new Font("宋体", Font.PLAIN, 16));
		label_2.setBounds(57, 68, 50, 32);
		contentPane.add(label_2);
		
		label_3 = new JLabel("\u6570\u91CF\uFF1A");
		label_3.setFont(new Font("宋体", Font.PLAIN, 16));
		label_3.setBounds(396, 68, 50, 32);
		contentPane.add(label_3);
		
		label_4 = new JLabel("\u5355\u4EF7\uFF1A");
		label_4.setFont(new Font("宋体", Font.PLAIN, 16));
		label_4.setBounds(57, 138, 50, 32);
		contentPane.add(label_4);
//		出版社
		textField_press = new JTextField();
		textField_press.setColumns(10);
		textField_press.setBounds(467, 138, 200, 32);
		contentPane.add(textField_press);
		String press = null;
//		press = textField_press.getText();
		
//		出版日期
		textField_press_date = new JTextField();
		textField_press_date.setColumns(10);
		textField_press_date.setBounds(160, 205, 157, 32);
		contentPane.add(textField_press_date);
		String press_date = null;
//		press_date = textField_press_date.getText();
		
//		总数
		textField_total = new JTextField();
		textField_total.setColumns(10);
		textField_total.setBounds(467, 205, 200, 32);
		contentPane.add(textField_total);
//		double total = 0;
//		total = Double.parseDouble(textField_total.getText());
		
//		分类
		textField_fenlei = new JTextField();
		textField_fenlei.setColumns(10);
		textField_fenlei.setBounds(117, 267, 200, 32);
		contentPane.add(textField_fenlei);
//		String fenlei = textField_fenlei.getText();
		
//		语言
		textField_lang = new JTextField();
		textField_lang.setColumns(10);
		textField_lang.setBounds(467, 267, 200, 32);
		contentPane.add(textField_lang);
//		String lang = textField_lang.getText();
		
//		装帧
		textField_zhuangzhen = new JTextField();
		textField_zhuangzhen.setColumns(10);
		textField_zhuangzhen.setBounds(117, 331, 200, 32);
		contentPane.add(textField_zhuangzhen);
//		String zhuangzhen = textField_zhuangzhen.getText();
		
//		特征
		textField_tezhen = new JTextField();
		textField_tezhen.setColumns(10);
		textField_tezhen.setBounds(467, 331, 200, 32);
		contentPane.add(textField_tezhen);
//		String tezhen = textField_tezhen.getText();
		
		
//		开本
		textField_kaiben = new JTextField();
		textField_kaiben.setColumns(10);
		textField_kaiben.setBounds(117, 391, 200, 32);
		contentPane.add(textField_kaiben);
//		String kaiben= textField_kaiben.getText();
		
		
		
		JLabel label_5 = new JLabel("\u51FA\u7248\u793E\uFF1A");
		label_5.setFont(new Font("宋体", Font.PLAIN, 16));
		label_5.setBounds(396, 138, 70, 32);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("\u51FA\u7248\u65E5\u671F\uFF1A");
		label_6.setFont(new Font("宋体", Font.PLAIN, 16));
		label_6.setBounds(57, 205, 93, 32);
		contentPane.add(label_6);
		
		JLabel label_7 = new JLabel("\u5206\u7C7B\uFF1A");
		label_7.setFont(new Font("宋体", Font.PLAIN, 16));
		label_7.setBounds(57, 267, 50, 32);
		contentPane.add(label_7);
		
		JLabel label_8 = new JLabel("\u88C5\u5E27\uFF1A");
		label_8.setFont(new Font("宋体", Font.PLAIN, 16));
		label_8.setBounds(57, 331, 50, 32);
		contentPane.add(label_8);
		
		JLabel label_9 = new JLabel("\u5F00\u672C\uFF1A");
		label_9.setFont(new Font("宋体", Font.PLAIN, 16));
		label_9.setBounds(57, 391, 50, 32);
		contentPane.add(label_9);
		
		JLabel label_10 = new JLabel("\u7279\u5F81\uFF1A");
		label_10.setFont(new Font("宋体", Font.PLAIN, 16));
		label_10.setBounds(396, 331, 50, 32);
		contentPane.add(label_10);
		
		JLabel label_11 = new JLabel("\u8BED\u8A00\uFF1A");
		label_11.setFont(new Font("宋体", Font.PLAIN, 16));
		label_11.setBounds(396, 267, 50, 32);
		contentPane.add(label_11);
		
		JLabel label_12 = new JLabel("\u603B\u4EF7\uFF1A");
		label_12.setFont(new Font("宋体", Font.PLAIN, 16));
		label_12.setBounds(396, 205, 50, 32);
		contentPane.add(label_12);
	}
}
