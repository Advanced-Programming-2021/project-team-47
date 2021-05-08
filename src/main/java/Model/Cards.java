package Model;

import java.util.ArrayList;

public class Cards {
    public static ArrayList<Cards> allCards = new ArrayList<>();
    Style style;
    private String cardName;
    private int level;
    private CardTypes type;
    private int ATK;
    private int DEF;
    private int price;
    private String description;
    private boolean visible;

    public Cards(String cardName, int level, CardTypes type, int ATK, int DEF, String description, int price, Style style) {
        setCardName(cardName);
        setLevel(level);
        setType(type);
        setATK(ATK);
        setStyle(style);
        setDEF(DEF);
        setDescription(description);
        setPrice(price);
        allCards.add(this);
        if (type==CardTypes.MONSTER_CARD){
            new MonsterCard(cardName,level,ATK,DEF,description,price,style);
        }
        if (type==CardTypes.SPELL_CARD){
            new SpellCard(cardName,level,ATK,DEF,description,price,style);
        }
        if (type==CardTypes.TRAP_CARD){
            new TrapCard(cardName,level,ATK,DEF,description,price,style);
        }
    }

    public static Cards getCardByName(String cardName) {
        for (Cards cards : allCards) {
            if (cards.cardName.equals(cardName)) {
                return cards;
            }
        }
        return null;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(CardTypes cardTypes) {
        this.type = cardTypes;

    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }
}
