

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class Message {
    private String id ;
    private String message;
    private String to;
    private String from;
    private String date;
    private String time;
    private static ArrayList<Message> allMessages;
    public Message(String message , String from ){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        setMessage(message);
        setFrom(from);
        setTo(to);
        setDate(now.toString());
        ChatRoom.display(this);
        setId(UUID.randomUUID().toString());
        allMessages.add(this);
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getTo() {
        return to;
    }
    public static Message getMessageByID(String id) {
        for (Message message: allMessages
             ) {
            if (message.getId().equals(id)) return message;
        }
        return null;
    }

    public static ArrayList<Message> getAllMessages() {
        return allMessages;
    }
}
