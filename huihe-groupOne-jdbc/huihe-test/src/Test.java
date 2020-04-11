import java.sql.Connection;
import java.sql.PreparedStatement;

public class Test {
    public static void main(String[] args) throws Exception{
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement psmt = null;

        //增
        String sql = "insert into group1 values(?,?,?)";
        Object[] objs = new Object[]{"20181101153", "李四", 15};
        int count = JDBCUtils.executeUpdate(conn, psmt, sql, objs);
        if(count > 0) System.out.println("添加成功");
        JDBCUtils.closeResource(null, psmt, conn);
/*
        //删
        sql = "delete from group1 where sid=?";
        objs = new Object[]{"20181101153"};
        count = JDBCUtils.executeUpdate(conn, psmt, sql, objs);
        if(count > 0) System.out.println("删除成功");

        //改
        sql = "update group1 set sname=? where sid=?";
        objs = new Object[]{"候鑫杰", "20181101152"};
        count = JDBCUtils.executeUpdate(conn, psmt, sql, objs);
        if(count > 0) System.out.println("修改成功");

        //查
        sql = "select * from group1 where sage=?";
        objs = new Object[]{18};
        ResultSet rs = JDBCUtils.executeQuery(conn, psmt, sql, objs);
        while(rs.next()){
            System.out.println(rs.getObject(1)+"-"+
                    rs.getObject(2)+"-"+
                    rs.getObject(3));
        }

        //最终释放资源
        JDBCUtils.closeResource(rs, psmt, conn);*/
    }
}
