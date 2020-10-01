package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet
{
    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
