package edu.uw.tacoma.tcss450.blm24.megaphone.groupChat;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;


public class GroupMessage {

    private String groupid;
    private String text;
    private String name;
    private @ServerTimestamp Date timestamp;

    public GroupMessage(){
    }

    public GroupMessage(String groupID, String text, String name, Date timeStamp) {
        this.groupid = groupID;
        this.text = text;
        this.name = name;
        this.timestamp = timeStamp;
    }

    public String getId() {
        return groupid;
    }

    public void setId(String id) {
        this.groupid = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
