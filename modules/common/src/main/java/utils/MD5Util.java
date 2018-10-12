package utils;

import org.apache.commons.lang.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

/**
 * @author tanglei
 * @date 18/10/12
 */
public class MD5Util {

    /**
     * 获取加密盐值
     *
     * @param password
     * @return
     * @throws Exception
     */
    public static String getSalty(String password) throws Exception {
        return encryptHexString(initSalty(), password);
    }

    private static String encryptHexString(String str, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, getKey(key.getBytes()));
        return getHexStringFromByteArray(cipher.doFinal(str.getBytes("UTF-8")));
    }

    private static SecretKey getKey(byte[] bytes) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(bytes);
            _generator.init(128, secureRandom);
            return _generator.generateKey();
        } catch (Exception var3) {
            return null;
        }
    }

    private static String initSalty() {
        return RandomStringUtils.randomAlphabetic(32);
    }

    private static String getHexStringFromByteArray(byte[] byteArray) {
        if (byteArray != null && byteArray.length >= 1) {
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < byteArray.length; ++i) {
                if ((byteArray[i] & 255) < 16) {
                    hexString.append("0");
                }

                hexString.append(Integer.toHexString(255 & byteArray[i]));
            }

            return hexString.toString().toLowerCase();
        } else {
            throw new IllegalArgumentException("this byteArray must not be null or empty");
        }
    }
}
