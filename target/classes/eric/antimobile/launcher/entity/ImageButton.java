package eric.antimobile.launcher.entity;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageButton extends JButton {
    private String imagePath;
    private double scaleX = 1.0;
    private double scaleY = 1.0;

    public ImageButton(String imagePath, double scaleX, double scaleY) {
        this.imagePath = imagePath;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        setImage(imagePath, scaleX, scaleY);
    }

    public void setImage(String path, double scaleX, double scaleY) {
        this.imagePath = path;
        this.scaleX = scaleX;
        this.scaleY = scaleY;

        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            int newWidth = (int) (originalImage.getWidth() * scaleX);
            int newHeight = (int) (originalImage.getHeight() * scaleY);
            Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledImage));
            setPreferredSize(new Dimension(newWidth + 10, newHeight + 10)); // cộng thêm padding nếu muốn
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScale(double scaleX, double scaleY) {
        setImage(this.imagePath, scaleX, scaleY);
    }

    public void setImagePath(String path) {
        setImage(path, this.scaleX, this.scaleY);
    }
}

