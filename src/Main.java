import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static Menus menu;
    private static String token;

    public static void main(String[] args) {
        String[] arguments = {"1", "2", "5", "3"};
        try {
            ServerSocket serverSocket = new ServerSocket(9696);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        while (true) {
                            try {
                                String input = dataInputStream.readUTF();
                                String result = run(input);
                                if (result.equals("-1")) break;
                                dataOutputStream.writeUTF(result);
                                dataOutputStream.flush();
                            } catch (Exception e) {
                                System.out.println("Client disconnected");
                                return;
                            }


                        }
                        dataInputStream.close();
                        serverSocket.close();
                        socket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String run(String command) {
        if (Regex.LOGIN.label.matcher(command).find()) return LoginController.login(command);
        else if (Regex.SIGNUP.label.matcher(command).find()) return SignupController.signup(command);
        else if (Regex.LOGOUT.label.matcher(command).find()) return LogoutController.logout(menu, token);
        else if (Regex.SCOREBOARD.label.matcher(command).find())
            return String.valueOf(Players.getPlayerByUsername(LoginController.thisPlayer.getUsername()).getScore());
        else if (Regex.CHATROOM.label.matcher(command).find()) return ChatRoom.display(new Message(command.split(" ")[2],command.split(" ")[3]));
        else if (Regex.EDIT_MESSAGE.label.matcher(command).find()) return ChatRoom.edit(command);
        else if (Regex.DELETE_MESSAEG.label.matcher(command).find()) return ChatRoom.delete(command);
        else if (Regex.REPLY_TO_MESSAGE.label.matcher(command).find()) return ChatRoom.reply(command);
        else if (Regex.SHOW_ONLINE_PLAYERS.label.matcher(command).find()) return ChatRoom.numberOfOnlinePlayers();
        return "";
    }

}
