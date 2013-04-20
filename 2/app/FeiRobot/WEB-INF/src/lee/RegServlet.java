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
public class RegServlet  extends HttpServlet
{
    public void service(HttpServletRequest request,HttpServletResponse response)
        throws IOException,ServletException
	{

        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        if (name == null || pass == null)
        {
            request.setAttribute("tip" , "�û��������붼����Ϊ��");
            forward("/reg.jsp" , request , response);
        }
        try
        {
            if (ChatService.instance().addUser(name , pass))
            {
                request.setAttribute("tip" , "ע��ɹ������½ϵͳ");
                forward("/reg.jsp" ,request , response);
            }
            else
            {
                request.setAttribute("tip" , "�޷�����ע�ᣬ������");
                forward("/reg.jsp" ,request , response);
            }        	
        }
        catch (Exception e)
        {
            request.setAttribute("tip" , e.getMessage());
            forward("/reg.jsp" ,request , response);
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