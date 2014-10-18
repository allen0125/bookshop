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
import com.sx.entity.Book;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Add extends JFrame {

	private JPanel contentPane;
	private JTextField textField_BookName;
	private JTextField textField_BookISBN;
	private JTextField textField_Author;
	private JTextField textField_BookNum;
	private JTextField textField_Price;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;

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
		
		setBounds((int)((dim.getWidth()-506)/2), 100, 506, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
//		书名输入框
		textField_BookName = new JTextField();
		textField_BookName.setBounds(196, 61, 200, 32);
		contentPane.add(textField_BookName);
		textField_BookName.setColumns(10);
//		书号输入框
		textField_BookISBN = new JTextField();
		textField_BookISBN.setBounds(196, 128, 200, 32);
		contentPane.add(textField_BookISBN);
		textField_BookISBN.setColumns(10);
//		作者输入框
		textField_Author = new JTextField();
		textField_Author.setBounds(196, 194, 200, 32);
		contentPane.add(textField_Author);
		textField_Author.setColumns(10);
//		图书数量输入框
		textField_BookNum = new JTextField();
		textField_BookNum.setBounds(196, 256, 200, 32);
		contentPane.add(textField_BookNum);
		textField_BookNum.setColumns(10);
//		价格输入框
		textField_Price = new JTextField();
		textField_Price.setBounds(196, 326, 200, 32);
		contentPane.add(textField_Price);
		textField_Price.setColumns(10);
		
		JButton button = new JButton("\u6DFB\u52A0");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}
		});
		button.setBounds(157, 433, 164, 50);
		contentPane.add(button);
		
		
		// 标签 无用……
		JLabel label = new JLabel("\u4E66\u540D\uFF1A");
		label.setFont(new Font("宋体", Font.PLAIN, 16));
		label.setBounds(136, 61, 50, 32);
		contentPane.add(label);
		
		label_1 = new JLabel("\u4E66\u53F7\uFF1A");
		label_1.setFont(new Font("宋体", Font.PLAIN, 16));
		label_1.setBounds(136, 128, 50, 32);
		contentPane.add(label_1);
		
		label_2 = new JLabel("\u4F5C\u8005\uFF1A");
		label_2.setFont(new Font("宋体", Font.PLAIN, 16));
		label_2.setBounds(136, 194, 50, 32);
		contentPane.add(label_2);
		
		label_3 = new JLabel("\u6570\u91CF\uFF1A");
		label_3.setFont(new Font("宋体", Font.PLAIN, 16));
		label_3.setBounds(136, 256, 50, 32);
		contentPane.add(label_3);
		
		label_4 = new JLabel("\u5355\u4EF7\uFF1A");
		label_4.setFont(new Font("宋体", Font.PLAIN, 16));
		label_4.setBounds(136, 326, 50, 32);
		contentPane.add(label_4);
	}
}
