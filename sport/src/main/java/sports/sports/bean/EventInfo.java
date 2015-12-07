package sports.sports.bean;

import java.io.Serializable;

/**
 * Created by yangle on 2015/11/6 0006.
 */
public class EventInfo implements Serializable {

    /**
     * {
     * "eventId": 3,
     * "userFollowEvent": 1,
     * "eventBegin": 1443628855000,
     * "eventCreate": 1442972568000,
     * "count": 2,
     * "eventType": 2,
     * "eventTags": [],
     * "eventDescribe": "国庆七天乐，越过越快乐",
     * "responseCode": "201",
     * "eventTitle": "国庆七天乐",
     * "eventEnd": 1444233659000,
     * "eventImg": "http://7xix0q.com2.z0.glb.qiniucdn.com/Avatar_film.jpg",
     * "userType": 0,
     * "criticismCount": 4
     * }
     */
    private int eventId;
    private int userFollowEvent;
    private long eventBegin;
    private long eventCreate;
    private int count;
    private int eventType;
    private String eventDescribe;
    private String responseCode;
    private String eventTitle;
    private long eventEnd;
    private String eventImg;
    private int userType;
    private int criticismCount;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserFollowEvent() {
        return userFollowEvent;
    }

    public void setUserFollowEvent(int userFollowEvent) {
        this.userFollowEvent = userFollowEvent;
    }

    public long getEventBegin() {
        return eventBegin;
    }

    public void setEventBegin(long eventBegin) {
        this.eventBegin = eventBegin;
    }

    public long getEventCreate() {
        return eventCreate;
    }

    public void setEventCreate(long eventCreate) {
        this.eventCreate = eventCreate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getEventDescribe() {
        return eventDescribe;
    }

    public void setEventDescribe(String eventDescribe) {
        this.eventDescribe = eventDescribe;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public long getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(long eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getEventImg() {
        return eventImg;
    }

    public void setEventImg(String eventImg) {
        this.eventImg = eventImg;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getCriticismCount() {
        return criticismCount;
    }

    public void setCriticismCount(int criticismCount) {
        this.criticismCount = criticismCount;
    }
}
