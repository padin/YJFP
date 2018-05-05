package com.yst.util;

import java.net.URLEncoder;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;



import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author 刘宗安
 * @version 1.0 功能 ：釆用3DES标准以模式为ECB、填充方式为PKCS7加密数据
 */
public class DESecbpkcs7Impl {
	private Cipher cipher = null;
	// base64编码
	private BASE64Encoder base64Encode = new BASE64Encoder();
	private BASE64Decoder base64Decode = new BASE64Decoder();
	// 密钥
	private String key = "";
	// 过滤换行
	private boolean filter = true;

	public String getKey() {
		return key;
	}

	public boolean getFilter() {
		return filter;
	}

	/**
	 * 设置密钥
	 * 
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	public void setFilter(boolean filter) {
		this.filter = filter;
	}

	private final Cipher initCipher(int mode) {
		try {
			// 添加新安全算法:PKCS7
			Security.addProvider(new BouncyCastleProvider());
			String algorithm = "DESede/ECB/PKCS7Padding";
//			SecretKey desKey = new SecretKeySpec((new BASE64Decoder()).decodeBuffer(key), algorithm);
			SecretKey desKey = new SecretKeySpec(key.getBytes("ASCII"), algorithm);  
			// SecretKey desKey = new SecretKeySpec(key.getBytes("ASCII"), algorithm);
			Cipher tcipher = Cipher.getInstance(algorithm);
			tcipher.init(mode, desKey);
			return tcipher;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密以charset编码做为密文
	 * 
	 * @param src
	 *            明文
	 * @param charset
	 *            编码,例：UTF8、BASE64
	 * @return
	 */
	public String encrypt(String src, String charset) {
		try {
			return URLEncoder.encode(encrypt(src), charset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            二进制数组
	 * @return
	 * @throws Exception
	 */
	private byte[] decrypt(byte[] src) throws Exception {
		cipher = initCipher(Cipher.DECRYPT_MODE);
		return cipher.doFinal(src);
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            密文
	 * @return
	 * @throws Exception
	 */
	public String decrypt(String src) throws Exception {
		byte[] bt = base64Decode.decodeBuffer(src);
		byte[] sbt = decrypt(bt);
		return new String(sbt, "ASCII");
	}

	/**
	 * 加密以base64做为密文
	 * 
	 * @param src
	 *            明文
	 * @return 密文
	 */
	public String encrypt(String src) {
		cipher = initCipher(Cipher.ENCRYPT_MODE);
		byte[] dd = encrypt(src.getBytes());
		String str = base64Encode.encode(dd);
		str = str.replaceAll("\r", "");
		str = str.replaceAll("\n", "");
		return str;
	}

	/**
	 * 
	 * @param src
	 * @return
	 */
	public byte[] encrypt(byte[] src) {
		try {
			return cipher.doFinal(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		DESecbpkcs7Impl cWebService3DES = new DESecbpkcs7Impl();
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		cWebService3DES.key = "18947f2c467c4763fd2492cf".toUpperCase();
		System.out.println("18947f2c467c4763fd2492cf".toUpperCase());
		String s = cWebService3DES.encrypt(
				"<?xml version=\"1.0\" encoding=\"utf-8\" ?><SXData><UserID>bjtele-union</UserID><EnCode>9289a37b0f5cb18abb5aa4d33e85afe9</EnCode></SXData>");
		s = s.replaceAll("\r", "");
		s = s.replaceAll("\n", "");
		System.out.println(s);
		System.out.println(Base64.getEncoder().encode(cWebService3DES.decrypt(s).getBytes()));
	}

}
