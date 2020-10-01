package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findUserServlet")
public class findUserServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("wojinlaile000");
        User user = (User) request.getSession().getAttribute("user");
        ResultInfo resultInfo = new ResultInfo();
        System.out.println("wojinlaile00000");
        if (user != null)
        {
            System.out.println("wojinlaile00000notnull");
            resultInfo.setFlag(true);
            resultInfo.setData(user);
        }
        else
        {
            System.out.println("wojinlaile00000null");
            resultInfo.setFlag(false);
            resultInfo.setData(null);
        }
//        User user1 = (User) (resultInfo.getData());
//        System.out.println(user1.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),resultInfo);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }
}
