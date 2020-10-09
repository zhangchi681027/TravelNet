package cn.itcast.travel.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;

public class SqlTest {
    @Test
    public void testsql()
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet i = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/travel?characterEncoding=utf-8&serverTimezone=UTC", "root", "zhangchi");
            String sql = "select * from tab_user";
            ps = con.prepareStatement(sql);
            i = ps.executeQuery();

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            try {
                // 最后一定要记得在finally代码块中，尽快在执行完SQL语句之后，就释放数据库连接
                if (ps != null){
                    ps.close();
                }
                if (con !=null){
                    con.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        assertEquals(null, i);
    }
}
