public class ChatRoom {
    public static void display(Message message){
        System.out.println(message.getMessage());
        System.out.println("____" + message.getFrom() + "   " + message.getDate());
    }
}
