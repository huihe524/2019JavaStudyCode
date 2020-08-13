package com.group1.book.util;

import java.io.FileReader;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    /**
     * 文件的读取，只需要读取一次即可拿到这些值。使用静态代码块
     */
    static {
        //读取资源文件，获取值。
        try {
            //1. 创建Properties集合类。
            Properties pro = new Properties();
            //获取src路径下的文件的方式--->ClassLoader 类加载器
            ClassLoader classLoader = JDBCUtils.class.getClassLoader();
            URL res = classLoader.getResource("jdbc.properties");
            String path = res.getPath();
            //这里的结果就是jdbc.properties的绝对路径
            System.out.println(path);
            //2. 加载文件
            pro.load(new FileReader(path));
            //3. 获取数据，赋值
            url = pro.getProperty("url");
            user = pro.getProperty("user");
            password = pro.getProperty("password");
            driver = pro.getProperty("driver");
            //4. 注册驱动
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return 连接对象
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 释放资源
     * @param rs 查询结果集
     * @param stmt 语句对象
     * @param conn 连接对象
     */
    public static void closeResource(ResultSet rs, Statement stmt, Connection conn){
        if( rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if( stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if( conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新操作 增、删、改
     * @param pstm
     * @param params
     * @return
     * @throws Exception
     */
    public static int executeUpdate(PreparedStatement pstm, Object[] params) throws Exception{
        int updateRows = 0;
        if(params != null)
            for(int i = 0; i < params.length; i++){
                pstm.setObject(i+1, params[i]);
            }
        updateRows = pstm.executeUpdate();
        return updateRows;
    }

    /**
     * 查询操作 查
     * @param pstm
     * @param params
     * @return 结果集
     */
    public static ResultSet executeQuery(PreparedStatement pstm, Object[] params) throws Exception{
        if (params != null){
            for(int i = 0; i < params.length; i++){
                pstm.setObject(i+1, params[i]);
            }
        }
        return pstm.executeQuery();
    }

}