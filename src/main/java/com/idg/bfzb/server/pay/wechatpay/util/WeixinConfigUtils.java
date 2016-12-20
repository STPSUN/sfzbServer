package com.idg.bfzb.server.pay.wechatpay.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 微信的配置参数
 * @author iYjrg_xiebin
 * @date 2015年11月25日下午4:19:57
 */
@SuppressWarnings("unused")
public class WeixinConfigUtils {

	private static final Log log = LogFactory.getLog(WeixinConfigUtils.class);


	public static String appid;
	public static String mch_id;
	public static String wechat_call_back_url;
	public static String wx_url;//请求地址
	public static String key;//
	public static String spbill_create_ip;//终端ID

	static {
		/*ResourceBundle bundle = ResourceBundle.getBundle("resources/sys");
		appid = bundle.getString("appid");
		mch_id = bundle.getString("mch_id");
		notify_url = bundle.getString("notify_url");*/

		try{
			InputStream is = WeixinConfigUtils.class.getResourceAsStream("/config/wechatpay.properties");
			Properties properties = new Properties();
			properties.load(is);
			
			appid = properties.getProperty("appid");
			mch_id = properties.getProperty("mch_id");
			wechat_call_back_url = properties.getProperty("wechat_call_back_url");
			wx_url = properties.getProperty("wx_url");
			key = properties.getProperty("key");
			spbill_create_ip = properties.getProperty("spbill_create_ip");
		}catch(Exception ex){
			log.debug("加载配置文件："+ex.getMessage());
		}
	}


	public static void main(String[] args) throws IOException {
		InputStream is = WeixinConfigUtils.class.getResourceAsStream("/resources/sys.properties");

		Properties properties = new Properties();

		properties.load(is);

		is.close();
		String appid = properties.getProperty("appid");
		System.out.println(appid);
	}

}
