package com.braval.retrofitdemo.utils.security;


import com.mobanker.crypto.Base64;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * 与服务端协商制定了填充模式,更改前请确认是兼容Java的填充模式的
 */
public class RSAUtil {
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    //RSA/ECB/PKCS1Padding
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    private static final String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALe/tPl/hNml+gqxgQdSG6ryp8rj44Dx9Pi4P0rhMUF7G7MBeEEEP5Oi8bayTThHZKFvZzlNtwvEPftQWGJPjMmp0hEcahXTtM8f+zN8iCETzQkHfU+uCkU2JnbO6DIEoBVMxjLw2URpZm7C1LuUye+Y0vxr64fCt+3TSvTdEb5zAgMBAAECgYBCpGPlrWsoaPsjz7cFSbFBMdrLShWkGZ0IbAKAjOYJYrXsGVdc9UZ9rDZQVaCODc6ggkAE13vQ5663LC+yEDn1+ImZPb5rYDr5ZLMAFve9+oQ0glyBntYMwb+Yy0J1ZmXXv9ICgTipcM6kjUOcX7shwjp9wT/UoFN+p38Hg3IVaQJBAOKGp3nlU+HyTDcTuCBxdyEFLVAhgzy87InrxvLLtAJwOTdUBeqs02ho6CCqvIR8W3SdmvvVKg0ZgPOXOvDmGG0CQQDPqDDOj+dSOdRC+OZXfc0W07dT9YNRsdwHp599jkuSl8q0PxEw61PgNx8stjreHl32eZmeYfmhPXXQNPscOKZfAkByn4RbL+m0KTDbpcTD1eZb9QyqwWpw74AZgg7XiK/7k+G/12pvByiqansbHInr2ClG5wEncQOoQUF5AQs4HpY5AkEAkGc/eYVssStxxhjb5V+gLQ2RYRtL9JWgkton+OHo/8n6thldVQ1wyGzMiwU2Lpwjb3DkONWdCFl7Agy1ysxCNQJBAIoTvwY/WRgB5/Hde4389+VWBWSU7wLboLVRqddEl7ofORMUj4BgMAA7nG+JdZh+WAVxnknvzCnMF77J5DZRj7U=";
    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3v7T5f4TZpfoKsYEHUhuq8qfK4+OA8fT4uD9K4TFBexuzAXhBBD+TovG2sk04R2Shb2c5TbcLxD37UFhiT4zJqdIRHGoV07TPH/szfIghE80JB31PrgpFNiZ2zugyBKAVTMYy8NlEaWZuwtS7lMnvmNL8a+uHwrft00r03RG+cwIDAQAB";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = Base64.decode(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return Base64.encode(signature.sign());
    }

    /**
     * 使用指定私钥签名
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data) throws Exception {
        return sign(data, privateKey);
    }

    /**
     * 使用指定公钥验签
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {

        // 解密由base64编码的公钥
        byte[] keyBytes = Base64.decode(publicKey);

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(Base64.decode(sign));
    }

    /**
     * 使用指定公钥验签
     *
     * @param data
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String sign) throws Exception {
        return verify(data, publicKey, sign);
    }

    /**
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
        byte[] keyBytes = Base64.decode(key);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding"/*keyFactory.getAlgorithm()*/);
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 使用指定私钥解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data) throws Exception {
        return decryptByPrivateKey(data, privateKey);
    }
    /**
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
        byte[] keyBytes = Base64.decode(key);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;

    }

    /**
     * 用指定公钥加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data) throws Exception {
        return encryptByPublicKey(data, publicKey);
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encode(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encode(key.getEncoded());
    }
    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }
}
