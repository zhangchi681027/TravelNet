package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao
{
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public List<User> findUser(String username)
    {
        List<User> userList = null;
        try
        {
            String sql = "select * from tab_user where username=?";
            userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class), username);        }
        catch (Exception e)
        {

        }
        return userList;
    }

    @Override
    public List<User> findPhone(String phoneNumber)
    {
        List<User> userList = null;
        try
        {
            String sql = "select * from tab_user where telephone=?";
            userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class), phoneNumber);
        }
        catch (Exception e)
        {

        }
        return userList;
    }

    @Override
    public List<User> findEmail(String email)
    {
        List<User> userList = null;
        try
        {
            String sql = "select * from tab_user where email=?";
            userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class), email);
        }
        catch (Exception e)
        {

        }
        return userList;
    }

    @Override
    public boolean registUser(User user)
    {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        int i = jdbcTemplate.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());

        return i>0?true:false;
    }

    @Override
    public boolean activeUserByEmailAndCode(String email, String code)
    {
        String sql = "update tab_user set status='y' where email=? and code=?";
        int i = jdbcTemplate.update(sql, email, code);
        return i>0?true:false;
    }
}
