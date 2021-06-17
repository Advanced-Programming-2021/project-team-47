package Model;

import View.State;

public class MonsterCard extends Cards {


    public MonsterCard(String cardName, int level, String type, int ATK, int DEF, String description, int price, CardTypes style, String kind, State state) {
        super(cardName, level, type, ATK, DEF, description, price, style, kind, state);
    }
}
