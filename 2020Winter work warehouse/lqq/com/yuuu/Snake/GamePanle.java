package com.yuuu.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

//游戏的面板
public class GamePanle extends JPanel implements KeyListener,ActionListener{

    //定义蛇的数据结构
    int length;//蛇的长度
    int[] snakeX = new int[600];//蛇的x坐标
    int[] snakeY = new int[500];//蛇的y坐标
    String fx;//方向

    //食物的坐标
    int foodx;
    int foody;
    Random random = new Random();//食物随机分布

    //成绩
    int score;

    //游戏当前状态：开始，停止
    boolean isStart = false;//默认是停止

    boolean isFail = false;//游戏失败状态

    //定时器
    Timer timer = new Timer(150,this);

    //构造器
    public GamePanle() {
        init();
        //获得焦点和键盘事件
        this.setFocusable(true);//获得焦点事件
        this.addKeyListener(this);//获得键盘监听事件
        timer.start();
    }

    //初始化方法
    public void init(){
        length = 3;
        snakeX[0] = 100;snakeY[0] = 100;//脑袋的坐标
        snakeX[1] = 75;snakeY[1] = 100;//第一个身体的坐标
        snakeX[2] = 50;snakeY[2] = 100;//第二个身体的坐标
        fx = "R";//初始方向向右

        //把食物随机分布在界面上
        foodx = 25+25*random.nextInt(34);
        foody = 75+25*random.nextInt(24);

        score = 0;
    }


    //绘制面板，游戏中所有东西都使用这个画笔来画
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//清屏
        this.setBackground(Color.white);
        //画静态的面板
        Data.header.paintIcon(this,g,25,11);//头部广告栏画上去
        g.fillRect(25,75,850,600);//游戏框为一个矩形

        //画食物
        Data.food.paintIcon(this,g,foodx,foody);

        //画小蛇
        if(fx.equals("R")){
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);//蛇脑袋初始化向右,需要通过方向来判断
        } else if(fx.equals("L")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);//蛇脑袋初始化向右
        }else if(fx.equals("U")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);//蛇脑袋初始化向右
        }else if(fx.equals("D")){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);//蛇脑袋初始化向右
        }

        for (int i = 1; i <length; i++) {
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }

        //画成绩
        g.setColor(Color.white);
        g.setFont(new Font("微软雅黑",Font.BOLD,18));
        g.drawString("长度"+length,750,32);
        g.drawString("分数"+score,750,55);

        //游戏状态
        if(isStart==false){
            g.setColor(Color.white);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始游戏！",300,300);
        }

        if (isFail){
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("失败！按下空格重新开始！",300,300);
        }


    }


    //键盘监听事件
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();//获得键盘按键是哪个

        if (keyCode == KeyEvent.VK_SPACE){
            if (isFail){
                isFail = false;//重新开始
                init();
            }else {
                isStart = !isStart;//取反
            }
            repaint();

        }
        //小蛇移动
        if (keyCode==KeyEvent.VK_UP){
            fx = "U";
        }else if (keyCode==KeyEvent.VK_DOWN){
            fx = "D";
        }else if (keyCode==KeyEvent.VK_LEFT){
            fx = "L";
        }else if (keyCode==KeyEvent.VK_RIGHT){
            fx = "R";
        }
    }

    //事件监听，通过固定时间来刷新
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isStart && isFail==false){ //如果游戏开始，小蛇就动起来
            //吃食物
            if (snakeX[0]==foodx && snakeY[0]==foody){
                length++;//长度增加1
                score+=5;//分数加5
                foodx = 25+25*random.nextInt(34);//再次随机食物
                foody = 75+25*random.nextInt(24);
            }

            //移动
            for (int i = length; i > 0; i--) {
                snakeX[i] = snakeX[i-1]; //后一节移到前一节的位置
                snakeY[i] = snakeY[i-1];
            }
            //走向
            if (fx.equals("R")){
                snakeX[0] = snakeX[0]+25;
                if (snakeX[0]>850){ //边界判断
                    snakeX[0] = 25;
                }
            }else if (fx.equals("L")){
                snakeX[0] = snakeX[0]-25;
                if (snakeX[0]<0){ //边界判断
                    snakeX[0] = 850;
                }
            }else if (fx.equals("U")){
                snakeY[0] = snakeY[0]-25;
                if (snakeY[0]<75){ //边界判断
                    snakeY[0] = 650;
                }
            }else if (fx.equals("D")){
                snakeY[0] = snakeY[0]+25;
                if (snakeY[0]>650){ //边界判断
                    snakeY[0] = 75;
                }

            }

            //失败判断，撞到自己失败
            for (int i = 1; i < length; i++) {
                if (snakeX[0]==snakeX[i] && snakeY[0]==snakeY[i]){
                    isFail = true;
                }
            }

            repaint();//重画
        }
        timer.start();

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

}
