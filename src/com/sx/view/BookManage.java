package com.sx.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.sx.db.BookDb;
import com.sx.fun.BookOp;
import com.sx.util.Constant;

public class BookManage extends ManageParent {

	private JButton button;
	private JButton button_1;
	private JButton button_2;
	private long del_isbn;

	/**
	 * Create the panel.
	 */
	public BookManage() {
		init();
		bookList = BookDb.getBookList();
		refreshTable(bookList);
	}

	private void init() {
		button = new JButton("\u6DFB\u52A0");// ���
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBookFrame add = new AddBookFrame();
				add.setVisible(true);
			}

		});
		panel.add(button);

		button_1 = new JButton("\u4FEE\u6539");// �޸�
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ModifyBookFrame add_gai = new ModifyBookFrame();
				add_gai.setVisible(true);
				int selectedRow = tableShow.getSelectedRow();// ���ѡ���е�����
				if (selectedRow != -1) // ����ѡ����
				{
					// ID ����", "����", "������", "��������", "����",
					// "����", "�ܼ�", "ISBN", "ͼ�����", "ͼ������", "����", "װ֡", "����"
					Object ID = defaultModel.getValueAt(selectedRow, 0);
					Object NM = defaultModel.getValueAt(selectedRow, 2);
					Object ZZ = defaultModel.getValueAt(selectedRow, 3);
					Object CBS = defaultModel.getValueAt(selectedRow, 4);
					Object CBRQ = defaultModel.getValueAt(selectedRow, 5);
					Object DJ = defaultModel.getValueAt(selectedRow, 6);
					Object SL = defaultModel.getValueAt(selectedRow, 7);
					Object ZJ = defaultModel.getValueAt(selectedRow, 8);
					Object ISBN = defaultModel.getValueAt(selectedRow, 9);
					Object TSFL = defaultModel.getValueAt(selectedRow, 10);
					Object YY = defaultModel.getValueAt(selectedRow, 11);
					Object KB = defaultModel.getValueAt(selectedRow, 12);
					Object ZhuangZ = defaultModel.getValueAt(selectedRow, 13);
					Object TZ = defaultModel.getValueAt(selectedRow, 14);

					add_gai.textField_BookName.setText((String) NM);
					add_gai.textField_BookISBN.setText(String.valueOf(ISBN));
					add_gai.textField_BookNum.setText(String.valueOf(SL));
					add_gai.textField_Author.setText((String) ZZ);
					add_gai.textField_fenlei.setText((String) TSFL);
					add_gai.textField_kaiben.setText((String) KB);
					add_gai.textField_lang.setText((String) YY);
					add_gai.textField_press.setText((String) CBS);
					add_gai.textField_tezhen.setText((String) TZ);
					add_gai.textField_total.setText(String.valueOf(ZJ));
					add_gai.textField_zhuangzhen.setText((String) ZhuangZ);
					add_gai.textField_press_date.setText((String) CBRQ);
					add_gai.textField_Price.setText(String.valueOf(DJ));

				}

			}
		});
		panel.add(button_1);

		button_2 = new JButton("\u5220\u9664");// ɾ��
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = tableShow.getSelectedRow();// ���ѡ���е�����
				if (selectedRow != -1) // ����ѡ����
				{
					del_isbn = (long) defaultModel.getValueAt(selectedRow, 9);
					BookOp.delBook(del_isbn);
					defaultModel.removeRow(selectedRow); // ɾ���� ɾ�����ݿ�δд
				}
			}
		});
		panel.add(button_2);
	}

	public void onClick() {
		if (textField.getText().equals("")) {
			bookList = BookOp.getBooks();
			// scrollPane.setViewportView(refreshTable(bookList));
			refreshTable(bookList);
			return;
		}

		// ����ISBN��ѯ
		if (comboBox.getSelectedItem().toString().trim().equals(Constant.ISBN)) {
			long ISBN = 0;
			try {
				ISBN = Long.parseLong(textField.getText());
				bookList = BookOp.getBookByISBN(ISBN);
				refreshTable(bookList);
			} catch (NumberFormatException nume) {
				JOptionPane.showMessageDialog(null, "�Ƿ����룡", "����",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		// ����������Ϣ��ѯ
		if (comboBox.getSelectedItem().toString().trim()
				.equals(Constant.Author)) {
			String str = textField.getText().trim();
			bookList = BookOp.getBookByAuthor(str);
			refreshTable(bookList);
		}

		// ����������ѯ
		if (comboBox.getSelectedItem().toString().trim()
				.equals(Constant.BookName)) {
			String str = textField.getText().trim();
			bookList = BookOp.getBookByBookName(str);
			refreshTable(bookList);
		}
	}
}
