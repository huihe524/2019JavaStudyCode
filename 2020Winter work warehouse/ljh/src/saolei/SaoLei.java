package saolei;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import static java.awt.BorderLayout.*;

public class SaoLei implements ActionListener {
    JFrame frame = new JFrame();
    ImageIcon headerIcon = new ImageIcon("src/mark/biaoqing01.png");
    ImageIcon winIcon = new ImageIcon("src/mark/biaoqing02.png");
    ImageIcon loseIcon = new ImageIcon("src/mark/biaoqing03.png");
    ImageIcon groundIcon = new ImageIcon("src/mark/fugai.png");
    ImageIcon leiIcon = new ImageIcon("src/mark/lei.png");
    ImageIcon flagIcon = new ImageIcon("src/mark/hongqi.jpg");
    JButton headerBtn = new JButton(headerIcon);
    //开始整一些数据
     int ROW = 16;
     int COL = 16;
    int [][] date = new int[ROW][COL];
    //整个数组，将来在数组里边加入按钮
    JButton[][] btns = new JButton[ROW][COL];
    int LEIMARK = -1;   //表示雷为-1
    int LEINUM = 60;    //雷的总数
    int notopened = ROW * COL;
    int opened = 0;
    int second = 0;
    JLabel label1 = new JLabel("剩余: " + notopened);
    JLabel label2 = new JLabel("积分: " + opened);
    JLabel label3 = new JLabel("用时: " + 2 + "s");
    Timer timer = new Timer(1000,this);
    private SaoLei(){
        frame.setSize(400,450); //创建一个窗口
        frame.setResizable(false);              //不可调整大小
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//可以关闭窗口
        frame.setLayout(new BorderLayout());

        setHeader();    //添加一些状态之类的
        //由于是先设置的字再生成的按钮，所以要先添加雷再添加按钮。
        insertLei();    //在按钮中添加雷
        setButtons();   //设置按钮
        frame.setTitle("扫雷");
       timer.start();
        frame.setVisible(true);
    }

    private void insertLei(){
        Random rand = new Random();
        for (int k = 0; k < LEINUM; ) {
            int r = rand.nextInt(ROW);
            int c = rand.nextInt(COL);
            //要防止一个格子中会有两个雷这种情况
            if (date[r][c] != LEIMARK){
                date[r][c] = LEIMARK;
                k++;
            }
            //计算周围的雷的数量
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COL; j++) {
                    if (date[i][j] == LEIMARK) continue;
                    int around = 0;
                    if (i>0 && j>0 && date[i-1][j-1] == LEIMARK) around++;
                    if (i>0 && date[i-1][j] == LEIMARK) around++;
                    if (i>0 && j<ROW-1 && date[i-1][j+1] == LEIMARK) around++;
                    if (j>0 && date[i][j-1] == LEIMARK) around++;
                    if (j<ROW-1 && date[i][j+1] == LEIMARK) around++;
                    if (i<COL-1 && j>0 && date[i+1][j-1] == LEIMARK) around++;
                    if (i<COL-1 && date[i+1][j] == LEIMARK) around++;
                    if (i<COL-1 && j<ROW-1 && date[i+1][j+1] == LEIMARK) around++;
                    date[i][j] = around;
                }
            }
        }
    }

    private  void setButtons(){
        Container con = new Container();
        con.setLayout(new GridLayout(ROW,COL));     //设置一个容器

        for (int i = 0; i < ROW; i++) {
            for(int j = 0; j < COL;j++){
                JButton btn = new JButton(groundIcon);
                btn.addActionListener(this);
//                JButton btn = new JButton(date[i][j] + "");
//                btn.setIcon(groundIcon);
                btn.setMargin(new Insets(0,0,0,0));
                con.add(btn);
                btns[i][j] = btn;
            }
        }
        frame.add(con,BorderLayout.CENTER);
    }
    private void setHeader(){
        JPanel panel = new JPanel(new GridBagLayout()); //给button和label都放进这一个容器里边
        GridBagConstraints c1 = new GridBagConstraints(0,0,3,1,1,0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
        panel.add(headerBtn,c1);
        headerBtn.addActionListener(this);
        label1.setOpaque(true);
        label2.setOpaque(true);
        label3.setOpaque(true);
        label1.setBackground(Color.GRAY);
        label2.setBackground(Color.GRAY);
        label3.setBackground(Color.GRAY);
        GridBagConstraints c2 = new GridBagConstraints(0,1,1,1,1,0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
        GridBagConstraints c3 = new GridBagConstraints(1,1,1,1,1,0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
        GridBagConstraints c4 = new GridBagConstraints(2,1,1,1,1,0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
        panel.add(label1,c2);
        panel.add(label2,c3);
        panel.add(label3,c4);

        headerBtn.setOpaque(true);
        headerBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        headerBtn.setBackground(Color.WHITE);

        frame.add(panel, NORTH);
    }

    public static void main(String[] args) {
        new SaoLei();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof Timer){
            second++;
            label3.setText("用时" + second + " s");
            timer.start();
            return;
        }

        JButton btn = (JButton)e.getSource();
        if (btn.equals(headerBtn)){
            restart();
            return;
        }
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (btn.equals(btns[i][j])){
                    if (date[i][j] == LEIMARK){
                        lose();
                    }else {
                        openGround(i,j);
                        checkWin();
                    }
                    return;
                }

            }
        }
    }
    //先清空数据，
    private void restart(){
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                date[i][j] = 0;
                btns[i][j].setEnabled(true);
                btns[i][j].setText("");
                btns[i][j].setIcon(groundIcon);
            }
        }
        notopened = ROW * COL;
        opened = 0;
        second = 0;
        label1.setText("剩余: " + notopened);
        label2.setText("积分: " + opened);
        label3.setText("用时：" + second + "s");
        insertLei();
        timer.start();
    }
    private void checkWin(){
        int num = 0, i, j;
        //数一下没开的个数，如果没开的个数等于雷的个数，那就行了
        for ( i = 0; i < ROW; i++) {
            for ( j = 0; j < COL; j++) {
                if (btns[i][j].isEnabled()) num++;
            }
        }
        if (num == LEINUM){
            timer.stop();
            for (i = 0; i < ROW; i++) {
                for ( j = 0; j < COL; j++) {
                    if (btns[i][j].isEnabled()){
                        btns[i][j].setIcon(flagIcon);
                    }
                }
            }
            headerBtn.setIcon(winIcon);
            JOptionPane.showMessageDialog(frame," Winner! \n 点击头像重新开始","You Win!",JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void lose(){
        timer.stop();
        headerBtn.setIcon(loseIcon);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (btns[i][j].isEnabled()){
                    JButton btn = btns[i][j];
                    if (date[i][j] == LEIMARK){
                        btn.setEnabled(false);
                        btn.setIcon(leiIcon);
                    }else{
                        btn.setIcon(null);
                        btn.setEnabled(false);
                        btn.setOpaque(false);
                        btn.setBackground(Color.cyan);  //浅蓝
                        btn.setText(date[i][j] + "");
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(frame," You are loser \n 点击头像将重新开始","You Lose",JOptionPane.PLAIN_MESSAGE);
    }

    private void openGround(int i, int j){
        JButton btn = btns[i][j];
        if(!btn.isEnabled()) return;

        btn.setIcon(null);
        btn.setEnabled(false);
        btn.setOpaque(true);
        btn.setBackground(Color.cyan);
        btn.setText(date[i][j] + "");
        state();

        if (date[i][j] == 0){
            //if (i>0 && j>0 && date[i-1][j-1] == 0) openGround(i-1,j-1);
            if (i>0 && date[i-1][j] == 0) openGround(i-1,j);
            //if (i>0 && j<ROW-1 && date[i-1][j+1] == 0) openGround(i-1,j+1);
            if (j>0 && date[i][j-1] == 0) openGround(i,j-1);
            if (j<ROW-1 && date[i][j+1] == 0) openGround(i,j+1);
            //if (i<COL-1 && j>0 && date[i+1][j-1] == 0) openGround(i+1,j-1);
            if (i<COL-1 && date[i+1][j] == 0) openGround(i+1,j);
            //if (i<COL-1 && j<ROW-1 && date[i+1][j+1] == 0) openGround(i+1,j+1);
        }
    }
    private void state(){
        opened++;
        notopened--;
        label1.setText("剩余：" + notopened);
        label2.setText("积分：" + opened);
    }
}
