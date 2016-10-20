package SocketImpl;


import demisocket.tcp.Packet;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by demi on 16/10/19.
 */
public  class WPBPacketConvert {
    private static WPBPacketConvert instance;
    protected ArrayList<SocketBean> beans = new ArrayList<SocketBean>();//存放发出去的packet对应的SocketBean
    protected HashMap<Integer,ArrayList<WPBResponseCallback>> map= new HashMap<Integer,ArrayList<WPBResponseCallback>>();
    private WPBResponseCallback responce;

    public synchronized void regist(WPBResponseCallback responce, WPBClientForMockServer connection, int cmd, String json) {
        connection.sendPacket(connection.buildRequestPacket(cmd, json, null));
        this.responce = responce;
    }

    public synchronized void registNotify(WPBResponseCallback responce, int cmd) {
        if(map.containsKey(cmd)){
            map.get(cmd).add(responce);
        }else{
            ArrayList<WPBResponseCallback> responses = new ArrayList<WPBResponseCallback>();
            responses.add(responce);
            map.put(cmd,responses);
        }

    }

    public static WPBPacketConvert getInstance() {
        if (null == instance) {
            instance = new WPBPacketConvert();
        }
        return instance;
    }

    public synchronized void getSendPacket(Packet packet) {
        SocketBean bean =new SocketBean();
        bean.seq = packet.getPacketID();
        bean.cmd = packet.getCommand();
        beans.add(bean);
    }

    public synchronized void convertPacket(Packet packet) {
        if(map.containsKey(packet.getCommand())){ //通知分发
            ArrayList<WPBResponseCallback> responses= map.get(packet.getCommand());
            for (WPBResponseCallback resp: responses) {
                resp.onSucess(packet);
            }
        }

        for (SocketBean sb : beans) {  //一对一请求
            if (sb.cmd == packet.getCommand() && packet.getPacketID().equals(sb.seq)) {
                responce.onSucess(packet);
                beans.remove(sb);
                return;
                }
            }
        }
}
