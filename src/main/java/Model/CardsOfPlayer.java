package Model;

import java.util.ArrayList;

public class CardsOfPlayer {
    public static ArrayList<CardsOfPlayer> cardsOfPlayer = new ArrayList<>();
    private Zones zones;
    private String mode;
    private String face;
    private String cardName;
    private int addressNumber;
    public CardsOfPlayer(Zones zones, String mode, String face, String cardName, int addressNumber) {
        setZones(zones);
        setMode(mode);
        setFace(face);
        setCardName(cardName);
        setAddressNumber(addressNumber);
        cardsOfPlayer.add(this);
    }

    public static CardsOfPlayer getCardByName(String cardName) {
        for (CardsOfPlayer cards : cardsOfPlayer) {
            if (cards.cardName.equals(cardName)) {
                return cards;
            }
        }
        return null;
    }

    public Zones getZones() {
        return zones;
    }

    public void setZones(Zones zones) {
        this.zones = zones;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(int addressNumber) {
        this.addressNumber = addressNumber;
    }
}
