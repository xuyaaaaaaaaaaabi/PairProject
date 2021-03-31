package web;

import com.mysql.cj.xdevapi.JsonArray;
import net.sf.json.JSONObject;
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
        response.setHeader("Access-Control-Allow-Origin", "*");
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        response.setHeader("Access-Control-Allow-Methods", "*");
        /* 重新预检验跨域的缓存时间 (s) */
        response.setHeader("Access-Control-Max-Age", "4200");
        /* 允许跨域的请求头 */
        response.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        response.setHeader("Access-Control-Allow-Credentials", "true");

        HashMap<String,Integer> hashMap=new Paperserviceimpl().GetHottestKeywords();

        List<JSONObject> jsonObjects=new ArrayList<>();
        for(Map.Entry<String,Integer> entry:hashMap.entrySet())
        {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("keyword",entry.getKey());
            jsonObject.put("count",entry.getValue());
            jsonObjects.add(jsonObject);
        }

        response.getWriter().print(jsonObjects);
    }
}
