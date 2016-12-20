package com.idg.bfzb.server.utility.encrypt;
import com.idg.bfzb.server.usercenter.model.Constants;

import java.security.MessageDigest;

/**
 * MD5工具
 */
public class MD5Util {
    public  static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        System.out.println("客户端:"+MD5Util.MD5(MD5Util.MD5("gaolan2015")+ Constants.SALT_VAL));
        System.out.println(""+MD5Util.MD5("gaolan2015"));
        System.out.println("A"+MD5Util.MD5(MD5Util.MD5("gaolan2015")+ Constants.UC_USER_SALT));
        System.out.println(MD5Util.MD5("加密"));
    }
}