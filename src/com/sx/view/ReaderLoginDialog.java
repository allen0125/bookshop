package com.sx.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
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

public class ReaderLoginDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1140356291457886610L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReaderLoginDialog.class);

	/**
	 * Create the dialog.
	 */
	public ReaderLoginDialog(List<Book> borrowedBooks) {
		init(borrowedBooks);
	}

	private void init(final List<Book> borrowedBooks) {
		setTitle("\u8BFB\u8005\u767B\u5F55");
		Dimension dim = getToolkit().getScreenSize();
		setBounds((dim.width - 337) / 2, 100, 337, 225);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel label = new JLabel(
				"\u8BF7\u8F93\u5165\u8BFB\u8005\u8BC1\u53F7\uFF1A");
		label.setBounds(37, 63, 112, 23);
		contentPanel.add(label);

		textField = new JTextField();
		textField.setBounds(157, 63, 112, 23);
		contentPanel.add(textField);
		textField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						onClickOkButton(borrowedBooks);
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u53D6\u6D88");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void onClickOkButton(List<Book> borrowedBooks) {
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

		if ((readers.get(0).getLimitCount() - borrowedBooks.size()) < 0) {
			JOptionPane.showMessageDialog(null, "读者 " + UID + " 借书数量达到上限",
					"错误", JOptionPane.ERROR_MESSAGE);
			dispose();
			return;
		}
		List<BookBrowser> bookBrowsers = BookBrowseOp.borrowBooks(
				readers.get(0), borrowedBooks);
		// 理论也不会到这
		if (0 == bookBrowsers.size()) {
			JOptionPane.showMessageDialog(null, "该用户没有借书，请确认后重试", "错误",
					JOptionPane.ERROR_MESSAGE);
			dispose();
			return;
		}
		// TODO: need judge if mysql update success???
		try {
			BookBrowseOp.insertBookBrowsers(bookBrowsers);
		} catch (Exception e) {
			LOGGER.error("BorrowBook: 1 insert into bookbrowse error!" + bookBrowsers);
			dispose();
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
			// rollback
			// BookBrowseOp.deleteBookBrowsers(bookBrowsers);
			return;
		}

		try {
			for (Book b : borrowedBooks) {
				BookBrowseOp.decBooks(b);
			}
		} catch (Exception e) {
			LOGGER.error("BorrowBook: 2 update booktable error!" + bookBrowsers);
			dispose();
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
			// rollback
			BookBrowseOp.deleteBookBrowsers(bookBrowsers);
			// for (Book b : borrowedBooks) {
			// BookBrowseOp.incBooks(b);
			// }
			return;
		}

		try {
			BookBrowseOp.decReaderLimit(UID, borrowedBooks.size());
		} catch (Exception e) {
			LOGGER.error("BorrowBook: 3 update usertable error!" + bookBrowsers);
			dispose();
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace().toString());
			// rollback
			BookBrowseOp.deleteBookBrowsers(bookBrowsers);
			for (Book b : borrowedBooks) {
				BookBrowseOp.incBooks(b);
			}
			return;
		}

		dispose();
	}
}
