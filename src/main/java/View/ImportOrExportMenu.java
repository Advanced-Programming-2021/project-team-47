package View;

import Controller.Regex;
import Model.CardTypes;
import Model.Cards;
import Model.Menus;
import Controller.GameProgramController;
import Controller.MenuProgramController;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportOrExportMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static ImportOrExportMenu importOrExportMenuSingleton;
    private static HashMap<Menus, String> menuEnter = new HashMap<>();

    static {
        menuEnter.put(Menus.LOGIN_MENU, Menus.LOGIN_MENU.label);
        menuEnter.put(Menus.MAIN_MENU, Menus.MAIN_MENU.label);
        menuEnter.put(Menus.PROFILE_MENU, Menus.PROFILE_MENU.label);
        menuEnter.put(Menus.SHOP_MENU, Menus.SHOP_MENU.label);
        menuEnter.put(Menus.SCOREBOARD_MENU, Menus.SCOREBOARD_MENU.label);
        menuEnter.put(Menus.IMPORT_OR_EXPORT_MENU, Menus.IMPORT_OR_EXPORT_MENU.label);
    }

    public static ImportOrExportMenu getInstance() {
        if (importOrExportMenuSingleton == null) {
            importOrExportMenuSingleton = new ImportOrExportMenu();
        }
        return importOrExportMenuSingleton;
    }

    public void takeCommand(String command) {
        for (Pattern commandReg : commandMap.keySet())
            if (command.matches(commandReg.pattern())) {
                commandMap.get(commandReg).accept(commandReg.matcher(command));
                return;
            }
        System.out.println("invalid command");
    }

    public void run(String command) {
        commandMap.put(Regex.SHOW_CURRENT_MENU.label, ImportOrExportMenu.commandChecker::showCurrentMenu);
        commandMap.put(Regex.MENU_ENTER.label, ImportOrExportMenu.commandChecker::menuEnterHandler);
        commandMap.put(Regex.CARD_IMPORT.label, ImportOrExportMenu.commandChecker::importCards);
        commandMap.put(Regex.CARD_EXPORT.label, ImportOrExportMenu.commandChecker::exportCards);
        while (!command.equals("menu exit")) {
            takeCommand(command);
            command = GameProgramController.scanner.nextLine().trim();
        }
        MenuProgramController.currentMenu = Menus.LOGIN_MENU;
    }

    static class commandChecker {
        static class csvToJson {
            public static List<Map<?, ?>> readObjectsFromCsv(File file) throws IOException {
                CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
                CsvMapper csvMapper = new CsvMapper();
                MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader(Map.class).with(bootstrap).readValues(file);
                return mappingIterator.readAll();
            }

            public static void writeAsJson(List<Map<?, ?>> data, String name) throws IOException {
                ObjectMapper mapper = new ObjectMapper();
                for (Map<?, ?> url : data) {
                    if (url.get("Name").equals(name)) {
                        File out = new File("src/main/resources/ExportCards/" + url.get("Name") + ".json");
                        mapper.writeValue(out, url);
                        return;
                    }
                }
            }
        }

        static void showCurrentMenu(Matcher matcher) {
            Menus current = MenuProgramController.currentMenu;
            System.out.println(current.label);
        }

        static void importCards(Matcher matcher) {
            if (matcher.find()) {
                ArrayList<String> cards = new ArrayList<>();
                cards.add("SpellTrap");
                cards.add("Monster");
                CardTypes style = null;
                String cardName = null;
                int level = 0;
                String kind = null;
                String type = null;
                int ATK = 0;
                int DEF = 0;
                int price = 0;
                String description = null;
                for (String card : cards) {
                    File input = new File("src/main/resources/+" + card + ".csv");
                    try {
                        List<Map<?, ?>> data = ImportOrExportMenu.commandChecker.csvToJson.readObjectsFromCsv(input);
                        for (Map<?, ?> url : data) {
                            if (url.get("Name").equals(matcher.group(1))) {
                                if (url.get("Type") != null) {
                                    kind = url.get("Type").toString();
                                } else {
                                    kind = url.get("Monster Type").toString();
                                }
                                if (url.get("Atk") != null) {
                                    kind = url.get("Atk").toString();
                                }
                                if (url.get("Def") != null) {
                                    kind = url.get("Def").toString();
                                }
                                if (url.get("Level") != null) {
                                    kind = url.get("Level").toString();
                                }
                                if (url.get("Monster type") != null) {
                                    type = url.get("Monster type").toString();
                                }
                                cardName = url.get("Name").toString();
                                description = url.get("Description").toString();
                                price = Integer.parseInt(url.get("Price").toString());
                                if (card.equals("Monster")) {
                                    style = CardTypes.MONSTER_CARD;
                                } else {
                                    if (url.get("Type").equals("Trap")) {
                                        style = CardTypes.TRAP_CARD;
                                    } else {
                                        style = CardTypes.SPELL_CARD;
                                    }
                                }
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                new Cards(cardName, level, type, ATK, DEF, description, price, style, kind, State.NULL);
            }
        }

        static void exportCards(Matcher matcher) {
            if (matcher.find()) {
                ArrayList<String> cards = new ArrayList<>();
                cards.add("SpellTrap");
                cards.add("Monster");
                for (String card : cards) {
                    File input = new File("src/main/resources/+" + card + ".csv");
                    try {
                        List<Map<?, ?>> data = ImportOrExportMenu.commandChecker.csvToJson.readObjectsFromCsv(input);
                        ImportOrExportMenu.commandChecker.csvToJson.writeAsJson(data, matcher.group(1));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        static void menuEnterHandler(Matcher matcher) {
            for (Map.Entry<Menus, String> entry : menuEnter.entrySet()) {
                if (matcher.group(1).equals(entry.getValue()) && entry.getKey().key == 1) {
                    MenuProgramController.currentMenu = entry.getKey();
                    break;
                } else if (matcher.group(1).equals(entry.getValue()) && entry.getKey().key == 2) {
                    System.out.println(Response.menuNotPossible);
                }
            }
        }
    }
}
