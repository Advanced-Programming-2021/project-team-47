package Model;

import java.util.HashMap;

public enum Phase {
    MAIN_PHASE1(3,"Main Phase1"),
    MAIN_PHASE2(5,"Main Phase2"),
    STANDBY_PHASE(2,"StandBy Phase"),
    END_PHASE(6,"End Phase"),
    BATTLE_PHASE(4,"Battle Phase"),
    DRAW_PHASE(1,"Draw Phase");
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
