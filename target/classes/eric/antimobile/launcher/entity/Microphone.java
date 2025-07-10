package eric.antimobile.launcher.entity;

import java.awt.event.KeyEvent;
import java.awt.KeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import javax.sound.sampled.*;
import java.net.*;
import javax.swing.JToggleButton;

public class Microphone {

    private static boolean micOn = false;
    private static boolean vPressed = false;
    private static TargetDataLine microphone;
    private static DatagramSocket socket;
    private static InetAddress targetIP;
    private static int targetPort = 5555;
    private static boolean running = false;
    private static Thread sendThread;

    // Gọi phương thức này khi chương trình khởi động
    public static void autoStart() {
        if (!micOn) {
            enableMicrophone();
            micOn = true;
            System.out.println("Microphone auto-started.");
        }
    }

    // Push-to-Talk (giữ lại nếu bạn vẫn muốn điều khiển bằng phím V)
    public static void initPushToTalk(JToggleButton tglbtnMic) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (tglbtnMic.isSelected()){
                    if (e.getKeyCode() == KeyEvent.VK_V) {
                    if (e.getID() == KeyEvent.KEY_PRESSED && !vPressed) {
                        vPressed = true;
                        startTalking();
                    } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                        vPressed = false;
                        stopTalking();
                    }
                }
                return false;
                }
                return false;
            }
        });
    }

    private static void startTalking() {
        if (!micOn) {
            enableMicrophone();
            micOn = true;
            System.out.println("Active Push-to-Talk");
        }
    }

    private static void stopTalking() {
        if (micOn) {
            disableMicrophone();
            micOn = false;
            System.out.println("Inactive Push-to-Talk");
        }
    }

    public static void enableMicrophone() {
        if (running) return;
        try {
            targetIP = InetAddress.getByName("127.0.0.1"); // Thay bằng IP server nếu cần
            socket = new DatagramSocket();
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            running = true;
            sendThread = new Thread(() -> {
                byte[] buffer = new byte[1024];
                while (running) {
                    int count = microphone.read(buffer, 0, buffer.length);
                    DatagramPacket packet = new DatagramPacket(buffer, count, targetIP, targetPort);
                    try {
                        socket.send(packet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            sendThread.start();

            System.out.println("Microphone enabled.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disableMicrophone() {
        running = false;
        if (microphone != null) {
            microphone.stop();
            microphone.close();
        }
        if (socket != null) {
            socket.close();
        }
        System.out.println("Microphone disabled.");
    }

    private static AudioFormat getAudioFormat() {
        return new AudioFormat(44100.0f, 16, 1, true, false);
    }
}
