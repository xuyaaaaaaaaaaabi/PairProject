package web;

import dao.UserDaoimpl;
import net.sf.json.JSONObject;
import pojo.User;
import service.impl.Userserviceimpl;
import utils.RequestToJson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UpdateUserInfoServlet", value = "/UpdateUserInfoServlet")
public class UpdateUserInfoServlet extends HttpServlet
{

    Userserviceimpl userserviceimpl=new Userserviceimpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user=new User();

        JSONObject requestJson= JSONObject.fromObject(
                RequestToJson.getRequestPostStr(request));

        user.setAccount(requestJson.getString("account"));
        user.setUsername(requestJson.getString("username"));
        user.setAddress(requestJson.getString("address"));
        user.setCompany(requestJson.getString("company"));
        user.setInfo(requestJson.getString("info"));

        userserviceimpl.UpdateInfo(user);

        response.getWriter().print(true);
    }
}
