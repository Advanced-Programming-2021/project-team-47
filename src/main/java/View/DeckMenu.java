package View;

import Controller.DeckProgramController;
import Controller.Regex;
import Model.Cards;
import Model.CardsOfPlayer;
import Model.Deck;
import Model.Players;

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
            if (matcher.find()) {
                if (DeckProgramController.getInstance().checkDeckNameExist(matcher.group(1))) {
                    System.out.println("deck with name " + matcher.group(1) + " already exists");
                } else {
                    new Deck(matcher.group(1), LoginMenu.loginUsername);
                    System.out.println(Response.deckCreateSuccessfully);
                }
            }
        }

        public static void deleteDeck(Matcher matcher) {
            if (matcher.find()) {
                if (DeckProgramController.getInstance().checkDeckNameExist(matcher.group(1))) {
                    System.out.println("deck with name " + matcher.group(1) + " already exists");
                } else {
                    Deck.decks.remove(Deck.getDeckByName(matcher.group(1)));
                    System.out.println(Response.deckDeleteSuccessfully);
                }
            }
        }

        public static void setActiveDeck(Matcher matcher) {
            if (matcher.find()) {
                if (!DeckProgramController.getInstance().checkDeckNameExist(matcher.group(1))) {
                    System.out.println("deck with name " + matcher.group(1) + " does not exist");
                } else {
                    Deck.getDeckByName(matcher.group(1)).setActive(true);
                    System.out.println("deck activated successfully");
                }
            }
        }

        public static void deckRemoveCard(Matcher matcher) {
            if (matcher.find()) {
                String card = DeckProgramController.getInstance().deckRemoveCard(matcher).get(0);
                String deck = DeckProgramController.getInstance().deckRemoveCard(matcher).get(1);
                String side = DeckProgramController.getInstance().deckRemoveCard(matcher).get(2);
                if (Deck.getDeckByName(deck) == null) {
                    System.out.println("deck with name " + deck + " does not exist");
                } else if (!Deck.getDeckByName(deck).getCardsInDecks().contains(card) && side == null) {
                    System.out.println("card with name" + card + " does not exist in main deck");
                } else if (!Deck.getDeckByName(deck).getCardsInSideDecks().contains(card) && side != null) {
                    System.out.println("card with name" + card + " does not exist in side deck");
                } else {
                    if (side == null) {
                        Deck.getDeckByName(deck).removeCardsInDecks(card);
                    } else {
                        Deck.getDeckByName(deck).removeCardsInSideDecks(card);
                    }
                }
            }
        }

        public static void deckAddCard(Matcher matcher) {
            if (matcher.find()) {
                String card = DeckProgramController.getInstance().deckAddCard(matcher).get(0);
                String deck = DeckProgramController.getInstance().deckAddCard(matcher).get(1);
                String side = DeckProgramController.getInstance().deckAddCard(matcher).get(2);
                if (!Players.getPlayerByUsername(LoginMenu.loginUsername).getPlayerCards().contains(card)) {
                    System.out.println("card with name " + card + " does not exist");
                } else if (Deck.getDeckByName(deck) == null) {
                    System.out.println("deck with name " + deck + " does not exist");
                } else if (Deck.getDeckByName(deck).getAllCardsNumber() == 60 && side == null) {
                    System.out.println("main deck is full");
                } else if (Deck.getDeckByName(deck).getAllCardsNumber() == 15 && side != null) {
                    System.out.println("side deck is full");
                } else if (DeckProgramController.getInstance().isDeckFull(deck, card)) {
                    System.out.println("there are already three cards with name " + card + " in deck " + deck);
                } else {
                    if (side == null) {
                        Deck.getDeckByName(deck).setCardsInDecks(card);
                    } else {
                        Deck.getDeckByName(deck).setCardsInSideDecks(card);
                    }
                }
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
