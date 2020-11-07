package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet
{
    private RouteService routeService = new RouteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String pageSizeStr = request.getParameter("pageSize");
        String currentPageStr = request.getParameter("currentPage");
        String cidStr = request.getParameter("cid");

        int pageSize = 0;
        if (pageSizeStr==null || pageSizeStr.length()==0)
        {
            pageSize = 10;
        }
        else
        {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        int currentPage = 0;
        if (currentPageStr==null || currentPageStr.length()==0)
        {
            currentPage = 1;
        }
        else
        {
            currentPage = Integer.parseInt(currentPageStr);
        }

        int cid = 0;
        if (cidStr==null || cidStr.length()==0)
        {
            cid = 0;
        }
        else
        {
            cid = Integer.parseInt(cidStr);
        }

        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize);

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(), pb);
    }
}
