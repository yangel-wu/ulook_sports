package sports.sports.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangle on 2015/11/27 0027.
 */
public class Messages implements Serializable {

    private List<Message> news;
    private String responseCode;
    private String responseMsg;

    public List<Message> getNews() {
        return news;
    }

    public void setNews(List<Message> news) {
        this.news = news;
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
}
