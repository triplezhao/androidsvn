package weibo4android.util;

import weibo4android.Weibo;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;

public class OAuthConstant {
	
	
	
	
	public static final String  EXTRA_WEIBO_CONTENT="EXTRA_WEIBO_CONTENT";
	private static Weibo weibo=null;
	private static OAuthConstant instance=null;
	private RequestToken requestToken;
	private AccessToken accessToken;
	private String token;
	private String tokenSecret;
	public static String verifier="oauth_verifier";
	
//	public  static final String CONSUMER_KEY = "157627005";
//	public static final String CONSUMER_SECRET = "4ec4d63909dff08bdb4408f6b6b22741";
	
	
	//爱祝福的
//	public static String CONSUMER_KEY = "1972526641";
//	  public static String CONSUMER_SECRET = "2ee77dfcfec763130d27e5e26c8afb1d";
	
	
	
	//weico的
	  public static String CONSUMER_KEY = "2605065268";
	  public static String CONSUMER_SECRET = "efc2cff6c8c7abc9cdd0315dac75bf31";
	  
	public static final String CALLBACK_URL = "sms.com";
	
	
	
	
	
	private OAuthConstant(){};
	
	public static synchronized OAuthConstant getInstance(){
		if(instance==null)
			instance= new OAuthConstant();
		return instance;
	}
	public Weibo getWeibo(){
		if(weibo==null)
			weibo= new Weibo();
		return weibo;
	}
	
	public AccessToken getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
		this.token=accessToken.getToken();
		this.tokenSecret=accessToken.getTokenSecret();
	}
	public RequestToken getRequestToken() {
		return requestToken;
	}
	public void setRequestToken(RequestToken requestToken) {
		this.requestToken = requestToken;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTokenSecret() {
		return tokenSecret;
	}
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	
}
