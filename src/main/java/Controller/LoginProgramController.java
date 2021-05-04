package main.java.Controller;


import main.java.Model.Players;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class LoginProgramController {
    private static LoginProgramController loginProgramController;

    public static LoginProgramController getInstance() {
        if (loginProgramController == null) {
            loginProgramController = new LoginProgramController();
        }
        return loginProgramController;
    }

    public boolean checkUsernameExist(String username) {
        return Players.getPlayerByUsername(username) != null;
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

    public ArrayList<String> signUpUser(Matcher matcher) {
        ArrayList<String> data = new ArrayList<>();
        String password = null;
        String username = null;
        String nickName = null;
        for (int i = 1; i < 4; ++i) {
            if (matcher.find()) {
                if (matcher.group(i).contains("--password")) {
                    password = matcher.group(i).replaceAll("--password", "").trim();
                } else if (matcher.group(i).contains("--username")) {
                    username = matcher.group(i).replaceAll("--username", "").trim();
                } else if (matcher.group(i).contains("--nickname")) {
                    nickName = matcher.group(i).replaceAll("--nickname", "").trim();
                }
            }
        }
        data.add(password);
        data.add(username);
        data.add(nickName);
        return data;
    }

    public ArrayList<String> loginUser(Matcher matcher) {
        ArrayList<String> data = new ArrayList<>();
        String password = null;
        String username = null;
        for (int i = 1; i < 3; ++i) {
            if (matcher.find()) {
                if (matcher.group(i).contains("--password")) {
                    password = matcher.group(i).replaceAll("--password", "").trim();
                } else if (matcher.group(i).contains("--username")) {
                    username = matcher.group(i).replaceAll("--username", "").trim();
                }
            }
        }
        data.add(password);
        data.add(username);
        return data;
    }

    public ArrayList<String> changePassword(Matcher matcher) {
        ArrayList<String> data = new ArrayList<>();
        String newPassword = null;
        String currentPassword = null;
        for (int i = 1; i < 4; ++i) {
            if (matcher.find()) {
                if (matcher.group(i).contains("--new")) {
                    newPassword = matcher.group(i).replaceAll("--new", "").trim();
                } else if (matcher.group(i).contains("--current")) {
                    currentPassword = matcher.group(i).replaceAll("--current", "").trim();
                }
            }
        }
        data.add(newPassword);
        data.add(currentPassword);
        return data;
    }
}
