package Model;

import java.util.ArrayList;

public class SpellCard extends Cards {
    public SpellCard(String cardName, int level, String type, int ATK, int DEF, String description, int price, Style style) {
        super(cardName, level, type, ATK, DEF, description, price, style);
        allSpellCards.add(this);
    }
    private ArrayList<SpellCard> allSpellCards=new ArrayList<>();
}
