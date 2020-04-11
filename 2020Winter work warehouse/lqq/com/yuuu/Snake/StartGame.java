package com.yuuu.Snake;

import javax.swing.*;

//游戏的主启动类
public class StartGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("贪吃蛇");

        //游戏界面应该在面板上
        frame.add(new GamePanle());

        frame.setVisible(true);
        frame.setBounds(10,5,900,720);
        frame.setResizable(false);//设置窗口大小不可变
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}
