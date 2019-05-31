package cn.graydove.talk.config;

import cn.graydove.talk.token.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.rmi.UnknownHostException;

@Configuration
public class MyRedisConfig {
    @Bean
    public RedisTemplate<Object, Token> tokenRedisTemplate(RedisConnectionFactory redisConnectionFactory)throws UnknownHostException{
        RedisTemplate<Object, Token> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<Token> tokenJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Token.class);
        template.setDefaultSerializer(tokenJackson2JsonRedisSerializer);

        return template;
    }
}
