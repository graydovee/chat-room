package cn.graydove.talk.webSocket.chartRoom.support;

import cn.graydove.talk.pojo.Message;
import cn.graydove.talk.pojo.User;
import cn.graydove.talk.webSocket.TalkWebSocket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class DefalultChartRoom extends AbstractChartRoom {
    private Logger logger = LoggerFactory.getLogger(DefalultChartRoom.class);
    private CopyOnWriteArraySet<TalkWebSocket> talkWebSockets;

    public DefalultChartRoom(User hoster) {
        this.hoster = hoster;
        this.talkWebSockets = new CopyOnWriteArraySet<>();
    }

    public User getHoster() {
        return hoster;
    }


    public void setHoster(User hoster) {
        this.hoster = hoster;
    }

    @Override
    public void enterRoom(TalkWebSocket talkWebSocket) {
        this.talkWebSockets.add(talkWebSocket);
        addOnlineCount();
    }

    @Override
    public void outRoom(TalkWebSocket talkWebSocket) {
        this.talkWebSockets.remove(talkWebSocket);
        subOnlineCount();
    }

    @Override
    public void sendAll(String msg, User sender) {
        Message message = new Message();
        message.setTimeNow();
        message.setInfo(msg);
        message.setUser(sender.getNickname());

        historyMessages.add(message);

        for(TalkWebSocket ts:talkWebSockets){
            try {
                if(!sender.equals(ts.getUser())){
                    ts.sendMessage(message.toString());
                }
            } catch (IOException e) {
                logger.error("IO异常");
            }
        }
    }
}
