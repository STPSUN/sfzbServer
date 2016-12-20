package com.idg.bfzb.server.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 添加国际化的支持
 */
public class I18nProvider {
    private static final Logger logger = LoggerFactory.getLogger(I18nProvider.class);
    private static ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    static{
        messageSource.setBasename("i18nMsg/message");
    }


    /**
     * 输出支持多语言的日志格式
     * @param message message.properties 中的格式化日志输出串
     * @param objects 日志参数
     * @return 本地化的日志
     */
    public static String getMessage(String message,Object... objects){
        String localMessage;
        try{
            localMessage = messageSource.getMessage(message,objects, LocaleContextHolder.getLocale());
        }catch (org.springframework.context.NoSuchMessageException exp){
            logger.error("Write log error:"+exp.getMessage(),exp);
            localMessage="";
        }
        return localMessage;
    }
}
