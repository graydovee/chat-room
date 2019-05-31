package cn.graydove.talk.webSocket.chartRoom.factory.support;

import cn.graydove.talk.pojo.User;
import cn.graydove.talk.webSocket.chartRoom.ChartRoom;
import cn.graydove.talk.webSocket.chartRoom.factory.ChatRoomFactory;
import cn.graydove.talk.webSocket.chartRoom.support.DefalultChartRoom;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultRoomFactory implements ChatRoomFactory {
    private static ConcurrentHashMap<String, ChartRoom> chartRooms;
    private static DefaultRoomFactory instance;

    private DefaultRoomFactory() {
        if(instance!=null){
            throw new RuntimeException();
        }
        chartRooms = new ConcurrentHashMap<>();
    }

    public static DefaultRoomFactory getInstance() {
        if(instance==null){
            synchronized (DefaultRoomFactory.class){
                DefaultRoomFactory temp = instance;
                if(temp == null){
                    synchronized (DefaultRoomFactory.class){
                        temp = new DefaultRoomFactory();
                    }
                }
                instance = temp;
            }
        }
        return instance;
    }



    @Override
    public ChartRoom getChatRoom(String roomID) {
        return chartRooms.get(roomID);
    }

    @Override
    public Map<String,String> getChatRoomsInfo() {
        Map<String, String> map = new HashMap<>();
        for(String key : chartRooms.keySet()){
            ChartRoom c = chartRooms.get(key);
            map.put(key,c.getHoster().getNickname()+"的房间");
        }
        return map;
    }

    @Override
    public synchronized ChartRoom createRoom(String roomID, User hoster) {
        if(roomID==null || chartRooms.get(roomID)!=null || hoster==null){
            return null;
        }
        ChartRoom tempChartRoom = chartRooms.get(roomID);
        for(ChartRoom chartRoom:chartRooms.values()){
            if(hoster.equals(chartRoom.getHoster())){
                return null;
            }
        }
        if(tempChartRoom==null){
            tempChartRoom = new DefalultChartRoom(hoster);
            chartRooms.put(roomID, tempChartRoom);
        }
        return tempChartRoom;
    }

    @Override
    public synchronized void closeRoom(String roomID) {
        if(chartRooms.get(roomID)!=null){
            chartRooms.remove(roomID);
        }
    }
}
