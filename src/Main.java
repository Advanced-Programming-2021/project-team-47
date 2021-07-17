import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static Menus menu;
    public static void main(String[] args) {
        String[] arguments ={"1","2","5","3"};
        try {
            ServerSocket serverSocket = new ServerSocket(9696);
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try{
                        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                        while(true){
                            try {
                                String input = dataInputStream.readUTF();
                                String result = run(input);
                                if (result.equals("-1")) break;
                                dataOutputStream.writeUTF(result);
                                dataOutputStream.flush();
                            }catch (Exception e){
                                System.out.println("Client disconnected");
                                return;
                            }


                        }
                        dataInputStream.close();
                        serverSocket.close();
                        socket.close();

                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }).start();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static String run(String command) {
        if (command.equals("login")) return LoginController.login(command);
        else if (command.startsWith("sign up")) return SignupController.signup(command);
        else if (command.startsWith("Logout")) return LogoutController.logout(menu);
        else
    }

}
