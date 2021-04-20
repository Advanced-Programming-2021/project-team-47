package Controller;

import Model.Cards;
import Model.Players;


public class ShopProgramController {
    public static boolean checkEnoughMoney(String username, String cardName) {
        return Players.getPlayerByUsername(username).getMoney() >= Cards.getCardByName(cardName).getPrice();
    }
}
