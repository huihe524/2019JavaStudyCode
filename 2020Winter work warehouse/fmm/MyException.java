package fmm;

import javax.swing.JOptionPane;

public class MyException extends Exception {

	String string;

	public MyException(String string) {
		super(string);
		this.string = string;
	}

	public void printStackTrace() {
		JOptionPane.showMessageDialog(null, string, "错误", JOptionPane.ERROR_MESSAGE);// 对话框显示在窗体中央，
	}
}