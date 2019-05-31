package cn.graydove.talk.webSocket;

import cn.graydove.talk.pojo.User;
import cn.graydove.talk.token.utils.TokenUtils;
import cn.graydove.talk.webSocket.chartRoom.ChartRoom;
import cn.graydove.talk.webSocket.chartRoom.factory.ChatRoomFactory;
import cn.graydove.talk.webSocket.chartRoom.factory.support.DefaultRoomFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/talkroom/{roomID}/{token}")
@Component
public class TalkWebSocket {

    private static Logger log = LoggerFactory.getLogger(TalkWebSocket.class);
    private static ChatRoomFactory chatRoomFactory = DefaultRoomFactory.getInstance();
    private static ObjectMapper objectMapper = new ObjectMapper();


    private ChartRoom chartRoom;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收客户端信息
    private String roomID="";
    private User user;

    public User getUser() {
        return user;
    }

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("roomID") String roomID,@PathParam("token") String token) {
        this.session = session;
        this.roomID=roomID;
        try {
            user = objectMapper.readValue(TokenUtils.decrypt(token),User.class);
        } catch (IOException e) {
            log.info("无效令牌");
            return;
        }
        log.info(user.getNickname()+"进入"+roomID+"号房间");

        chartRoom = chatRoomFactory.getChatRoom(roomID);
        try {
            if(chartRoom==null) {
                sendMessage("房间不存在");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        chartRoom.enterRoom(this);     //加入set中

        try {
            this.sendMessage(objectMapper.writeValueAsString(chartRoom.getHistoryMessages()));
        } catch (IOException e) {
            log.error("历史记录异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(chartRoom!=null){
            chartRoom.outRoom(this);
            log.info("有一连接关闭！当前在线人数为" + chartRoom.getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口"+roomID+"的信息:"+message);
        //群发消息
        if (chartRoom!=null)
            chartRoom.sendAll(message,user);
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(roomID+":"+user+"ws连接发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


}