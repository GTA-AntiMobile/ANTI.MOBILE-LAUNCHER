package eric.antimobile.launcher.entity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Data {

    private static final String DATA_FILE_NAME = "anti-mobile.desma";
    
    
    public static void createData(JTextField txtUsername, JLabel lblFolder){
        Path dataFilePath = Paths.get(DATA_FILE_NAME);

        // Kiểm tra nếu file chưa tồn tại thì tạo file rỗng
        if (!Files.exists(dataFilePath)) {
            try {
                Files.createFile(dataFilePath);
                saveData(txtUsername, lblFolder);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
    

    // Lưu dữ liệu từ giao diện
    public static void saveData(JTextField txtUsername, JLabel lblFolder) {
        String textFieldText = txtUsername.getText();
        String labelText = lblFolder.getText();
        String dataToSave = textFieldText + ";" + labelText;
        try {
            Files.write(Paths.get(DATA_FILE_NAME), dataToSave.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Tải lại dữ liệu từ file và đưa lên giao diện
    public static void loadData(JTextField txtUsername, JLabel lblFolder) {
        try {
            String savedData = new String(Files.readAllBytes(Paths.get(DATA_FILE_NAME)));
            String[] parts = savedData.split(";", 2);
            if (parts.length == 2) {
                txtUsername.setText(parts[0]);
                lblFolder.setText(parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
