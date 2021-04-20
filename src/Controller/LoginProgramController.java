package Controller;

import Model.Players;

public class LoginProgramController {
    public boolean checkUsernameExist(String username) {
        return Players.getPlayerByUsername(username) != null;
    }

    public boolean checkWrongUsernameAndPassword(String username, String password) {
        if (Players.getPlayerByUsername(username) == null ||
                Players.getPlayerByUsername(username) != null && !Players.getPlayerByUsername(username).getPassword().equals(password)) {
            return false;
        }
        return true;
    }

    public boolean checkNicknameExist(String nickname) {
        return Players.getPlayerByNickName(nickname) != null;
    }

    public boolean checkInvalidPassword(String username, String password) {
        return !Players.getPlayerByUsername(username).getPassword().equals(password);
    }

    public boolean checkSamePassword(String username, String password) {
        return Players.getPlayerByUsername(username).getPassword().equals(password);
    }
}