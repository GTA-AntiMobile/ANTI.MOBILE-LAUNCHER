package eric.antimobile.launcher.entity;

import java.io.*;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;

public class AntiCheatScanner {

    private final Map<String, String> whitelist = new HashMap<>();

    /**
     * Constructor: tự động nạp whitelist từ file .properties
     */
    public AntiCheatScanner(File whitelistFile) throws IOException {
        loadWhitelist(whitelistFile);
    }

    /**
     * Đọc danh sách file hợp lệ và hash SHA-256 từ file whitelist (.properties)
     */
    private void loadWhitelist(File file) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(file)) {
            props.load(fis);
        }

        for (String key : props.stringPropertyNames()) {
            whitelist.put(key, props.getProperty(key));
        }
    }

    /**
     * Quét thư mục game và phát hiện các vấn đề về file lạ, thiếu, hoặc chỉnh sửa
     *
     * @param gameDir thư mục cài đặt gốc của game
     * @return danh sách cảnh báo hoặc lỗi phát hiện
     */
    public List<String> scanGameFolder(File gameDir) {
        List<String> issues = new ArrayList<>();

        if (!gameDir.exists() || !gameDir.isDirectory()) {
            issues.add("❌ Thư mục không hợp lệ: " + gameDir.getAbsolutePath());
            return issues;
        }

        Set<String> foundFiles = new HashSet<>();
        scanDirectory(gameDir, gameDir, foundFiles, issues);

        // Kiểm tra file bị thiếu
        for (String expected : whitelist.keySet()) {
            if (!foundFiles.contains(expected)) {
                issues.add("⚠️ Thiếu file: " + expected);
            }
        }

        // Kiểm tra file lạ
        for (String found : foundFiles) {
            if (!whitelist.containsKey(found)) {
                issues.add("⚠️ File lạ: " + found);
            }
        }

        return issues;
    }

    /**
     * Đệ quy quét toàn bộ thư mục và kiểm tra từng file
     */
    private void scanDirectory(File root, File current, Set<String> foundFiles, List<String> issues) {
        File[] files = current.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(root, file, foundFiles, issues);
            } else {
                Path relativePath = root.toPath().relativize(file.toPath()).normalize();
                String relative = relativePath.toString().replace("\\", "/");
                foundFiles.add(relative);

                if (whitelist.containsKey(relative)) {
                    String expectedHash = whitelist.get(relative);
                    String actualHash = getFileChecksum(file);
                    if (!expectedHash.equals(actualHash)) {
                        issues.add("⚠️ File bị chỉnh sửa: " + relative);
                    }
                }
            }
        }
    }

    /**
     * Tính hash SHA-256 của 1 file
     */
    private String getFileChecksum(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] byteArray = new byte[16384];
            int bytesCount;

            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
            fis.close();

            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder(64);
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
