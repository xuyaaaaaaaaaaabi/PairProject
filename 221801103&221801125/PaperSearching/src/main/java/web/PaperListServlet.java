package web;

import com.mysql.cj.xdevapi.JsonArray;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pojo.Paper;
import service.impl.Paperserviceimpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PaperListServlet", value = "/PaperListServlet")
public class PaperListServlet extends HttpServlet
{
    Paperserviceimpl paperserviceimpl=new Paperserviceimpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] searchingString = (String[])JSONArray.fromObject
                (request.getAttribute("str")).toArray();
        int type=(int)request.getAttribute("type");
        String username=(String)request.getAttribute("username");

        List<JSONObject> jsonObjects=new ArrayList<>();
        List<Paper> papers=paperserviceimpl.GetPaperList(searchingString,type);
        for(Paper paper:papers)
        {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("title",paper.getTitle());
            jsonObject.put("author",paper.getAuthors());
            jsonObject.put("keyword",paper.getKeywords());
            jsonObject.put("info",paper.getTheabstract());
            jsonObject.put("iscollect",paperserviceimpl.IsCollected(username,paper.getTitle()));
        }
        response.getWriter().print(jsonObjects);
    }
}
