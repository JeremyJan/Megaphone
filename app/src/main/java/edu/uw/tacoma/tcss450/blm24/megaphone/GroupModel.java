package edu.uw.tacoma.tcss450.blm24.megaphone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupModel implements Serializable {

    private static final String NAME = "groupName";
    private static final String RADIUS = "groupRadius";
    private static final String ALL_SEND = "canSendMessages";
    private static final String PRIVATE = "private";
    private static final String LAT = "latitude";
    private static final String LON = "longitude";

    private String name;
    private int radius;
    private double lat, lon;
    private boolean sendMessage, isPrivate;

    public GroupModel(String name, int radius, double lat, double lon, boolean sendMessage, boolean isPrivate) {
        this.name = name;
        this.radius = radius;
        this.lat = lat;
        this.lon = lon;
        this.sendMessage = sendMessage;
        this.isPrivate = isPrivate;
    }

    public static List<GroupModel> parseCourseJson(String courseJson) throws JSONException {
        List<GroupModel> courseList = new ArrayList<>();
        if (courseJson != null) {
            JSONArray arr = new JSONArray(courseJson) ;
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                GroupModel course = new GroupModel(obj.getString(GroupModel.NAME), obj.getInt(GroupModel.RADIUS),
                        obj.getDouble(LAT), obj.getDouble(LON), obj.getBoolean(GroupModel.ALL_SEND), obj.getBoolean(GroupModel.PRIVATE));
                courseList.add (course);
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
