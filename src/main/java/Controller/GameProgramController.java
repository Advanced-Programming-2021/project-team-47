package Controller;

import Model.*;
import View.Console.DuelMenu;
import Model.State;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;

public class GameProgramController {
    public static Scanner scanner = new Scanner(System.in);
    private static GameProgramController gameProgramController;
    public static Menus currentMenu = Menus.MAIN_MENU;
    private Players firstPlayer;
    private Players secondPlayer;
    private Players showTurn;
    private static Phase phase = Phase.MAIN_PHASE1;

    public static GameProgramController getInstance() {
        if (gameProgramController == null) {
            gameProgramController = new GameProgramController();
        }
        return gameProgramController;
    }

    public void setPhaseByKey(int key) {
        if (key == 3) {
            this.phase = Phase.MAIN_PHASE1;
            return;
        }
        if (key == 5) {
            this.phase = Phase.MAIN_PHASE2;
            return;
        }
        if (key == 1) {
            this.phase = Phase.DRAW_PHASE;
            return;
        }
        if (key == 2) {
            this.phase = Phase.STANDBY_PHASE;
            return;
        }
        if (key == 4) {
            this.phase = Phase.BATTLE_PHASE;
            return;
        }
        if (key == 6) {
            this.phase = Phase.END_PHASE;

        }
    }

    public void nextPhase() {
        Phase currentPhase = gameProgramController.getPhase();
        this.setPhaseByKey(currentPhase.getKey() + 1);
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Players getShowTurn() {
        return showTurn;
    }

    public void setShowTurn(Players showTurn) {
        this.showTurn = showTurn;
    }

    public Players getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Players firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Players getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Players secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public void startMultiplePlayerGame(Matcher matcher) {
        String username2 = null;
        String rounds = null;
        for (int i = 1; i < 4; ++i) {
            if (matcher.find()) {
                if (matcher.group(i).contains("--rounds")) {
                    rounds = matcher.group(i).replaceAll("--rounds", "").trim();
                } else if (matcher.group(i).contains("--second-player") || matcher.group(i).contains("--ai")) {
                    username2 = matcher.group(i).replaceAll("--second-player", "").trim();
                }
            }
        }
        GameProgramController.getInstance().setFirstPlayer(LoginProgramController.loginUsername);
        if (username2.equals("--ai"))
            new Players("--ai", "--ai", "");
        GameProgramController.getInstance().setSecondPlayer(Players.getPlayerByUsername(username2));
        DuelMenu.getInstance().setRound(Integer.parseInt(rounds));
        GameProgramController.currentMenu = Menus.DUEL_MENU;
    }

    static class csvToJson {
        public static List<Map<?, ?>> readObjectsFromCsv(File file) throws IOException {
            CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
            CsvMapper csvMapper = new CsvMapper();
            MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader(Map.class).with(bootstrap).readValues(file);
            return mappingIterator.readAll();
        }

        public static void writeAsJson(List<Map<?, ?>> data, String name) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            for (Map<?, ?> url : data) {
                if (url.get("Name").equals(name)) {
                    File out = new File("src/main/resources/ExportCards/" + url.get("Name") + ".json");
                    mapper.writeValue(out, url);
                    return;
                }
            }
        }
    }

    public void exportCards(String matcher) {
        ArrayList<String> cards = new ArrayList<>();
        cards.add("SpellTrap");
        cards.add("Monster");
        for (String card : cards) {
            File input = new File("src/main/resources/+" + card + ".csv");
            try {
                List<Map<?, ?>> data = GameProgramController.csvToJson.readObjectsFromCsv(input);
                GameProgramController.csvToJson.writeAsJson(data, matcher);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void importCards(String matcher) {
        ArrayList<String> cards = new ArrayList<>();
        cards.add("SpellTrap");
        cards.add("Monster");
        CardTypes style = null;
        String cardName = null;
        int level = 0;
        String kind = null;
        String type = null;
        int ATK = 0;
        int DEF = 0;
        int price = 0;
        String description = null;
        for (String card : cards) {
            File input = new File("src/main/resources/+" + card + ".csv");
            try {
                List<Map<?, ?>> data = GameProgramController.csvToJson.readObjectsFromCsv(input);
                for (Map<?, ?> url : data) {
                    if (url.get("Name").equals(matcher)) {
                        if (url.get("Type") != null) {
                            kind = url.get("Type").toString();
                        } else {
                            kind = url.get("Monster Type").toString();
                        }
                        if (url.get("Atk") != null) {
                            kind = url.get("Atk").toString();
                        }
                        if (url.get("Def") != null) {
                            kind = url.get("Def").toString();
                        }
                        if (url.get("Level") != null) {
                            kind = url.get("Level").toString();
                        }
                        if (url.get("Monster type") != null) {
                            type = url.get("Monster type").toString();
                        }
                        cardName = url.get("Name").toString();
                        description = url.get("Description").toString();
                        price = Integer.parseInt(url.get("Price").toString());
                        if (card.equals("Monster")) {
                            style = CardTypes.MONSTER_CARD;
                        } else {
                            if (url.get("Type").equals("Trap")) {
                                style = CardTypes.TRAP_CARD;
                            } else {
                                style = CardTypes.SPELL_CARD;
                            }
                        }
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        new Cards(cardName, level, type, ATK, DEF, description, price, style, kind, State.NULL);
    }

    public Players getPlayer(String userName) {
        return Players.getPlayerByUsername(userName);
    }

    public boolean isAnyCardSelected() {
        return DuelMenu.getInstance().getCardAddressNumberSelected() == 0 || DuelMenu.getInstance().getCardZoneSelected() == null;
    }

    public void deSelected() {
        DuelMenu.getInstance().setCardAddressNumberSelected(0);
        DuelMenu.getInstance().setCardZoneSelected(null);
    }

    public void changePhase(Phase phase) {
        gameProgramController.setPhase(phase);
    }

    public boolean isMonsterCardZoneFull(String username) {
        int full = 0;
        for (Cards card : Players.getPlayerByUsername(username).getMonsterCardZoneArray()) {
            if (!card.equals(null))
                ++full;
        }
        return full == 5;
    }

    public boolean isSpellZoneFull(String username) {
        int full = 0;
        for (Cards card : Players.getPlayerByUsername(username).getSpellCardZone()) {
            if (!card.equals(null))
                ++full;
        }
        return full == 5;
    }

    public ArrayList<String> scoreboardShow() {
        try {
            InitializeNetwork.dataOutputStream.writeUTF(Regex.SCOREBOARD.label.toString());
            InitializeNetwork.dataOutputStream.flush();
            String score = InitializeNetwork.dataInputStream.readUTF();
            Players.getPlayerByUsername(LoginProgramController.loginUsername.getUsername()).setScore(Integer.parseInt(score));
        } catch (IOException ignored) {
        }
        ArrayList<Players> scoreSorting = Players.allPlayers;
        scoreSorting.sort(Comparator.comparing(Players::getScore)
                .thenComparing(Players::getUsername));
        ArrayList<String> scoreSortingUsernames = new ArrayList<>();
        for (Players players : scoreSorting) {
            scoreSortingUsernames.add(players.getUsername());
        }
        return scoreSortingUsernames;
    }

    public boolean checkWrongPosition(int position) {
        if (position > 5 || position < 1)
            return false;
        return true;
    }

    public boolean checkCardExistInThisPosition(String username, int position) {
        if (Players.getPlayerByUsername(username).getCardsInHand(position) != null)
            return true;
        return false;
    }

    public boolean isAnyCardSelected(int position) {
        if (DuelMenu.getInstance().getCardAddressNumberSelected() == position || DuelMenu.getInstance().getCardZoneSelected() != null)
            return true;
        return false;
    }

    public boolean isNormalSummonValid() {
        if (DuelMenu.getInstance().getCardZoneSelected().getCanSummon()) {
            return true;
        }
        return false;
    }

    public void summon(String username) {
        for (int i = 1; i < 10; ++i) {
            if (!Players.getPlayerByUsername(username).getCardsInHand(i).equals("E")) {
                Players.getPlayerByUsername(username).putInCardsInHandZone(Players.getPlayerByUsername(username).getCardsInHand(i), "OO");
                break;
            }
        }
    }

    public boolean isAnyCard(String username, int addressNumber) {
        if (!Players.getPlayerByUsername(username).getMonsterCardZone(addressNumber).equals("E"))
            return true;
        return false;
    }

    public boolean isTwoMonsterCard(String username) {
        int counter = 0;
        for (int i = 1; i < 10; ++i) {
            if (!Players.getPlayerByUsername(username).getMonsterCardZone(i).equals("E"))
                ++counter;
            if (counter == 2)
                return true;
        }
        return false;
    }

    public boolean userExist(String username) {
        return Players.getPlayerByUsername(username) != null;
    }

    public boolean userDeckIsActive(String username) {
        return Players.getPlayerByUsername(username).getActiveDeck().size() != 0;
    }

    public void addCardByType(String cardName, int level, String type, int ATK, int DEF, String description, int price, CardTypes cardTypes, String kind, State state) {
        if (type.equals("Monster"))
            new MonsterCard(cardName, level, type, ATK, DEF, description, price, cardTypes, kind, state);
        else if (type.equals("Trap"))
            new TrapCard(cardName, level, type, ATK, DEF, description, price, cardTypes, kind, state);
        else
            new SpellCard(cardName, level, type, ATK, DEF, description, price, cardTypes, kind, state);

    }

    public void normalSet(String username) {
        for (int i = 1; i < 10; ++i) {
            if (!Players.getPlayerByUsername(username).getCardsInHand(i).equals("E")) {
                Players.getPlayerByUsername(username).putInCardsInHandZone(Players.getPlayerByUsername(username).getCardsInHand(i), "DH");
                break;
            }
        }
    }

    public void flipSummon(String username, int addressNumber) {
        Cards cards = Players.getPlayerByUsername(username).getCardsInHand(addressNumber);
        Players.getPlayerByUsername(username).putInCardsInHandZone(cards, "OO");
    }

    public void attackMonster(String username, int addressNumber) {
        Players playerOpponent = Players.getPlayerByUsername(username);
        Players playerCurrent = gameProgramController.getShowTurn();
        if (playerOpponent.getMonsterCardZone(addressNumber).equals("OO") && DuelMenu.getInstance().getCardZoneSelected()
                .getATK() > playerOpponent.getMonsterCardZone(addressNumber).getATK()) {
            playerOpponent.decreaseLifePoint(DuelMenu.getInstance().getCardZoneSelected()
                    .getATK() - playerOpponent.getMonsterCardZone(addressNumber).getATK());
            playerOpponent.setCardsInGraveyard(playerOpponent.getMonsterCardZone(addressNumber));
            playerOpponent.removeFromArrayList(playerOpponent.getMonsterCardZoneArray(), playerOpponent.getMonsterZone(),
                    playerOpponent.getMonsterCardZone(addressNumber));
        } else if (playerOpponent.getMonsterCardZone(addressNumber).equals("OO") && DuelMenu.getInstance().getCardZoneSelected()
                .getATK() == playerOpponent.getMonsterCardZone(addressNumber).getATK()) {
            playerOpponent.setCardsInGraveyard(playerOpponent.getMonsterCardZone(addressNumber));
            playerCurrent.setCardsInGraveyard(DuelMenu.getInstance().getCardZoneSelected());
            playerCurrent.removeFromArrayList(playerCurrent.getMonsterCardZoneArray(), playerCurrent.getMonsterZone(), playerCurrent
                    .getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected()));
            playerOpponent.removeFromArrayList(playerOpponent.getMonsterCardZoneArray(), playerOpponent.getMonsterZone(),
                    playerOpponent.getMonsterCardZone(addressNumber));

        } else if (playerOpponent.getMonsterCardZone(addressNumber).equals("OO") && playerOpponent.getMonsterCardZone(addressNumber).
                equals("OO") && DuelMenu.getInstance().getCardZoneSelected().getATK() < playerOpponent.getMonsterCardZone(addressNumber).getATK()) {
            playerCurrent.decreaseLifePoint(playerOpponent.getMonsterCardZone(addressNumber).getATK() - DuelMenu.getInstance().
                    getCardZoneSelected().getATK());
            playerCurrent.setCardsInGraveyard(playerCurrent.getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected()));
            playerCurrent.removeFromArrayList(playerCurrent.getMonsterCardZoneArray(), playerCurrent.getMonsterZone(), playerCurrent
                    .getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected()));
        } else if (playerOpponent.getMonsterCardZone(addressNumber).equals("DO") && DuelMenu.getInstance().getCardZoneSelected()
                .getATK() > playerOpponent.getMonsterCardZone(addressNumber).getDEF()) {
            playerOpponent.decreaseLifePoint(DuelMenu.getInstance().getCardZoneSelected()
                    .getATK() - playerOpponent.getMonsterCardZone(addressNumber).getATK());
            playerOpponent.setCardsInGraveyard(playerOpponent.getMonsterCardZone(addressNumber));
            playerOpponent.removeFromArrayList(playerOpponent.getMonsterCardZoneArray(), playerOpponent.getMonsterZone(),
                    playerOpponent.getMonsterCardZone(addressNumber));
        } else if (playerOpponent.getMonsterCardZone(addressNumber).equals("DO") && DuelMenu.getInstance().getCardZoneSelected()
                .getATK() < playerOpponent.getMonsterCardZone(addressNumber).getDEF()) {
            playerCurrent.decreaseLifePoint(playerOpponent.getMonsterCardZone(addressNumber).getATK() - DuelMenu.getInstance().
                    getCardZoneSelected().getATK());
            playerCurrent.setCardsInGraveyard(playerCurrent.getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected()));
            playerCurrent.removeFromArrayList(playerCurrent.getMonsterCardZoneArray(), playerCurrent.getMonsterZone(), playerCurrent
                    .getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected()));
        } else if (playerOpponent.getMonsterCardZone(addressNumber).equals("DH") && DuelMenu.getInstance().getCardZoneSelected()
                .getATK() > playerOpponent.getMonsterCardZone(addressNumber).getDEF()) {
            playerOpponent.decreaseLifePoint(DuelMenu.getInstance().getCardZoneSelected()
                    .getATK() - playerOpponent.getMonsterCardZone(addressNumber).getATK());
            playerOpponent.setCardsInGraveyard(playerOpponent.getMonsterCardZone(addressNumber));
            playerOpponent.removeFromArrayList(playerOpponent.getMonsterCardZoneArray(), playerOpponent.getMonsterZone(),
                    playerOpponent.getMonsterCardZone(addressNumber));
        } else if (playerOpponent.getMonsterCardZone(addressNumber).equals("DH") && DuelMenu.getInstance().getCardZoneSelected()
                .getATK() < playerOpponent.getMonsterCardZone(addressNumber).getDEF()) {
            playerCurrent.decreaseLifePoint(playerOpponent.getMonsterCardZone(addressNumber).getATK() - DuelMenu.getInstance().
                    getCardZoneSelected().getATK());
            playerCurrent.setCardsInGraveyard(playerCurrent.getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected()));
            playerCurrent.removeFromArrayList(playerCurrent.getMonsterCardZoneArray(), playerCurrent.getMonsterZone(), playerCurrent
                    .getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected()));
        }

    }

    public void directAttack(String username, String cardName) {
        Players.getPlayerByUsername(username).decreaseLifePoint(Cards.getCardByName(cardName).getATK());
    }

    public void activateEffect(String username, Cards cards) {
        for (int i = 1; i < Players.getPlayerByUsername(username).getSpellCardZone().size(); ++i) {
            if (!Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(i).equals("E"))
                Players.getPlayerByUsername(username).putInSpellZone(Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(i), "O");
        }
        if (Players.getPlayerByUsername(username).getFieldZone().contains(cards.getCardName()))
            Players.getPlayerByUsername(username).setFieldZone(cards);
    }

    public void setSpell(String username) {
        for (int i = 1; i < Players.getPlayerByUsername(username).getSpellCardZone().size(); ++i) {
            if (!Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(i).equals("E"))
                Players.getPlayerByUsername(username).putInSpellZone(Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(i), "H");
        }
    }

    public void rewards(Players winner, Players loser, int round) {
        if (round == 1) {
            winner.increaseMoney(1000 + winner.getLifePoint());
            loser.increaseMoney(100);
            winner.increaseScore(1000);
        } else {
            winner.increaseMoney(3000 + 3 * winner.getLifePoint());
            winner.increaseScore(3000);
            loser.increaseMoney(300);
        }
    }

    public void setTrap(String username) {
        for (int i = 1; i < Players.getPlayerByUsername(username).getSpellCardZone().size(); ++i) {
            if (!Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(i).equals("E"))
                Players.getPlayerByUsername(username).putInSpellZone(Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(i), "H");
        }
    }

    public void swapTurn() {
        GameProgramController game = GameProgramController.getInstance();
        Players turn = game.getShowTurn();
        if (turn.equals(game.getFirstPlayer())) {
            gameProgramController.setShowTurn(game.getSecondPlayer());
            DuelMenu.getInstance().setShowOpponent(game.getFirstPlayer());
            if (gameProgramController.getShowTurn().equals("--ai")) {
                DuelMenu.getInstance().aiPlay();
            }
        } else {
            gameProgramController.setShowTurn(game.getFirstPlayer());
            DuelMenu.getInstance().setShowOpponent(game.getSecondPlayer());
            if (gameProgramController.getShowTurn().equals("--ai")) {
                DuelMenu.getInstance().aiPlay();
            }
        }
        DuelMenu.getInstance().getAttackedThisTurn().clear();
    }

    public void setPosition(String username, String position) {
        if (position.equals("attack"))
            Players.getPlayerByUsername(username).putInMonsterZone(DuelMenu.getInstance().getCardZoneSelected(), "OO");
        else
            Players.getPlayerByUsername(username).putInMonsterZone(DuelMenu.getInstance().getCardZoneSelected(), "DO");
    }

    public boolean canSetPosition(String username, String position) {
        if (position.equals("attack") && Players.getPlayerByUsername(username).getMonsterZone().get(DuelMenu.getInstance().getCardZoneSelected()).equals("OO"))
            return false;
        else if (position.equals("defence") && Players.getPlayerByUsername(username).getMonsterZone().get(DuelMenu.getInstance().getCardZoneSelected()).equals("DO"))
            return false;
        return true;
    }

    public void ritualSummon(String username, String position) {
        for (int i = 1; i < 10; ++i) {
            if (!Players.getPlayerByUsername(username).getCardsInHand(i).equals("E")) {
                Players.getPlayerByUsername(username).putInCardsInHandZone(Players.getPlayerByUsername(username).getCardsInHand(i), position);
                break;
            }
        }
    }

}
