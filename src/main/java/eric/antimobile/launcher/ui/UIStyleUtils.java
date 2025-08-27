/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eric.antimobile.launcher.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author MY LINH
 */

public class UIStyleUtils {

    // 🎨 Màu chính
    public static final Color TEXT_COLOR = new Color(142, 248, 236);   // xanh ngọc
    public static final Color BG_COLOR = new Color(46, 49, 54); // xám đậm
//    public static final Color GLOW_COLOR = new Color(0, 255, 200, 120);


    // Style cho JButton
    public static JButton styleButton(JButton btn) {
        btn.setForeground(TEXT_COLOR);
        btn.setBackground(BG_COLOR);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        btn.setContentAreaFilled(false);

        btn.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 30; // bo góc
                int w = c.getWidth();
                int h = c.getHeight();

                // nền
                g2.setColor(BG_COLOR);
                g2.fillRoundRect(0, 0, w, h, arc, arc);

                // hiệu ứng glow khi hover
                if (btn.getModel().isRollover()) {
                    g2.setColor(TEXT_COLOR);
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawRoundRect(1, 1, w - 3, h - 3, arc, arc);
                }

                super.paint(g2, c);
                g2.dispose();
            }
        });
        return btn;
    }

    // Style cho JComboBox
    public static JComboBox<String> styleCombo(JComboBox<String> combo) {
        combo.setForeground(TEXT_COLOR);
        combo.setBackground(BG_COLOR);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return combo;
    }

    // Style cho ProgressBar
    public static JProgressBar styleProgress(JProgressBar pgb) {
        pgb.setOpaque(false);
        pgb.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = c.getWidth();
                int h = c.getHeight();
                int arc = 30;

                // nền
                g2.setColor(BG_COLOR);
                g2.fillRoundRect(0, 0, w, h, arc, arc);

                // phần progress
                int amount = (int) (w * progressBar.getPercentComplete());
                g2.setColor(TEXT_COLOR);
                g2.fillRoundRect(0, 0, amount, h, arc, arc);

                g2.dispose();
            }
        });
        return pgb;
    }
}
