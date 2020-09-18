package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

import java.util.List;

public interface UserDao
{
    /**
     * 寻找数据库中是否已经有该User
     * @param Username
     * @return
     */
    public List<User> findUser(String Username);

    /**
     * 寻找数据库中是否已经有该phone
     * @param phoneNumber
     * @return
     */
    public List<User> findPhone(String phoneNumber);


    /**
     * 寻找数据库中是否已经有该email
     * @param email
     * @return
     */
    public List<User> findEmail(String email);

    /**
     * 注册用户
     * @param user
     * @return
     */
    public boolean registUser(User user);
}
