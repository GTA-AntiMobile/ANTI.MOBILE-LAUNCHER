package eric.antimobile.launcher.entity;

import eric.antimobile.launcher.ui.MainLauncher;
import java.io.File;

/**
 *
 * @author Eric
 */
public class Config {
    private static String ip = "127.0.0.1";
    private static String password = "";
    
    private static String bg = "/eric/antimobile/launcher/icon/bg_amb.png";
    private static String logo = "/eric/antimobile/launcher/icon/logo.png";
    private static String error = "/eric/antimobile/launcher/icon/install_logo1.png";
    
    private static String title = "LAUCHER v1.0 [ANTI-MOBILE]";
    
    private static File whiteList = new File("AntiCheatScanner.txt");
    
    private static File folder = new File("");

    public static String getIp(){
        return ip;
    }

    public static String getPassword(){
        return password;
    }

    public static String getBgPatch(){
        return bg;
    }
    
    public static String getLogo(){
        return logo;
    }
    
    public static String getErrorLogo(){
        return error;
    }
    
    public static String getTitle(){
        return title;
    }
    
    public static File getWhiteList(){
        return whiteList;
    }
    
    public static File getFoler(){
        return folder;
    }
    
}
