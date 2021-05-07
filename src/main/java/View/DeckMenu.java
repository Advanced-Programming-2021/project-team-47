package View;

import Controller.DeckProgramController;
import Model.Cards;
import Model.Deck;
import main.java.Controller.Regex;
import main.java.View.LoginMenu;
import main.java.View.Response;

import java.util.Collections;
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
        public static void createDeck(Matcher matcher) {
            if (matcher.find()){
                if (DeckProgramController.getInstance().checkDeckNameExist(matcher.group(1))){
                    System.out.println("deck with name "+matcher.group(1)+" already exists");
                }
                else{
                    new Deck(matcher.group(1), LoginMenu.loginUsername);
                    System.out.println(Response.deckCreateSuccessfully);
                }
            }
        }

        public static void deleteDeck(Matcher matcher) {
            if (matcher.find()){
                if (DeckProgramController.getInstance().checkDeckNameExist(matcher.group(1))){
                    System.out.println("deck with name "+matcher.group(1)+" already exists");
                }
                else{
                    Deck.decks.remove(Deck.getDeckByName(matcher.group(1)));
                    System.out.println(Response.deckDeleteSuccessfully);
                }
            }
        }
        public static void setActiveDeck(Matcher matcher){
            if (matcher.find()){
                if (!DeckProgramController.getInstance().checkDeckNameExist(matcher.group(1))){
                    System.out.println("deck with name "+matcher.group(1)+" does not exist");
                }
                else {
                    Deck.getDeckByName(matcher.group(1)).setActive(true);
                    System.out.println("deck activated successfully");
                }
            }
        }
        public static void deckRemoveCard(Matcher matcher){
            if (matcher.find()){
                if ()
            }
        }
        public static void showDeckCard(Matcher matcher) {
            if (matcher.find()) {
                Collections.sort(Deck.getDeckByName(LoginMenu.loginUsername).getCardsInDecks());
                for (int i = 0; i < Deck.getDeckByName(LoginMenu.loginUsername).getCardsInDecks().size(); ++i) {
                    System.out.println(Deck.getDeckByName(LoginMenu.loginUsername).getCardsInDecks().get(i) + Cards.getCardByName
                            (Deck.getDeckByName(LoginMenu.loginUsername).getCardsInDecks().get(i)).getDescription());
                }
            }
        }
    }

}
