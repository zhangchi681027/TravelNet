package cn.itcast.travel.service;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;

public interface UserService
{
    /**
     * 注册用户
     * @param user
     * @return
     */
    public ResultInfo registUser(User user);

    /**
     * 激活用户
     * @param email
     * @param code
     * @return
     */
    public boolean activeUser(String email, String code);
}
