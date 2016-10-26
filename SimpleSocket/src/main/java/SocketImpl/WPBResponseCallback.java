package SocketImpl;


import demisocket.tcp.Packet;

/**
 * Created by demi on 16/10/19.
 */
public abstract class WPBResponseCallback {

    public abstract void onSucess(Packet packet);

}
