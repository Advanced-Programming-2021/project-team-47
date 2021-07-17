import java.util.ArrayList;

public class Players {
    private String password ;
    private String username ;
    private String nickname ;
    private static ArrayList<Players> allPlayers = new ArrayList<>();
    public Players(String username , String password){
        setPassword(password);
        setUsername(username);
        allPlayers.add(this);
    }
    public Players(String username ,String nickname , String password){
        setPassword(password);
        setUsername(username);
        setNickname(nickname);
        allPlayers.add(this);
    }
    public Players() {

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

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
    public Boolean doesPlayerExist(String username){
        for (Players player:allPlayers) {
            if (player.getUsername().equals(username)) return true;
        }
        return false;
    }
    public Players getPlayerByUsername(String username){
        if (!doesPlayerExist(username)) return null;
        for (Players player:allPlayers) {
            if (player.getUsername().equals(username)) return player;
        }
        return null;
    }

    public boolean isPasswordCorrect(String username, String password) {
        return getPlayerByUsername(username).getPassword().equals(password);
    }

    public boolean isUsernameAvailable(String username) {
        for (Players player:allPlayers) {
            if (player.getUsername().equals(username)) return false;
        }
        return true;
    }

    public boolean isNicknameAvailable(String nickname) {
        for (Players player:allPlayers) {
            if (player.getNickname().equals(nickname)) return false;
        }
        return true;
    }
}
