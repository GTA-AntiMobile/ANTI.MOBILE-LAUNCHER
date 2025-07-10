/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eric.antimobile.launcher.entity;

import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;

/**
 *
 * @author Eric
 */
public class Properties1 {
    
    public Properties1(){
        
    }
    
    public static void Properties(JFrame PartenFrame){
        PartenFrame.setLocationRelativeTo(null);
        
        PartenFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(Properties1.class.getResource(Config.getLogo())));

        PartenFrame.setShape(new RoundRectangle2D.Double(0, 0, PartenFrame.getWidth(), PartenFrame.getHeight(), 20, 20));

//        loadSavedData();
        
        PartenFrame.setTitle(Config.getTitle());
       
    }
    
}
