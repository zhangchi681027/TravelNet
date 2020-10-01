package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;

import java.util.List;

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
        List<User> username_find = userDao.findUser(user.getUsername());
        List<User> userphone_find = userDao.findPhone(user.getTelephone());
        List<User> useremail_find = userDao.findEmail(user.getEmail());
        if (username_find.size() != 0)
        {
            rst.setFlag(false);
            rst.setErrorMsg("用户名已经被注册");
        }
        else if (useremail_find.size() != 0)
        {
            rst.setFlag(false);
            rst.setErrorMsg("邮箱已经被注册");
        }
        else if (userphone_find.size() != 0)
        {
            rst.setFlag(false);
            rst.setErrorMsg("手机号已经被注册");
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

    @Override
    public boolean activeUser(String email, String code)
    {
        return userDao.activeUserByEmailAndCode(email, code);
    }

    @Override
    public ResultInfo loginUser(User user)
    {
        ResultInfo rst = new ResultInfo();
        User s = userDao.loginUser(user);
        if (s == null)
        {
            rst.setFlag(false);
            rst.setErrorMsg("用户名或密码输入有误！");
        }
        else if (s.getStatus().equals("n"))
        {
            rst.setFlag(false);
            rst.setErrorMsg("用户未激活！请<a>激活</a>");
        }
        else if (s.getStatus().equals("y"))
        {
            rst.setFlag(true);
        }
        else
        {
            rst.setFlag(false);
            rst.setErrorMsg("未知错误！");
        }
        return rst;
    }
}
