package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.UuidUtil;
import cn.itcast.travel.util.mail.SendEmail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet
{
    UserService userService = new UserServiceImpl();

    /**
     * 用户注册
     * @param request
     * @param response
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ResultInfo rst = new ResultInfo();

        String code = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        System.out.println(checkcode_server);
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        if (!(code.equalsIgnoreCase(checkcode_server)))
        {
            rst.setFlag(false);
            rst.setErrorMsg("验证码错误！");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(rst);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        try
        {
            User user = new User();
            Map<String, String[]> parameterMap = request.getParameterMap();
            BeanUtils.populate(user, parameterMap);
            user.setCode(UuidUtil.getUuid());
            user.setStatus("n");
            rst = userService.registUser(user);
            if (rst.isFlag())
            {
                SendEmail.sendEmailMessage(user.getEmail(), user.getCode());
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(rst);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 用户登录
     * @param request
     * @param response
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        ResultInfo resultInfo = new ResultInfo();
        try
        {
            String code_login = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
            request.getSession().removeAttribute("CHECKCODE_SERVER");
            String code = request.getParameter("check");
            System.out.println(code);
            System.out.println(code_login);

            if (!(code.equalsIgnoreCase(code_login)))
            {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("验证码错误！");
                System.out.println("验证码错误");
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(resultInfo);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(json);
                return;
            }

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            System.out.println(username+" "+password);
            resultInfo = userService.loginUser(user);
            if(resultInfo.isFlag())
            {
                request.getSession().setAttribute("user", user);
            }


            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(resultInfo);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 寻找已经登陆过的用户信息
     * @param request
     * @param response
     * @throws IOException
     */
    public void findLoginedUser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        User user = (User) request.getSession().getAttribute("user");
        ResultInfo resultInfo = new ResultInfo();
        if (user != null)
        {
            resultInfo.setFlag(true);
            resultInfo.setData(user.getUsername());
        }
        else
        {
            resultInfo.setFlag(false);
            resultInfo.setData(null);
        }
//        User user1 = (User) (resultInfo.getData());
//        System.out.println(user1.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),resultInfo);
    }

    /**
     * 激活用户
     * @param request
     * @param response
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String email = request.getParameter("email");
        String validateCode = request.getParameter("validateCode");

        String msg = null;
        if (userService.activeUser(email, validateCode))
        {
            msg = "激活成功，请<a href='login.html'>登录</a>";
        }
        else
        {
            msg = "激活失败，请联系管理员处理";
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }


    /**
     * 用户退出
     * @param request
     * @param response
     * @throws IOException
     */
    public void quit(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
}
