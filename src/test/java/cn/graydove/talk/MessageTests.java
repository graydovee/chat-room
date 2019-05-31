package cn.graydove.talk;

import cn.graydove.talk.pojo.Message;
import cn.graydove.talk.pojo.User;
import cn.graydove.talk.token.verifaction.TokenVerfication;
import cn.graydove.talk.token.verifaction.support.TokenValidator;
import org.junit.Assert;
import org.junit.Test;
public class MessageTests {

    @Test
    public void msgTest(){
        Message message = new Message();
        message.setTimeNow();
        Assert.assertNotNull(message.getDate());
    }


}
