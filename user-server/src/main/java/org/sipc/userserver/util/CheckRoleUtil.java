package org.sipc.userserver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.sipc.userserver.pojo.domain.UserB;
import org.sipc.userserver.pojo.domain.UserC;

import java.util.Calendar;

@Slf4j
public class CheckRoleUtil {
    private static final String BTokenPara = "45j9089uy89*&^&*67er86790-_*89689t";
    private static final String CTokenPara = "809&*(GHoi678^&*(^&TGUY&*%67r65#$@";
    private static final String BUserIdTokenKey = "buidtkk";
    private static final String CUserIdTokenKey = "cuidtkk";
    private static final String BUserNameTokenKey = "buntkk";
    private static final String CUserNameTokenKey = "cuntkk";


    /**
     * 生成 B 端 Token
     * @param userB B 端用户信息
     * @return Token
     */
    public static String createBToken(UserB userB){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_MONTH, 7);
        return JWT.create()
                .withClaim(BUserIdTokenKey, userB.getId())
                .withClaim(BUserNameTokenKey, userB.getName())
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC512(BTokenPara));
    }

    /**
     * 生成 C 端 Token
     * @param userB C 端用户信息
     * @return Token
     */
    public static String createCToken(UserC userB){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DAY_OF_MONTH, 7);
        return JWT.create()
                .withClaim(CUserIdTokenKey, userB.getId())
                .withClaim(CUserNameTokenKey, userB.getName())
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC512(CTokenPara));
    }

    /**
     * 校验并解析 B 端 Token
     * @param token Token
     * @return 解析结果
     */
    public static UserB verifyBToken(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(BTokenPara)).build();
        DecodedJWT verify;
        try {
            verify = verifier.verify(token);
        } catch (JWTVerificationException e){
            log.info("Verify BToken \"" + token + "\" Error: " + e.getMessage());
            return null;
        }
        UserB result = new UserB();
        result.setId(verify.getClaim(BUserIdTokenKey).asInt());
        result.setName(verify.getClaim(BUserNameTokenKey).asString());
        return result;
    }

    /**
     * 校验并解析 C 端 Token
     * @param token Token
     * @return 解析结果
     */
    public static UserC verifyCToken(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(CTokenPara)).build();
        DecodedJWT verify;
        try {
            verify = verifier.verify(token);
        } catch (JWTVerificationException e){
            log.info("Verify CToken \"" + token + "\" Error: " + e.getMessage());
            return null;
        }
        UserC result = new UserC();
        result.setId(verify.getClaim(CUserIdTokenKey).asInt());
        result.setName(verify.getClaim(CUserNameTokenKey).asString());
        return result;
    }
}
