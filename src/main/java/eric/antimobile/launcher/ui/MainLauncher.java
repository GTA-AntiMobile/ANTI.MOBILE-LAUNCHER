package eric.antimobile.launcher.ui;

import eric.antimobile.launcher.entity.Background;
import eric.antimobile.launcher.entity.Config;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import eric.antimobile.launcher.entity.Data;
import eric.antimobile.launcher.entity.ImageButton;
import eric.antimobile.launcher.entity.Link;
import eric.antimobile.launcher.entity.Login;
import eric.antimobile.launcher.entity.Microphone;
import static eric.antimobile.launcher.entity.Properties1.Properties;
import eric.antimobile.launcher.entity.Speaker;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MainLauncher extends javax.swing.JFrame {

    public static MainLauncher frame;

    private JFileChooser fileChooser = new JFileChooser();
    private String selectPatch;

    public MainLauncher() {

        Background bg = new Background(Config.getBgPatch());
        bg.applyTo(this);

        frame = this;

        setUndecorated(true);

        initComponents();

        // Resize image to btnLogin
        resizeLoginButtonIcon();
        Properties(this);

        Data.createData(txtUsername, lblFolder);
        Data.loadData(txtUsername, lblFolder);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLogin = new javax.swing.JButton();
        txtUsername = new javax.swing.JTextField();
        btnMinimize = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        lblFolder = new javax.swing.JLabel();
        btnInstall = new javax.swing.JButton();
        lblEric = new javax.swing.JLabel();
        tglbtnMic = new javax.swing.JToggleButton();
        tglbtnVol = new javax.swing.JToggleButton();
        btnSettings = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        setSize(new java.awt.Dimension(0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnLogin.setIcon(
                new javax.swing.ImageIcon(getClass().getResource("/eric/antimobile/launcher/icon/nutdangnhap.png"))); // NOI18N
        btnLogin.setText("enter");
        btnLogin.setContentAreaFilled(false);
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        txtUsername.setBackground(new java.awt.Color(204, 204, 255));
        txtUsername.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        txtUsername.setForeground(new java.awt.Color(153, 153, 153));
        txtUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsername.setText("Nhập tên nhân vật.");
        txtUsername.setToolTipText("");
        txtUsername.setBorder(null);
        txtUsername.setCaretColor(new java.awt.Color(0, 102, 102));
        txtUsername.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        txtPassword = new javax.swing.JPasswordField();
        txtPassword.setBackground(new java.awt.Color(204, 204, 255));
        txtPassword.setFont(new java.awt.Font("Arial", 3, 18));
        txtPassword.setForeground(new java.awt.Color(153, 153, 153));
        txtPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassword.setText("Nhập mật khẩu.");
        txtPassword.setBorder(null);
        txtPassword.setCaretColor(new java.awt.Color(0, 102, 102));
        txtPassword.setEchoChar((char) 0);
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (String.valueOf(txtPassword.getPassword()).equals("Nhập mật khẩu.")) {
                    txtPassword.setText("");
                    txtPassword.setEchoChar('•');
                    txtPassword.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
                    txtPassword.setText("Nhập mật khẩu.");
                    txtPassword.setEchoChar((char) 0);
                    txtPassword.setForeground(new Color(153, 153, 153));
                }
            }
        });

        btnMinimize.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        btnMinimize.setForeground(new java.awt.Color(255, 255, 255));
        btnMinimize.setText("--");
        btnMinimize.setBorder(null);
        btnMinimize.setContentAreaFilled(false);
        btnMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMinimize.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMinimize.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnMinimize.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        btnMinimize.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinimizeActionPerformed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Palatino Linotype", 1, 24)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setText("X");
        btnExit.setBorder(null);
        btnExit.setContentAreaFilled(false);
        btnExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExit.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnExit.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        lblFolder.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        lblFolder.setForeground(new java.awt.Color(255, 255, 255));
        lblFolder.setText("Chọn đường dẫn đến thư mục chứa samp.exe");
        lblFolder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFolder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFolderMouseClicked(evt);
            }
        });

        btnInstall.setFont(new java.awt.Font("Segoe UI Semibold", 2, 14)); // NOI18N
        btnInstall.setForeground(new java.awt.Color(0, 255, 0));
        btnInstall.setIcon(
                new javax.swing.ImageIcon(getClass().getResource("/eric/antimobile/launcher/icon/install_logo1.png"))); // NOI18N
        btnInstall.setText("Install");
        btnInstall.setContentAreaFilled(false);
        btnInstall.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInstall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInstallActionPerformed(evt);
            }
        });

        lblEric.setFont(new java.awt.Font("Sitka Small", 3, 12)); // NOI18N
        lblEric.setForeground(new java.awt.Color(255, 255, 255));
        lblEric.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblEric.setText("© COPYRIGHT BY ERIC ");
        lblEric.setToolTipText("");
        lblEric.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblEric.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblEric.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEricMouseClicked(evt);
            }
        });

        tglbtnMic.setBackground(new Color(0, 0, 0, 0));
        tglbtnMic.setIcon(
                new javax.swing.ImageIcon(getClass().getResource("/eric/antimobile/launcher/icon/microphone1_1.png"))); // NOI18N
        tglbtnMic.setBorder(null);
        tglbtnMic.setSelectedIcon(new javax.swing.ImageIcon(
                getClass().getResource("/eric/antimobile/launcher/icon/mute-microphone1.png"))); // NOI18N
        tglbtnMic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglbtnMicActionPerformed(evt);
            }
        });

        tglbtnVol.setBackground(new Color(0, 0, 0, 0));
        tglbtnVol.setIcon(
                new javax.swing.ImageIcon(getClass().getResource("/eric/antimobile/launcher/icon/volume-up1.png"))); // NOI18N
        tglbtnVol.setBorder(null);
        tglbtnVol.setSelectedIcon(
                new javax.swing.ImageIcon(getClass().getResource("/eric/antimobile/launcher/icon/mute1.png"))); // NOI18N
        tglbtnVol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglbtnVolActionPerformed(evt);
            }
        });

        btnSettings.setText("Settings");
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblEric, javax.swing.GroupLayout.PREFERRED_SIZE, 150,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(btnInstall)
                                                                .addGap(171, 171, 171)
                                                                .addComponent(tglbtnMic)
                                                                .addGap(10, 10, 10)
                                                                .addComponent(tglbtnVol))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblFolder,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                350,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(txtUsername,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                310,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(txtPassword,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                310,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))) // Thêm
                                                                                                                           // dòng
                                                                                                                           // này
                                                .addGap(56, 56, 56))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addComponent(btnSettings)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                layout.createSequentialGroup()
                                                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(147, 147, 147)))));

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSettings))
                                .addGap(247, 247, 247)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(lblFolder)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
                                        javax.swing.GroupLayout.PREFERRED_SIZE) // Thêm dòng này
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnInstall)
                                        .addComponent(tglbtnMic)
                                        .addComponent(tglbtnVol))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblEric, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tglbtnMicActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tglbtnMicActionPerformed
        // TODO add your handling code here:
        // if (tglbtnMic.isSelected()){
        // Microphone.disableMicrophone();
        // } else {
        // Microphone.enableMicrophone();
        // }
    }// GEN-LAST:event_tglbtnMicActionPerformed

    private void lblEricMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblEricMouseClicked
        // TODO add your handling code here:
        Link.Eric();
    }// GEN-LAST:event_lblEricMouseClicked

    private void btnInstallActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnInstallActionPerformed
        // TODO add your handling code here:
        new InstallLauncher(this, true).setVisible(true);
    }// GEN-LAST:event_btnInstallActionPerformed

    private void lblFolderMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblFolderMouseClicked
        // TODO add your handling code here:

        // - chỉnh sửa Choose File (Chọn thẳng đến file samp.exe và lấy đường dẫn)
        // - build JDialog thông báo lỗi riêng
        // - tối ưu hoá lưu trữ đường dần file và tên nhân vật
        // - tối ưu hoá file Login - re-build code
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = fileChooser.showDialog(null, "Select Folder");
        if (x == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            selectPatch = f.getAbsolutePath();
            lblFolder.setText(selectPatch);
        }
    }// GEN-LAST:event_lblFolderMouseClicked

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
        // frame.addWindowListener(new WindowAdapter() {
        // @Override
        // public void windowClosing(WindowEvent e) {
        // frame.setVisible(false);
        // frame.dispose(); // <-- tùy chọn, giúp tránh lỗi trên vài máy Windows
        // }
        // });

    }// GEN-LAST:event_btnExitActionPerformed

    private void btnMinimizeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnMinimizeActionPerformed
        // TODO add your handling code here:
        this.setExtendedState(JFrame.ICONIFIED);
    }// GEN-LAST:event_btnMinimizeActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtUsernameActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLoginActionPerformed
        String userName = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());
        if (userName.equals("Nhập tên nhân vật.") || userName.isEmpty()) {
            txtUsername.setForeground(Color.BLACK);
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhân vật!");
            return;
        }
        if (password.equals("Nhập mật khẩu.") || password.isEmpty()) {
            txtPassword.setForeground(Color.BLACK);
            javax.swing.JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!");
            return;
        }
        try {
            boolean success = eric.antimobile.launcher.entity.JDBC.checkLogin(userName, password);
            if (success) {
                javax.swing.JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                // Thực hiện các bước tiếp theo ở đây
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Sai tên hoặc mật khẩu!");
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Lỗi kết nối database!");
        }
    }// GEN-LAST:event_btnLoginActionPerformed

    private void tglbtnVolActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_tglbtnVolActionPerformed
        // TODO add your handling code here:
        // if (tglbtnVol.isSelected()) {
        // Speaker.muteOutput();
        // } else {
        // Speaker.unmuteOutput();
        // }
    }// GEN-LAST:event_tglbtnVolActionPerformed

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSettingsActionPerformed
        // TODO add your handling code here:
        new SettingsLauncher(this, true).setVisible(true);
    }// GEN-LAST:event_btnSettingsActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowOpened
        // Sự kiện focus
        txtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtUsername.getText().equals("Nhập tên nhân vật.")) {
                    txtUsername.setText("");
                    txtUsername.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtUsername.getText().isEmpty()) {
                    txtUsername.setText("Nhập tên nhân vật.");
                    txtUsername.setForeground(new Color(153, 153, 153));
                }
            }
        });
    }// GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainLauncher.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainLauncher.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainLauncher.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainLauncher.class.getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainLauncher().setVisible(true);
            }
        });
    }

    private void resizeLoginButtonIcon() {
        ImageIcon originalIcon = new ImageIcon(
                getClass().getResource("/eric/antimobile/launcher/icon/nutdangnhap.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Adjust size
        btnLogin.setIcon(new ImageIcon(scaledImage));
        btnLogin.setText("");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnInstall;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnMinimize;
    private javax.swing.JButton btnSettings;
    private javax.swing.JLabel lblEric;
    private javax.swing.JLabel lblFolder;
    private javax.swing.JToggleButton tglbtnMic;
    private javax.swing.JToggleButton tglbtnVol;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JPasswordField txtPassword;

    // End of variables declaration//GEN-END:variables
}
