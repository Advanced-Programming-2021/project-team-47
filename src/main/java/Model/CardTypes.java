package Model;

public enum CardTypes {
    MONSTER_CARD(1),
    SPELL_CARD(2),
    TRAP_CARD(3);
    public final int label;

    public int getLabel() {
        return label;
    }

    CardTypes(int label) {
        this.label = label;
    }

}
