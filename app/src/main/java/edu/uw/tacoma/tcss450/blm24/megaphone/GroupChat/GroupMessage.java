package edu.uw.tacoma.tcss450.blm24.megaphone.GroupChat;

public class GroupMessage {

    private String groupid;
    private String text;
    private String name;

    public GroupMessage(){

    }

    public GroupMessage(String groupID, String text, String name) {
        this.groupid = groupID;
        this.text = text;
        this.name = name;
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
}
