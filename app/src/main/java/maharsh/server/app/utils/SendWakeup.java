package maharsh.server.app.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import maharsh.server.app.datamodel.ServerTable;

public class SendWakeup {

    public static void to(ServerTable details) throws SocketException, UnknownHostException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(details.getServerBroadcastAddress());

        byte[] buf = constructMessage(details.getServerMacAddress());

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 9);

        try {
            socket.send(packet);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static byte[] constructMessage(String serverMac) {

        String[] splitMac = serverMac.split("[:\\-]");
        byte[] mac = new byte[6];
        for (int i = 0; i < 6; i++) {
            // 16 for hex value parsing
            int hex = Integer.parseInt(splitMac[i], 16);
            mac[i] = (byte) hex;
        }

        final byte[] bytes = new byte[102];
        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) 0xff;
        }
        for (int i = 6; i < bytes.length; i += mac.length) {
            System.arraycopy(mac, 0, bytes, i, mac.length);
        }
        return bytes;
    }

}
