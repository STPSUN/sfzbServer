package com.idg.bfzb.server.utility.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESUtil {

	private final static String KEY_DES = "DES";
	private final static String KEY_CBC = "DES/CBC/PKCS5Padding";

	// 密钥
	public static final String KEY = "!@#ASD12";

	private final static Logger logger = LoggerFactory.getLogger(DESUtil.class);


	/**
	 * des加密
	 *
	 * @param data
	 * @param encode
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String encode, String key) throws Exception {
		byte[] bt = null;
		if(null==encode)bt = encrypt(data.getBytes(), key.getBytes());
		else bt = encrypt(data.getBytes(encode), key.getBytes());
		String strs = ByteConvertString(bt);
		return strs.toUpperCase();
	}

	/**
	 * des加密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		return encrypt(data,null,key);
	}

	/**
	 * des加密
	 *
	 * @param data
	 * @param encode
	 * @return
	 * @throws Exception
	 */
	public static String encryptWithEncode(String data, String encode) throws Exception {
		return encrypt(data,encode,KEY);
	}

	/**
	 * des加密
	 *
	 * @param data
	 * @param encode
	 * @return
	 * @throws Exception
	 */
	public static String encryptGB2312(String data) {

		String str = "";
		try {
			str = encrypt(data,"gb2312",KEY);
		} catch (Exception e) {
			logger.error("encryptGB2312 data:" + data, e);
		}
		return str;
	}

	/**
	 * des加密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
		return encrypt(data,KEY);
	}

	/**
	 * des 解密
	 *
	 * @param data
	 * @param encode
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data, String encode, String key) throws Exception {
		byte[] inputByteArray = new byte[data.length() / 2];
		for (int x = 0; x < data.length() / 2; x++) {
			int i = Integer.parseInt(data.substring(x * 2, x * 2 + 2), 16);
			inputByteArray[x] = (byte) (i);
		}

		byte[] bt = decrypt(inputByteArray, key.getBytes());
		if(null==encode) return new String(bt);
		String strs = new String(bt,encode);
		return strs;
	}

	/**
	 * des 解密
	 *
	 * @param data
	 * @param encode
	 * @return
	 * @throws Exception
	 */
	public static String decryptWithEncode(String data, String encode) throws Exception {
		return decrypt(data, encode, KEY);
	}

	/**
	 * des 解密
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String data) throws Exception {
		return decrypt(data,null, KEY);
	}

	/**
	 * 根据键值进行加密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// 生成一个固定的IV向量
		IvParameterSpec iv = new IvParameterSpec(key);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(KEY_CBC);
		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
		return cipher.doFinal(data);
	}

	/**
	 * 根据键值进行解密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// 生成一个固定的IV向量
		IvParameterSpec iv = new IvParameterSpec(key);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(KEY_CBC);
		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
		return cipher.doFinal(data);
	}

	/**
	 * 将byte数据组转换为字符串
	 *
	 * @param bResult
	 * @return
	 */
	private static String ByteConvertString(byte[] bResult) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bResult.length; i++) {
			int val = ((int) bResult[i]) & 0xff;
			if (val < 16) {
				sb.append("0");
			}
			// 转换为十六进制
			sb.append(Integer.toHexString(val));
		}
		return sb.toString();
	}

}