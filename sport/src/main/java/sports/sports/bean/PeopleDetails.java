package sports.sports.bean;

import java.io.Serializable;

/**
 * Created by invinjun on 2015/5/14.
 */
public class PeopleDetails implements Serializable {


    private String responseCode;
    private String responseMsg;

    private int userId;


    private String password;
    private boolean isFollow;
    private long created;
    private String nickName;
    private String photo;
    private int liveLikes;
    private String sign;
    private String wechatNickName;
    private String sinaNickName;
    private int focusNum;
    private int fansNum;
    private double lng;
    private double lat;
    private String addr;
    private String commonUploadToken;
    private String especialUploadToken;
    private String accessToken;
    private int livesCount;
    private String keyVersion;
    private String[] keys;
    private int registered;
    private int money;
    private int drawMoney;
    private int earnings;
    private int type;
    private String url;
    private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getDrawMoney() {
        return drawMoney;
    }

    public void setDrawMoney(int drawMoney) {
        this.drawMoney = drawMoney;
    }

    public int getEarnings() {
        return earnings;
    }

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    protected PeopleDetails() {

    }


    public String getKeyVersion() {
        return keyVersion;
    }

    public void setKeyVersion(String keyVersion) {
        this.keyVersion = keyVersion;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
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

    public int getLiveLikes() {
        return liveLikes;
    }

    public void setLiveLikes(int liveLikes) {
        this.liveLikes = liveLikes;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getWechatNickName() {
        return wechatNickName;
    }

    public void setWechatNickName(String wechatNickName) {
        this.wechatNickName = wechatNickName;
    }

    public String getSinaNickName() {
        return sinaNickName;
    }

    public void setSinaNickName(String sinaNickName) {
        this.sinaNickName = sinaNickName;
    }

    public int getFocusNum() {
        return focusNum;
    }

    public void setFocusNum(int focusNum) {
        this.focusNum = focusNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCommonUploadToken() {
        return commonUploadToken;
    }

    public void setCommonUploadToken(String commonUploadToken) {
        this.commonUploadToken = commonUploadToken;
    }

    public String getEspecialUploadToken() {
        return especialUploadToken;
    }

    public void setEspecialUploadToken(String especialUploadToken) {
        this.especialUploadToken = especialUploadToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getLivesCount() {
        return livesCount;
    }

    public void setLivesCount(int livesCount) {
        this.livesCount = livesCount;
    }

}
