package cn.graydove.talk;

import cn.graydove.talk.pojo.User;
import cn.graydove.talk.token.Token;
import cn.graydove.talk.token.TokenFactory;
import cn.graydove.talk.token.utils.TokenUtils;
import cn.graydove.talk.token.verifaction.TokenRegister;
import cn.graydove.talk.token.verifaction.TokenVerfication;
import cn.graydove.talk.token.verifaction.support.DefaultTokenValidator;
import cn.graydove.talk.token.verifaction.support.RedisTokenValidator;
import cn.graydove.talk.token.verifaction.support.TokenValidator;
import cn.graydove.talk.token.verifaction.support.TokenValidatorFactory;
import cn.graydove.talk.utils.MD5Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TalkApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class securityTests {

    private String exceptedJson = "{\"uid\":1,\"username\":\"username\",\"nickname\":null,\"password\":\"123123\"}";
    private User user = new User();


    @Autowired
    private TokenValidator tokenValidator;

    @Autowired
    private TokenValidatorFactory tokenValidatorFactory;

    @Autowired
    private TokenFactory tokenFactory;

    private TokenRegister tokenRegister;
    private TokenVerfication tokenVerfication;

    @Autowired
    private ApplicationContext ioc;

    @Before
    public void before(){
        user.setPassword("123123");
        user.setUid(1);
        user.setUsername("username");
        tokenVerfication = tokenValidatorFactory.getVerfication();
        tokenRegister = tokenValidatorFactory.getRegister();
    }

    @Test
    public void Md5(){
        String password = user.getPassword();
        String md5 = MD5Utils.encrypt(password);
        Assert.assertEquals("4297f44b13955235245b2497399d7a93",md5);
    }

    @Test
    public void testJson() throws Exception {

		ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(user);
        Assert.assertEquals(exceptedJson,json);

        User u = mapper.readValue(json, User.class);
        Assert.assertEquals(user,u);
    }


    @Test
    public void testRSA() {
        String Token = TokenUtils.encrypt(exceptedJson);
        String decrypt = TokenUtils.decrypt(Token);
        Assert.assertEquals(decrypt,exceptedJson);
    }

    @Test
    public void testToken(){
        Token token = tokenFactory.createToken(user);
        String RSA = token.encrypt();

        Token t = TokenUtils.decryptToBean(RSA);
        Assert.assertEquals(t,token);
        Assert.assertTrue(tokenVerfication.Verify(token));
    }


    @Test
    public void tokenValidatorTest(){
        Assert.assertTrue(ioc.containsBean("tokenValidator"));

        Assert.assertNotNull(tokenValidatorFactory);
        TokenValidator tokenValidator = (TokenValidator)ioc.getBean("tokenValidator");
        Assert.assertEquals(tokenValidator.getClass(), RedisTokenValidator.class);
    }

    @Test
    public void ConfigTest(){
        Assert.assertNotNull(tokenValidator);
        Assert.assertNotNull(tokenValidatorFactory);
        Assert.assertNotNull(tokenFactory);
        Assert.assertTrue(tokenValidatorFactory.getRegister()==tokenValidator);
        Assert.assertTrue(tokenFactory.getTokenValidatorFactory()==tokenValidatorFactory);
    }
}
