import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class LoginController {
    private static int token = 0;
    public static Players thisPlayer;
    private static HashMap<String , Players> allLoggedInPlayers = new HashMap<>();
    public static String login(String command) {
        String[] commandSplit = command.split(" ");
        String username = commandSplit[3];
        String password = commandSplit[5];
        if (!new Players().doesPlayerExist(username)) return Response.wrongUsernameOrPassword;
        else if (!new Players().isPasswordCorrect(username , password)) return Response.wrongUsernameOrPassword;
        thisPlayer = new Players().getPlayerByUsername(username);
        thisPlayer.setToken(UUID.randomUUID().toString());
        allLoggedInPlayers.put(thisPlayer.getToken() , thisPlayer);
        //Login return form -->  "user logged in successfully! <playersToken>"
//        thisPlayer.setToken(token);
//        token ++;
        return Response.userLoginSuccessfully + " " + thisPlayer.getToken();
    }

    public static HashMap<String, Players> getAllLoggedInPlayers() {
        return allLoggedInPlayers;
    }
}
