package sports.sports.bean;

import java.io.Serializable;

/**
 * Created by invinjun on 2015/7/16.
 */
public class Stream implements Serializable {
    private String id;
    private String hub;
    private String title;
    private String publishKey;
    private String publishSecurity;
    private Hosts hosts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHub() {
        return hub;
    }

    public void setHub(String hub) {
        this.hub = hub;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishKey() {
        return publishKey;
    }

    public void setPublishKey(String publishKey) {
        this.publishKey = publishKey;
    }

    public String getPublishSecurity() {
        return publishSecurity;
    }

    public void setPublishSecurity(String publishSecurity) {
        this.publishSecurity = publishSecurity;
    }

    public Hosts getHosts() {
        return hosts;
    }

    public void setHosts(Hosts hosts) {
        this.hosts = hosts;
    }


}
