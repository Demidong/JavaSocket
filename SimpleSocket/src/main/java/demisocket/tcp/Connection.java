package demisocket.tcp;

/**
 * Created by demi on 16/10/20.
 */
public interface Connection {
    void connect() throws Exception;

    void disconnect();

    boolean isConnected();
}
