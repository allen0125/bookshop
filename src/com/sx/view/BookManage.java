package com.sx.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.sx.entity.Book;
import com.sx.fun.BookOp;

public class BookManage extends JPanel {
	private JTextField textField;
	private JTable tableShow;
	DefaultTableModel defaultModel;
	private JPanel panel = null;
	private JLabel labelSearch;
	private JComboBox comboBox;
	private String selected;
	List<Book> bookList;
	JScrollPane scrollPane;

	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private long del_isbn;

	/**
	 * Create the panel.
	 */
	public BookManage(int width) {
		setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 1000, 33);
		add(panel);

		labelSearch = new JLabel("\u641C\u7D22\u5B57\u6BB5");
		labelSearch.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(labelSearch);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "ISBN",
				"\u4E66\u540D", "\u4F5C\u8005" }));
		panel.add(comboBox);

		JLabel labelKeyWords = new JLabel("\u5173\u952E\u5B57");
		panel.add(labelKeyWords);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		JButton btnSearch = new JButton("\u67E5\u8BE2");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("")) {
					bookList = BookOp.getBooks();
					scrollPane.setViewportView(refreshTable(bookList));
					return;
				}

				if (comboBox.getSelectedItem().toString().trim().equals("ISBN")) {
					long ISBN = 0;
					try {
						ISBN = Long.parseLong(textField.getText());
						bookList = BookOp.getBookByISBN(ISBN);
						scrollPane.setViewportView(refreshTable(bookList));
						scrollPane.validate();
					} catch (NumberFormatException nume) {
						JOptionPane.showMessageDialog(null, "非法输入！", "错误",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		panel.add(btnSearch);

		button = new JButton("\u6DFB\u52A0");// 添加
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBookFrame add = new AddBookFrame();
				add.setVisible(true);
			}

		});
		panel.add(button);

//test
		button_1 = new JButton("\u4FEE\u6539");// 修改
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ModifyBookFrame add_gai = new ModifyBookFrame();
				add_gai.setVisible(true);
				int selectedRow = tableShow.getSelectedRow();// 获得选中行的索引
				if (selectedRow != -1) // 存在选中行
				{
					// ID 书名", "作者", "出版社", "出版日期", "单价",
					// "数量", "总价", "ISBN", "图书分类", "图书语言", "开本", "装帧", "特征"
					Object ID = defaultModel.getValueAt(selectedRow, 0);
					Object NM = defaultModel.getValueAt(selectedRow, 1);
					Object ZZ = defaultModel.getValueAt(selectedRow, 2);
					Object CBS = defaultModel.getValueAt(selectedRow, 3);
					Object CBRQ = defaultModel.getValueAt(selectedRow, 4);
					Object DJ = defaultModel.getValueAt(selectedRow, 5);
					Object SL = defaultModel.getValueAt(selectedRow, 6);
					Object ZJ = defaultModel.getValueAt(selectedRow, 7);
					Object ISBN = defaultModel.getValueAt(selectedRow, 8);
					Object TSFL = defaultModel.getValueAt(selectedRow, 9);
					Object YY = defaultModel.getValueAt(selectedRow, 10);
					Object KB = defaultModel.getValueAt(selectedRow, 10);
					Object ZhuangZ = defaultModel.getValueAt(selectedRow, 12);
					Object TZ = defaultModel.getValueAt(selectedRow, 13);

					add_gai.textField_BookName.setText((String) NM);
					add_gai.textField_BookISBN.setText((String) ISBN);
					add_gai.textField_BookNum.setText((String) SL);
					add_gai.textField_Author.setText((String) ZZ);
					add_gai.textField_fenlei.setText((String) TSFL);
					add_gai.textField_kaiben.setText((String) KB);
					add_gai.textField_lang.setText((String) YY);
					add_gai.textField_press.setText((String) CBS);
					add_gai.textField_tezhen.setText((String) TZ);
					add_gai.textField_total.setText((String) ZJ);
					add_gai.textField_zhuangzhen.setText((String) ZhuangZ);
					add_gai.textField_press_date.setText((String) CBRQ);
					add_gai.textField_Price.setText((String) DJ);

				}

			}
		});
		panel.add(button_1);

		button_2 = new JButton("\u5220\u9664");// 删除
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = tableShow.getSelectedRow();// 获得选中行的索引
				if (selectedRow != -1) // 存在选中行
				{
					del_isbn = (long) defaultModel.getValueAt(selectedRow, 8);
					BookOp.delBook(del_isbn);
					defaultModel.removeRow(selectedRow); // 删除行 删除数据库未写
				}
			}
		});
		panel.add(button_2);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 33, 1000, 600);
		add(scrollPane);

		bookList = BookOp.getBooks();
		scrollPane.setViewportView(getShowTable(bookList));
	}

	private JTable refreshTable(List<Book> bookList) {
		if (tableShow == null) {
			tableShow = new JTable();
		}
		tableShow.removeAll();
		getBookDetail(bookList);
		tableShow.setModel(defaultModel);
		tableShow.validate();
		return tableShow;
	}

	/*
	 * 更新
	 */
	private JTable getShowTable(List<Book> bookList) {
		if (tableShow != null) {
			return tableShow;
		}
		tableShow = new JTable();
		Object[][] data = new Object[][] {};
		String[] name = new String[] { "ID", "书名", "作者", "出版社", "出版日期", "单价",
				"数量", "总价", "ISBN", "图书分类", "图书语言", "开本", "装帧", "特征" };
		defaultModel = new DefaultTableModel(data, name);
		defaultModel.setColumnCount(14);
		getBookDetail(bookList);
		tableShow.setModel(defaultModel);

		return tableShow;
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
