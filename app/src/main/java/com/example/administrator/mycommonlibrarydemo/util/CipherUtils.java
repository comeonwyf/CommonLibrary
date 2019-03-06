package com.example.administrator.mycommonlibrarydemo.util;

import android.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @auther: 吴锐
 * @date: 2018-11-21 11:12
 * @describe: 加密工具类 MD5 SHA1 AES RSA
 */
public class CipherUtils {


    public static String MD5Encode(String content) {
        return MD5Encode(content, 32);
    }

    /**
     * MD5加密
     *
     * @param content 明文
     * @param bit       可填参数 32 16
     * @return
     */
    public static String MD5Encode(String content, int bit) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes("UTF-8"));
            byte[] password = md.digest();
            String pass = bytesToHex(password);
            if (bit < 32) {
                return pass.substring(8, 24);
            }
            return pass;
        } catch (Exception e) {
        }
        return "";
    }

    public static String MD5EncodeFile(File file) {
        return MD5EncodeFile(file, 32);
    }

    // 文件MD5加密
    public static String MD5EncodeFile(File file, int bit) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            InputStream is = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            byte[] password = md.digest();
            String pass = bytesToHex(password);
            if (bit < 32) {
                return pass.substring(8, 24);
            }
            return pass;
        } catch (Exception e) {
        }
        return "";
    }

    // 转换成十六进制
    private static String bytesToHex(byte[] password) {
        int i;
        StringBuilder builder = new StringBuilder();
        for (int offset = 0; offset < password.length; offset++) {
            i = password[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                builder.append("0");
            builder.append(Integer.toHexString(i));
        }
        return builder.toString();
    }

    //sha1加密
    public static String SHA1Encode(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            digest.update(content.getBytes("UTF-8"));
            byte[] password = digest.digest();
            return bytesToHex(password);
        } catch (Exception e) {

        }
        return "";
    }

    // sha1文件加密
    public static String SHA1EncodeFile(File file){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            InputStream is = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            byte[] password = digest.digest();
            return bytesToHex(password);
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
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
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    /**
     * Aes 加密 CBC
     * @param content 内容
     * @param password 字符串密码
     * @return 返回的暗文使用base64编码
     */
    public static String AesEncrypt(String content, String password){
        try {
            Key key = new SecretKeySpec(password.getBytes(), "AES");
            //将待加密字符串转二进制
            byte[] byteContent = content.getBytes("UTF-8");
            //创建密码器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("2624750004598718".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            //加密的结果
            byte[] result = cipher.doFinal(byteContent);
            //Log.d("TAG", new String(result));
            //return Base64.encodeToString(result, Base64.NO_WRAP);
            return bytesToHex(result);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Aes 解密 CBC
     * @param data 暗文数据 先进行base64解码
     * @param password 字符串密码
     * @return 明文
     */
    public static String AesDecrypt(String data, String password){
        try {
            Key key = new SecretKeySpec(password.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("2624750004598718".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] byteData = Base64.decode(data, Base64.NO_WRAP);
            byte[] byteContent = cipher.doFinal(byteData);
            return new String(byteContent);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 动态随机生成一个秘钥
     * @param size 秘钥字符串的长度 128 10个加密循环 192 12个加密循环 256 14个加密循环
     * @return 返回一个秘钥
     * @throws Exception
     */
    public static String keyGenerator(int size) throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(size);
        SecretKey secretKey = generator.generateKey();
        byte[] encoded = secretKey.getEncoded();
        return bytesToHex(encoded);
    }

    /**
     * 动态随机生成一个指定key的秘钥
     * @param size 秘钥字符串的长度 128 10个加密循环 192 12个加密循环 256 14个加密循环
     * @return 返回一个秘钥
     * @throws Exception
     */
    public static String keyGenerator(String key, int size) throws Exception {
        // 由于android 9.0移除了Crypto 适配了 api 14 - 28
/*        KeyGenerator generator = KeyGenerator.getInstance("AES");
        SecureRandom random = null;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.P){
           // random = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            random = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else {
            random = SecureRandom.getInstance("SHA1PRNG");
        }
        random.setSeed(key.getBytes());
        generator.init(size);
        SecretKey secretKey = generator.generateKey();
        byte[] encoded = secretKey.getEncoded();*/

        /* Store these things on disk used to derive key later: */
        //循环次数
        int iterationCount = 1000;
        //随机数的长度
        byte[] salt = new byte[32]; // Should be of saltLength
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        /* Use this to derive the key from the password: */
        //指定key salt随机数 iterationCount 循环次数 size 生成key的长度
        KeySpec keySpec = new PBEKeySpec(key.toCharArray(), salt, iterationCount, size);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();

        return bytesToHex(keyBytes);
    }



    //生成秘钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    //获取公钥(Base64编码)
    public static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    //将Base64编码后的公钥转换成PublicKey对象
    private static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = Base64.decode(pubStr, Base64.NO_WRAP);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    private static PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = Base64.decode(priStr, Base64.NO_WRAP);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //RSA公钥加密
    public static String RSAPublicEncrypt(String content, String key){
        try {
            PublicKey publicKey = string2PublicKey(key);
            return publicEncrypt(content, publicKey);
        }catch (Exception e){

        }
        return "";
    }

    //公钥加密
    private static String publicEncrypt(String content, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content.getBytes());
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    //RSA公钥解密
    public static String RSAPublicDecrypt(String content, String key){
        try {
            PublicKey publicKey = string2PublicKey(key);
            return publicDecrypt(content, publicKey);
        }catch (Exception e){

        }
        return "";
    }

    // 公钥解密
    private static String publicDecrypt(String content, PublicKey publicKey) throws Exception{
        byte[] byteContent = Base64.decode(content, Base64.NO_WRAP);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(byteContent);
        return new String(bytes);
    }

    public static String RSAPrivateDecrypt(String content, String key){
        try {
            PrivateKey privateKey = string2PrivateKey(key);
            return privateDecrypt(content, privateKey);
        } catch (Exception e){

        }
        return "";
    }

    //私钥解密
    private static String privateDecrypt(String content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] byteContent = Base64.decode(content, Base64.NO_WRAP);
        byte[] bytes = cipher.doFinal(byteContent);
        return new String(bytes, "UTF-8");
    }

    static String RSA_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1XOOkY2aUgKbqxZUOGK0ulKxTNj7mKfPpiiPLN8Z4s/zhKrt3iFen70k3PGNaCZP/7HE0baEkik+yjko4MRNgY8FuKLF1FiCOYIVnXMuuBIpl2jgg6mVAmFeT9olSizrD9kN3v6UeDVXiPPT73w5nGPrbo5/lM5GNfxEy1yD0LxnvYk3zF0+3ziy44eTgOjlzuoHzytD0tRUTDiI/QHXb/jYNSeWenJE90gpG0aDPtAXuaqqD0hXPGWkSf6bTs3h5uZOVAyauG28wJxRvcEkeBQ2Lyu4S+B1854XMzOWdEMWrLAN0YsPJHszeFP6fFWpIKWXhTW0GjPStQ0pGr14twIDAQAB";
    static String AES_KEY = "751f621ea5c8f110";

    //app加密
    public static String encrypt(String content){
        content = RSAPublicEncrypt(content, RSA_PUBLIC_KEY);
        return AesEncrypt(content, AES_KEY);
    }

    //app解密
    public static String decrypt(String content){
        content = AesDecrypt(content, AES_KEY);
        return RSAPublicDecrypt(content, RSA_PUBLIC_KEY);
    }
}
