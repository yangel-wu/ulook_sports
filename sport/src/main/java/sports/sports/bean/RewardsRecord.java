package sports.sports.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangle on 2015/11/3 0003.
 */
public class RewardsRecord implements Serializable {

    private String responseCode;
    private String responseMsg;
    private List<Pays> pay;

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

    public List<Pays> getPay() {
        return pay;
    }

    public void setPay(List<Pays> pay) {
        this.pay = pay;
    }
}
