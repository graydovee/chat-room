package cn.graydove.talk.controller;

import cn.graydove.talk.pojo.User;
import cn.graydove.talk.token.Token;
import cn.graydove.talk.token.verifaction.TokenVerfication;
import cn.graydove.talk.token.verifaction.support.DefaultTokenValidator;
import cn.graydove.talk.token.verifaction.support.TokenValidator;
import cn.graydove.talk.token.verifaction.support.TokenValidatorFactory;
import cn.graydove.talk.webSocket.chartRoom.ChartRoom;
import cn.graydove.talk.webSocket.chartRoom.factory.ChatRoomFactory;
import cn.graydove.talk.webSocket.chartRoom.factory.support.DefaultRoomFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class VerificationController {
    private Logger logger = LoggerFactory.getLogger(VerificationController.class);

    @Autowired
    private TokenValidatorFactory tokenValidatorFactory;

    private ObjectMapper mapper = new ObjectMapper();
    private ChatRoomFactory chatRoomFactory = DefaultRoomFactory.getInstance();
    private int roomNumber = 1;

    @RequestMapping("/room_list")
    public Map<String, String> getRooms(){
        Map<String, String> map = chatRoomFactory.getChatRoomsInfo();
        logger.info(map.toString());
        return map;
    }

    @RequestMapping("/create_room")
    public String createChatRoom(String tokenJson){
        Token token = verify(tokenJson);
        if(token==null){
            return "0";
        }
        try {
            User user = mapper.readValue(token.getUserJson(), User.class);
            String number;
            do {
                number = String.format("%04d",roomNumber);
                roomNumber++;
            } while(chatRoomFactory.getChatRoom(number)!=null);
            ChartRoom chartRoom = chatRoomFactory.createRoom(number,user);
            if(chartRoom==null){
                logger.info("重复创建房间");
                return "0";
            }

            logger.info(user.getUsername()+"创建房间："+number);
            return "1";
        } catch (IOException e) {
            return "0";
        }
    }

    @RequestMapping("/verify")
    public Token verify(String tokenJson){
        if(tokenJson==null){
            return null;
        }
        logger.info(tokenJson);
        Token token = null;
        Token t = null;
        try {
            t = mapper.readValue(tokenJson,Token.class);
        } catch (IOException e) {
            logger.info("令牌伪造");
        }
        if(tokenValidatorFactory.getVerfication().Verify(t)){
            token = t;
        }else{
            logger.info("验证失败");
        }

        return token;
    }
}
