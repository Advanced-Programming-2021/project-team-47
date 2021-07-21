import java.util.ArrayList;

public class Players {
    //    private static int token;
    private String password;
    private String username;
    private String nickname;
    private int score;
    private ArrayList<Message> allSentMessages = new ArrayList<>();

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private String token;
    private static ArrayList<Players> allPlayers = new ArrayList<>();

    public Players(String username, String password) {
        setPassword(password);
        setUsername(username);
//        setToken();
        allPlayers.add(this);
    }

    public Players(String username, String nickname, String password) {
        setPassword(password);
        setUsername(username);
        setNickname(nickname);
//        setToken();
        allPlayers.add(this);
    }

    public static Players getPlayerByUsername(String username) {
        for (Players player : allPlayers) {
            if (player.getUsername().equals(username))
                return player;
        }
        return null;
    }

    public Players() {

    }

    public void setToken(String token) {
//        this.token = token;
        this.token = token;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static ArrayList<Players> getAllPlayers() {
        return allPlayers;
    }

    public String getNickname() {
        return nickname;
    }

    public String getToken() {
        return token;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Boolean doesPlayerExist(String username) {
        for (Players player : allPlayers) {
            if (player.getUsername().equals(username)) return true;
        }
        return false;
    }

    public boolean isPasswordCorrect(String username, String password) {
        return getPlayerByUsername(username).getPassword().equals(password);
    }

    public boolean isUsernameAvailable(String username) {
        for (Players player : allPlayers) {
            if (player.getUsername().equals(username)) return false;
        }
        return true;
    }

    public boolean isNicknameAvailable(String nickname) {
        for (Players player : allPlayers) {
            if (player.getNickname().equals(nickname)) return false;
        }
        return true;
    }
    public static Players getPlayerByToken (String token){
        for (Players player :allPlayers
             ) {
            if (player.getToken().equals(token)) return player;
        }
        return null;
    }

    public ArrayList<Message> getAllSentMessages() {
        return allSentMessages;
    }
    //    public void loginPlayer (String username){
//        LoginController.getAllLoggedInPlayers().add(getPlayerByUsername(username));
//        getPlayerByUsername(username).setToken();
//    }
}
