package Model;

import java.util.HashMap;

public enum Phase {
    MAIN_PHASE1(1,"Main Phase1"),
    MAIN_PHASE2(2,"Main Phase2"),
    STANDBY_PHASE(4,"StandBy Phase"),
    END_PHASE(6,"Main Phase2"),
    BATTLE_PHASE(5,"Main Phase2"),
    DRAW_PHASE(3,"Main Phase2");
    public final int key;
    public final String label;
    public int getKey(){
        return key;
    }
    public String getlabel(){
        return label;
    }
    Phase(int key , String label) {
        this.key = key;
        this.label=label;
    }
}
