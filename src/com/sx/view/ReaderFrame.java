package com.sx.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReaderFrame extends JFrame {

	 JPanel contentPane;
	 JTextField textField_rNum;
	 JTextField textField_rName;
	 JTextField textField_rSex;
	 JTextField textField_rGrade;
	 JTextField textField_rHisNum;
	 JTextField textField_rLimNum;


	public ReaderFrame(String framename) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 501, 586);
		setTitle(framename);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField_rNum = new JTextField();
		textField_rNum.setBounds(177, 32, 236, 39);
		contentPane.add(textField_rNum);
		textField_rNum.setColumns(10);
		
		textField_rName = new JTextField();
		textField_rName.setColumns(10);
		textField_rName.setBounds(177, 96, 236, 39);
		contentPane.add(textField_rName);
		
		textField_rSex = new JTextField();
		textField_rSex.setColumns(10);
		textField_rSex.setBounds(177, 162, 236, 39);
		contentPane.add(textField_rSex);
		
		textField_rGrade = new JTextField();
		textField_rGrade.setColumns(10);
		textField_rGrade.setBounds(177, 229, 236, 39);
		contentPane.add(textField_rGrade);
		
		textField_rHisNum = new JTextField();
		textField_rHisNum.setColumns(10);
		textField_rHisNum.setBounds(177, 293, 236, 39);
		contentPane.add(textField_rHisNum);
		
		textField_rLimNum = new JTextField();
		textField_rLimNum.setColumns(10);
		textField_rLimNum.setBounds(177, 360, 236, 39);
		contentPane.add(textField_rLimNum);
		
		JButton btnNewButton = new JButton("\u63D0\u4EA4");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				click();
			}
		});
		btnNewButton.setBounds(158, 444, 148, 54);
		contentPane.add(btnNewButton);
		
		JLabel label = new JLabel("\u7F16\u53F7\uFF1A");
		label.setFont(new Font("свт╡", Font.PLAIN, 17));
		label.setBounds(73, 44, 54, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u59D3\u540D\uFF1A");
		label_1.setFont(new Font("свт╡", Font.PLAIN, 17));
		label_1.setBounds(73, 108, 54, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u6027\u522B\uFF1A");
		label_2.setFont(new Font("свт╡", Font.PLAIN, 17));
		label_2.setBounds(73, 174, 54, 15);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u5E74\u7EA7\uFF1A");
		label_3.setFont(new Font("свт╡", Font.PLAIN, 17));
		label_3.setBounds(73, 241, 54, 15);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("\u5DF2\u501F\u6570\u91CF\uFF1A");
		label_4.setFont(new Font("свт╡", Font.PLAIN, 17));
		label_4.setBounds(73, 305, 94, 15);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("\u501F\u4E66\u9650\u5236\uFF1A");
		label_5.setFont(new Font("свт╡", Font.PLAIN, 17));
		label_5.setBounds(73, 372, 85, 15);
		contentPane.add(label_5);
	}
	protected void click(){
		
	}
}
