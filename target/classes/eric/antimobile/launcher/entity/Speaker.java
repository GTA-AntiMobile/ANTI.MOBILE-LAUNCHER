package eric.antimobile.launcher.entity;

import javax.sound.sampled.*;
import java.net.*;

public class Speaker {

    private static SourceDataLine speaker;
    private static DatagramSocket socket;
    private static boolean running = false;
    private static Thread receiveThread;
    private static int listenPort = 5555;

    // Tự động khởi động loa khi mở chương trình
    public static void autoStart() {
        if (!running) {
            unmuteOutput();
            System.out.println("Speaker auto-started.");
        }
    }

    public static void unmuteOutput() {
        if (running) return;
        try {
            socket = new DatagramSocket(listenPort);
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            speaker = (SourceDataLine) AudioSystem.getLine(info);
            speaker.open(format);
            speaker.start();

            running = true;
            receiveThread = new Thread(() -> {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                while (running) {
                    try {
                        socket.receive(packet);
                        speaker.write(packet.getData(), 0, packet.getLength());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            receiveThread.start();

            System.out.println("Speaker unmuted.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void muteOutput() {
        running = false;
        if (speaker != null) {
            speaker.stop();
            speaker.close();
        }
        if (socket != null) {
            socket.close();
        }
        System.out.println("Speaker muted.");
    }

    private static AudioFormat getAudioFormat() {
        return new AudioFormat(44100.0f, 16, 1, true, false);
    }
}
