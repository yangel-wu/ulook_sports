package sports.sports.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangle on 2015/10/23 0023.
 */
public class HotWords implements Serializable {

    private String responseCode;
    private String responseMsg;
    private List<hotWord> hotWords;

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

    public List<hotWord> getHotWords() {
        return hotWords;
    }

    public void setHotWords(List<hotWord> hotWords) {
        this.hotWords = hotWords;
    }

    public class hotWord {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
