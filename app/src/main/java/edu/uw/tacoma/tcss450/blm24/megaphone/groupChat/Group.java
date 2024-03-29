package edu.uw.tacoma.tcss450.blm24.megaphone.groupChat;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

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
     * This group's latitude and longitude as a geopoint object.
     */
    private GeoPoint geoPoint;

    /**
     * This group's settings.
     * Whether all users in the group may sent messages.
     * Whether the group is available to all users.
     */
    private boolean sendMessage, isPrivate;

    private String groupID;

    private @ServerTimestamp Date timestamp;

    /**
     * Empty constructor for firestore toObject
     */
    public Group() {

    }

    public Group(String name, boolean isPrivate, boolean sendMessage, int radius
            , GeoPoint geoPoint, Date timeStamp) {
        this.name = name;
        this.radius = radius;
        this.geoPoint = geoPoint;
        this.sendMessage = sendMessage;
        this.isPrivate = isPrivate;
        this.timestamp = timeStamp;

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

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getGroupID() {
        return groupID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}