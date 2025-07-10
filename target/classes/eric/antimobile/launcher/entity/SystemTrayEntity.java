package eric.antimobile.launcher.entity;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SystemTrayEntity {
    private TrayIcon trayIcon;
    private SystemTray tray;

    public SystemTrayEntity() {
        try {
            System.setProperty("java.awt.headless", "false");
            Toolkit.getDefaultToolkit();

            if (!SystemTray.isSupported()) {
                System.out.println("System tray không được hỗ trợ.");
                return;
            }

            JFrame frame = getMainFrame();
            if (frame == null) {
                System.out.println("Không tìm thấy JFrame launcher.");
                return;
            }

            tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("/eric/antimobile/launcher/icon/logo.png");

            PopupMenu popup = new PopupMenu();

            MenuItem openItem = new MenuItem("Mở lại Launcher");
            openItem.addActionListener(e -> showLauncher(frame));

            MenuItem closeItem = new MenuItem("Close (Ẩn xuống Taskbar)");
            closeItem.addActionListener(e -> frame.setVisible(false));

            MenuItem exitItem = new MenuItem("Thoát hoàn toàn");
            exitItem.addActionListener(e -> {
                tray.remove(trayIcon);
                System.exit(0);
            });

            popup.add(openItem);
            popup.add(closeItem);
            popup.addSeparator();
            popup.add(exitItem);

            trayIcon = new TrayIcon(image, "GTA Launcher", popup);
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(e -> showLauncher(frame));

            tray.add(trayIcon);

            // Ẩn khi thu nhỏ cửa sổ
            frame.addWindowStateListener(e -> {
                if (e.getNewState() == Frame.ICONIFIED) {
                    frame.setVisible(false);
                }
            });

            // Ẩn khi đóng
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(false);
                }
            });

        } catch (Exception ex) {
            System.out.println("Lỗi SystemTray: " + ex.getMessage());
        }
    }

    private void showLauncher(JFrame frame) {
        frame.setVisible(true);
        frame.setExtendedState(JFrame.NORMAL);
        frame.toFront();
    }

    private JFrame getMainFrame() {
        try {
            return (JFrame) Class.forName("MainLauncher") // <-- Đổi tên class nếu cần
                    .getDeclaredField("frame")
                    .get(null);
        } catch (Exception e) {
            return null;
        }
    }
}
