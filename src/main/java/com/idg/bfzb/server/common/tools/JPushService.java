package com.idg.bfzb.server.common.tools;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 类名称：JPushService
 * 类描述：极光推送封装类
 * 创建人：ouzhb
 * 创建日期：2016/12/08
 */
@Service
public class JPushService {
	
    private Logger logger = LoggerFactory.getLogger(JPushService.class);

    private String appKey = "38f06f2ad62659e4278d95ee";
    private String masterSecret = "61fe9609dd53ac33eca048fe";
    
    public JPushService(){
    	
    }
    
    public JPushService(String appKey,String masterSecret){
        this.appKey = appKey;
        this.masterSecret = masterSecret;
    }

    /**
     * 发送纯文本消息(所有平台)
     * @param megContent 发送消息内容
     * @return 发送是否成功
     */
    public  boolean sendTextMsg(String megContent){
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        PushPayload payload = PushPayload.alertAll(megContent);
        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("result",result);
            return true;
        } catch (APIConnectionException e) {
            logger.error("error  " + e);
            return false;
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. " + e);
            logger.error("HTTP Status: " + e.getStatus());
            logger.error("Error Code: " + e.getErrorCode());
            logger.error("Error Message: " + e.getErrorMessage());
            logger.error("Msg ID: " + e.getMsgId());
            return false;
        }
    }
    /**
     * 发送纯文本消息（安卓，苹果平台）
     * @param megContent 发送消息内容
     * @param userId 用户注册ID
     * @return 发送是否成功
     */
    public  boolean sendTextMsg(String megContent,String[] userId){
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())//必填    推送平台设置
                .setAudience(Audience.registrationId(userId))//用户注册id,一次最多1000个
                .setNotification(Notification.alert(megContent))
                        /**
                         * 如果目标平台为 iOS 平台 需要在 options
                         * 中通过 apns_production 字段来制定推送环境。
                         * True 表示推送生产环境，False 表示要推送开发环境； 如
                         * 果不指定则为推送生产环境
                         */
                .setOptions(Options.newBuilder()
                        .setApnsProduction(false)
                        .build())
                .build();
        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("result",result);
            return true;
        } catch (APIConnectionException e) {
            logger.error("error  " + e);
            return false;
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. " + e);
            logger.error("HTTP Status: " + e.getStatus());
            logger.error("Error Code: " + e.getErrorCode());
            logger.error("Error Message: " + e.getErrorMessage());
            logger.error("Msg ID: " + e.getMsgId());
            return false; 
        }
    }

    /**
     * 根据别名推送消息
     * @param megContent 推送消息内容
     * @param alias 别名数组
     * @return 推送是否成功
     */
    public  boolean sendTextMsgByAlias(String megContent,String... alias){
        JPushClient jpushClient = new JPushClient(masterSecret, appKey);
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.alias(alias))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(megContent)
                        .build())
                .build();
        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("result",result);
            return true;
        } catch (APIConnectionException e) {
            logger.error("error  " + e);
            return false;
        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. " + e);
            logger.error("HTTP Status: " + e.getStatus());
            logger.error("Error Code: " + e.getErrorCode());
            logger.error("Error Message: " + e.getErrorMessage());
            logger.error("Msg ID: " + e.getMsgId());
            return false;
        }
    }
}
