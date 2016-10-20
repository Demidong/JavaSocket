package SocketImpl;


import demisocket.tcp.Packet;
import demisocket.tcp.PacketFactory;
import demisocket.tcp.PacketListener;
import demisocket.tcp.SocketConnection;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

/**
 * Created by demi on 16/10/20.
 */
public class WPBClientForMockServer extends SocketConnection implements PacketFactory {
    public WPBClientForMockServer(String host, int port) {
        super(host, port);
    }

    @Override
    protected PacketFactory createPacketFactory() {
        return this;
    }

    @Override
    public Packet buildRequestPacket(int command, String body, Map<String, String> attach) {
        return new WPBPacket(command,body);
    }

    @Override
    public Packet buildPacket(DataInputStream source) throws IOException {
        return WPBPacket.build(source);
    }

    public static void main(String[] args) {
        WPBClientForMockServer client = new WPBClientForMockServer("127.0.0.1",9103);
        client.addPacketListener(new PacketListener() {
            @Override
            public void onSendSuccessful(Packet packet) {
                System.out.println("send: " + packet);
                WPBPacketConvert.getInstance().getSendPacket(packet);
            }

            @Override
            public void processPacket(Packet packet) {
                System.out.println("rec: " + packet);
                WPBPacketConvert.getInstance().convertPacket(packet);
            }

        });
        client.connectAndStartWatch();
        WPBResponseCallback price = new WPBResponseCallback(){

            @Override
            public void onSucess(Packet packet) {
                System.out.println("convert1: " + packet);
            }
        };
        WPBPacketConvert.getInstance().registNotify(price ,WPBPacket.CMD_PRICE);
        WPBResponseCallback price2 = new WPBResponseCallback(){

            @Override
            public void onSucess(Packet packet) {
                System.out.println("convert2: " + packet);
            }
        };
        WPBPacketConvert.getInstance().registNotify(price2 ,WPBPacket.CMD_PRICE);
        while (true) {
            try {
                String json = "{\"productId\" : \"1\",\"isJuan\" : \"0\",\"type\" : \"2\",\"sl\" : \"1\"}";
                WPBResponseCallback order = new WPBResponseCallback(){

                    @Override
                    public void onSucess(Packet packet) {
                        System.out.println("convert: " + packet);
                    }

                };
                WPBPacketConvert.getInstance().regist(order,client,WPBPacket.CMD_CREATE_ORDER,json);

                Thread.sleep(new Random().nextInt(4000) + 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
