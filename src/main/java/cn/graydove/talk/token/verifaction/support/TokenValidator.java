package cn.graydove.talk.token.verifaction.support;

import cn.graydove.talk.token.Token;
import cn.graydove.talk.token.utils.TokenUtils;
import cn.graydove.talk.token.verifaction.TokenRegister;
import cn.graydove.talk.token.verifaction.TokenVerfication;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public abstract class TokenValidator implements TokenRegister, TokenVerfication {


    protected Logger logger;

    protected abstract boolean contains(Token token);

    protected abstract void remove(Token token);
    protected abstract void updateEffetiveTime(Token token,long tims);

    @Override
    public boolean Verify(Token token){
        if(token==null || token.getRSAcode()==null){
            logger.info("无令牌");
            return false;
        }

        try {
            if(!TokenUtils.decrypt(token.getRSAcode()).equals(token.getUserJson())){
                logger.info("令牌密钥不匹配");
                return false;
            }
        }catch (Exception e){
            logger.info("令牌密钥不匹配");
            return false;
        }

        if(contains(token)){

            long now = new Date().getTime();
            if(now < token.getEffetiveTime()){
                updateEffetiveTime(token,now + Token.MaxEffetiveTime);
                return true;
            }else{
                logger.info("令牌已失效");
                remove(token);
                return false;
            }
        }
        logger.info("无该令牌");
        return false;
    }
}
