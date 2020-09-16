package cn.itcast.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CharacterFilter", urlPatterns="/", dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST})
public class CharacterFilter implements Filter
{
    public void destroy()
    {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String method = request.getMethod();
        if (method.equalsIgnoreCase("post"))
        {
            request.setCharacterEncoding("utf-8");
        }

        response.setContentType("text/html;character=utf-8");

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException
    {

    }

}
