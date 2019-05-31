package cn.graydove.talk.webSocket.chartRoom;

import cn.graydove.talk.pojo.Message;
import cn.graydove.talk.pojo.User;
import cn.graydove.talk.webSocket.TalkWebSocket;

import java.util.List;

public interface ChartRoom {
    void enterRoom(TalkWebSocket talkWebSocket);

    void outRoom(TalkWebSocket talkWebSocket);

    void sendAll(String msg,User sender);

    int getOnlineCount();

    void addOnlineCount();

    void subOnlineCount();

    User getHoster();

    List<Message> getHistoryMessages();

}
