package Model;

import java.util.ArrayList;

public class MonsterCard extends Cards {

    public MonsterCard(String cardName, int level, int ATK, int DEF, String description, int price, Style style) {
        super(cardName, level, CardTypes.MONSTER_CARD, ATK, DEF, description, price, style);
        allMonsterCards.add(this);
    }
    private ArrayList<MonsterCard> allMonsterCards=new ArrayList<>();
}
