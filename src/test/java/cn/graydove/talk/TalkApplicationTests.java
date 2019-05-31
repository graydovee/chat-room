package cn.graydove.talk;

import cn.graydove.talk.pojo.User;
import cn.graydove.talk.service.UserService;
import cn.graydove.talk.token.Token;
import cn.graydove.talk.token.verifaction.support.DefaultTokenValidator;
import cn.graydove.talk.token.verifaction.support.RedisTokenValidator;
import cn.graydove.talk.token.verifaction.support.TokenValidator;
import cn.graydove.talk.utils.MD5Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TalkApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TalkApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	RedisTemplate redisTemplate;

	private User user;



	@Before
	public void before(){
		user = new User();
		user.setUsername("username");
		user.setNickname("admin");
		user.setPassword(MD5Utils.encrypt("123456"));
	}

//	@Test
//	public void registerTest() {
//		int c = userService.register(user);
//		Assert.assertNotEquals(0,c);
//	}

	@Test
	public void loginTest(){
		User u = userService.login(user);
		Assert.assertNotNull(u);
	}

	@Test
	public void TestRedis(){
	}

}
