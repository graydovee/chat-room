package cn.graydove.talk.token.verifaction.support;

import cn.graydove.talk.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class DefaultTokenValidator extends TokenValidator {
    private static CopyOnWriteArraySet<Token> tokens = new CopyOnWriteArraySet<>();

    public DefaultTokenValidator() {
        logger = LoggerFactory.getLogger(DefaultTokenValidator.class);
    }

    @Override
    protected boolean contains(Token token) {
        return tokens.contains(token);
    }

    @Override
    protected void remove(Token token) {
        tokens.remove(token);
    }

    @Override
    protected void updateEffetiveTime(Token token, long tims) {
        token.setEffetiveTime(tims);
    }


    @Override
    public void register(Token token){
        tokens.add(token);
    }

}
