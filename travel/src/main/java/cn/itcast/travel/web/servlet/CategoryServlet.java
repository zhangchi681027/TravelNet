package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.rngom.parse.host.Base;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet
{
    private CategoryService categoryService = new CategoryServiceImpl();

    /**
     * 加载category列表
     * @param request
     * @param response
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        List<Category> allCategory = categoryService.findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(), allCategory);
    }
}
