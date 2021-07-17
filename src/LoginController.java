public class LoginController {
    public static String login(String command) {
        String[] commandSplit = command.split(" ");
        String username = commandSplit[3];
        String password = commandSplit[5];
        if (!new Players().doesPlayerExist(username)) return Response.wrongUsernameOrPassword;
        else if (!new Players().isPasswordCorrect(username , password)) return Response.wrongUsernameOrPassword;
        return Response.userLoginSuccessfully;
    }
}
