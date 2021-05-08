package Model;

import java.util.ArrayList;

public class TrapCard extends Cards {
    public TrapCard(String cardName, int level, String type, int ATK, int DEF, String description, int price, Style style) {
        super(cardName, level, type, ATK, DEF, description, price, style);
        allTrapCards.add(this);
    }
    private ArrayList<TrapCard> allTrapCards=new ArrayList<>();

}
