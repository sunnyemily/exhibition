package cn.org.chtf.card.support.util;

import java.nio.charset.Charset;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.Md5Hash;

public class CryptographyUtil {
	/** 
     * title:CryptographyUtil.java 
     * description:Md5加密 
     * time:2017年1月16日 下午10:55:20 
     * author:wushixing 
     * @param str 密码 
     * @param salt 盐 
     * @return 
     */
    public static String md5(String str,String salt){  
        return new Md5Hash(str,salt).toString();
    }
    
    public static String GeCurrenttUrl(HttpServletRequest request){
    	String url= request.getScheme()+"://"+request.getServerName();
    	return url;
    }

    /**
     * 对给定的字符串以指定的编码方式和密钥进行加密
     * @param srcStr 待加密的字符串
     * @param charset 字符集，如utf8
     * @param sKey 密钥 
     */
    public static String encrypt(String srcStr, Charset charset, String sKey) {
        byte[] src = srcStr.getBytes(charset);
        byte[] buf = encrypt(src, sKey);
        return parseByte2HexStr(buf);
    }

    /**
     * 对给定的密文以指定的编码方式和密钥进行解密
     * @param hexStr 需要解密的密文
     * @param charset 字符集
     * @param sKey 密钥
     * @return 解密后的原文
     * @throws Exception
     */
    public static String decrypt(String hexStr, Charset charset, String sKey) throws Exception {
        byte[] src = parseHexStr2Byte(hexStr);
        byte[] buf = decrypt(src, sKey);
        return new String(buf, charset);
    }

    public static byte[] encrypt(byte[] data, String sKey) {
        try {
            byte[] key = sKey.getBytes();

            IvParameterSpec iv = new IvParameterSpec(key);
            DESKeySpec desKey = new DESKeySpec(key);

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);

            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param src
     * @param sKey
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String sKey) throws Exception {
        byte[] key = sKey.getBytes();
        // 初始化向量
        IvParameterSpec iv = new IvParameterSpec(key);
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(key);
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    
}
