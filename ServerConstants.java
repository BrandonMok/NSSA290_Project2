import java.text.*;
import java.net.*;

/**
 * ServerConstants
 * Interface for TCP and UDP servers
 */
public interface ServerConstants {

    // Date formatter
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Server Information
    public void serverInfo(int port) throws UnknownHostException;

    // Timestamp
    public String getTimeStamp();
}