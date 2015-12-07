package sports.sports.bean;

import java.io.Serializable;

/**
 * Created by yangle on 2015/10/22 0022.
 * <p/>
 * 在认证操作时  用来接收服务器返回来的数据
 */
public class VIPinfo implements Serializable {

    private int exist;
    private int oauthId;
    private int responseCode;

    public int getExist() {
        return exist;
    }

    public void setExist(int exist) {
        this.exist = exist;
    }

    public int getOauthId() {
        return oauthId;
    }

    public void setOauthId(int oauthId) {
        this.oauthId = oauthId;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
