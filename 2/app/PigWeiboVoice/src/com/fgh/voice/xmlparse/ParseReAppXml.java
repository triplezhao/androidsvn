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
public class ParseReAppXml
{
	
	private ArrayList<ReAppBean>  getReApps(InputStream inputStream) throws Exception{  
		
		
		
		ArrayList<ReAppBean> list=new ArrayList<ReAppBean>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder = factory.newDocumentBuilder();  
        Document document = builder.parse(inputStream);  
        Element element = document.getDocumentElement();  
          
        NodeList bookNodes = element.getElementsByTagName("reappinfo");  
        for(int i=0;i<bookNodes.getLength();i++){  
            Element bookElement = (Element) bookNodes.item(i);  
            ReAppBean reapp = new ReAppBean();  
          
            NodeList childNodes = bookElement.getChildNodes();  
//          System.out.println("*****"+childNodes.getLength());  
            for(int j=0;j<childNodes.getLength();j++){  
                if(childNodes.item(j).getNodeType()==Node.ELEMENT_NODE){  
                    if("name".equals(childNodes.item(j).getNodeName())){  
                    	reapp.setName(childNodes.item(j).getFirstChild().getNodeValue());  
                    }else if("apkurl".equals(childNodes.item(j).getNodeName())){  
                    	reapp.setApkurl(childNodes.item(j).getFirstChild().getNodeValue());   
                    } else if("pict".equals(childNodes.item(j).getNodeName())){  
                    	reapp.setPic(childNodes.item(j).getFirstChild().getNodeValue());   
                    }   
                }  
            }//end for j  
            list.add(reapp);  
        }//end for i  
        return list;  
	}  
	
	/**
	 * static_reapp_list.xml
	 * 从 assets中获取xml文件的流，进行xml解析，解析static_reapp_bean;
	 * @param ctx
	 * @return
	 */
	public ArrayList<ReAppBean>  getReApps(Context ctx){
		
		ArrayList<ReAppBean> list=new ArrayList<ReAppBean>();
		
		InputStream inStream=null;
		
			try {
				
				inStream = ctx.getAssets().open("reapp_list.xml");
				
				list=getReApps(inStream);
				
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
