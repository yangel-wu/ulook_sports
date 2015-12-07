package sports.sports.Utils;

import android.view.View;

import tv.liangzi.quantum.bean.Live;

/**
 * Created by yangle on 2015/9/24 0024.
 */
public interface RecyclerViewListener {

   public void onItemClick(View view, Live live);

    public void onItemLongClick(View view, Live live);

}
