package utils;


import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author tanglei
 * @date 18/10/11
 */
public class PasswordUtil {

    //加密名称
    public final static String ALGORITHMNAME = "MD5";
    //迭代次数
    public final static int HASHITERATIONS = 2;

    /**
     * 生成账号密码加密盐值
     *
     * @param passwordPlaintext
     * @return
     */
    public static String generateSalty(String passwordPlaintext) {
        try {
            return MD5Util.getSalty(passwordPlaintext);
        } catch (Exception e) {
            //ignore Exception
        }
        return null;
    }

    /**
     * 对密码明文进行加密
     *
     * @param passwordPlaintext
     * @param credentialsSalt
     * @return
     */
    public static String encryptPassword(String passwordPlaintext, String credentialsSalt) {
        SimpleHash simpleHash = new SimpleHash(ALGORITHMNAME, passwordPlaintext, ByteSource.Util.bytes(credentialsSalt), HASHITERATIONS);
        return simpleHash.toString();
    }
}
