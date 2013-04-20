package lee;

import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fgh.alice.WebAliceBot;

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
/**
 * @author ztw
 *	这个是聊天的servlet 
 */
public class ChatServlet  extends HttpServlet
{
	
	WebAliceBot alicebot;
    @Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
       	//获取
		 alicebot=new WebAliceBot();
	}

	public void service(HttpServletRequest request,HttpServletResponse response)
        throws IOException,ServletException
	{
        request.setCharacterEncoding("UTF-8");
        String msg = request.getParameter("chatMsg");
        String re_msg = request.getParameter("chatMsg");
        //如果页面传来了不为空的聊天内容，则存储在缓存中。
        if ( msg != null && !msg.equals(""))
        {	
        	re_msg=alicebot.getRespond(msg);
            String user = (String)request.getSession(true).getAttribute("user");
//            ChatService.instance().addMsg(user , msg);
            ChatService.instance().addMsg(user , msg,re_msg);
        }
        //把本次对话所有文字输出给页面
        response.setContentType("text/html;charset=GBK");
        PrintWriter out =  response.getWriter();
        out.println(ChatService.instance().getMsg());
	}
}