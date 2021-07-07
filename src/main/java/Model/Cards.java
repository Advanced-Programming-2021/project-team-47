package Model;

import java.util.ArrayList;
import java.util.Locale;

public class Cards {
    public static ArrayList<Cards> allCards = new ArrayList<>();
    public CardTypes style;
    private String cardName;
    private int level;
    private String type;
    private int ATK;
    private boolean canSpecialSummonBoolean=false;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    private int DEF;
    private State state;
    private int price;
    private String kind;
    private String description;
    private boolean visible;
    private boolean canSummon = true;
    private boolean attackable;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void increaseATK(int ATK) {
        this.ATK += ATK;
    }

    public void decreaseATK(int ATK) {
        this.ATK -= ATK;
    }

    public void increaseDEF(int DEF) {
        this.DEF += DEF;
    }

    public void decreaseDEF(int DEF) {
        this.DEF -= DEF;
    }

    public boolean canRitualSummon = false;

    public void canRitualSummon() {
        this.canRitualSummon = true;
    }

    public boolean getCanRitualSummon() {
        return this.canRitualSummon;
    }

    public void setCanSummon(boolean canSummon) {
        this.canSummon = canSummon;
    }

    public boolean getCanSummon() {
        return this.canSummon;
    }

    public Cards(String cardName, int level, String type, int ATK, int DEF, String description, int price, CardTypes style, String kind, State state) {
        setCardName(cardName);
        setLevel(level);
        setType(type);
        setATK(ATK);
        setStyle(style);
        setDEF(DEF);
        setKind(kind);
        setDescription(description);
        setPrice(price);
        setState(state);
        allCards.add(this);
        if (cardName.equals("Gate Guardian")) {
            setCanSummon(false);
        }
    }

    public Cards(String cardName, int level, String type, int ATK, int DEF, String description, int price, CardTypes style, String kind, boolean canRitualSummon) {
        setCardName(cardName);
        setLevel(level);
        setType(type);
        setATK(ATK);
        setStyle(style);
        setDEF(DEF);
        setKind(kind);
        setDescription(description);
        setPrice(price);
        canRitualSummon();
        allCards.add(this);
    }
    public Cards(String cardName, int level, String type, int ATK, int DEF, String description, int price, CardTypes style, String kind, boolean canRitualSummon , boolean canSpecialSummon) {
        setCardName(cardName);
        setLevel(level);
        setType(type);
        setATK(ATK);
        setStyle(style);
        setDEF(DEF);
        setKind(kind);
        setDescription(description);
        setPrice(price);
        canRitualSummon();
        canSpecialSummon();
        allCards.add(this);
    }

    private boolean isRitual;

    public void setRitual(boolean ritual) {
        isRitual = ritual;
    }

    public boolean getRitual() {
        return isRitual;
    }

    public static Cards getCardByName(String cardName) {
        for (Cards cards : allCards) {
            if (cards.cardName.equals(cardName) || cards.cardName.toLowerCase(Locale.ROOT).equals(cardName)) {
                return cards;
            }
        }
        return null;
    }
    public void canSpecialSummon(){
        this.canSpecialSummonBoolean=true;
    }
    public boolean getCanSpecialSummon(){
        return canSpecialSummonBoolean;
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

    public void setType(String type) {
        this.type = type;
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

    public CardTypes getStyle() {
        return style;
    }

    public void setStyle(CardTypes style) {
        this.style = style;
    }

    public boolean getAttackable() {
        return this.attackable;
    }

    public void setAttackable(boolean b) {
        attackable = b;
    }
}
