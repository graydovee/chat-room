package cn.graydove.talk.token.utils;

import cn.graydove.talk.pojo.User;
import cn.graydove.talk.token.Token;
import cn.graydove.talk.utils.RSAUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

public class TokenUtils {
    private static Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    private static ObjectMapper mapper = new ObjectMapper();
    private static RSAPublicKey publicKey;
    private static RSAPrivateKey privateKey;

    static {
        Map<String,String> map = RSAUtils.createKeys(1024);
        try {
            privateKey = RSAUtils.getPrivateKey(map.get("privateKey"));
            publicKey = RSAUtils.getPublicKey(map.get("publicKey"));
        } catch (Exception e) {
            logger.error("公钥异常");
        }

    }

    public static String encrypt(String msg){
        return RSAUtils.publicEncrypt(msg,publicKey);
    }


    public static String encrypt(Token token){
        String json;
        try {
            json = mapper.writeValueAsString(token);
        } catch (JsonProcessingException e) {
            return null;
        }
        return encrypt(json);
    }

    public static String decrypt(String msg){
        if(msg==null)
            return null;
        return RSAUtils.privateDecrypt(msg,privateKey);
    }

    public static Token decryptToBean(String msg){
        String json = decrypt(msg);
        Token token = null;
        try {
            token = mapper.readValue(json, Token.class);
        } catch (IOException e) {
            logger.error("密码不匹配"+json);
        }
        return token;
    }
}
