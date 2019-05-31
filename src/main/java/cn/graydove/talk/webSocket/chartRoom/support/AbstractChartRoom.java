package cn.graydove.talk.webSocket.chartRoom.support;

import cn.graydove.talk.pojo.Message;
import cn.graydove.talk.pojo.User;
import cn.graydove.talk.webSocket.chartRoom.ChartRoom;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractChartRoom implements ChartRoom {

    private int count;

    protected User hoster;
    protected List<Message> historyMessages = new ArrayList<>();

    @Override
    public List<Message> getHistoryMessages() {
        return historyMessages;
    }

    @Override
    public synchronized int getOnlineCount(){
        return this.count;
    }

    @Override
    public synchronized void addOnlineCount(){
        ++this.count;
    }

    @Override
    public synchronized void subOnlineCount(){
        --this.count;
    }

}
