

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Message {
    private String message;
    private String to;
    private String from;
    private String date;
    private String time;
    private ArrayList<Message> allMessages;
    public Message(String message , String from ){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        setMessage(message);
        setFrom(from);
        setTo(to);
        setDate(now.toString());
        ChatRoom.display(this);
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
}
