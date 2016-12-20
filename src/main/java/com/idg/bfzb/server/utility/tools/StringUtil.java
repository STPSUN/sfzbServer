package com.idg.bfzb.server.utility.tools;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;


public class StringUtil {

	/**
	 * 隐藏密码，密码不为空时用"*"代替
	 * @param password
	 * @return
	 */
	public static String hidePassword(String password) {
		if(StringUtils.isNotBlank(password)) {
			int length = password.length();
			if(length<4){
				password = "******";
			}else {
				password = "******" + password.substring(length-3, length);
			}
		}
		return password;
	}
	
    /**
     * 生成UUID
     */
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    /** 
     * 将一个8位字节数组转换为长整数。<br> 
     * 注意，函数中不会对字节数组长度进行判断，请自行保证传入参数的正确性。 
     *  
     * @param b 
     *            字节数组 
     * @return 长整数 
     */  
    public static long bytesToLong(byte[] b) {  
        long l = ((long) b[0] << 56) & 0xFF00000000000000L;  
        // 如果不强制转换为long，那么默认会当作int，导致最高32位丢失  
        l |= ((long) b[1] << 48) & 0xFF000000000000L;  
        l |= ((long) b[2] << 40) & 0xFF0000000000L;  
        l |= ((long) b[3] << 32) & 0xFF00000000L;  
        l |= ((long) b[4] << 24) & 0xFF000000L;  
        l |= ((long) b[5] << 16) & 0xFF0000L;  
        l |= ((long) b[6] << 8) & 0xFF00L;  
        l |= (long) b[7] & 0xFFL;  
        return l;  
    }
    /** 
     * 将一个长整数转换位字节数组(8个字节)，b[0]存储高位字符，大端 
     *  
     * @param l 
     *            长整数 
     * @return 代表长整数的字节数组 
     */  
    public static byte[] longToBytes(long l) {  
        byte[] b = new byte[8];  
        b[0] = (byte) (l >>> 56);  
        b[1] = (byte) (l >>> 48);  
        b[2] = (byte) (l >>> 40);  
        b[3] = (byte) (l >>> 32);  
        b[4] = (byte) (l >>> 24);  
        b[5] = (byte) (l >>> 16);  
        b[6] = (byte) (l >>> 8);  
        b[7] = (byte) (l);  
        return b;  
    }  
    /**
	 * 判断字符串是否为null、“ ”、“null”
	 * @param obj
	 * @return
	 */
	public static boolean isNull(String obj) {
		if (obj == null){
			return true;
		}else if (obj.toString().trim().equals("")){
			return true;
		}else if(obj.toString().trim().toLowerCase().equals("null")){
			return true;
		}
		
		return false;
	}
	
	/**
     * 获取一个字符在字符串里第n次出现的位置
     * @param str 查询范围（一个字符串）
     * @param c 查询的字符
     * @param n 第n次出现
     * @return
     */
    public static int lookIndex(String str,char c,int n){
    	int cnt=0;
    	//字符串为空 返回-1
    	if(isNull(str)) return -1;
    	for(int i=0;i<str.length();i++){
    		if(c==str.charAt(i)){
    			cnt++;
    			if(cnt==n) return i;//返回第n次出现的位置
    		}
    	}
    	return -1;
    }
    /**
	 * 正则验证是否是数字
	 * @param str 传入字符串
	 * @return 是数字返回true，否则返回false
	 * 
	 */
	public static boolean isNumber(String str){
		java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("[+-]?[0-9]+[0-9]*(\\.[0-9]+)?");    ;
		java.util.regex.Matcher match=pattern.matcher(str);
		if(match.matches()==false)return false;
		else  return true;
	}
}
