import java.text.SimpleDateFormat;
import java.net.UnknownHostException;

/**
 * ServerConstants
 * Interface for TCP and UDP servers
 */
public interface ServerConstants {

    // Date formatter
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // UDPServer + TCPServer both need to display serverInfo
    public void serverInfo(int port) throws UnknownHostException;

    // Timestamp
    public String getTimeStamp();
}