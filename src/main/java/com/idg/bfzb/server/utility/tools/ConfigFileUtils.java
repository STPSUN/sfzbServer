package com.idg.bfzb.server.utility.tools;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 
 * 
 * 读取配置文件的工具类，如果需要多个配置文件，可以在static块中添加
 * 
 * @author ws
 * 
 *
 */
public class ConfigFileUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(ConfigFileUtils.class);

	public  static String SMS_URL = "";
	public  static String SMS_APP_KEY = "";
	public  static String SMS_APP_SECRET = "";
	public  static String HEAD_ICON_URL = "";
	public  static List<Map<String,Object>> regionAllVoList = null;

	static {
		try {
			Resource resource = new ClassPathResource("config/conf.properties");
			Properties prop = PropertiesLoaderUtils.loadProperties(resource);

			ConfigFileUtils.SMS_URL = prop.getProperty("sms_url");
			ConfigFileUtils.SMS_APP_KEY = prop.getProperty("sms_app_key");
			ConfigFileUtils.SMS_APP_SECRET = prop.getProperty("sms_app_secret");
			ConfigFileUtils.HEAD_ICON_URL = prop.getProperty("head_icon_url");
		} catch (Exception e) {
			logger.error("读取配置文件失败，原因：" + e.getMessage());
		}
	}
}
