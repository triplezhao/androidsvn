package com.fgh.alice;

import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bitoflife.chatterbean.AliceBot;
import bitoflife.chatterbean.Context;
import bitoflife.chatterbean.parser.AliceBotParser;
import bitoflife.chatterbean.util.Searcher;

public class AliceBotMother
{
  
  private ByteArrayOutputStream gossip;
  
  
  public void setUp()
  {
    gossip = new ByteArrayOutputStream();
  }
  
  public String gossip()
  {
    return gossip.toString();
  }

  /**
 * @return
 * @throws Exception
 * 
 *  1.return this.getClass().getClassLoader().getResource("/").getPath();
	2.return this.getClass().getClassLoader().getResource("").getPath(); 
	3.return this.getClass().getClassLoader().getResource(".").getPath();
	4.return this.getClass().getClassLoader().getResource("./").getPath();
	5.return this.getClass().getResource("/").getPath(); 
	6.return this.getClass().getResource("").getPath(); 
	7.return this.getClass().getResource(".").getPath();
	8.return this.getClass().getResource("./").getPath();
	
	1.结果：报错
	2.3.4.结果：/D:/workspace/Learning/WebRoot/WEB-INF/classes/
	5.结果：/D:/workspace/Learning/WebRoot/WEB-INF/classes/
	6.7.8.结果：/D:/workspace/Learning/WebRoot/WEB-INF/classes/test/
 */
public AliceBot newInstance() throws Exception
  {
	PathUtil pathUtil = new PathUtil();
	String webroot=pathUtil.getWebInfPath();
	
    Searcher searcher = new Searcher();
    AliceBotParser parser = new AliceBotParser();
    AliceBot bot = parser.parse(new FileInputStream(webroot+"/Bots/context.xml"),
                                new FileInputStream(webroot+"/Bots/splitters.xml"),
                                new FileInputStream(webroot+"/Bots/substitutions.xml"),
                                searcher.search(webroot+"/Bots/mydomain", ".*\\.aiml"));

    Context context = bot.getContext(); 
    context.outputStream(gossip);
    return bot;
  }
  
  
}
