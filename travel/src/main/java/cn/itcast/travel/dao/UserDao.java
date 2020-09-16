package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao
{
    /**
     * 寻找数据库中是否已经有该User
     * @param Username
     * @return
     */
    public User findUser(String Username);

    /**
     * 注册用户
     * @param user
     * @return
     */
    public boolean registUser(User user);
}
