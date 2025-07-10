package eric.antimobile.launcher.ui;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;

public class Scanner {

    public static void main(String[] args) {
        // Chỉ định thư mục cài game tại đây
        File gameDir = new File("D:\\Downloads\\GTA San Andreas (GTAVN.VN)");
        File outputFile = new File("AntiCheatScanner.txt");

        if (!gameDir.exists() || !gameDir.isDirectory()) {
            System.err.println("❌ Thư mục không tồn tại: " + gameDir.getAbsolutePath());
            return;
        }

        Map<String, String> fileHashes = new TreeMap<>();

        scanDirectory(gameDir, gameDir, fileHashes);

        try (PrintWriter writer = new PrintWriter(outputFile)) {
            for (Map.Entry<String, String> entry : fileHashes.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
            System.out.println("✅ Xuất file thành công: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("❌ Lỗi khi ghi file: " + e.getMessage());
        }
    }

    private static void scanDirectory(File root, File current, Map<String, String> fileHashes) {
        for (File file : Objects.requireNonNull(current.listFiles())) {
            if (file.isDirectory()) {
                scanDirectory(root, file, fileHashes);
            } else {
                String relativePath = root.toPath().relativize(file.toPath()).toString().replace("\\", "/");
                String hash = getFileChecksum(file);
                fileHashes.put(relativePath, hash);
            }
        }
    }

    private static String getFileChecksum(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(file);
            byte[] byteArray = new byte[1024];
            int bytesCount;

            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
            fis.close();

            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}
