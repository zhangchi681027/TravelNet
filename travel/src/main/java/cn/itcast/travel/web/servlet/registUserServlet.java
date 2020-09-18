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

@WebServlet("/registUserServlet")
public class registUserServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ResultInfo rst = new ResultInfo();

        String code = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
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

        UserService userService = new UserServiceImpl();

        try
        {
            User user = new User();
            Map<String, String[]> parameterMap = request.getParameterMap();
            BeanUtils.populate(user, parameterMap);
            rst = userService.registUser(user);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(rst);
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


    public void checkEmail(HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println("nihaoya");
    }
}
