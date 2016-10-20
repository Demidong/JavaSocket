package demisocket.tcp;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by demi on 16/10/20.
 */
public interface PacketFactory {
    /**
     * create request packet with command and body
     * @param command
     * @param requestBody
     * @param attach
     * @return
     */
    Packet buildRequestPacket(int command, String requestBody, Map<String, String> attach);

    /**
     * create packet from the stream of server
     * @param source
     * @return
     * @throws IOException
     */
    Packet buildPacket(DataInputStream source) throws IOException;

}
