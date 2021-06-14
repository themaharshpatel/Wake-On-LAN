package maharsh.server.app.datamodel;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SavedServersTable")
public class ServerTable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String serverName;
    private String serverAddress;
    private int serverPort;
    private String serverBroadcastAddress;
    private String serverMacAddress;
    private int isShutDownEnabled;

    public ServerTable( String serverName, String serverAddress, int serverPort, String serverBroadcastAddress, String serverMacAddress, int isShutDownEnabled) {
        this.serverName = serverName;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.serverBroadcastAddress = serverBroadcastAddress;
        this.serverMacAddress = serverMacAddress;
        this.isShutDownEnabled = isShutDownEnabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerBroadcastAddress() {
        return serverBroadcastAddress;
    }

    public void setServerBroadcastAddress(String serverBroadcastAddress) {
        this.serverBroadcastAddress = serverBroadcastAddress;
    }

    public String getServerMacAddress() {
        return serverMacAddress;
    }

    public void setServerMacAddress(String serverMacAddress) {
        this.serverMacAddress = serverMacAddress;
    }

    public int getIsShutDownEnabled() {
        return isShutDownEnabled;
    }

    public void setIsShutDownEnabled(int isShutDownEnabled) {
        this.isShutDownEnabled = isShutDownEnabled;
    }
}
