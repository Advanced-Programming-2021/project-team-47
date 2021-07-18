import java.util.ArrayList;

public class LoginController {
    private static Players thisPlayer;
    private static ArrayList<Players> allLoggedInPlayers = new ArrayList<>();
    public static String login(String command) {
        String[] commandSplit = command.split(" ");
        String username = commandSplit[3];
        String password = commandSplit[5];
        if (!new Players().doesPlayerExist(username)) return Response.wrongUsernameOrPassword;
        else if (!new Players().isPasswordCorrect(username , password)) return Response.wrongUsernameOrPassword;
        thisPlayer = new Players().getPlayerByUsername(username);
        allLoggedInPlayers.add(thisPlayer);
        //Login return form -->  "user logged in successfully! <playersToken>"
        return Response.userLoginSuccessfully + " " + thisPlayer.getToken();
    }
}
