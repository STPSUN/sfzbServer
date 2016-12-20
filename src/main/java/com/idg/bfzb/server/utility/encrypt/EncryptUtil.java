package com.idg.bfzb.server.utility.encrypt;

// import com.alibaba.druid.util.Base64;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.SecureRandom;

//import org.slf4j.Logger;

public class EncryptUtil {
	
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";
	private final static String KEY_DES = "DES";
	public static final String KEY_HMD5_256 = "HmacSHA256";

	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(EncryptUtil.class);
	//十六进制下数字到字符的映射数组  
    private final static String[] hexDigits = {"0", "1", "2", "3", "4",  
        "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};  

	/**
	 * 标准MD5加密
	 * @param content 要加密的字符串
	 * @return 加密后的结果
	 */
	public static String encryptStdMD5(String content) {
		String resultString = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
			md5.update(content.getBytes());

			// 将得到的字节数组变成字符串返回
			resultString = byteArrayToHexString(md5.digest());
		} catch (Exception e) {
			logger.error("encryptMD5 fail", e);
		}

		return resultString.toLowerCase();
	}

    /**
     * HMac256加密
     * @param content 混淆盐值
     * @param key 要进行签名校验的字符串
     * @return 计算后的签名结果
     */
   /* public static String encryptHMac256(String content, String key) {
        String resultString = "";
        try {
            // 还原密钥
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            // 实例化Mac
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            // 初始化mac
            mac.init(secretKey);
            // 执行消息摘要
            byte[] digest = mac.doFinal(content.getBytes());
            resultString = Base64.byteArrayToBase64(digest);
        } catch (Exception e) {
            logger.error("encryptHMac256 fail", e);
        }
        return  resultString;
    }*/

    /**
	 * MD5+Salt加密
	 * @param content
	 * @return
	 */
	public static String encryptSaltMD5(String content,String salt) {
		String resultString = "";
		
		byte[] a = salt.getBytes();
        byte[] datSource = content.getBytes();
		byte[] b = new byte[a.length + 4 + datSource.length];

        int i;
        for (i = 0; i < datSource.length; i++) {
            b[i] = datSource[i];
        }

        b[i++] = (byte)163;
        b[i++] = (byte)172;
        b[i++] = (byte)161;
        b[i++] = (byte)163;

        for (int k = 0; k < a.length; k++) {
            b[i] = a[k];
            i++;
        }
		
		try {
			MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
			md5.update(b);

			// 将得到的字节数组变成字符串返回
			resultString =new HexBinaryAdapter().marshal(md5.digest());//转为十六进制的字符串 
		} catch (Exception e) {
			logger.error("encryptMD5 fail", e);
		}

		return resultString.toLowerCase();
	}
	
	/**
	 * 混淆MD5加密
	 * @param content
	 * @return
	 */
	public static String encryptMD5_ND(String content) {
		String resultString = "";
		String appkey = "fdjf,jkgfkl"; 
		
		byte[] a = appkey.getBytes();
        byte[] datSource = content.getBytes();
		byte[] b = new byte[a.length + 4 + datSource.length];

        int i;
        for (i = 0; i < datSource.length; i++)
        {
            b[i] = datSource[i];
        }

        b[i++] = (byte)163;
        b[i++] = (byte)172;
        b[i++] = (byte)161;
        b[i++] = (byte)163;

        for (int k = 0; k < a.length; k++)
        {
            b[i] = a[k];
            i++;
        }
		
		try {
			MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
			md5.update(b);

			// 将得到的字节数组变成字符串返回
			resultString = byteArrayToHexString(md5.digest());
		} catch (Exception e) {
			logger.error("encryptMD5 fail", e);
		}

		return resultString.toLowerCase();
	}

	public static byte[] encryptSHA(String content) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(content.getBytes());

		return sha.digest();

	}
	
	public static byte[] encryptDes(byte[] src, byte[] key)throws Exception {

        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(KEY_DES);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        return cipher.doFinal(src);
     }
	
	public static byte[] decryptDes(byte[] src, byte[] key)throws Exception {

        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(KEY_DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(src);

     }
	
	public static String encryptDes(String data, String key) throws Exception {
        byte[] bt = encryptDes(data.getBytes(), key.getBytes());
        return new BASE64Encoder().encode(bt);
    }
	
	public static String decrypt(String data, String key) throws Exception {
		if (data == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buf = decoder.decodeBuffer(data);
		byte[] bt = decryptDes(buf, key.getBytes());
		return new String(bt);
	}
	
	/**  
     * 转换字节数组为十六进制字符串 
     * @param     b 字节数组
     * @return    十六进制字符串 
     */  
    private static String byteArrayToHexString(byte[] b){  
        StringBuilder resultSb = new StringBuilder();
        for (int i = 0; i < b.length; i++){  
            resultSb.append(byteToHexString(b[i]));  
        }  
        return resultSb.toString();  
    }  
      
    /** 将一个字节转化成十六进制形式的字符串     */  
    private static String byteToHexString(byte b){  
        int n = b;  
        if (n < 0)  
            n = 256 + n;  
        int d1 = n / 16;  
        int d2 = n % 16;  
        return hexDigits[d1] + hexDigits[d2];  
    }
}
