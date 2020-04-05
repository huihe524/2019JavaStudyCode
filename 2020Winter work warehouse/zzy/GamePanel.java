package com.zzy.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.Random;

//游戏面板
public class GamePanel extends JPanel implements KeyListener, ActionListener {
    //P1变量
    int length;//蛇的长度
    int[] snakeX = new int[600];
    int[] snakeY = new int[500];
    String fx;
    int foodx, foody;//食物坐标
    int foodsx, foodsy;
    int score;//P1分数
    int score2;//P2分数
    int win;//控制双人模式的输赢情况

    //P2变量
    int length2;//蛇的长度
    int[] snake2X = new int[600];
    int[] snake2Y = new int[500];
    String fx2;

    int dou = 3;//控制双人，0为单人，1为双人

    Random random = new Random();//随机数

    boolean isStart = false;//默认停止
    boolean isfail = false;//控制失败
    boolean issec = false;//控制难度
    boolean isdouble = false;//双人模式设置

    public GamePanel() throws FileNotFoundException {
        init();//菜单
        this.setFocusable(true);
        this.addKeyListener(this);//键盘监听
    }



    public void init() {
        length = 3;
        snakeX[0] = 100;
        snakeY[0] = 100;
        snakeX[1] = 75;
        snakeY[1] = 100;
        snakeX[2] = 50;
        snakeY[2] = 100;
        fx = "R";
        foodx = 25 + 25 * random.nextInt(34);
        foody = 75 + 25 * random.nextInt(23);
        foodsx = 25 + 25 * random.nextInt(34);
        foodsy = 75 + 25 * random.nextInt(23);
        score = 0;
        length2 = 3;//蛇的长度
        snake2X[0] = 100;
        snake2Y[0] = 100;
        snake2X[1] = 75;
        snake2Y[1] = 100;
        snake2X[2] = 50;
        snake2Y[2] = 100;
        fx2 = "R";
    }

    //绘制面板,画游戏
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.black);

        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑", Font.BOLD, 15));
        g.drawString("红方长度" + length, 750, 35);
        g.drawString("红方分数" + score, 750, 55);
        g.drawString("蓝方长度" + length2, 75, 35);
        g.drawString("蓝方分数" + score2, 75, 55);

        Data.food.paintIcon(this, g, foodx, foody);
        if((score % 300 == 0) || score2 % 300 == 0){
            Data.foods.paintIcon(this, g, foodsx, foodsy);
        }
        if (fx.equals("R")) {
            Data.right.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("L")) {
            Data.left.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("U")) {
            Data.up.paintIcon(this, g, snakeX[0], snakeY[0]);
        } else if (fx.equals("D")) {
            Data.down.paintIcon(this, g, snakeX[0], snakeY[0]);
        }
        for (int i = 1; i < length; i++) {
            Data.body.paintIcon(this, g, snakeX[i], snakeY[i]);
        }
        if(dou == 1){
            if (fx2.equals("R")) {
                Data.right2.paintIcon(this, g, snake2X[0], snake2Y[0]);
            } else if (fx2.equals("L")) {
                Data.left2.paintIcon(this, g, snake2X[0], snake2Y[0]);
            } else if (fx2.equals("U")) {
                Data.up2.paintIcon(this, g, snake2X[0], snake2Y[0]);
            } else if (fx2.equals("D")) {
                Data.down2.paintIcon(this, g, snake2X[0], snake2Y[0]);
            }
            for (int i = 1; i < length2; i++) {
                Data.body2.paintIcon(this, g, snake2X[i], snake2Y[i]);
            }
        }

        if (issec == false && isdouble == false){
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("请选择游戏模式：", 300, 300);
            g.setFont(new Font("微软雅黑", Font.BOLD, 20));
            g.drawString("1.单人模式", 400, 325);
            g.drawString("2.双人模式", 400, 350);
        }
        if(issec == false && isdouble == true){//难度设置
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("请选择游戏难度：", 300, 300);
            g.setFont(new Font("微软雅黑", Font.BOLD, 20));
            g.drawString("3.简单", 400, 325);
            g.drawString("4.普通", 400, 350);
            g.drawString("5.困难", 400, 375);
            g.drawString("6.地狱", 400, 400);
            g.drawString("7.光速", 400, 425);
        }
        if (isStart == false && issec == true) {
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("请按空格开始你的表演", 270, 300);
            g.setFont(new Font("微软雅黑", Font.BOLD, 20));
            g.drawString("蓝球为普通食物，红球为超级食物", 300, 75);
        }

        if (isfail && dou == 0) {//失败设置
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("你失败了，辣鸡", 300, 300);
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑", Font.BOLD, 20));
            g.drawString("按空格重新开始", 355, 360);
            g.drawString("按ESC退出游戏", 355, 400);
        }else if(isfail && dou ==1){
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            if(win == 0){
                g.drawString("蓝方胜", 375, 300);

            }else if(win == 1){
                g.drawString("红方胜", 375, 300);
            }
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑", Font.BOLD, 20));
            g.drawString("按空格重新开始", 355, 360);
            g.drawString("按ESC退出游戏", 355, 400);
        }
    }


    //键盘监听
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {//空格开始游戏
            if (isfail) {
                isfail = false;
                init();
            } else {
                isStart = !isStart;
            }
            repaint();
        }
        if(keyCode == KeyEvent.VK_ESCAPE){//ESC退出
            System.exit(0);
        }
        //模式设置
        if ((keyCode == KeyEvent.VK_1||keyCode == KeyEvent.VK_NUMPAD1) && isdouble == false){
            if (isfail) {
                isfail = false;
                init();
            }else{
                dou = 0;
                isdouble = true;
            }
            repaint();
        }else if ((keyCode == KeyEvent.VK_2||keyCode == KeyEvent.VK_NUMPAD2) && isdouble == false){
            if (isfail) {
                isfail = false;
                init();
            }else{
                dou = 1;
                isdouble = true;
            }
            repaint();
        }

        //难度设置
        if ((keyCode == KeyEvent.VK_3||keyCode == KeyEvent.VK_NUMPAD3) && (dou == 1||dou == 0)){
            if (isfail) {
                isfail = false;
                init();
            } else {
                new Timer(100, this).start();
                issec = !issec;
            }
            repaint();
        }else if ((keyCode == KeyEvent.VK_4||keyCode == KeyEvent.VK_NUMPAD4) && (dou == 1||dou == 0)){
            if (isfail) {
                isfail = false;
                init();
            } else {
                new Timer(70, this).start();
                issec = !issec;
            }
            repaint();
        }else if ((keyCode == KeyEvent.VK_5||keyCode == KeyEvent.VK_NUMPAD5) && (dou == 1||dou == 0)){
            if (isfail) {
                isfail = false;
                init();
            } else {
                new Timer(40, this).start();
                issec = !issec;
            }
            repaint();
        }else if ((keyCode == KeyEvent.VK_6||keyCode == KeyEvent.VK_NUMPAD6) && (dou == 1||dou == 0)){
            if (isfail) {
                isfail = false;
                init();
            } else {
                new Timer(25, this).start();
                issec = !issec;
            }
            repaint();
        }else if ((keyCode == KeyEvent.VK_7||keyCode == KeyEvent.VK_NUMPAD7) && (dou == 1||dou == 0)){
            if (isfail) {
                isfail = false;
                init();
            } else {
                new Timer(10, this).start();
                issec = !issec;
            }
            repaint();
        }


        //控制P1移动
        if (keyCode == KeyEvent.VK_UP && !fx.equals("D")) {
            fx = "U";
        } else if (keyCode == KeyEvent.VK_DOWN && !fx.equals("U")) {
            fx = "D";
        } else if (keyCode == KeyEvent.VK_LEFT && !fx.equals("R")) {
            fx = "L";
        } else if (keyCode == KeyEvent.VK_RIGHT && !fx.equals("L")) {
            fx = "R";
        }
        //控制P2移动
        if (keyCode == KeyEvent.VK_W && !fx2.equals("D")) {
            fx2 = "U";
        } else if (keyCode == KeyEvent.VK_S && !fx2.equals("U")) {
            fx2 = "D";
        } else if (keyCode == KeyEvent.VK_A && !fx2.equals("R")) {
            fx2 = "L";
        } else if (keyCode == KeyEvent.VK_D && !fx2.equals("L")) {
            fx2 = "R";
        }
    }

    //事件监听
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isStart && isfail == false) {
            //P1食物判定
            if (snakeX[0] == foodx && snakeY[0] == foody) {
                length++;
                score += 100;
                //生成食物
                foodx = 25 + 25 * random.nextInt(34);
                foody = 75 + 25 * random.nextInt(23);
            }else if (snakeX[0] == foodsx && snakeY[0] == foodsy && score % 300 == 0) {
                length+=4;
                score += 400;
                //生成超级食物
                foodsx = 25 + 25 * random.nextInt(34);
                foodsy = 75 + 25 * random.nextInt(23);
            }
            //P2食物判定
            if (snake2X[0] == foodx && snake2Y[0] == foody) {
                length2++;
                score2 += 100;
                //生成食物
                foodx = 25 + 25 * random.nextInt(34);
                foody = 75 + 25 * random.nextInt(23);
            }else if (snake2X[0] == foodsx && snake2Y[0] == foodsy && score2 % 300 == 0) {
                length2+=4;
                score2 += 400;
                //生成超级食物
                foodsx = 25 + 25 * random.nextInt(34);
                foodsy = 75 + 25 * random.nextInt(23);
            }

            for (int i = length - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            for (int i = length2 - 1; i > 0; i--) {
                snake2X[i] = snake2X[i - 1];
                snake2Y[i] = snake2Y[i - 1];
            }
            //控制P1转向
            if (fx.equals("R")) {
                snakeX[0] = snakeX[0] + 25;
                if (snakeX[0] > 850) {
                    snakeX[0] = 25;
                }
            } else if (fx.equals("L")) {
                snakeX[0] = snakeX[0] - 25;
                if (snakeX[0] < 25) {
                    snakeX[0] = 850;
                }
            } else if (fx.equals("U")) {
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] < 0) {
                    snakeY[0] = 650;
                }
            } else if (fx.equals("D")) {
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] > 650) {
                    snakeY[0] = 0;
                }
            }
            //控制P2转向
            if (fx2.equals("R")) {
                snake2X[0] = snake2X[0] + 25;
                if (snake2X[0] > 850) {
                    snake2X[0] = 25;
                }
            } else if (fx2.equals("L")) {
                snake2X[0] = snake2X[0] - 25;
                if (snake2X[0] < 25) {
                    snake2X[0] = 850;
                }
            } else if (fx2.equals("U")) {
                snake2Y[0] = snake2Y[0] - 25;
                if (snake2Y[0] < 0) {
                    snake2Y[0] = 650;
                }
            } else if (fx2.equals("D")) {
                snake2Y[0] = snake2Y[0] + 25;
                if (snake2Y[0] > 650) {
                    snake2Y[0] = 0;
                }
            }
            //失败判定
            for (int i = 1; i < length; i++) {
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isfail = true;
                    win = 0;
                }
            }
            for (int j = 1; j < length2; j++) {
                if (snake2X[0] == snake2X[j] && snake2Y[0] == snake2Y[j]) {
                    isfail = true;
                    win = 1;
                }
            }
            repaint();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}



