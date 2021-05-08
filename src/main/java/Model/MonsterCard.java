package Model;

import java.util.ArrayList;

public class MonsterCard extends Cards {

    public MonsterCard(String cardName, int level, String type, int ATK, int DEF, String description, int price, Style style) {
        super(cardName, level, type, ATK, DEF, description, price, style);
        allMonsterCards.add(this);
    }
    private ArrayList<MonsterCard> allMonsterCards=new ArrayList<>();
}
