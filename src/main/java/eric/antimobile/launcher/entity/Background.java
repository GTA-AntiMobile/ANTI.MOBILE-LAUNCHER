package eric.antimobile.launcher.entity;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Background {
    private ImageIcon imageIcon;

    public Background(String path) {
        URL location = getClass().getResource(path);
        if (location != null) {
            imageIcon = new ImageIcon(location);
        } else {
            throw new IllegalArgumentException("Không tìm thấy ảnh: " + path);
        }
    }

    public JLabel getBackgroundLabel() {
        return new JLabel(imageIcon);
    }

    public void applyTo(JFrame frame) {
        
        JLabel bgLabel = getBackgroundLabel();
        bgLabel.setLayout(null); // Để có thể thêm thành phần khác nếu cần
        bgLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                // Khi click vào background, chuyển focus sang nơi khác
                frame.requestFocusInWindow(); // Chuyển focus khỏi textfield
            }
        });
        
        frame.setContentPane(getBackgroundLabel());
        frame.setSize(getWidth(), getHeight());
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Center on screen
    }

    public int getWidth() {
        return imageIcon.getIconWidth();
    }

    public int getHeight() {
        return imageIcon.getIconHeight();
    }

    public Image getImage() {
        return imageIcon.getImage();
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }
}
