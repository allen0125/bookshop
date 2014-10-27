package com.sx.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReaderLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReaderLogin frame = new ReaderLogin();
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
	public ReaderLogin() {
		setTitle("\u8BFB\u8005\u767B\u9646");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u8BFB\u8005\u7F16\u53F7\uFF1A");
		lblNewLabel.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
		lblNewLabel.setBounds(84, 71, 80, 26);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("ËÎÌו", Font.PLAIN, 16));
		textField.setBounds(174, 72, 187, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("\u8FD8\u4E66");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnNewButton.setFont(new Font("ËÎÌו", Font.PLAIN, 18));
		btnNewButton.setBounds(138, 161, 164, 55);
		contentPane.add(btnNewButton);
	}
}
