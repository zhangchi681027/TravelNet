package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;

public class UserServiceImpl implements UserService
{
    UserDao userDao = new UserDaoImpl();
    ResultInfo rst = new ResultInfo();
    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public ResultInfo registUser(User user)
    {
        User user_find = userDao.findUser(user.getUsername());
        if (user_find != null)
        {
            rst.setFlag(false);
            rst.setErrorMsg("用户名已经被注册");
        }
        else
        {
            boolean flag = userDao.registUser(user);
            if (flag)
            {
                rst.setFlag(true);
            }
            else
            {
                rst.setFlag(false);
                rst.setErrorMsg("注册失败");
            }
        }
        return rst;
    }
}
