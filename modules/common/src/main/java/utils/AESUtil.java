package utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @author tanglei
 * @date 18/10/11
 */
public class AESUtil {

    private static final String KEY = "hgk743#$%/*pm79v";
    private static final String IV_PARAMETER = "nhg53804*&^198mn";

    private AESUtil() {

    }

    public static String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] keyBytes = KEY.getBytes("utf-8");
            byte[] ivBytes = IV_PARAMETER.getBytes("utf-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes("utf-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            return text;
        }
    }

    public static String decrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] keyBytes = KEY.getBytes("utf-8");
            byte[] ivBytes = IV_PARAMETER.getBytes("utf-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryted = Base64.getDecoder().decode(text);
            byte[] origin = cipher.doFinal(encryted);
            return new String(origin, "utf-8");
        } catch (Exception e) {
            return text;
        }
    }

    public static void main(String[] args) {
        System.out.println(encrypt("1"));
    }
}
