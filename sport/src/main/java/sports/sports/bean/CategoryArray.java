package sports.sports.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by invinjun on 2015/9/18.
 */
public class CategoryArray implements Serializable {
    List<Category> categorys;
    private String responseCode;

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

    private String responseMsg;
    public List<Category> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<Category> categorys) {
        this.categorys = categorys;
    }


}
