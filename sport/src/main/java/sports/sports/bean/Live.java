package sports.sports.bean;

import java.io.Serializable;

public class Live implements Serializable {
    private String responseCode;
    private String responseMsg;
    private int liveId;
    private int userId;
    private String nickName;
    private String photo;
    private String title;
    private String describe;
    private String img;
    private String rtmpPublishUrl;
    private String hlsPublishUrl;
    private String rtmpPlayUrl;
    private String hlsPlayUrl;
    private String hlsPlayBackUrl;
    private String groupid;
    private String shareUrl;
    private int scope;
    private int subscibeId;
    private int subscibes;
    private int shares;
    private int likes;
    private int audience;
    private int liveNumber;
    private int state;
    private long reserved;
    private long begin;
    private String chatroomId;
    private String lchatroomId;
    private Stream stream;
    private int userType;
    private int userFollowEvent;
    private String created;
    private int eventId;
    private String addr;
    private String end;
    private int liveCount;
    private boolean isCollect;


    public int getLiveCount() {
        return liveCount;
    }

    public void setLiveCount(int liveCount) {
        this.liveCount = liveCount;
    }

    public String getHlsPlayBackUrl() {
        return hlsPlayBackUrl;
    }

    public void setHlsPlayBackUrl(String hlsPlayBackUrl) {
        this.hlsPlayBackUrl = hlsPlayBackUrl;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getLchatroomId() {
        return lchatroomId;
    }

    public void setLchatroomId(String lchatroomId) {
        this.lchatroomId = lchatroomId;
    }


    public boolean isCollect() {
        return isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }


    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    private long duration;

    public boolean isFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }

    private boolean isFollow;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserFollowEvent() {
        return userFollowEvent;
    }

    public void setUserFollowEvent(int userFollowEvent) {
        this.userFollowEvent = userFollowEvent;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getLiveNumber() {
        return liveNumber;
    }

    public void setLiveNumber(int liveNumber) {
        this.liveNumber = liveNumber;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRtmpPublishUrl() {
        return rtmpPublishUrl;
    }

    public void setRtmpPublishUrl(String rtmpPublishUrl) {
        this.rtmpPublishUrl = rtmpPublishUrl;
    }

    public String getHlsPublishUrl() {
        return hlsPublishUrl;
    }

    public void setHlsPublishUrl(String hlsPublishUrl) {
        this.hlsPublishUrl = hlsPublishUrl;
    }

    public String getRtmpPlayUrl() {
        return rtmpPlayUrl;
    }

    public void setRtmpPlayUrl(String rtmpPlayUrl) {
        this.rtmpPlayUrl = rtmpPlayUrl;
    }

    public String getHlsPlayUrl() {
        return hlsPlayUrl;
    }

    public void setHlsPlayUrl(String hlsPlayUrl) {
        this.hlsPlayUrl = hlsPlayUrl;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public int getSubscibeId() {
        return subscibeId;
    }

    public void setSubscibeId(int subscibeId) {
        this.subscibeId = subscibeId;
    }

    public int getSubscibes() {
        return subscibes;
    }

    public void setSubscibes(int subscibes) {
        this.subscibes = subscibes;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getAudience() {
        return audience;
    }

    public void setAudience(int audience) {
        this.audience = audience;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getReserved() {
        return reserved;
    }

    public void setReserved(long reserved) {
        this.reserved = reserved;
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }

}
