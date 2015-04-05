import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Launcher {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);

            while (true) {
                System.out.println("Waiting for message...");

                byte[] recvBuf = new byte[15000];

                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);

                socket.receive(packet);

                System.out.println("Message received from: " + packet.getAddress().getHostAddress() + ":" + packet.getPort() + " : " + new String(packet.getData()));

                byte[] sendData = "I am DummyService".getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                socket.send(sendPacket);
                System.out.println("Sent response to: " + sendPacket.getAddress().getHostAddress() + ":" + sendPacket.getPort());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
