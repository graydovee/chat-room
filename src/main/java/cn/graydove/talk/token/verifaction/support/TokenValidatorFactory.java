package cn.graydove.talk.token.verifaction.support;

import cn.graydove.talk.token.verifaction.TokenRegister;
import cn.graydove.talk.token.verifaction.TokenVerfication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenValidatorFactory {

    @Autowired
    private TokenValidator instance;

    private Logger logger = LoggerFactory.getLogger(TokenValidatorFactory.class);

    private TokenValidator getInstance() {
        if(instance==null){
            synchronized (TokenValidator.class){
                TokenValidator temp = instance;
                if(temp == null){
                    synchronized (TokenValidator.class){
                        try {
                            temp = new DefaultTokenValidator();
                            logger.info("创建默认验证器");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                instance = temp;
            }
        }
        logger.info("验证器为"+instance.getClass());
        return instance;
    }

    public TokenRegister getRegister(){
        return getInstance();
    }

    public TokenVerfication getVerfication(){
        return getInstance();
    }

}
