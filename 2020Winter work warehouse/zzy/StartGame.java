package com.zzy.snake;

import javax.swing.*;
import java.io.FileNotFoundException;

public class StartGame {
    public static void main(String[] args) throws FileNotFoundException {
        JFrame frame = new JFrame("贪吃蛇");

        frame.setBounds(10,10,900,720);
        frame.setResizable(false);//可见

        frame.add(new GamePanel());//面板添加
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
