package sports.sports.bean;

import java.io.Serializable;

/**
 * Created by yangle on 2015/11/3 0003.
 */
public class Trades implements Serializable {

    private int tradeId;
    private int userId;
    private String cardNo;
    private String platform;

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
