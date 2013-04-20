package com.fgh.voice.xmlparse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;

/**
 *@author coolszy
 *@date 2012-4-26
 *@blog http://blog.92coding.com
 */
public class ParseXmlService
{
	
	private ArrayList<StaticUserBean>  getStaticUsers(InputStream inputStream) throws Exception{  
		
		
		
		ArrayList<StaticUserBean> list=new ArrayList<StaticUserBean>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder = factory.newDocumentBuilder();  
        Document document = builder.parse(inputStream);  
        Element element = document.getDocumentElement();  
          
        NodeList bookNodes = element.getElementsByTagName("userinfo");  
        for(int i=0;i<bookNodes.getLength();i++){  
            Element bookElement = (Element) bookNodes.item(i);  
            StaticUserBean user = new StaticUserBean();  
          
            NodeList childNodes = bookElement.getChildNodes();  
//          System.out.println("*****"+childNodes.getLength());  
            for(int j=0;j<childNodes.getLength();j++){  
                if(childNodes.item(j).getNodeType()==Node.ELEMENT_NODE){  
                    if("name".equals(childNodes.item(j).getNodeName())){  
                    	user.setName(childNodes.item(j).getFirstChild().getNodeValue());  
                    }else if("nickname".equals(childNodes.item(j).getNodeName())){  
                    	user.setNickname(childNodes.item(j).getFirstChild().getNodeValue());   
                    } else if("pict".equals(childNodes.item(j).getNodeName())){  
                    	user.setPic(childNodes.item(j).getFirstChild().getNodeValue());   
                    }   
                }  
            }//end for j  
            list.add(user);  
        }//end for i  
        return list;  
	}  
	
	/**
	 * static_user_list.xml
	 * 从 assets中获取xml文件的流，进行xml解析，解析static_user_bean;
	 * @param ctx
	 * @return
	 */
	public ArrayList<StaticUserBean>  getStaticUsers(Context ctx){
		
		ArrayList<StaticUserBean> list=new ArrayList<StaticUserBean>();
		
		InputStream inStream=null;
		
			try {
				
				inStream = ctx.getAssets().open("static_user_list.xml");
				
				list=getStaticUsers(inStream);
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		return list;
		
	}
	
}
