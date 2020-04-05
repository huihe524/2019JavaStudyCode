package com.guikai.a.Snake;

import javax.swing.*;

//游戏的主启动类
public class startgame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        frame.setBounds(10,10,900,720);
        frame.setResizable(false);//窗口大小不可变
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //正常的游戏界面应该在面板上！
        frame.add(new GamePanel());

        frame.setVisible(true);
    }
}
