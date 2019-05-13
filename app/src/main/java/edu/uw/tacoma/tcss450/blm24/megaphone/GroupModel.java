package edu.uw.tacoma.tcss450.blm24.megaphone;

import java.io.Serializable;

public class GroupModel implements Serializable {

    private String name;
    private int radius;
    private boolean sendMessage, isPrivate;
    private double lat, lon; //Location Model?

    public GroupModel(String name, int radius, boolean sendMessage, boolean isPrivate) {
        this.name = name;
        this.radius = radius;
        this.sendMessage = sendMessage;
        this.isPrivate = isPrivate;
    }

    public String getName() {
        return this.name;
    }

    public int getRadius() {
        return radius;
    }

    public boolean getSendMessage() {
        return this.sendMessage;
    }

    public boolean getIsPrivate() {
        return this.isPrivate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setSendMessage(boolean sendMessage) {
        this.sendMessage = sendMessage;
    }
}
