
package com.sx.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sx.entity.Book;
import com.sx.entity.BookBrowser;
import com.sx.entity.Reader;
import com.sx.fun.BookBrowseOp;
import com.sx.fun.BookOp;
import com.sx.fun.ReaderOp;

public class browseManage extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private int user_id;
	private JTable tableShow;
	List<Reader> readerlist;
	JScrollPane scrollPane;
	JScrollPane scrollPane2;
	List<Book> bookList;
	List<Book> books = null;
	List<Book> borrowedBooks;
	DefaultTableModel defaultModel;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReaderLogin.class);
	private JButton button_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					browseManage frame = new browseManage();
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
	private List<Book> setSelectedBooks() {
		List<Book> borrowedBookList = new ArrayList<Book>();
		int[] selectedRows = tableShow.getSelectedRows();
		// select nothing
		if (0 == selectedRows.length) {
			return borrowedBookList;
		}

		// "ID" "图书编号" "书名", "作者", "出版社", "出版日期", "单价",
		// "ISBN", "图书分类", "图书语言", "开本", "装帧", "特征"
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

	public browseManage() {
		setTitle("\u501F\u9605\u7BA1\u7406");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 949, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		final JLabel label_userInfo = new JLabel("");
		label_userInfo.setBounds(0, 54, 923, 26);
		contentPane.add(label_userInfo);

		final JLabel label_bookInfo = new JLabel("");
		label_bookInfo.setBounds(0, 90, 923, 26);
		contentPane.add(label_bookInfo);
		textField = new JTextField();
		
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int uid;
				uid = Integer.valueOf(textField.getText());
				List<Reader> userInfo = ReaderOp.getReaderByUID(uid);
				label_userInfo.setText(userInfo.get(0).toCString());
				List<Book> bookInfo = BookBrowseOp.getReaderBook(uid);
				label_bookInfo.setText(" ");
				label_bookInfo.setText(bookInfo.get(0).toCString());
			}
		});
		textField.setBounds(362, 12, 158, 32);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel label = new JLabel("\u8BFB\u8005\u7F16\u53F7:");
		label.setBounds(212, 15, 80, 25);
		label.setFont(new Font("Dialog", Font.BOLD, 15));
		contentPane.add(label);

		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					if (textField_1.getText().equals("")) {
						bookList = BookOp.getBooks();
						// scrollPane.setViewportView(refreshTable(bookList));
						refreshTable(bookList, tableShow);
						return;
					}
					long isbn = 0;
					isbn = Long.parseLong(textField_1.getText());
					bookList = BookOp.getBookByISBN(isbn);
					refreshTable(bookList, tableShow);
				}

			}
		});
		textField_1.setBounds(362, 139, 158, 32);
		textField_1.setColumns(10);
		contentPane.add(textField_1);

		JLabel label_1 = new JLabel("\u56FE\u4E66\u7F16\u53F7:");
		label_1.setBounds(212, 142, 80, 25);
		label_1.setFont(new Font("Dialog", Font.BOLD, 15));
		contentPane.add(label_1);

		JButton button_2 = new JButton("\u8FD8\u4E66");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

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
					JOptionPane.showMessageDialog(null, "读者 " + UID + " 不存在",
							"错误", JOptionPane.ERROR_MESSAGE);
					textField.setText("");
					return;
				}
				// 正常情况不可能到这
				if (readers.size() != 1) {
					JOptionPane.showMessageDialog(null, "读者 " + UID
							+ " 在数据库中不唯一", "错误", JOptionPane.ERROR_MESSAGE);
					textField.setText("");
					return;
				}

				BookBrowser bookBrowser = BookBrowseOp.getBookBrowser(UID);

				// 理论也不会到这
				if (null == bookBrowser) {
					JOptionPane.showMessageDialog(null, "该用户不存在", "错误",
							JOptionPane.ERROR_MESSAGE);
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
					LOGGER.error(e.getMessage());
					LOGGER.error(e.getStackTrace().toString());
					BookBrowseOp.decReaderLimit(UID, 1);
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
					LOGGER.error(e.getMessage());
					LOGGER.error(e.getStackTrace().toString());
					// rollback
					BookBrowseOp.decBooks(book);
					BookBrowseOp.decReaderLimit(UID, 1);
					return;
				}

				JOptionPane.showMessageDialog(null, "还书成功", "恭喜",
						JOptionPane.INFORMATION_MESSAGE);

				int uid;
				uid = Integer.valueOf(textField.getText());
				List<Reader> userInfo = ReaderOp.getReaderByUID(uid);
				label_userInfo.setText(userInfo.get(0).toCString());
				List<Book> bookInfo = BookBrowseOp.getReaderBook(uid);
				label_bookInfo.setText(" ");
				label_bookInfo.setText(bookInfo.get(0).toCString());
			}
		});
		button_2.setBounds(566, 14, 107, 27);
		contentPane.add(button_2);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 181, 923, 391);
		tableShow = new JTable();
		initShowTable(bookList, tableShow);
		scrollPane.setViewportView(tableShow);
		getContentPane().add(scrollPane);

		button_3 = new JButton("\u501F\u51FA");// 借出
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				borrowedBooks = setSelectedBooks();

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
					JOptionPane.showMessageDialog(null, "读者 " + UID + " 不存在",
							"错误", JOptionPane.ERROR_MESSAGE);
					textField.setText("");
					return;
				}
				// 正常情况不可能到这
				if (readers.size() != 1) {
					JOptionPane.showMessageDialog(null, "读者 " + UID
							+ " 在数据库中不唯一", "错误", JOptionPane.ERROR_MESSAGE);
					textField.setText("");
					return;
				}

				if ((readers.get(0).getLimitCount() - borrowedBooks.size()) < 0) {
					JOptionPane.showMessageDialog(null, "读者 " + UID
							+ " 借书数量达到上限", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				List<BookBrowser> bookBrowsers = BookBrowseOp.borrowBooks(
						readers.get(0), borrowedBooks);
				// 理论也不会到这
				if (0 == bookBrowsers.size()) {
					JOptionPane.showMessageDialog(null, "该用户没有借书，请确认后重试", "错误",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// TODO: need judge if mysql update success???
				try {
					BookBrowseOp.insertBookBrowsers(bookBrowsers);
					BookBrowseOp.incReaderHis(UID);
				} catch (Exception e) {
					LOGGER.error("BorrowBook: 1 insert into bookbrowse error!"
							+ bookBrowsers);
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
					LOGGER.error("BorrowBook: 2 update booktable error!"
							+ bookBrowsers);
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
					LOGGER.error("BorrowBook: 3 update usertable error!"
							+ bookBrowsers);
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
				JOptionPane.showMessageDialog(null, "借书成功", "恭喜",
						JOptionPane.INFORMATION_MESSAGE);

				int uid;
				uid = Integer.valueOf(textField.getText());
				List<Reader> userInfo = ReaderOp.getReaderByUID(uid);
				label_userInfo.setText(userInfo.get(0).toCString());
				List<Book> bookInfo = BookBrowseOp.getReaderBook(uid);
				label_bookInfo.setText(" ");
				label_bookInfo.setText(bookInfo.get(0).toCString());
			}
		});
		button_3.setBounds(566, 141, 107, 27);
		contentPane.add(button_3);
		
		

	}

	private void initShowTable(List<Book> bookList, JTable table) {
		Object[][] data = new Object[][] {};
		String[] name = new String[] { "ID", "图书编号", "书名", "作者", "出版社", "出版日期",
				"单价", "数量", "总价", "ISBN", "图书分类", "图书语言", "开本", "装帧", "特征" };
		defaultModel = new DefaultTableModel(data, name);
		if (bookList != null) {
			getBookDetail(bookList);
		}
		table.setModel(defaultModel);

	}

	/*
	 * 更新
	 */
	protected JTable refreshTable(List<Book> bookList, JTable table) {
		// if (table == null) {
		// table = new JTable();
		// }



		table.removeAll();
		getBookDetail(bookList);
		table.setModel(defaultModel);
		table.validate();
		return table;
	}

	// 获取书籍详细信息，存入向量用以向JTable中添加
	private void getBookDetail(List<Book> bookList) {
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
			data.add(book.getCount());
			data.add(book.getTotalPrice());
			data.add(book.getISBN());
			data.add(book.getBookCategory());
			data.add(book.getLanguage());
			data.add(book.getSize());
			data.add(book.getBinding());
			data.add(book.getFeature());
			defaultModel.addRow(data);
		}

	}
}
=======
package com.sx.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sx.entity.Book;
import com.sx.entity.BookBrowser;
import com.sx.entity.Reader;
import com.sx.fun.BookBrowseOp;
import com.sx.fun.BookOp;
import com.sx.fun.ReaderOp;

public class browseManage extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTable tableShow_Down;
	private JTable tableShow_Up;
	List<Reader> readerlist;
	JScrollPane scrollPane;
	JScrollPane scrollPane2;
	List<Book> bookList;
	List<Book> books = null;
	List<Book> borrowedBooks;
	DefaultTableModel defaultModel = new DefaultTableModel();
	DefaultTableModel defaultModel_Up = new DefaultTableModel();
	DefaultTableModel defaultModel_Down = new DefaultTableModel();
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReaderLogin.class);
	private JScrollPane scrollPane_Up;
	private JButton button_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					browseManage frame = new browseManage();
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
	private List<Book> setSelectedBooks() {
		List<Book> borrowedBookList = new ArrayList<Book>();
		int[] selectedRows = tableShow_Down.getSelectedRows();
		// select nothing
		if (0 == selectedRows.length) {
			return borrowedBookList;
		}

		// "ID" "图书编号" "书名", "作者", "出版社", "出版日期", "单价",
		// "ISBN", "图书分类", "图书语言", "开本", "装帧", "特征"
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

	public browseManage() {
		setTitle("\u501F\u9605\u7BA1\u7406");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 949, 621);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label_userInfo = new JLabel("");
		label_userInfo.setBounds(0, 54, 923, 26);
		contentPane.add(label_userInfo);

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int uid;
				uid = Integer.valueOf(textField.getText());
				List<Reader> userInfo = ReaderOp.getReaderByUID(uid);
				label_userInfo.setText(userInfo.get(0).toCString());
			}
		});
		textField.setBounds(362, 12, 158, 32);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel label = new JLabel("\u8BFB\u8005\u7F16\u53F7:");
		label.setBounds(212, 15, 80, 25);
		label.setFont(new Font("Dialog", Font.BOLD, 15));
		contentPane.add(label);

		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					if (textField_1.getText().equals("")) {
						bookList = BookOp.getBooks();
						// scrollPane.setViewportView(refreshTable(bookList));
						refreshTable(bookList, tableShow_Down,
								defaultModel_Down);
						return;
					}
					long isbn = 0;
					isbn = Long.parseLong(textField_1.getText());
					bookList = BookOp.getBookByISBN(isbn);
					refreshTable(bookList, tableShow_Down, defaultModel_Down);
				}

			}
		});
		textField_1.setBounds(362, 203, 158, 32);
		textField_1.setColumns(10);
		contentPane.add(textField_1);

		JLabel label_1 = new JLabel("\u56FE\u4E66\u7F16\u53F7:");
		label_1.setBounds(212, 206, 80, 25);
		label_1.setFont(new Font("Dialog", Font.BOLD, 15));
		contentPane.add(label_1);

		JButton button_2 = new JButton("\u8FD8\u4E66");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

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
					JOptionPane.showMessageDialog(null, "读者 " + UID + " 不存在",
							"错误", JOptionPane.ERROR_MESSAGE);
					textField.setText("");
					return;
				}
				// 正常情况不可能到这
				if (readers.size() != 1) {
					JOptionPane.showMessageDialog(null, "读者 " + UID
							+ " 在数据库中不唯一", "错误", JOptionPane.ERROR_MESSAGE);
					textField.setText("");
					return;
				}

				BookBrowser bookBrowser = BookBrowseOp.getBookBrowser(UID);

				// 理论也不会到这
				if (null == bookBrowser) {
					JOptionPane.showMessageDialog(null, "该用户不存在", "错误",
							JOptionPane.ERROR_MESSAGE);
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
					LOGGER.error(e.getMessage());
					LOGGER.error(e.getStackTrace().toString());
					// rollback
					BookBrowseOp.decBooks(book);
					BookBrowseOp.decReaderLimit(UID, 1);
					return;
				}

				JOptionPane.showMessageDialog(null, "还书成功", "恭喜",
						JOptionPane.INFORMATION_MESSAGE);

			}
		});
		button_2.setBounds(566, 14, 107, 27);
		contentPane.add(button_2);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 261, 923, 288);
		tableShow_Down = new JTable();
		initShowTable(bookList, tableShow_Down, defaultModel_Down);
		scrollPane.setViewportView(tableShow_Down);
		getContentPane().add(scrollPane);

		scrollPane_Up = new JScrollPane();
		scrollPane_Up.setBounds(0, 92, 923, 102);
		tableShow_Up = new JTable();
		initShowTable(books, tableShow_Up, defaultModel_Up);
		scrollPane_Up.setViewportView(tableShow_Up);
		getContentPane().add(scrollPane_Up);

		button_3 = new JButton("\u501F\u51FA");// 借出
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				borrowedBooks = setSelectedBooks();

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
					JOptionPane.showMessageDialog(null, "读者 " + UID + " 不存在",
							"错误", JOptionPane.ERROR_MESSAGE);
					textField.setText("");
					return;
				}
				// 正常情况不可能到这
				if (readers.size() != 1) {
					JOptionPane.showMessageDialog(null, "读者 " + UID
							+ " 在数据库中不唯一", "错误", JOptionPane.ERROR_MESSAGE);
					textField.setText("");
					return;
				}

				if ((readers.get(0).getLimitCount() - borrowedBooks.size()) < 0) {
					JOptionPane.showMessageDialog(null, "读者 " + UID
							+ " 借书数量达到上限", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				List<BookBrowser> bookBrowsers = BookBrowseOp.borrowBooks(
						readers.get(0), borrowedBooks);
				// 理论也不会到这
				if (0 == bookBrowsers.size()) {
					JOptionPane.showMessageDialog(null, "该用户没有借书，请确认后重试", "错误",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// TODO: need judge if mysql update success???
				try {
					BookBrowseOp.insertBookBrowsers(bookBrowsers);
				} catch (Exception e) {
					LOGGER.error("BorrowBook: 1 insert into bookbrowse error!"
							+ bookBrowsers);
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
					LOGGER.error("BorrowBook: 2 update booktable error!"
							+ bookBrowsers);
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
					LOGGER.error("BorrowBook: 3 update usertable error!"
							+ bookBrowsers);
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
				JOptionPane.showMessageDialog(null, "借书成功", "恭喜",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		button_3.setBounds(566, 205, 107, 27);
		contentPane.add(button_3);

	}

	/*
	 * private JTable initShowTableReaderbook(List<Book> books) { if
	 * (tableShow_Up != null) { return tableShow_Up; } tableShow_Up = new
	 * JTable(); Object[][] data = new Object[][] {}; String[] name = new
	 * String[] { "ID", "图书编号", "书名", "作者", "出版社", "出版日期", "单价", "数量", "总价",
	 * "ISBN", "图书分类", "图书语言", "开本", "装帧", "特征" }; defaultModel = new
	 * DefaultTableModel(data, name); if (books != null) {
	 * getBookDetail1(books); } tableShow_Up.setModel(defaultModel);
	 * 
	 * return tableShow_Up; }
	 * 
	 * private void getBookDetail1(List<Book> books) { if
	 * (defaultModel.getRowCount() != 0) { defaultModel.setRowCount(0); } //
	 * List<Book> bookList = BookOp.getBooks(); Book book = null; for (int i =
	 * 0; i < books.size(); i++) { Vector<Object> data = new Vector<>(); book =
	 * books.get(i); data.add(i + 1); data.add(book.getBID());
	 * data.add(book.getBookName()); data.add(book.getAuthor());
	 * data.add(book.getPress()); data.add(book.getPressDate());
	 * data.add(book.getPrice()); data.add(book.getCount());
	 * data.add(book.getTotalPrice()); data.add(book.getISBN());
	 * data.add(book.getBookCategory()); data.add(book.getLanguage());
	 * data.add(book.getSize()); data.add(book.getBinding());
	 * data.add(book.getFeature()); defaultModel.addRow(data); }
	 * 
	 * }
	 */
	// ------------------------------------------------------

	private void initShowTable(List<Book> bookList, JTable table,
			DefaultTableModel tableModel) {
		Object[][] data = new Object[][] {};
		String[] name = new String[] { "ID", "图书编号", "书名", "作者", "出版社", "出版日期",
				"单价", "数量", "总价", "ISBN", "图书分类", "图书语言", "开本", "装帧", "特征" };
		tableModel.setDataVector(data, name);
		if (bookList != null) {
			getBookDetail(bookList, tableModel);
		}
		table.setModel(tableModel);

	}

	/*
	 * 更新
	 */
	protected JTable refreshTable(List<Book> bookList, JTable table,
			DefaultTableModel tableModel) {
		// if (table == null) {
		// table = new JTable();
		// }

		table.removeAll();
		getBookDetail(bookList, tableModel);
		table.setModel(tableModel);
		table.validate();
		return table;
	}

	// 获取书籍详细信息，存入向量用以向JTable中添加
	private void getBookDetail(List<Book> bookList, DefaultTableModel tableModel) {
		if (tableModel.getRowCount() != 0) {
			tableModel.setRowCount(0);
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
			data.add(book.getCount());
			data.add(book.getTotalPrice());
			data.add(book.getISBN());
			data.add(book.getBookCategory());
			data.add(book.getLanguage());
			data.add(book.getSize());
			data.add(book.getBinding());
			data.add(book.getFeature());
			tableModel.addRow(data);
		}

	}
}

