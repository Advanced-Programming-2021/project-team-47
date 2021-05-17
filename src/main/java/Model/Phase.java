package Model;

import java.util.HashMap;

public enum Phase {
    MAIN_PHASE1(1,"Main Phase1"),
    MAIN_PHASE2(2,"Main Phase2"),
    STANDBY_PHASE(4,"StandBy Phase"),
    END_PHASE(6,"End Phase"),
    BATTLE_PHASE(5,"Battle Phase"),
    DRAW_PHASE(3,"Draw Phase");
    public final int key;
    public final String label;
    public int getKey(){
        return key;
    }
    public String getLabel(){
        return label;
    }
    Phase(int key , String label) {
        this.key = key;
        this.label=label;
    }
}
