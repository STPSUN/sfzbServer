

/**

 * Alipay.com Inc.

 * Copyright (c) 2004-2014 All Rights Reserved.

 */

package com.idg.bfzb.server.pay.alipay.constants;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 支付宝环境常量（demo中常量只是参考，需要修改成自己的常量值）
 * 
 * @author taixu.zqq
 * @version $Id: AlipayServiceConstants.java, v 0.1 2014年7月24日 下午4:33:49 taixu.zqq Exp $
 */
public class AlipayConfig {
	
	private static Logger logger = LoggerFactory
			.getLogger(AlipayConfig.class);

    /**支付宝公钥-从支付宝服务窗获取*/
    public static String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    /**字符编码-传递给支付宝的数据编码*/
    public static String CHARSET           = "UTF-8";

    /**签名类型*/
    public static String SIGN_TYPE         = "RSA";
    
    /**合作身份者ID*/
    public static String PARTNER           = "2088421450332879";
    
    public static String APP_ID           = "2016111102715844";

    //开发者请使用openssl生成的密钥替换此处  请看文档：https://fuwu.alipay.com/platform/doc.htm#2-1接入指南
    //TODO !!!! 注：该私钥为测试账号私钥  开发者必须设置自己的私钥 , 否则会存在安全隐患 
    public static String PRIVATE_KEY       = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALVS76Vv+DUSoUMH"
											+"GqUJvchoxCtDrXktlqEhVMqFuKprVOfaZDfygxiPZbF1YdPakLPWX6djlgTSz9BQ"
											+"mBAm1olNBDyioPN+tJorEV0H9A4HPL+9q69tsYzULfjrnFd39EgI5eg0qwkSEJXP"
											+"vxsRz0NYIqZaHl+qPXbR7jsR6YzpAgMBAAECgYBD7/Yga0p5QCixv3ipO3QjIbXW"
											+"a/4UOBOG/cESPHYLTDm8wSPpnnCmBBxbGBr2UJ8E8p5WTkbIOeBpMbk33AVnPIxi"
											+"GmADvjaAT629ZDLmZF9OP/rNmNXyK39waou/NxbHZrYx7T5+E219DnjK5J+0nI8L"
											+"zsgjp+g88F3ZDopeLQJBAOLoh+NxJyINpA5dydVNCgqjE/l1ajlVcZXBXgn7/DFK"
											+"rnEDsUS4lOaly0p1F1mmkIV2plYilNaipuH2y8RK9qsCQQDMkkJGyns3oOC3iqS5"
											+"UQeZ/5MQtt0hOSOUZcmejpVbzj2idZrPSjEs+aB5h9hFi10cjRLwlQXYZCRjfpu+"
											+"Thq7AkEAoiK/vsBC0y1mFbHkn5qd2z3t7ul8Tp2EPRrfxEifSuVvQmf+CfnOxASh"
											+"/4EtfyzqxcMjVhb9vWSZ1IrRzWA3YwJBAJJH96qFOWzC1k2Ij1eQcJ4XGgN2r1MF"
											+"d8KysmR10qmOJZaY//VAOT/O1IhyshywlglHe4abGU3t9fNLYnTHhucCQDnnRbS0"
											+"AkgfGUie1jSCcJqZ79Br2spMDN3MXZRqe+hv+PFwlpD0/LEwj6d3UX1W1sfeGdaQ"
											+"34ZjCkPLHX8ozgM=";
    //TODO !!!! 注：该公钥为测试账号公钥  开发者必须设置自己的公钥 ,否则会存在安全隐患
    public static String PUBLIC_KEY        = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1Uu+lb/g1EqFDBxqlCb3IaMQrQ615LZahIVTKhbiqa1Tn2mQ38oMYj2WxdWHT2pCz1l+nY5YE0s/QUJgQJtaJTQQ8oqDzfrSaKxFdB/QOBzy/vauvbbGM1C3465xXd/RICOXoNKsJEhCVz78bEc9DWCKmWh5fqj120e47EemM6QIDAQAB";

    /**支付宝网关*/
    public static String ALIPAY_GATEWAY    = "https://openapi.alipay.com/gateway.do";

    /**授权访问令牌的授权类型*/
    public static String GRANT_TYPE        = "authorization_code";
    /**异步回调URL */
    public static String ALI_CALL_BACK_URL = "";
    
    public static String SELLER_ID = "";
    
	static {
		try {
			Resource resource = new ClassPathResource("config/alipay.properties");
			Properties prop = PropertiesLoaderUtils.loadProperties(resource);

			AlipayConfig.CHARSET = prop.getProperty("charset");
			AlipayConfig.SIGN_TYPE = prop.getProperty("sign_type");
			AlipayConfig.PARTNER = prop.getProperty("parter");
			AlipayConfig.PRIVATE_KEY = prop.getProperty("private_key");
			AlipayConfig.ALIPAY_PUBLIC_KEY = prop.getProperty("alipay_public_key");
			AlipayConfig.ALIPAY_GATEWAY = prop.getProperty("alipay_gateway");
			AlipayConfig.ALI_CALL_BACK_URL = prop.getProperty("ali_call_back_url");
			AlipayConfig.SELLER_ID = prop.getProperty("seller_id");
			AlipayConfig.APP_ID = prop.getProperty("app_id");
		} catch (Exception e) {
			logger.error("读取支付配置文件失败，原因：" + e.getMessage());
		}
	}
    
    
}