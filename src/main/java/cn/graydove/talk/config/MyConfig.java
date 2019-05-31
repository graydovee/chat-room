package cn.graydove.talk.config;

import cn.graydove.talk.token.TokenFactory;
import cn.graydove.talk.token.verifaction.support.DefaultTokenValidator;
import cn.graydove.talk.token.verifaction.support.RedisTokenValidator;
import cn.graydove.talk.token.verifaction.support.TokenValidator;
import cn.graydove.talk.token.verifaction.support.TokenValidatorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public TokenValidator tokenValidator(){
        return new DefaultTokenValidator();
    }

    @Bean
    public TokenValidatorFactory tokenValidatorFactory(){
        return new TokenValidatorFactory();
    }

    @Bean
    public TokenFactory tokenFactory(){
        return new TokenFactory();
    }
}
