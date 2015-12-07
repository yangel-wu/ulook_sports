package sports.sports.bean;

import java.io.Serializable;

/**
 * Created by invinjun on 2015/7/16.
 */
public class Hosts implements Serializable {
    private Publish publish;
    private Play play;


    public Publish getPublish() {
        return publish;
    }

    public void setPublish(Publish publish) {
        this.publish = publish;
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }


}
