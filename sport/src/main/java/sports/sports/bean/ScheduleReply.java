package sports.sports.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangle on 2015/9/25 0025.
 */
public class ScheduleReply implements Serializable {

    private List<Criticism> criticisms;
    private String responseCode;
    private String responseMsg;
    public List<Criticism> getCriticisms() {
        return criticisms;
    }

    public void setCriticisms(List<Criticism> criticisms) {
        this.criticisms = criticisms;
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

    public class Criticism implements Serializable {

        private Soncriticism criticism;

        public Soncriticism getCriticism() {
            return criticism;
        }
        public void setCriticism(Soncriticism criticism) {
            this.criticism = criticism;
        }
    }
}
