package com.sx.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sx.entity.Book;
import com.sx.entity.BookBrowser;
import com.sx.entity.Reader;
import com.sx.fun.BookBrowseOp;
import com.sx.fun.ReaderOp;

public class ReaderLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReaderLogin.class);

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
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel.setBounds(84, 71, 80, 26);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setFont(new Font("宋体", Font.PLAIN, 16));
		textField.setBounds(174, 72, 187, 26);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("\u8FD8\u4E66");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				onClickOkButton();
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 18));
		btnNewButton.setBounds(138, 161, 164, 55);
		contentPane.add(btnNewButton);
	}

	private void onClickOkButton() {
		int UID = 0;
		try {
			UID = Integer.parseInt(textField.getText().trim());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "输入非法，请重新输入", "错误",
					JOptionPane.ERROR_MESSAGE);
			textField.setText("");
			return;
		}

		// 判断读者是否存在
		List<Reader> readers = ReaderOp.getReaderByUID(UID);
		if (readers.isEmpty()) {
			JOptionPane.showMessageDialog(null, "读者 " + UID + " 不存在", "错误",
					JOptionPane.ERROR_MESSAGE);
			textField.setText("");
			return;
		}
		// 正常情况不可能到这
		if (readers.size() != 1) {
			JOptionPane.showMessageDialog(null, "读者 " + UID + " 在数据库中不唯一",
					"错误", JOptionPane.ERROR_MESSAGE);
			textField.setText("");
			return;
		}

		BookBrowser bookBrowser = BookBrowseOp.getBookBrowser(UID);

		// 理论也不会到这
		if (null == bookBrowser) {
			JOptionPane.showMessageDialog(null, "该用户不存在", "错误",
					JOptionPane.ERROR_MESSAGE);
			dispose();
			return;
		}

		// return book!!!
		List<BookBrowser> bookBrowsers = new ArrayList<BookBrowser>();
		bookBrowsers.add(bookBrowser);

		try {
			BookBrowseOp.incReaderLimit(UID, 1);
		} catch (Exception e) {
			LOGGER.error("ReturnBook: 1 update usertable error(increase reader's limit)!"
					+ bookBrowsers.get(0));
			JOptionPane.showMessageDialog(null, "还书失败", "错误",
					JOptionPane.ERROR_MESSAGE);
			dispose();
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
			return;
		}

		int BID = bookBrowser.getBID();
		Book book = new Book();
		book.setBID(BID);
		try {
			BookBrowseOp.incBooks(book);
		} catch (Exception e) {
			LOGGER.error("ReturnBook: 2 update booktable error(increase book count)!"
					+ bookBrowsers.get(0));
			JOptionPane.showMessageDialog(null, "还书失败", "错误",
					JOptionPane.ERROR_MESSAGE);
			dispose();
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
			// rollback
			BookBrowseOp.decReaderLimit(UID, 1);
			return;
		}

		try {
			BookBrowseOp.deleteBookBrowsers(bookBrowsers);
		} catch (Exception e) {
			LOGGER.error("ReturnBook: 3 delete from bookbrowse error!"
					+ bookBrowsers.get(0));
			JOptionPane.showMessageDialog(null, "还书失败", "错误",
					JOptionPane.ERROR_MESSAGE);
			dispose();
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
			// rollback
			BookBrowseOp.decBooks(book);
			BookBrowseOp.decReaderLimit(UID, 1);
			return;
		}

		dispose();
		JOptionPane.showMessageDialog(null, "还书成功", "恭喜",
				JOptionPane.INFORMATION_MESSAGE);

	}
}
