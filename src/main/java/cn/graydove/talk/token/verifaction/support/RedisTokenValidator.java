package cn.graydove.talk.token.verifaction.support;

import cn.graydove.talk.token.Token;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTokenValidator extends TokenValidator{
    private static final String key = "token";

    @Autowired
    RedisTemplate<Object,Token> tokenRedisTemplate;

    public RedisTokenValidator() {
        logger = LoggerFactory.getLogger(RedisTokenValidator.class);
    }

    @Override
    protected boolean contains(Token token) {
        if(tokenRedisTemplate==null || token==null){
            return false;
        }
        Boolean ret = tokenRedisTemplate.opsForSet().isMember(key, token);
        if(ret==null)
            return false;
        return ret;
    }

    @Override
    protected void remove(Token token) {
        tokenRedisTemplate.opsForSet().remove(key,token);
    }

    @Override
    protected void updateEffetiveTime(Token token, long tims) {
        token.setEffetiveTime(tims);
        register(token);
    }

    @Override
    public void register(Token token) {
        tokenRedisTemplate.opsForSet().add(key,token);
    }
}
