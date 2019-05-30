package edu.uw.tacoma.tcss450.blm24.megaphone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A model that handles the properties of a chat-room "group"
 */
public class Group implements Serializable {

    /**
     * Name of JSON element for a group's name.
     */
    private static final String NAME = "groupname";

    /**
     * Name of JSON element for whether a group is private.
     */
    private static final String PRIVATE = "isprivate";

    /**
     * Name of JSON element for if all users can send messages.
     */
    private static final String ALL_SEND = "sendmessage";

    /**
     * Name of JSON element for the radius of the circle.
     */
    private static final String RADIUS = "radius";

    /**
     * Name of JSON element for the latitude.
     */
    private static final String LAT = "lat";

    /**
     * Name of JSON element for the longitude.
     */
    private static final String LON = "lon";

    /**
     * This group's name.
     */
    private String name;

    /**
     * This group's radius.
     */
    private int radius;

    /**
     * This group's latitude and longitude.
     */
    private double lat, lon;

    /**
     * This group's settings.
     * Whether all users in the group may sent messages.
     * Whether the group is available to all users.
     */
    private boolean sendMessage, isPrivate;

    public Group(String name, boolean isPrivate, boolean sendMessage, int radius, double lat, double lon) {
        this.name = name;
        this.radius = radius;
        this.lat = lat;
        this.lon = lon;
        this.sendMessage = sendMessage;
        this.isPrivate = isPrivate;
    }

    /**
     * @param groupJson the JSON to be parsed into a group.
     * @return the Group representation of the JSON.
     * @throws JSONException if it cannot parse correctly
     */
    public static List<Group> parseJson(String groupJson) throws JSONException {
        List<Group> groupList = new ArrayList<>();
        if (groupJson != null) {
            JSONArray arr = new JSONArray(groupJson) ;
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Group group = new Group(obj.getString(Group.NAME), obj.getBoolean(Group.PRIVATE),
                        obj.getBoolean(Group.ALL_SEND), obj.getInt(Group.RADIUS),
                        obj.getDouble(LAT), obj.getDouble(LON));
                groupList.add(group);
            }
        }
        return groupList;
    }

    /**
     * @return a JSON representation of this object.
     * @throws JSONException if it cannot convert into JSON correctly.
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(NAME, name);
        json.put(PRIVATE, true); //TODO broken, db doesn't take falses...
        json.put(ALL_SEND, true); //TODO "                           "
        json.put(RADIUS, radius + 10); //TODO hard min, db doesn't take 0s
        json.put(LAT, lat);
        json.put(LON, lon);
        return json;
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

    /**
     * Returns a string representation of this group.
     *
     * @return a string representation.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(' ').append(lat)
                .append(':').append(lon);
        return builder.toString();
    }
}