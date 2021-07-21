public class ChatRoom {
    public static String display(Message message){
        Players.getPlayerByUsername(message.getFrom()).getAllSentMessages().add(message);
        Message.getAllMessages().add(message);
        return message.getMessage() + "\n" + "____" + message.getFrom() + "   " + message.getDate() + message.getId();
    }

    public static String edit(String command) {
        String[] commandSplit = command.split(" ");
        String newMessage = commandSplit[1];
        String token = commandSplit[3];
        String messageId = commandSplit[2];
        if (Message.getMessageByID(messageId).getFrom().equals(Players.getPlayerByToken(token).getUsername())){
            String hold = Message.getMessageByID(messageId).getMessage();
            Message.getMessageByID(messageId).setMessage(newMessage);
            return Players.getPlayerByToken(token).getUsername() + "Edited : " + hold +"\n" +
                    "To :" + newMessage;
        }
        return "";
    }
    public static String delete(String command){
        String[] commandSplit = command.split(" ");
        String id = commandSplit[1];
        String token = commandSplit[2];
        if (Players.getPlayerByToken(token).getUsername().equals(Message.getMessageByID(id).getFrom())){
            Message.getAllMessages().remove(Message.getMessageByID(id));
            Players.getPlayerByToken(token).getAllSentMessages().remove(Message.getMessageByID(id));
            return "Message deleted successfully";
        }
        return "";
    }

    public static String reply(String command) {
        String[] commandSplit = command.split(" ");
        String id = commandSplit[1];
        String message = commandSplit[2];
        String token = commandSplit[3];
        Message thisMessage = new Message(message , Players.getPlayerByToken(token).getUsername());
        Players.getPlayerByUsername(thisMessage.getFrom()).getAllSentMessages().add(thisMessage);
        Message.getAllMessages().add(thisMessage);
        Message replyingMessage = Message.getMessageByID(id);
        return thisMessage.getFrom() + "Replying to : " + replyingMessage.getMessage() + "\n" + "____"  +
                thisMessage.getMessage() + "\n" +
                thisMessage.getDate() + thisMessage.getId();
    }

    public static String numberOfOnlinePlayers() {
        return String.valueOf(LoginController.getAllLoggedInPlayers().size());
    }
}
