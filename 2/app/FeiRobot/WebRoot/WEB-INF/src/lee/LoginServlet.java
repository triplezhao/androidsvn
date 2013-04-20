package lee;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.IOException;

/*
 * @author  yeeku.H.lee kongyeeku@163.com
 * @version  1.0
 * <br>Copyright (C), 2005-2008, yeeku.H.Lee
 * <br>This program is protected by copyright laws.
 * <br>Program Name:
 * <br>Date: 
 */
public class LoginServlet  extends HttpServlet
{
    public void service(HttpServletRequest request,HttpServletResponse response)
        throws IOException,ServletException
	{

        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        if (name == null || pass == null)
        {
            request.setAttribute("error" , "用户名和密码都不能为空");
            forward("/index.jsp" , request , response);
        }
        if (ChatService.instance().validLogin(name , pass))
        {
            request.getSession(true).setAttribute("user" , name);
            request.setAttribute("msg" , ChatService.instance().getMsg());
            forward("/chat.htm" ,request , response);
        }
        else
        {
            request.setAttribute("error" , "用户名和密码不匹配");
            forward("/index.htm" , request , response);
        }

	}

    public void forward(String url , HttpServletRequest request,HttpServletResponse response)
        throws ServletException,IOException
    {
        ServletContext sc = getServletConfig().getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request,response);
    }
}