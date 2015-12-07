package sports.sports.bean;

import java.io.Serializable;

/**
 * Created by invinjun on 2015/7/16.
 */
public class Play implements Serializable{
    private String hls;
    private String rtmp;



    public String getHls() {
        return hls;
    }

    public void setHls(String hls) {
        this.hls = hls;
    }

    public String getRtmp() {
        return rtmp;
    }

    public void setRtmp(String rtmp) {
        this.rtmp = rtmp;
    }

}
