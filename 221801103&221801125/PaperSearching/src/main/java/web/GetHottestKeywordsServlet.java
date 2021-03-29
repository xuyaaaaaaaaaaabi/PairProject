package web;

import service.impl.Paperserviceimpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GetHottestKeywordsServlet", value = "/GetHottestKeywordsServlet")
public class GetHottestKeywordsServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HashMap<String,Integer> hashMap=new Paperserviceimpl().GetHottestKeywords();
        List<String> strings=new ArrayList<>();
        for(Map.Entry<String,Integer> entry:hashMap.entrySet())
        {
            strings.add(entry.getKey());
        }
        response.getWriter().print(strings);
    }
}
