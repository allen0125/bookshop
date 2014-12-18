package com.sx.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.sx.entity.Book;

public class BookInfoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	DefaultTableModel defaultModel;
	private JTable table;

	/**
	 * Create the dialog.
	 */
	public BookInfoDialog(List<Book> borrowedBooks) {
		init(borrowedBooks);
	}

	private void init(final List<Book> borrowedBooks) {
		setTitle("\u501F\u9605\u4E66\u7C4D\u4FE1\u606F");
		Dimension dim = getToolkit().getScreenSize();
		setBounds((int) ((dim.getWidth() - 1000) / 2), 100, 1000, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 984, 229);
		contentPanel.add(scrollPane);

		table = initShowTable();
		setBookDetail(borrowedBooks);
		scrollPane.setViewportView(table);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u786E\u5B9A");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new ReaderLoginDialog(borrowedBooks);
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
	}

	private JTable initShowTable() {
		if (table != null) {
			return table;
		}
		table = new JTable();
		Object[][] data = new Object[][] {};
		String[] name = new String[] { "ID", "图书编号", "书名", "作者", "出版社", "出版日期",
				"单价", "ISBN", "图书分类", "图书语言", "开本", "装帧", "特征" };
		defaultModel = new DefaultTableModel(data, name);
		table.setModel(defaultModel);

		return table;
	}

	// 获取书籍详细信息，存入向量用以向JTable中添加
	private void setBookDetail(List<Book> bookList) {
		if (defaultModel.getRowCount() != 0) {
			defaultModel.setRowCount(0);
		}
		// List<Book> bookList = BookOp.getBooks();
		Book book = null;
		for (int i = 0; i < bookList.size(); i++) {
			Vector<Object> data = new Vector<>();
			book = bookList.get(i);
			data.add(i + 1);
			data.add(book.getBID());
			data.add(book.getBookName());
			data.add(book.getAuthor());
			data.add(book.getPress());
			data.add(book.getPressDate());
			data.add(book.getPrice());
			data.add(book.getISBN());
			data.add(book.getBookCategory());
			data.add(book.getLanguage());
			data.add(book.getSize());
			data.add(book.getBinding());
			data.add(book.getFeature());
			defaultModel.addRow(data);
		}

		table.setModel(defaultModel);
		table.validate();
	}
}
