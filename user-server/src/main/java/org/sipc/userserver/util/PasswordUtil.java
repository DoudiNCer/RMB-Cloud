package org.sipc.userserver.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码相工具类
 * @author DoudiNCer
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public final class PasswordUtil {
    private static final String B_PASSWD_SALT = "SipcRmbBPassSalt6gvi9*t8g^$f90@5";
    private static final String C_PASSWD_SALT = "SipcRmbCPassSalt98h9*56v_)%_=T87";

    /**
     * 密码生成
     *
     * @param password 原始密码
     * @param passwdSalt 盐
     * @return 处理后的密码
     */
    private static String generatePasswd(String password, String passwdSalt){
        MessageDigest message512Digest;
        MessageDigest message256Digest;
        try {
            message512Digest = MessageDigest.getInstance("SHA-512");
            message256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            log.warn("Get Algorithm Error When Process Password: " + e.getMessage());
            return null;
        }
        String firstResult = Base64.encodeBase64String(message512Digest.digest((password + passwdSalt).getBytes()));
        return Base64.encodeBase64String(message256Digest.digest(firstResult.concat(passwdSalt).getBytes()));
    }

    /**
     * 生成 B 端密码
     * @param password 原始密码
     * @return 处理后的密码
     */
    public static String getBPassword(String password){
        return generatePasswd(password, B_PASSWD_SALT);
    }

    /**
     * 生成 C 端密码
     * @param password 原始密码
     * @return 处理后的密码
     */
    public static String getCPassword(String password){
        return generatePasswd(password, C_PASSWD_SALT);
    }

    /**
     * 校验 B 端密码
     *
     * @param originPass 原始密码
     * @param stoPass 处理后的密码
     * @return 校验结果
     */
    public static boolean verifyBPassword(String originPass, String stoPass){
        return stoPass.equals(getBPassword(originPass));
    }

    /**
     * 校验 C 端密码
     *
     * @param originPass 原始密码
     * @param stoPass 处理后的密码
     * @return 校验结果
     */
    public static boolean verifyCPassword(String originPass, String stoPass){
        return stoPass.equals(getCPassword(originPass));
    }
}
