package sports.sports.bean;

import java.io.Serializable;

/**
 * Created by yangle on 2015/11/3 0003.
 */
public class Pays implements Serializable {

    private String toUserPhoto;
    private int money;
    private int paymentPlatform;
    private String nickName;
    private long created;
    private String toUserNickName;
    private String photo;
    private int payId;
    private int userId;
    private int toUserId;


    public String getToUserPhoto() {
        return toUserPhoto;
    }

    public void setToUserPhoto(String toUserPhoto) {
        this.toUserPhoto = toUserPhoto;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPaymentPlatform() {
        return paymentPlatform;
    }

    public void setPaymentPlatform(int paymentPlatform) {
        this.paymentPlatform = paymentPlatform;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getToUserNickName() {
        return toUserNickName;
    }

    public void setToUserNickName(String toUserNickName) {
        this.toUserNickName = toUserNickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }
}
