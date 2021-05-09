package Controller;

import Model.Cards;
import Model.Players;


import java.util.TreeMap;


public class ShopProgramController {
    private static ShopProgramController shopProgramController;

    public static ShopProgramController getInstance() {
        if (shopProgramController == null) {
            shopProgramController = new ShopProgramController();
        }
        return shopProgramController;
    }

    public boolean checkEnoughMoney(String username, String cardName) {
        return Players.getPlayerByUsername(username).getMoney() >= Cards.getCardByName(cardName).getPrice();
    }

    public TreeMap<String, Integer> sortCard() {
        TreeMap<String, Integer> sortCard = new TreeMap<>();
        for (Cards card : Cards.allCards) {
            sortCard.put(card.getCardName(), card.getPrice());
        }
        return sortCard;
    }
}
