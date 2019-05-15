package edu.uw.tacoma.tcss450.blm24.megaphone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {

    private static final String NAME = "groupname";
    private static final String PRIVATE = "isprivate";
    private static final String ALL_SEND = "sendmessage";
    private static final String RADIUS = "radius";
    private static final String LAT = "lat";
    private static final String LON = "lon";

    private String name;
    private int radius;
    private double lat, lon;
    private boolean sendMessage, isPrivate;

    public Group(String name, boolean isPrivate, boolean sendMessage, int radius, double lat, double lon) {
        this.name = name;
        this.radius = radius;
        this.lat = lat;
        this.lon = lon;
        this.sendMessage = sendMessage;
        this.isPrivate = isPrivate;
    }

    public static List<Group> parseCourseJson(String courseJson) throws JSONException {
        List<Group> courseList = new ArrayList<>();
        if (courseJson != null) {
            JSONArray arr = new JSONArray(courseJson) ;
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Group course = new Group(obj.getString(Group.NAME), obj.getBoolean(Group.PRIVATE),
                        obj.getBoolean(Group.ALL_SEND), obj.getInt(Group.RADIUS),
                        obj.getDouble(LAT), obj.getDouble(LON));
                courseList.add(course);
            }
        }
        return courseList;
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