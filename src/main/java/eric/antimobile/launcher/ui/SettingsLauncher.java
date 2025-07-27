/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package eric.antimobile.launcher.ui;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Eric
 */
public class SettingsLauncher extends javax.swing.JDialog {

    private List<Mixer.Info> inputMixers = new ArrayList<>();
    private List<Mixer.Info> outputMixers = new ArrayList<>();
    private TargetDataLine microphone;
    private SourceDataLine speaker;
    private boolean micRunning = false;
    private byte[] lastAudioBuffer = new byte[0]; // Dữ liệu âm thanh cuối cùng được ghi lại
    private Thread micThread;
    private AudioFormat recordedFormat; // ✅ Lưu định dạng đã ghi âm để phát lại chính xác

    public SettingsLauncher(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setUndecorated(true);
        initComponents();
        init();
        loadAudioDevices();
    }

    void init() {
        setLocationRelativeTo(null);
    }

    private void loadAudioDevices() {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        cboChooseMicro.removeAllItems();
        cboChooseSpeaker.removeAllItems();
        inputMixers.clear();
        outputMixers.clear();

        for (Mixer.Info info : mixers) {
            Mixer mixer = AudioSystem.getMixer(info);

            boolean hasTargetLine = false;
            for (Line.Info lineInfo : mixer.getTargetLineInfo()) {
                if (lineInfo instanceof DataLine.Info && AudioSystem.isLineSupported(lineInfo)) {
                    hasTargetLine = true;
                    break;
                }
            }

            boolean hasSourceLine = false;
            for (Line.Info lineInfo : mixer.getSourceLineInfo()) {
                if (lineInfo instanceof DataLine.Info && AudioSystem.isLineSupported(lineInfo)) {
                    hasSourceLine = true;
                    break;
                }
            }

            if (hasTargetLine) {
                inputMixers.add(info);
                cboChooseMicro.addItem(info.getName());
            }

            if (hasSourceLine) {
                outputMixers.add(info);
                cboChooseSpeaker.addItem(info.getName());
            }
        }

        if (inputMixers.isEmpty()) {
            cboChooseMicro.addItem("Không có thiết bị đầu vào");
            cboChooseMicro.setEnabled(false);
        } else {
            cboChooseMicro.setEnabled(true);
        }

        if (outputMixers.isEmpty()) {
            cboChooseSpeaker.addItem("Không có thiết bị đầu ra");
            cboChooseSpeaker.setEnabled(false);
        } else {
            cboChooseSpeaker.setEnabled(true);
        }

        // ✅ Thêm log khi danh sách thiết bị được cập nhật
        System.out.println("Danh sách thiết bị micro:");
        inputMixers.forEach(m -> System.out.println("  - " + m.getName()));

        System.out.println("Danh sách thiết bị loa:");
        outputMixers.forEach(m -> System.out.println("  - " + m.getName()));
    }

    private void playLastAudioBuffer() {
        if (lastAudioBuffer == null || lastAudioBuffer.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa có dữ liệu ghi âm.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int index = cboChooseSpeaker.getSelectedIndex();
        if (index < 0 || index >= outputMixers.size()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thiết bị loa đầu ra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Mixer.Info selectedInfo = outputMixers.get(index);
        Mixer mixer = AudioSystem.getMixer(selectedInfo);

        playAudioWithLevel(lastAudioBuffer, recordedFormat, mixer);
    }

    private void startMic() {
        int index = cboChooseMicro.getSelectedIndex();
        if (index < 0 || index >= inputMixers.size()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thiết bị micro đầu vào!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Mixer.Info selectedInfo = inputMixers.get(index);
            Mixer mixer = AudioSystem.getMixer(selectedInfo);

            AudioFormat[] formats = {
                new AudioFormat(44100.0f, 16, 1, true, false),
                new AudioFormat(16000.0f, 16, 1, true, false),
                new AudioFormat(8000.0f, 16, 1, true, false)
            };

            TargetDataLine line = null;
            AudioFormat supportedFormat = null;

            for (AudioFormat fmt : formats) {
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, fmt);
                if (mixer.isLineSupported(info)) {
                    try {
                        line = (TargetDataLine) mixer.getLine(info);
                        supportedFormat = fmt;
                        recordedFormat = fmt; // ✅ Gán định dạng đã ghi lại để dùng khi phát
                        break;
                    } catch (LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            if (line == null || supportedFormat == null) {
                JOptionPane.showMessageDialog(this, "Không thể mở dòng micro với bất kỳ định dạng nào được hỗ trợ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            line.open(supportedFormat);
            line.start();
            microphone = line;
            micRunning = true;

            micThread = new Thread(() -> {
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                while (micRunning) {

                    int bytesRead = microphone.read(buffer, 0, buffer.length);
                    out.write(buffer, 0, bytesRead);
//                    int level = calculateLevel(buffer, bytesRead);
                    updateProgressBarLevel(buffer);

                }
                lastAudioBuffer = out.toByteArray();
                System.out.println("Ghi âm xong: " + lastAudioBuffer.length + " byte");

                // ✅ Thêm log kiểm tra nội dung dữ liệu ghi âm
                System.out.print("Mẫu dữ liệu đầu: ");
                for (int i = 0; i < lastAudioBuffer.length && lastAudioBuffer[i] > 1; i++) {
                    System.out.print(lastAudioBuffer[i] + " ");
                }
                System.out.println();
            });
            micThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopMic() {
        micRunning = false;
        if (microphone != null) {
            microphone.stop();
            microphone.close();
        }
        pgbCheck.setValue(0);
    }

    /**
     * Phát audio từ buffer và hiển thị độ nhạy song song.
     *
     * @param audioBuffer Mảng byte chứa âm thanh
     * @param format Định dạng âm thanh
     * @param mixer (có thể null) nếu muốn mặc định
     */
    private void playAudioWithLevel(byte[] audioBuffer, AudioFormat format, Mixer mixer) {
        new Thread(() -> {
            try {
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                SourceDataLine line = (SourceDataLine) (mixer != null
                        ? mixer.getLine(info)
                        : AudioSystem.getLine(info));
                line.open(format);
                line.start();

                byte[] buffer = new byte[1024];
                for (int i = 0; i < audioBuffer.length; i += buffer.length) {
                    int length = Math.min(buffer.length, audioBuffer.length - i);
                    System.arraycopy(audioBuffer, i, buffer, 0, length);
                    line.write(buffer, 0, length);
                    updateProgressBarLevel(buffer); // ✅ dùng hàm xử lý độ nhạy
                }

                line.drain();
                line.stop();
                line.close();

                // Reset về 0 sau khi phát xong
                SwingUtilities.invokeLater(() -> {
                    pgbCheck.setValue(0);
                    pgbCheck.setForeground(Color.GREEN);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateProgressBarLevel(byte[] audioData) {
        List<Integer> audioSamples = new ArrayList<>();

        // Giải mã byte[] thành mẫu âm thanh 16-bit signed
        for (int i = 0; i < audioData.length - 1; i += 2) {
            int sample = ((audioData[i + 1] & 0xFF) << 8) | (audioData[i] & 0xFF);
            if (sample > 32767) {
                sample -= 65536;
            }
            audioSamples.add(Math.abs(sample));
        }

        // Xử lý hiển thị độ nhạy trên 1 luồng riêng
        new Thread(() -> {
            for (int i = 0; i < audioSamples.size(); i += 1000) {
                int level = audioSamples.get(i) / 300;
                level = Math.min(100, level);
                final int finalLevel = level;

                SwingUtilities.invokeLater(() -> {
                    pgbCheck.setValue(finalLevel);
                    if (finalLevel > 60) {
                        pgbCheck.setForeground(Color.BLUE);
                    } else if (finalLevel > 30) {
                        pgbCheck.setForeground(Color.ORANGE);
                    } else {
                        pgbCheck.setForeground(Color.GREEN);
                    }
                });

                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            // ✅ Reset độ nhạy về mặc định sau khi xử lý xong
            SwingUtilities.invokeLater(() -> {
                pgbCheck.setValue(0);
                pgbCheck.setForeground(Color.GREEN);
            });

        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnClose = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cboChooseMicro = new javax.swing.JComboBox<>();
        cboChooseSpeaker = new javax.swing.JComboBox<>();
        pgbCheck = new javax.swing.JProgressBar();
        btnMicro = new javax.swing.JButton();
        btnSpeaker = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        btnClose.setText("X");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        jLabel1.setText("Cài đặt");

        cboChooseMicro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboChooseSpeaker.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        pgbCheck.setBackground(new java.awt.Color(255, 255, 255));

        btnMicro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/antimobile/launcher/icon/microphone1_1.png"))); // NOI18N
        btnMicro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMicroActionPerformed(evt);
            }
        });

        btnSpeaker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eric/antimobile/launcher/icon/volume-up1.png"))); // NOI18N
        btnSpeaker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpeakerActionPerformed(evt);
            }
        });

        jButton1.setText("Mẫu ghi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cboChooseMicro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnMicro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(btnSpeaker)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(260, 260, 260))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cboChooseSpeaker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(pgbCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboChooseMicro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboChooseSpeaker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSpeaker, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMicro, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pgbCheck, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClose)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
//        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnMicroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMicroActionPerformed
        // TODO add your handling code here:
        if (micRunning) {
            stopMic();
            btnMicro.setBackground(Color.white);

        } else {
            startMic();
             btnMicro.setBackground(Color.red);

        }
    }//GEN-LAST:event_btnMicroActionPerformed

    private void btnSpeakerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpeakerActionPerformed
        // TODO add your handling code here:
//        testSpeaker();
        playLastAudioBuffer();

    }//GEN-LAST:event_btnSpeakerActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        recordedFormat = new AudioFormat(44100.0f, 16, 1, true, false);
        lastAudioBuffer = new byte[44100 * 2];
        for (int i = 0; i < 44100; i++) {
            short val = (short) (Math.sin(2.0 * Math.PI * 440 * i / 44100.0) * 32767);
            lastAudioBuffer[i * 2] = (byte) (val & 0xFF);
            lastAudioBuffer[i * 2 + 1] = (byte) ((val >> 8) & 0xFF);
        }
        playAudioWithLevel(lastAudioBuffer, recordedFormat, null); // phát qua mixer mặc định


    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SettingsLauncher.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsLauncher.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsLauncher.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsLauncher.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SettingsLauncher dialog = new SettingsLauncher(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnMicro;
    private javax.swing.JButton btnSpeaker;
    private javax.swing.JComboBox<String> cboChooseMicro;
    private javax.swing.JComboBox<String> cboChooseSpeaker;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar pgbCheck;
    // End of variables declaration//GEN-END:variables
}
