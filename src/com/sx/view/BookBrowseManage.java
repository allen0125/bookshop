package com.sx.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.sx.entity.Book;
import com.sx.fun.BookBrowseOp;
import com.sx.util.Constant;

public class BookBrowseManage extends ManageParent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6083366880724782047L;
	private JButton button;

	/**
	 * Create the panel.
	 */
	public BookBrowseManage() {
		init();
		bookList = BookBrowseOp.getBorrowBooks();
		refreshTable(bookList);
	}

	private void init() {
		button = new JButton("\u501f\u4e66"); // ����
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BookInfoDialog(setSelectedBooks());
			}
		});
		panel.add(button);
	}

	private List<Book> setSelectedBooks() {
		List<Book> borrowedBookList = new ArrayList<Book>();
		int[] selectedRows = tableShow.getSelectedRows();
		// select nothing
		if (0 == selectedRows.length) {
			return borrowedBookList;
		}

		// "ID" "ͼ����" "����", "����", "������", "��������", "����",
		// "ISBN", "ͼ�����", "ͼ������", "����", "װ֡", "����"
		for (int row : selectedRows) {
			Book book = new Book();
			book.setBID((int) defaultModel.getValueAt(row, 1));
			book.setBookName((String) defaultModel.getValueAt(row, 2));
			book.setAuthor((String) defaultModel.getValueAt(row, 3));
			book.setPress((String) defaultModel.getValueAt(row, 4));
			book.setPressDate((String) defaultModel.getValueAt(row, 5));
			book.setPrice((double) defaultModel.getValueAt(row, 6));
			book.setISBN((long) defaultModel.getValueAt(row, 9));
			book.setBookCategory((String) defaultModel.getValueAt(row, 10));
			book.setLanguage((String) defaultModel.getValueAt(row, 11));
			book.setSize((String) defaultModel.getValueAt(row, 12));
			book.setBinding((String) defaultModel.getValueAt(row, 13));
			book.setFeature((String) defaultModel.getValueAt(row, 14));

			borrowedBookList.add(book);
		}
		return borrowedBookList;
	}

	@Override
	public void onClick() {
		if (textField.getText().equals("")) {
			bookList = BookBrowseOp.getBorrowBooks();
			scrollPane.setViewportView(refreshTable(bookList));
			return;
		}

		// ����ISBN��ѯ
		if (comboBox.getSelectedItem().toString().trim().equals(Constant.ISBN)) {
			long ISBN = 0;
			try {
				ISBN = Long.parseLong(textField.getText());
				bookList = BookBrowseOp.getBorrowBooksByISBN(ISBN);
				refreshTable(bookList); // refresh JTable
			} catch (NumberFormatException nume) {
				JOptionPane.showMessageDialog(null, "�Ƿ����룡", "����",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		// ����������Ϣ��ѯ
		if (comboBox.getSelectedItem().toString().trim()
				.equals(Constant.Author)) {
			String str = textField.getText().trim();
			bookList = BookBrowseOp.getBorrowBooksByAuthor(str);
			refreshTable(bookList);
		}

		// ����������ѯ
		if (comboBox.getSelectedItem().toString().trim()
				.equals(Constant.BookName)) {
			String str = textField.getText().trim();
			bookList = BookBrowseOp.getBorrowBooksByBookName(str);
			refreshTable(bookList);
		}
	}
}
