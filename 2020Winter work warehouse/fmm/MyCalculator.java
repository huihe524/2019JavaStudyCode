package fmm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyCalculator extends JFrame implements ActionListener {

	// 定义成员变量
	JButton[] nums = new JButton[10]; // 数字0-9
	String[] op = new String[] { ".", "+", "-", "*", "/", "%", "^", "C", "Back", "=" };// 符号
	JButton[] operator = new JButton[10]; // 操作符
	JPanel jPanel;// 用于添加数字键和符号键等的面板
	JTextField textField;// ,文本框，用于存储计算结果
	Font font;// 字体设置
	String nowButton;// 现在按下的键

	// 构造方法
	public MyCalculator() {

		setBounds(400, 200, 350, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		textField = new JTextField();
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setFont(new Font("黑体", Font.BOLD, 23));
		textField.setEditable(false);// 设置文本不可编辑
		font = new Font("黑体", Font.BOLD, 17);

		Container c = getContentPane();// 调用getContentPane()方法，初始化容器对象，用于添加组件

		textField.setPreferredSize(new Dimension(350, 55));// 运算结果文本框大小设置
		c.setLayout(new BorderLayout());// 设置布局为边界布局
		c.add(textField, BorderLayout.NORTH);
		jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(5, 4, 3, 3));// 将面板设置为表格布局，用于添加数字键和符号键
		c.add(jPanel, BorderLayout.CENTER);// 添加面板组件，放置于窗体的中心部分
		setTitle("计算器");

		// 添加数字键于面板中
		for (int i = 0; i < 10; i++) {
			nums[i] = new JButton(i + "");
			nums[i].addActionListener(this);
			nums[i].setFont(font);
		}
		// 添加符号键于面板中
		for (int i = 0; i < 10; i++) {
			operator[i] = new JButton(op[i]);
			operator[i].addActionListener(this);
			operator[i].setFont(font);
		}

		jPanel.add(operator[4]);
		jPanel.add(operator[6]);
		jPanel.add(operator[7]);
		jPanel.add(operator[8]);
		jPanel.add(nums[7]);
		jPanel.add(nums[8]);
		jPanel.add(nums[9]);
		jPanel.add(operator[1]);
		jPanel.add(nums[4]);
		jPanel.add(nums[5]);
		jPanel.add(nums[6]);
		jPanel.add(operator[2]);
		jPanel.add(nums[1]);
		jPanel.add(nums[2]);
		jPanel.add(nums[3]);
		jPanel.add(operator[3]);
		jPanel.add(operator[5]);
		jPanel.add(nums[0]);
		jPanel.add(operator[0]);
		jPanel.add(operator[9]);

		setVisible(true);// 设置窗体可见
	}

	@Override
	// 实现接口中需要实现的方法
	// getActionCommand()依赖于按钮上的字符串，得到的是标签
	public void actionPerformed(ActionEvent event) {

		nowButton = event.getActionCommand();
		if (nowButton != "Back" && nowButton != "=") {
			// 如果是退位和=就不执行了
			textField.setText(textField.getText() + nowButton);// 显示文本框中的内容和当前按下键中的内容
		}

		if (nowButton.equals("=")) {
			// 如果是等于号，就调用函数计算结果
			if (calculator(textField.getText()).equals("除数不能为零，操作错误！")) {
				textField.setText("");
			} else {
				textField.setText(calculator(textField.getText()));// 调用calculator方法，传参进入，返回结果
			}

		}

		if (nowButton.equals("Back")) {
			// 回退一个字符
			StringBuffer sb = new StringBuffer(textField.getText());
			textField.setText(sb.substring(0, sb.length() - 1));
		}

		if (nowButton.equals("C")) {// 清空
			textField.setText("");// 文本内容设置为空
		}
	}

	// 用来计算的方法，传参进入，返回String类型
	public String calculator(String string) {
		StringBuffer sb = new StringBuffer(string);
		int operatorCount = 0;// 符号数量

		int j = 0;// 控制索引，进行计数
		// 计算有多少个运算符，就有n+1个数字，因为符号数量总是比数字数量少1
		for (j = 0; j < sb.length(); j++) {
			if (sb.charAt(j) <= '9' && sb.charAt(j) >= '0' || sb.charAt(j) == '.') {
				continue;
			} else {
				operatorCount++;
			}
		}

		// 初始化符号数组
		char[] fuhao = new char[operatorCount];
		// 初始化数字数组（用字符串表示）
		String[] num = new String[operatorCount + 1];
		for (j = 0; j < num.length; j++) {
			num[j] = "";
		}

		/*
		 * 把每个数字存进数字数组，每个符号存进符号数组 因为要保证符号必须紧跟在数字的后面，所以控制存储位置的k的变化保存在了符号存储的代码块中，因为先按下数字键
		 * 将字符串直接转化为字符数组不方便，因为用户按下的字符串中有数字有符号，当进行符号判断等操作时，不方便进行提取 所以采用charAt()方法进行遍历存储
		 */

		int k = 0;
		for (j = 0; j < sb.length(); j++) {
			if (sb.charAt(j) <= '9' && sb.charAt(j) >= '0' || sb.charAt(j) == '.') {
				num[k] += sb.charAt(j);// 字符串的拼接操作，因为在初始化的时候都为""
				continue;
			} else {
				fuhao[k] = sb.charAt(j);
				k++;
			}
		}
		// 计算
		double result = 0;
		String isZero = "";// 除数是否为零标志
		String is = "错误";
		for (int i = 0; i < operatorCount; i++) {

			// 取前两个数，和第一个操作符，运算
			double num1 = Double.parseDouble(num[i]);// 将字符串类型转换为double类型，使用Double包装类
			double num2 = Double.parseDouble(num[i + 1]);
			char ch = fuhao[i];

			// 计算
			switch (ch) {
			case '+':
				result = num1 + num2;
				break;
			case '-':
				result = num1 - num2;
				break;
			case '*':
				result = num1 * num2;
				break;
			case '/':
				if (num2 == 0) {
					isZero = "除数不能为零，操作错误！";
					try {
						if (!isZero.equals(is)) {
							throw new MyException("异常：" + isZero);
						}
						System.out.println(num1 / num2);
					} catch (MyException e) {
						e.printStackTrace();
					}
					result = 0;
					break;
				} else {
					result = num1 / num2;
					break;
				}
			case '%':// 延用C语言（取模！）
				result = (int) num1 % (int) num2;
				break;
			case '^':
				result = Math.pow(num1, num2);
				break;
			default:
				break;
			}
			num[i + 1] = String.valueOf(result);// 将数字强制转换为字符串类型（显式转换），作为下一次计算的num1
		}

		if (isZero.equals("除数不能为零，操作错误！"))
			return isZero;
		else
			return String.valueOf(result);// 返回字符串类型的结果（将数字强制转换为字符串类型）
	}

	public static void main(String[] args) {
		new MyCalculator();
	}
}
