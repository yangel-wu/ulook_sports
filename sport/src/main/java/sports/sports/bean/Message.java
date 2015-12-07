package sports.sports.bean;

import java.io.Serializable;

/**
 * Created by yangle on 2015/11/27 0027.
 * <p/>
 * state:(消息状态)[0:未读;1:已读;2:发消息方当消息接收方未读时删除;3:发消息方当消息接收方已读时删除;4:接收消息方删除;5:消息违禁或双方均删除;]
 */
public class Message implements Serializable {
    private int newsId;
    private int userId;
    private String nickName;
    private String photo;
    private int toUserId;
    private String toUserNickName;
    private String toUserPhoto;
    private String content;
    private int type;
    private Long create;
    private int state;

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
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

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserNickName() {
        return toUserNickName;
    }

    public void setToUserNickName(String toUserNickName) {
        this.toUserNickName = toUserNickName;
    }

    public String getToUserPhoto() {
        return toUserPhoto;
    }

    public void setToUserPhoto(String toUserPhoto) {
        this.toUserPhoto = toUserPhoto;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getCreate() {
        return create;
    }

    public void setCreate(Long create) {
        this.create = create;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "newsId=" + newsId +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", photo='" + photo + '\'' +
                ", toUserId=" + toUserId +
                ", toUserNickName='" + toUserNickName + '\'' +
                ", toUserPhoto='" + toUserPhoto + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", create=" + create +
                ", state=" + state +
                '}';
    }
}
