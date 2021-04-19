package View;

import Controller.GameProgramController;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportOrExportMenu implements Runnable{
    public static HashMap<Pattern, GameProgramController<Matcher>> commandMap = new HashMap<>();
    private static ImportOrExportMenu importOrExportMenuSingleton;

    public static ImportOrExportMenu getInstance() {
        if (importOrExportMenuSingleton == null) {
            importOrExportMenuSingleton = new ImportOrExportMenu();
        }
        return importOrExportMenuSingleton;
    }

    public void run() {

    }
}
