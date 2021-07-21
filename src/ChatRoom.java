public class ChatRoom {
    public static String display(Message message){
        return message.getMessage() + "\n" + "____" + message.getFrom() + "   " + message.getDate();
    }
}
