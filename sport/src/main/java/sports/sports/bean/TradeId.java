package sports.sports.bean;

import java.io.Serializable;

/**
 * Created by yangle on 2015/11/3 0003.
 */
public class TradeId implements Serializable {

    private int tradeId;
    private String responseCode;
    private String responseMsg;

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

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }


}
