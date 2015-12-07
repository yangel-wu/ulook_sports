package sports.sports.Utils;

import android.hardware.Camera;
import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by invinjun on 2015/11/13.
 */
public class CameraUtils {

    public static Camera.Size getProperSize(List<Camera.Size> sizeList,int h,int w )
    {
        float displayRatio=(float)h/w;
        //先对传进来的size列表进行排序
        Collections.sort(sizeList, new SizeComparator());
        Camera.Size result = null;
        for(Camera.Size size: sizeList) {
            //先按屏幕分辨率优先
            if (h == size.width) {
                float curRatio = ((float) size.width) / size.height;
                Log.v("FragmentUlook", "工具w:" + size.width + ",h:" + size.height);
                if (curRatio - displayRatio == 0) {
                    Log.v("FragmentUlook", "选中w:" + size.width + ",h:" + size.height);
                    result = size;
                    return result;
                }
            }else {
                float curRatio = ((float) size.width) / size.height;
                Log.v("FragmentUlook", "工具w:" + size.width + ",h:" + size.height);
                if (curRatio - displayRatio == 0) {
                    Log.v("FragmentUlook", "工具w:" + size.width + ",h:" + size.height);
                    result = size;
                }
            }
        }
        if(null == result)
        {
            for(Camera.Size size: sizeList)
            {
                float curRatio =  ((float)size.width) / size.height;
                if(curRatio == 16f/9)
                {
                    result = size;
                }
            }
        }
        Log.v("FragmentUlook", "选中w:" + result.width + ",h:" + result.height);
        return result;
    }

    static class SizeComparator implements Comparator<Camera.Size>
    {

        @Override
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            // TODO Auto-generated method stub
            Camera.Size size1 = lhs;
            Camera.Size size2 = rhs;
            if(size1.width < size2.width
                    || size1.width == size2.width && size1.height < size2.height)
            {
                return -1;
            }
            else if(!(size1.width == size2.width && size1.height == size2.height))
            {
                return 1;
            }
            return 0;
        }

    }
}
