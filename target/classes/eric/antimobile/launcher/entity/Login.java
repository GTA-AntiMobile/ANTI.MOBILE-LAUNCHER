package eric.antimobile.launcher.entity;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.Icon;



public class Login {

    private static final String REGISTRY_PATH = "SOFTWARE\\SAMP";
    private static final String VALUE_NAME = "PlayerName";
    
    
    public static Icon getErrorIcon(){
        return new ImageIcon(Config.getErrorLogo());
    }
    
    private static final Icon ERROR_LOGO = getErrorIcon();
    

    public static void performLogin(String sampPath, JTextField txtUsername, JLabel lblFolder, JFrame parentFrame) throws IOException {
        
        
        

        // Validate user input

        Data.saveData(txtUsername, lblFolder);
        if (txtUsername.getText().equals("Nhập tên nhân vật.")) {
            JOptionPane.showMessageDialog(parentFrame,"Tên nhân vật không được để trống.", "[Anti-Moblie] - Lỗi tên nhân vật.",JOptionPane.ERROR_MESSAGE, ERROR_LOGO);
        } else if (txtUsername.getText().length() < 3) {
            JOptionPane.showMessageDialog(parentFrame,"Tên nhân vật quá ngắn, nhập tên nhân vật dài hơn 3 kí tự.","[Anti-Moblie] - Lỗi tên nhân vật.",JOptionPane.ERROR_MESSAGE,ERROR_LOGO);
        } else if (!sampPath.equals("Chọn đường dẫn đến thư mục chứa samp.exe") && 
                   !sampPath.equalsIgnoreCase("Vui lòng chọn đường dẫn đến thư mục chứa samp.exe !!")) {
            
            File fileToCheck = new File(sampPath, "samp.exe");
            boolean fileExists = fileToCheck.exists();

            if (!fileExists) {
                JOptionPane.showMessageDialog(parentFrame,"Không có tệp samp.exe ở thư mục này, hãy chọn lại.", "[Anti-Moblie] - Lỗi đường dẫn.",JOptionPane.ERROR_MESSAGE, ERROR_LOGO);
            } else {
                
                // anti cheat scanner
                
//                File folder = new File("D:\\Downloads\\GTA San Andreas (GTAVN.VN)");
//                
//                
//                try {
//                AntiCheatScanner scanner = new AntiCheatScanner(Config.getWhiteList());
//                List<String> result = scanner.scanGameFolder(folder);
//
//                if (result.isEmpty()) {
//                    System.out.println("✅ Game sạch. Không phát hiện vấn đề.");
//                } else {
//                    System.out.println("🔍 Phát hiện vấn đề:");
//                    for (String issue : result) {
//                        System.out.println(issue);
//                    }
//                }
//
//            } catch (IOException e) {
//                System.err.println("❌ Không thể nạp whitelist: " + e.getMessage());
//            }
//
//                // TODO: Code khởi chạy game ở đây nếu không có cheat   
                
                AntiCheatScanner scanner = new AntiCheatScanner(Config.getWhiteList());
                List<String> result = scanner.scanGameFolder(new File("D:\\Downloads\\GTA San Andreas (GTAVN.VN)"));

                for (String issue : result) {
                    System.out.println(issue);
                }

                ProcessBuilder processBuilder = new ProcessBuilder(sampPath + File.separator + "samp.exe", Config.getIp(), Config.getPassword() );
                processBuilder.directory(new File(sampPath));

                try {
                    Process process = Runtime.getRuntime().exec("reg query \"HKEY_CLASSES_ROOT\\samp\\shell\\open\\command\"");
                    int exitCode = process.waitFor();
                    if (exitCode != 0) {
                        JOptionPane.showMessageDialog(parentFrame, "Bạn hãy cài đặt lại SA-MP Client 0.3DL để tham gia", "[Anti-Moblie] - Lỗi kết nối.", JOptionPane.ERROR_MESSAGE,ERROR_LOGO);
                    } else {
                        process.destroy();
                        Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, REGISTRY_PATH, VALUE_NAME, txtUsername.getText());
                        try {
                            processBuilder.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Vui lòng chọn đường dẫn đến thư mục chứa samp.exe !!", "[Anti-Moblie] - Lỗi đường dẫn.", JOptionPane.ERROR_MESSAGE, ERROR_LOGO);
        }
    }
}
