package sports.sports.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by invinjun on 2015/9/28.
 */
public class Soncriticism implements Serializable {


    Integer thumbupState;

    Integer criticismId;//评论 ID

    Integer eventId;//话题/活动 ID

    Integer liveId;//预告/活动内/话题内 直播Id

    String liveTitle;//直播名称

    String liveImg;//直播缩略图

    Integer userId;//评论者ID

    String userPhoto;//评论者头像

    String userNickName;//评论者名字

    String criticismContent;//评论内容

    Integer thumbupCount;//点赞数量

    Integer toUserId;//被回复人的ID

    String toUserNickName;//被回复人的名字

    Integer criticismParentId;//回复的上级ID

    String criticismContentGroup;//前三条回复json数组

    Integer criticismState;//状态[预告：0；话题：1；活动：2]

//    Date criticismCreateDate;//评论创建时间

    long created;               //同上

    Date criticismModifyDate;//评论修改时间

    Integer criticismStatus;//存储状态[有效：0；删除：1；]


    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }


    public Integer getCriticismId() {
        return criticismId;
    }

    public void setCriticismId(Integer criticismId) {
        this.criticismId = criticismId;
    }

    public Integer getThumbupState() {
        return thumbupState;
    }

    public void setThumbupState(Integer thumbupState) {
        this.thumbupState = thumbupState;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }

    public String getLiveImg() {
        return liveImg;
    }

    public void setLiveImg(String liveImg) {
        this.liveImg = liveImg;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getCriticismContent() {
        return criticismContent;
    }

    public void setCriticismContent(String criticismContent) {
        this.criticismContent = criticismContent;
    }

    public Integer getThumbupCount() {
        return thumbupCount;
    }

    public void setThumbupCount(Integer thumbupCount) {
        this.thumbupCount = thumbupCount;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserNickName() {
        return toUserNickName;
    }

    public void setToUserNickName(String toUserNickName) {
        this.toUserNickName = toUserNickName;
    }

    public Integer getCriticismParentId() {
        return criticismParentId;
    }

    public void setCriticismParentId(Integer criticismParentId) {
        this.criticismParentId = criticismParentId;
    }

    public String getCriticismContentGroup() {
        return criticismContentGroup;
    }

    public void setCriticismContentGroup(String criticismContentGroup) {
        this.criticismContentGroup = criticismContentGroup;
    }

    public Integer getCriticismState() {
        return criticismState;
    }

    public void setCriticismState(Integer criticismState) {
        this.criticismState = criticismState;
    }

//    public Date getCriticismCreateDate() {
//        return criticismCreateDate;
//    }
//
//    public void setCriticismCreateDate(Date criticismCreateDate) {
//        this.criticismCreateDate = criticismCreateDate;
//    }

    public Date getCriticismModifyDate() {
        return criticismModifyDate;
    }

    public void setCriticismModifyDate(Date criticismModifyDate) {
        this.criticismModifyDate = criticismModifyDate;
    }

    public Integer getCriticismStatus() {
        return criticismStatus;
    }

    public void setCriticismStatus(Integer criticismStatus) {
        this.criticismStatus = criticismStatus;
    }
}
