package eric.antimobile.launcher.entity;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class PushToTalkManager {

    private static boolean micOn = false;

    public static void init() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            private boolean vPressed = false;

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_V) {
                    if (e.getID() == KeyEvent.KEY_PRESSED && !vPressed) {
                        vPressed = true;
                        System.out.println("Active Push-to-talk");
                        startTalking();
                    } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                        vPressed = false;
                        stopTalking();
                    }
                }
                return false; // Cho phép tiếp tục xử lý các phím khác
            }
        });
    }

    private static void startTalking() {
        if (!micOn) {
            eric.antimobile.launcher.entity.Microphone.enableMicrophone();
            micOn = true;
        }
    }

    private static void stopTalking() {
        if (micOn) {
            eric.antimobile.launcher.entity.Microphone.disableMicrophone();
            micOn = false;
        }
    }
}
