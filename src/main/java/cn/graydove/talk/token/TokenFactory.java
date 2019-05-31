package cn.graydove.talk.token;

import cn.graydove.talk.pojo.User;
import cn.graydove.talk.token.utils.TokenUtils;
import cn.graydove.talk.token.verifaction.support.TokenValidatorFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;

public class TokenFactory {

    private static Logger logger = LoggerFactory.getLogger(TokenFactory.class);
    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TokenValidatorFactory tokenValidatorFactory;

    public Token createToken(User user) {
        String uuid = UUID.randomUUID().toString();
        String u;
        try {
            u = mapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            logger.error("创建令牌失败：json转化错误");
            return null;
        }
        String rsa = TokenUtils.encrypt(u);
        long time = new Date().getTime()+Token.MaxEffetiveTime;
        Token token = new Token(uuid,u,rsa,time);
        tokenValidatorFactory.getRegister().register(token);
        return token;
    }

    public TokenValidatorFactory getTokenValidatorFactory() {
        return tokenValidatorFactory;
    }

}
