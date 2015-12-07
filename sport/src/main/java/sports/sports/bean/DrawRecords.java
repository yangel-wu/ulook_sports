package sports.sports.bean;

import java.util.List;

/**
 * Created by yangle on 2015/11/3 0003.
 */
public class DrawRecords {

    private String responseCode;
    private String responseMsg;
    private List<Draw> drawMoney;


    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<Draw> getDrawMoney() {
        return drawMoney;
    }

    public void setDrawMoney(List<Draw> drawMoney) {
        this.drawMoney = drawMoney;
    }


}
