package cn.graydove.talk.webSocket.chartRoom.factory;

import cn.graydove.talk.pojo.User;
import cn.graydove.talk.webSocket.chartRoom.ChartRoom;

import java.util.Map;

public interface ChatRoomFactory {
    ChartRoom getChatRoom(String roomID);

    Map<String, String> getChatRoomsInfo();

    ChartRoom createRoom(String roomID, User hoster);

    void closeRoom(String roomID);
}
