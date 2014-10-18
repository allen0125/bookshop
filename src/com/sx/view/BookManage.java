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
import com.sx.view.Add;

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

	/**
	 * Create the panel.   ��BUG��������������������������������������ѯ�������ã�
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
				"\u4E66\u540D", "\u4F5C\u8005" }));//���� ����
		panel.add(comboBox);

		JLabel labelKeyWords = new JLabel("\u5173\u952E\u5B57");
		panel.add(labelKeyWords);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		JButton btnSearch = new JButton("\u67E5\u8BE2");//��ѯ
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
						JOptionPane.showMessageDialog(null, "�Ƿ����룡", "����",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				
				if (comboBox.getSelectedItem().toString().trim().equals("\u4E66\u540D")) {    //������ѯ
					String BookName = null;
					try {
						BookName = textField.getText();
						bookList = BookOp.getBookByBookName(BookName);
						scrollPane.setViewportView(refreshTable(bookList));
						scrollPane.validate();
					}  catch (NumberFormatException e3) {
						JOptionPane.showMessageDialog(null, "�Ƿ����룡", "����",
								JOptionPane.ERROR_MESSAGE);
					}
					
				}
				
				if (comboBox.getSelectedItem().toString().trim().equals("\u4F5C\u8005")) {//���߲�ѯ
					String Author = null;
					try {
						Author = textField.getText();
						bookList = BookOp.getBookByAuthor(Author);
						scrollPane.setViewportView(refreshTable(bookList));
						scrollPane.validate();
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "�Ƿ����룡", "����",
									JOptionPane.ERROR_MESSAGE);
						// TODO: handle exception
					}
					
				}

			}
		});
		panel.add(btnSearch);
		
		button = new JButton("\u6DFB\u52A0");//���
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Add add = new Add();
				add.setVisible(true);
			}
			
		});
		panel.add(button);
		
		button_1 = new JButton("\u4FEE\u6539");//�޸�
		panel.add(button_1);
		
		button_2 = new JButton("\u5220\u9664");//ɾ��
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
	 * ����
	 */
	private JTable getShowTable(List<Book> bookList) {
		if (tableShow != null) {
			return tableShow;
		}
		tableShow = new JTable();
		Object[][] data = new Object[][] {};
		String[] name = new String[] { "ID", "����", "����", "������", "��������", "����",
				"����", "�ܼ�", "ISBN", "ͼ�����", "ͼ������", "����", "װ֡", "����" };
		defaultModel = new DefaultTableModel(data, name);
		defaultModel.setColumnCount(14);
		getBookDetail(bookList);
		tableShow.setModel(defaultModel);

		return tableShow;
	}

	// ��ȡ�鼮��ϸ��Ϣ����������������JTable�����
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
