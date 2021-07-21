public class SignupController {

    public static String signup(String command) {
        String[] commandSplit = command.split(" ");
        String username = commandSplit[3];
        String nickname = commandSplit[5];
        String password = commandSplit[7];
        if (!new Players().isUsernameAvailable(username)) return Response.usernameAlreadyExists;
        if (!new Players().isNicknameAvailable(nickname)) return Response.nicknameAlreadyExists;
        Players.getAllPlayers().add(new Players(username , nickname , password));
        return Response.userCreateSuccessfully;
    }
}
