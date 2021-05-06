package View;

import main.java.Controller.Regex;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static DeckMenu deckMenu;

    public static DeckMenu getInstance() {
        if (deckMenu == null) {
            deckMenu = new DeckMenu();
        }
        return deckMenu;
    }

    public void run(String command) {
        commandMap.put(Regex.CREATE_DECK.label, DeckMenu.commandChecker::createDeck);
        commandMap.put(Regex.DELETE_DECK.label, DeckMenu.commandChecker::deleteDeck);
        commandMap.put(Regex.SET_ACTIVE_DECK.label, DeckMenu.commandChecker::setActiveDeck);
        commandMap.put(Regex.DECK_REMOVE_CARD.label, DeckMenu.commandChecker::deckRemoveCard);
        commandMap.put(Regex.DECK_ADD_CARD.label, DeckMenu.commandChecker::deckAddCard);
        commandMap.put(Regex.DECK_SHOW_ALL.label, DeckMenu.commandChecker::deckShowAll);
        commandMap.put(Regex.SHOW_DECK.label, DeckMenu.commandChecker::showDeck);
        commandMap.put(Regex.DECK_SHOW_CARD.label, DeckMenu.commandChecker::showDeckCard);
    }

    public void takeCommand(String command) {
        for (Pattern commandReg : commandMap.keySet())
            if (command.matches(commandReg.pattern())) {
                commandMap.get(commandReg).accept(commandReg.matcher(command));
                return;
            }
        System.out.println("invalid command");
    }

    static class commandChecker {

    }
}
