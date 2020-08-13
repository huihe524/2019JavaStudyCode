## 1.mysql server的安装

**---安装好的可以直接跳到第二步了**

压缩包下载：https://dev.mysql.com/downloads/mysql/

MSI下载：https://dev.mysql.com/downloads/windows/installer/8.0.html

到mysql官网下载社区服务器版(付费版的应该只是多提供了官方技术支持还有一些服务什么的，操作起来应该没啥区别)，我这里是下的是mysql8.0.19的应该是目前最新的，你们下载5.7+的应该都没什么问题

我这里以压缩包版的为例，安装版的应该更简单基本一路下一步。

![](img\sql-install0.png)

![](img\sql-install1.png)

然后解压到你想安装的目录就行 我这里安到了mysql安装版的默认目录，我的5.7是之前就安装的，你们忽略它就好。

![](img\sql-install2.png)



然后进去目录，在目录mysql的根目录下，新建一个my.ini文件(新建文本文件，然后重命名即可)

![](img\sql-install3.png)

mysql.ini文件里配置这些 basedir注意改成自己的解压目录

```sql
[mysql]
# 设置mysql客户端默认字符集
default-character-set=utf8
 
[mysqld]
# 设置3306端口
port = 3306
# 设置mysql的安装目录
basedir=C:\\Program Files\\MySQL\\mysql-8.0.19-winx64
# 设置 mysql数据库的数据的存放目录，MySQL 8+ 不需要以下配置，系统自己生成即可，否则有可能报错
# datadir=C:\\Program Files\\Mysql\\mysql-8.0.19-winx64\\data
# 允许最大连接数
max_connections=20
# 服务端使用的字符集默认为8比特编码的latin1字符集
character-set-server=utf8
# 创建新表时将使用的默认存储引擎
default-storage-engine=INNODB
```

以管理员身份打开命令行，将目录切换到你解压文件的bin目录。

初始化Mysql，Mysql8.0之后自动生成data文件夹。

```sql
mysqld  --initialize-insecure （建议使用，不设置root密码）
 
//生成的密码在实际连接的时候可能会不小心输入错误或忘记，导致无法连接Mysql
mysqld  --initialize --console（不建议使用，在控制台生成一个随机的root密码）

//然后执行mysqld install mysql将mysql服务安装到你的电脑上 后面这个mysql第一次好像不能省(我没搞懂)
mysqld install mysql

//最后启动mysql服务
net start mysql

//服务启动后  可以在继续在bin目录下执行 就可以使用自带的命令行客户端操作mysql服务器 
mysql -uroot  //因为我们初始化的时候没配密码 直接这样就好


//切换数据库
use mysql;
 
//修改root用户的密码为123456，根据需要自己设置
alter user 'root'@localhost identified by '123456';
```



![mysql](img\sql-install4.png)

![mysql](img\sql-install5.png)

![mysql](img\sql-install6.png)

后面我们就用一些客户端像navicat,sqlyong...都可以 操作我们的数据库服务器，比命令行要舒服多了。

那些客户端安装基本都是下一步，我就偷个懒不写了。 我用的是navicat





## 2.简单得jdbc教程 

**如果看过黑马57期的jdbc这步也可以跳过直接看第三步我们的'小项目'吧**

### 1.jdbc简介

​		**Java数据库连接,（Java Database Connectivity，简称JDBC）**是[Java语言](https://baike.baidu.com/item/Java语言)中用来规范客户端程序如何来访问数据库的[应用程序接口](https://baike.baidu.com/item/应用程序接口/10418844)，提供了诸如查询和更新数据库中数据的方法。JDBC也是Sun Microsystems的商标。我们通常说的JDBC是面向关系型数据库的。

​		**JDBC本质**：其实是官方（sun公司）定义的一套操作所有关系型数据库的规则，即接口。各个数据库厂商去实现这套接口，提供数据库驱动jar包。我们可以使用这套接口（JDBC）编程，真正执行的代码是驱动jar包中的实现类。

### 1.java使用jdbc操控mysql的步骤

```
	1. 导入驱动jar包 mysql-connector-java-8.0.19.jar
			1.复制mysql-connector-java-8.0.19.jar到项目的libs目录下
			2.右键-->Add As Library
	如果有可能版本的错误把jar包换成mysql-connector-java-5.1.37-bin.jar试试，我印象中jar包是可以向下兼容的，至少我的5.7版本的mysql服务器用这个8的jar包也没问题。
	2. 注册驱动
	3. 获取数据库连接对象 Connection
	4. 定义sql
	5. 获取执行sql语句的对象 Statement
	6. 执行sql，接受返回结果
	7. 处理结果
	8. 释放资源
```

#### 1.先新建一个数据库

```mysql
#创建数据库test并且设置编码和排序规则为utf8 对于编码有疑问的可以自行百度mysql中utf和utf8mb4的区别
create database if not exists test default character set utf8mb4 collate utf8mb4_unicode_ci;
#使用新建的test数据库
use test;
#创建表group1 有sid、sname、sage三个字段
create table if not exists `group1`(
	`sid` varchar(11) primary key comment'主键-学号',
	`sname` varchar(30) comment'姓名',
	`sage` int(3) default 18 comment'年龄 默认为18'
);
insert into group1 values('20181101152', 'zs', 18), ('20181101051', 'ls', 18);
```

#### 2.下面我们随意新建一个java工程体验一下  

1.建好以后自己新建libs目录然后导入jar包后右键->Add As Library

2.新建一个测试类Test.java

![](img\jdbc1.png)

下面是真正的代码操作了  单表的简单的增、删、改、查 ---  举例而已  有的有其他写法

我这里不写createStatement()产生Statement对象的写法了 --这个有sql注入的风险

这里直接用prepareStatement(String sql)产生PreparedStatement对象的写法

有兴趣的可以自己去试试

####  3.增

PreparedStatement对象动态设置参数时可以指定类型setXxx(index, value);

或者粗暴的setObject(index, value);因为万物皆对象

这段代码是向数据库test的group1表添加一条记录('20181101153', '张三' ,15)

```java
public static void main(String[] args) throws Exception{
        //1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取数据库连接对象
        //参数分别为url:  语法：jdbc:mysql://ip地址(域名):端口号/数据库名称(?参数)
    	//数据库的地址?后拼接参数mysql8这个数据库需要设置时区，或者直接修改数据库的属性时区
        //user:用户名  password:密码
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai", "root", "123456");
        //3.定义sql语句
        String sql = "insert into group1(sid, sname, sage) values(?, ?, ?)";
        //4.获取执行sql的对象 PrepareStatement
        PreparedStatement pstm = conn.prepareStatement(sql);
		//pstm.setString(1, "20181101153");
    	//pstm.setString(2, "张三");
		//pstm.setInt(3, 15);
        pstm.setObject(1, "20181101154");
        pstm.setObject(2, "张三");
        pstm.setObject(3, 15);
        //5.执行sql 获得结果  增删改的结果都是影响的行数
        int count = pstm.executeUpdate();
        //6.处理结果
        System.out.println(count);
        //7.释放资源
        pstm.close();
        conn.close();
    }
```

#### 4.删 

这段代码是在数据库test的group1表根据学号删除刚才添加的张三

```java
public static void main(String[] args) throws Exception{
        //1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取数据库连接对象
        //参数分别为url:数据库的地址?后拼接参数mysql8这个数据库需要设置时区，或者直接修改数据库的属性时区
        //user:用户名  password:密码
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai", "root", "123456");
        //3.定义sql语句
        String sql = "delete from group1 where sid=?";
        //4.获取执行sql的对象 PrepareStatement
        PreparedStatement pstm = conn.prepareStatement(sql);
    	//pstm.setString(1, "20181101153");
        pstm.setObject(1, "20181101154");
        //5.执行sql 获得结果  增删改的结果都是影响的行数
        int count = pstm.executeUpdate();
        //6.处理结果
        System.out.println(count);
        //7.释放资源
        pstm.close();
        conn.close();
}
```

#### 5.改

这段代码是在数据库test的group1表根据学号修改用户名

```java
    public static void main(String[] args) throws Exception{
        //1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取数据库连接对象
        //参数分别为url:数据库的地址?后拼接参数mysql8这个数据库需要设置时区，或者直接修改数据库的属性时区
        //user:用户名  password:密码
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai", "root", "123456");
        //3.定义sql语句
        String sql = "update group1 set sname=? where sid=?";
        //4.获取执行sql的对象 PrepareStatement
        PreparedStatement pstm = conn.prepareStatement(sql);
        //pstm.setString(1, "李四");
        //pstm.setString(2, "20181101152");
        pstm.setObject(1, "李四");
        pstm.setObject(2, "20181101152");
        //5.执行sql 获得结果  增删改的结果都是影响的行数
        int count = pstm.executeUpdate();
        //6.处理结果
        System.out.println(count);
        //7.释放资源
        pstm.close();
        conn.close();
    }
```



增、删、改大家通过这三段代码应该能看出来这是一样的套路，而且**数据操纵语言DML**

1) 插入：INSERT
2) 更新：UPDATE
3) 删除：DELETE   这就是归到一类的

#### 6.查

**数据查询语言DQL** 它单独是一类，其实就是select语句 各种select这也是sql语句中最复杂的，最有学问的。

这里我也是只给大家一个最简单示例 --道理都一样

这里我以年龄为条件构造的查询，从结果集中遍历数据 三种遍历方式(这么说其实不止三种，因为getObject(param)参数也可以是字段名)大家理解就好,写法很灵活。

```java
public static void main(String[] args) throws Exception{
    //1.注册驱动
    Class.forName("com.mysql.jdbc.Driver");
    //2.获取数据库连接对象
    //参数分别为url:数据库的地址?后拼接参数mysql8这个数据库需要设置时区，或者直接修改数据库的属性时区
    //user:用户名  password:密码
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai", "root", "123456");
    //3.定义sql语句
    String sql = "select * from group1 where sage=?";
    //4.获取执行sql的对象 PrepareStatement
    PreparedStatement pstm = conn.prepareStatement(sql);
    //pstm.setObject(1, 18);
    pstm.setInt(1, 18);
    //5.执行sql 获得结果集合,封装着我们查到的数据
    ResultSet  rs= pstm.executeQuery();
    //6.处理结果 -- 遍历输出 一个next()里就是一行
    while(rs.next()){
        //1.
        //String sid = (String) rs.getObject(1);
        //String sname = (String) rs.getObject(2);
        //int sage = (int) rs.getObject(3);
        //2.
        //String sid = rs.getString(1);
        //String sname = rs.getString(2);
        //int sage = rs.getInt(3);
        //3.
        String sid = rs.getString("sid");
        String sname = rs.getString("sname");
        int sage = rs.getInt("sage");
        System.out.println(sid+"-"+sname+"-"+sage);
    }
    //7.释放资源 注意这里ResultSet也需要关闭释放
    rs.close();
    pstm.close();
    conn.close();
}
```

#### 7.JDBCUtils

​		上面我们知道了简单的增删该查的写法，但是问题很明显，那么写太累了，而且重复性代码很明显很多，这显然是'真'码农才会干的事。为了抽象出数据库的连接以及对增删改的这一类的抽象，还有对查询的抽象，我们就会定义一个工具类,工具类其实就是对公共代码的抽离，目的就是简化书写。

​		工具类一般都是可以拿来就用的，也就是说，我们并不想修改修改代码的情况下，这个类可以，在各种简单的项目中使用。(当然我们现在是在理解思想，不是真的在造轮子，只是那么个意思意思)

​		首先我们就要做到驱动的连接可以通用，那么驱动的四个必要的参数首先就要分离出来(轮子的首要任务就是可以拿来就用，不需要修改代码)

​		这里我们使用一个约定俗成的命名配置类jdbc.properties，properties文件是java中支持的专门用来做配置

文件中一行一个key=value这种形式的配置

在src目录下新建jdbc.properties配置文件,我这里的内容如下，这里的url省略了ip/域名哪里，就是连接本地的时候127.0.0.1/localhost可以省略

```properties
url=jdbc:mysql:///test?serverTimezone=Asia/Shanghai
user=root
password=123456
driver=com.mysql.jdbc.Driver
```

```java
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
```

这个就是我们封装简单的JDBCUtils，麻雀挺小，而且这个五脏好像也不太全，但是够大概封装工具的意思已经表达出来了。

#### 8.用JDBCUtils重写上述语句

代码已经显然简单多了，而且还挺通用的。

Test.java的代码，我觉得稍微精简点了

```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Test {
    public static void main(String[] args) throws Exception{
       Connection conn = JDBCUtils.getConnection();
        PreparedStatement psmt = null;

        //增
        String sql = "insert into group1 values(?,?,?)";
        Object[] objs = new Object[]{"20181101153", "李四", 15};
        psmt = conn.prepareStatement(sql);
        int count = JDBCUtils.executeUpdate(psmt, objs);
        if(count > 0) System.out.println("添加成功");

        //删
        sql = "delete from group1 where sid=?";
        objs = new Object[]{"20181101153"};
        psmt = conn.prepareStatement(sql);
        count = JDBCUtils.executeUpdate(psmt, objs);
        if(count > 0) System.out.println("删除成功");

        //改
        sql = "update group1 set sname=? where sid=?";
        objs = new Object[]{"ww", "20181101152"};
        psmt = conn.prepareStatement(sql);
        count = JDBCUtils.executeUpdate(psmt, objs);
        if(count > 0) System.out.println("修改成功");

        //查
        sql = "select * from group1 where sage=?";
        objs = new Object[]{18};
        psmt = conn.prepareStatement(sql);
        ResultSet rs = JDBCUtils.executeQuery(psmt, objs);
        while(rs.next()){
            System.out.println(rs.getObject(1)+"-"+
                    rs.getObject(2)+"-"+
                    rs.getObject(3));
        }

        //最终释放资源
        JDBCUtils.closeResource(rs, psmt, conn);
    }
}
```

运行结果

![test](img\test.png)

## 3.小例子

### 0.前置软件

-- **Tomcat** 一个免费的web服务器用来部署我们的web应用

这里我就偷个懒了https://blog.csdn.net/weixin_42109012/article/details/94383001

上面自己写个mysql安装还挺费劲的。。这里我就给你们个scdn链接我看着挺对的，应该没毛病也挺新的，感谢这位博主，我们只需要安装好就行了(应该连环境变量都不需要配，但是最好还是配上也不难)。

**常用的WEB服务器**

    WEB服务器也称为WWW服务器、HTTP服务器，其主要功能是提供网上信息浏览服务。Unix和Linux平台下常用的服务器有Apache、Nginx、Lighttpd、Tomcat、IBM WebSphere等，其中应用最广泛的是Apache。而Window NT/2000/2003平台下最常用的服务器是微软公司的IIS。




图书管理系统(超精简版。。)

这确实只是个小例子，但是对于初学者理解前后台的交互还是挺有帮助的

### 1.建数据库

```mysql
create database if not exists book;
use book;
CREATE TABLE if not exists `book` (
  `bid` int(9) NOT NULL AUTO_INCREMENT COMMENT'图书编号',
  `bname` varchar(255) COMMENT'书名',
  `author` varchar(255) COMMENT'作者',
  `category` varchar(255) COMMENT'分类',
  `description` varchar(255) COMMENT'简介',
  PRIMARY KEY (`bid`) #设置bid为主键
);
insert into book (bid, bname,author,category,description) values 
(1,'三国演义','罗贯中','文学','一个杀伐纷争的年代')
,(2,'水浒传','施耐庵','文学','108条好汉的故事')
,(3,'西游记','吴承恩','文学','佛教与道教的斗争')
,(4,'红楼梦','曹雪芹','文学','一个封建王朝的缩影')
,(5,'天龙八部','金庸','文学','武侠小说');
```

### 2.创建原生的javaweb项目

![](img\example1.png)

然后next，给项目命名 这里我命名为BookManager

然后你就可以看到原生javaweb项目的目录结构

![](img\example2.png)

相比普通java项目这里多了一个web目录，这个web目录放的东西可以有html页面,js文件,css文件，图片等静态资源，还有jar包一般我们也放到WEB-INF下新建的一个libs目录下。

它还默认的有一个jsp文件，但是我不想写jsp你们也可以了解一下,我也不建议你们学，毕竟这玩意确实是过时，替代方法有很多可以用各种模板引擎(thymeleaf等)。

**这里我贴一张程序员前辈的一篇简短博客**

![](img\example3.png)

但是我们这里呢，因为实在是简单的没有必要去使用各种其他的技术来简化操作了，我就直接用html+jQuery了，

jquery还是可以学学的，很多第三方库、框架也是jQuery的基础上写的，jQuery就是简化原生js代码的库而且还帮我们实现了兼容浏览器，挺好用的。

### 3.配置tomcat启动

![](img\example4.png)

然后随意选一个版本启动

![](img\example5.png)

![](img\example6.png)

![](img\example7.png)

图中的Application context是我们的项目根路径

就是当我们请求的时候可以在浏览器请求http:localhost:8080/book就是我们的项目路径了

然后点ok就将tomcat作为我们的web容器了

### 4.编写后端代码

#### 0.设置字符集

防止可能的各种乱码--也许还是会有问题

![](img\example9.png)

#### 1.搭建后端骨架

![](img\example8.png)

其中jdbc.properties文件是直接放在src目录下的，注意修改url的数据库名

util下的JDBCUtils直接使用我们之前写好的就ok。

目录介绍：

因为我们只写一个简单的对于书籍的增删改查所以目录看起来反而复杂，但是当规模大的时候，这样组织结构思路还是会很清晰。

com.group1.book这个路径是说我们的业务和组织。

controller - 控制器层，这里是和前端的请求对接的处理前端请求并返结果

dao - 持久化层，是对于我们数据库的操作，对应基本的增删改查操作BookDao是接口，定义标准的数据库操作方法，对应一个或多个Impl实现类

 model - 模型层，定义了操作的实体类型

service - 业务曾 处理主要的复杂的逻辑 一个BookService接口，定义标准的业务方法，对应一个或多个Impl实现类

util - 一些工具类 如我们现在的JDBCUtils

filter - 过滤器 过滤器实际上就是对web资源进行拦截，做一些处理后再交给下一个过滤器或servlet处理
通常都是用来拦截request进行处理的，也可以对返回的response进行拦截处理

web下的WEB-INF/lib 用来整合一些第三方jar包 这里我引入了jdbc、javax.servlet、fast-json。

javax.servlet:Servlet是一种[服务器端](https://www.baidu.com/s?wd=服务器端&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao)的Java应用程序，具有独立于知平台和协议的特性,可以生成动态的Web页面。 它担当客户请求（[Web浏览器](https://www.baidu.com/s?wd=Web浏览器&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao)或其他HTTP客户程序）与服务器响应道（HTTP服务器上的数据库或应用程序）的中间层。 Servlet是位于Web 服务器内部的专[服务器端](https://www.baidu.com/s?wd=服务器端&tn=SE_PcZhidaonwhc_ngpagmjz&rsv_dl=gh_pc_zhidao)的属Java应用程序，与传统的从命令行启动的Java应用程序不同，Servlet由Web服务器进行加载，该Web服务器必须包含支持Servlet的Java虚拟机。

fast-json:JSON，全称：JavaScript Object Notation，作为一个常见的轻量级的数据交换格式，应该在一个程序员的开发生涯中是常接触的。简洁和清晰的层次结构使得 JSON 成为理想的数据交换语言。 易于人阅读和编写，同时也易于机器解析和生成，并有效地提升网络传输效率。阿里官方给的定义是， fastjson 是阿里巴巴的开源JSON解析库，它可以解析 JSON 格式的字符串，支持将 Java Bean 序列化为 JSON 字符串，也可以从 JSON 字符串反序列化到 JavaBean。

#### 2.实体类

```java
package com.group1.book.model;

public class Book {
    Integer bid;
    String bname;
    String author;
    String category;
    String description;
    //省略构造 无参和全参
    //省略geter seter
}
```

#### 3.数据库Dao层

##### 1.接口BookDao

```java
package com.group1.book.dao;

import com.group1.book.model.Book;

import java.util.List;

public interface BookDao {
    List<Book> selectAll(); //查询全部

    Book selectById(int id); //用编号查询

    int deleteById(int id); //通过id删除

    int addBook(Book book); //添加图书

    int updateById(Book book); //通过id更新
}
```

##### 2.实现BookDaoImpl

```java
package com.group1.book.dao;

import com.group1.book.model.Book;
import com.group1.book.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    Connection conn; //维护一个数据库连接
    PreparedStatement pstm; //预处理sql对象
    ResultSet rs; //结果集
    @Override
    public List<Book> selectAll() {
        List<Book> books = new ArrayList<>();
        String sql = "select * from book";
        try {
            conn = JDBCUtils.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = JDBCUtils.executeQuery(pstm, null);
            while(rs.next()){
                Book book = new Book(rs.getInt(1),rs.getString(2),rs.getString(3)
                ,rs.getString(4),rs.getString(5));//构建对象
                books.add(book); //添加
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(rs, pstm, conn); //释放资源
        }
        return books;
    }

    @Override
    public Book selectById(int id) {
        Book book = null;
        String sql = "select * from book where bid=?";
        Object[] objects = new Object[]{id};
        try {
            conn = JDBCUtils.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = JDBCUtils.executeQuery(pstm, objects);
            while(rs.next()){
                book = new Book(rs.getInt(1),rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5));//构建对象

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(rs, pstm, conn); //释放资源
        }
        return book;
    }

    @Override
    public int deleteById(int id) {
        String sql = "delete from book where bid=?";
        int count = 0;
        Object[] objects = new Object[]{id};
        try {
            conn = JDBCUtils.getConnection();
            pstm = conn.prepareStatement(sql);
            count = JDBCUtils.executeUpdate(pstm, objects);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(rs, pstm, conn); //释放资源
        }
        return count;
    }

    @Override
    public int addBook(Book book) {
        String sql = "insert into book(bname, author, category, description)values(?,?,?,?)";
        int count = 0;
        Object[] objects = new Object[]{book.getBname(), book.getAuthor(), book.getCategory(), book.getDescription()};
        try {
            conn = JDBCUtils.getConnection();
            pstm = conn.prepareStatement(sql);
            count = JDBCUtils.executeUpdate(pstm, objects);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(rs, pstm, conn); //释放资源
        }
        return count;
    }

    @Override
    public int updateById(Book book) {
        String sql = "update book set bname=?, author=?, category=?, description=? where bid=?";
        int count = 0;
        Object[] objects = new Object[]{book.getBname(), book.getAuthor(), book.getCategory(), book.getDescription(), book.getBid()};
        try {
            conn = JDBCUtils.getConnection();
            pstm = conn.prepareStatement(sql);
            count = JDBCUtils.executeUpdate(pstm, objects);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(rs, pstm, conn); //释放资源
        }
        return count;
    }
}

```

#### 4.Service层

##### 1.接口Service

```java
public interface BookService {
    List<Book> selectAll(); //查询全部

    Book selectById(int id); //用编号查询

    int deleteById(int id); //通过id删除

    int addBook(Book book); //添加图书

    int updateById(Book book); //通过id更新
}
```

##### 2.实现ServiceImpl

```java
public class BookServiceImpl implements BookService {

    BookDao bookDao = new BookDaoImpl();

    @Override
    public List<Book> selectAll() {
        return bookDao.selectAll();
    }

    @Override
    public Book selectById(int id) {
        return bookDao.selectById(id);
    }

    @Override
    public int deleteById(int id) {
        return bookDao.deleteById(id);
    }

    @Override
    public int addBook(Book book) {
        return bookDao.addBook(book);
    }

    public int updateById(Book book) {
        return bookDao.updateById(book);
    }


}
```

#### 5.控制层

控制层就是处理web前端请求，返回处理结果

既然要处理网页发来的请求首先就要有一个请求地址 

@WebServlet("/manager")这个类上的注解就是表明该Servlet的请求地址为它指定的value前面再加上项目的根

即 http://localhost:8080/book/manager这个请求地址就会请求到这个Servlet

而一个Servlet想要处理请求首先要声明它是一个Servlet也就是它需要有处理请求的能力

这里我们让这个类继承HttpServlet(我们引入的javax.servlet.jar里面的)，也有一些其他可以声明这个类是Servlet，感兴趣的可以自己百度。

对于一个基本的Servlet我们想让他处理我们的请求，就要定义相关的方法

这里我们重写父类的两个方法doGet(req, resp)和doPost(req, resp)

这两个方法分别可以处理GET请求和POST请求

这里我们再用个小技巧在doPost里调用doGet，也就是说，我们可以用一套逻辑对GET请求和POST请求

//浏览器的默认请求方式是GET

在这里解释一下我写的doGet的逻辑：

即我们向服务器发起请求的时候，我通过你的请求参数里判断有没有method这个属性，然后取出method属性的值，和我定义好的一套逻辑比较，比较上了就调用相关的方法，都没匹配上，默认是在主页，也就是请求图书列表返回给前台

例：浏览器请求 http://localhost:8080/book?method=getById&bid=1

?后面的就是我们的Get请求参数    参数之间拼接用&每个参数都是key=value的形式

这里的method取出后，值为getById，然后调用相应的getById(req, resp);方法 通过fastjson将java对象转化成json字符串的形式然后返回给前台

//前台界面接受到json数据后可以JSON.parse将它解析为js对象，然后使用其中的数据

```java
@WebServlet("/manager")
public class BookServlet extends HttpServlet {

    BookService bookService = new BookServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        System.out.println(method);
        if("add".equals(method)){
            addBook(req, resp);
        }else if("update".equals(method)){
            updateById(req, resp);
        }else if("delete".equals(method)){
            deleteBook(req, resp);
        }else if("getById".equals(method)){
            getById(req, resp);
        }else {
            getAll(req, resp);
        }
    }

    private void updateById(HttpServletRequest req, HttpServletResponse resp) {
        Book book = new Book(Integer.parseInt(req.getParameter("bid")),req.getParameter("bname"),
                req.getParameter("author"), req.getParameter("category"),
                req.getParameter("desc"));
        bookService.updateById(book);
        try {
            req.getRequestDispatcher("index.html").forward(req, resp); //转发到
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    private void getById(HttpServletRequest req, HttpServletResponse resp) {
        PrintWriter writer = null;
        try {
            int bid = Integer.parseInt(req.getParameter("bid"));
            Book book = bookService.selectById(bid);
            writer = resp.getWriter();
            writer.print(JSON.toJSON(book));
        }catch (NumberFormatException | IOException e){
            e.printStackTrace();
        }finally {
            if(writer != null) writer.close();
        }
    }

    private void deleteBook(HttpServletRequest req, HttpServletResponse resp) {
        try {
            int bid = Integer.parseInt(req.getParameter("bid"));
            bookService.deleteById(bid);
            resp.sendRedirect("index.html");
        }catch (NumberFormatException | IOException e){
            e.printStackTrace();
        }
    }

    private void addBook(HttpServletRequest req, HttpServletResponse resp) {
        Book book = new Book(0,req.getParameter("name"),
                req.getParameter("author"), req.getParameter("category"),
                req.getParameter("desc"));

        bookService.addBook(book);
        try {
            resp.sendRedirect("index.html"); //重定向到首页
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAll(HttpServletRequest req, HttpServletResponse resp){
        List<Book> books = bookService.selectAll();
        System.out.println(books);
        String books_json = JSON.toJSONString(books);
        PrintWriter writer = null;
        System.out.println(books_json);
        try {
            writer = resp.getWriter();
            writer.print(books_json);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(writer != null) writer.close();
        }
    }
}
```

#### 6.filter - 过滤器

过滤器 - 顾名思义就是对某些资源进行过滤的操作

Filter称之为过滤器，是用来做一些拦截的任务， 在Servlet接受请求之前，做一些事情，如果不满足限定，可以拒绝进入Servlet

定义一个过滤器和servlet类似

首先通过一个注解@WebFilter("/*") //表明需要它过滤的地址 /* *代表所有地址

声明这是一个过滤器需要基础Filter接口

继承接口就要实现接口中定义的方法   这里需要重新三个方法 init、doFilter、destory

init、destory就是在程序创建和销毁的时候分别调用一次 

doFilter就是我们的真正的过滤方法了  这个过滤拦截的资源其实就是request和response即请求和响应

若要方法不被拦截在这里就必须调用filterChain.doFilter(request, response);调用下一个过滤器或者是Servlet

这里我们定义的过滤所有请求的过滤器，是为了防止乱码的发生，对于request和response都将期字符集设置为utf-8

```java
package com.group1.book.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*") //过滤所有请求 设置字符集
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
```

#### 7.web.xml

web.xml的作用其实是和我们前面的两个注解@WebServlet("/manager")  @WebFilter("/*")的作用是一样的，标明我们的请求路径该有那些类处理，具体的配置如果有兴趣用web.xml也请自行百度。(注解还是舒服啊。)

### 5.前端代码

前端的代码是没啥难度的，就是要学点jquery

首先在web目录下新建js文件夹将jquery放进去

然后编写我们的三个页面web下的index.html， index.html/index.jsp是默认放在web根下的，当项目启动的适合默认就是找index这个文件打开。

然后是在web下新建page目录存放addBook.html和editBook.html

下面是代码

#### 1.index.html

```html
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <title>图书管理系统</title>
    <style>
        table {
            border-collapse: separate;
            border-spacing: 5px;
            table-layout: fixed;
        }

        table td {
            width: 150px;
            height: 50px;
            text-align: center;
            background-color: lightgreen;
        }
    </style>
</head>
<body style="text-align: center">
<div style="width: 50%; margin: 100px auto 0">
    <div>
        <table cellpadding="1" cellspacing="1">
            <caption align="top">图书管理系统<a href="/book/page/addBook.html">添加图书</a></caption>
            <thead>
                <tr>
                    <th>编号</th>
                    <th>名称</th>
                    <th>作者</th>
                    <th>分类</th>
                    <th>描述</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody id="mainContent">
            </tbody>
        </table>
    </div>
</div>
</body>
<script src="js/jquery-1.8.3.min.js"></script>
<script>
    $(window).load(()=>{
        $.ajax({
            url: "/book/manager",
            type: "get",
            success: function(data) {
                console.log(data);
                let books = JSON.parse(data);
                let html = '';
                for (let i = 0; i < books.length; i++){
                    html+="<tr><td>"+books[i].bid+"</td><td>"+books[i].bname+"</td><td>"+books[i].author+"</td>\n" +
                        "<td>"+books[i].category+"</td>\n" +
                        "<td>"+books[i].description+"</td><td><a href=\"/book/page/editBook.html?bid="+books[i].bid+"\">修改</a>|<a href=\"/book/manager?method=delete&bid="+books[i].bid+"\">删除</a></td></tr>"
                }
                $('#mainContent').html(html);
            }
        });
    })
</script>
</html>
```

#### 2.addBook.html

```html
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>图书管理系统-修改图书</title>
</head>
<script src="../js/jquery-1.8.3.min.js"></script>
<body>
<div style="width:500px; margin: 150px auto 0">
    <div>修改图书</div>
    <form action="/book/manager?method=update" method="post">
        <input type="hidden" name="bid" value="">
        名称：<input type="text" name="bname" value=""><br>
        作者：<input type="text" name="author" value=""><br>
        分类：<input type="text" name="category" value=""><br>
        描述：<input type="text" name="desc" value=""><br>
        <input type="submit" value="提交">
    </form>
</div>
</body>
<script>
    //获取url中的参数
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg); //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
    $(window).load(()=>{
        $.ajax({
            url: "/book/manager?method=getById&bid="+getUrlParam("bid"),
            type: "get",
            success: function(data) {
                console.log(data);
                let book = JSON.parse(data);
                let inputs = document.getElementsByTagName("input");
                inputs[0].value = book.bid;
                inputs[1].value = book.bname;
                inputs[2].value = book.author;
                inputs[3].value = book.category;
                inputs[4].value = book.description;
            }
        });
    })
</script>
</html>
```

#### 3.editBook.html

```html
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>图书管理系统-添加图书</title>
</head>
<body>
<div style="width:500px; margin: 150px auto 0">
    <div>添加图书</div>
    <form action="/book/manager?method=add" method="post" >
        名称：<input type="text" name="name"><br>
        作者：<input type="text" name="author"><br>
        分类：<input type="text" name="category"><br>
        描述：<input type="text" name="desc"><br>
        <input type="submit" value="提交">
    </form>
</div>
</body>
</html>
```

## 4.总结

各位如果能把这个做完，我觉得也算是入了javaweb的基础了

现在的javaweb也就是来写一些熟悉底层的小例子来打基础了吧，我觉得应该没有几个会头铁的放着简易的框架不用，一直用这玩意。

这也是我们java后台的过度阶段，直接学框架不是不行，但是一点下面的东西也不懂很难往高处发展

学到这里也就是web的开端，后面的东西还有好多(真的好多) 

前面的基础还有多线程、文件IO、网络IO、集合框架..

后面继续学的话可以把web的几个其他的东西用一用 像Cookie、Session、Listener...等等

然后可以学数据库层面的框架ORM（Mybatis等）、还有Spring（控制反转（ IoC ）和面向切面（ AOP ））、SpringMVC（分离了 [控制器 ](http://baike.baidu.com/view/122229.htm)、模型 [对象 ](http://baike.baidu.com/view/2387.htm)、分派器以及处理程序对象的角色）这三就是SSM..

就说到这吧  重要的还是基础 再往上的东西真的在简单应用的层面上难度比这些原生的小多了，抄文档都可以起飞

我们的时间还算比较充裕的，你们还多我一年呢

加油吧。

