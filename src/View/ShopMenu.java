package View;

import Controller.GameProgramController;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopMenu implements Runnable{
    public static HashMap<Pattern, GameProgramController<Matcher>> commandMap = new HashMap<>();
    private static ShopMenu shopMenuSingleton;

    public static ShopMenu getInstance() {
        if (shopMenuSingleton == null) {
            shopMenuSingleton = new ShopMenu();
        }
        return shopMenuSingleton;
    }

    public void run() {

    }
}
