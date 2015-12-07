package sports.sports.Utils;

import java.io.File;

/**
 * Created by yangle on 2015/7/21 0021.
 * <p/>
 * 查看缓存文件的大小 删除文件中的图片
 */
public class FileManager {

    //  得到文件夹的大小
    public static long getFileSize(File file) {
        long size = 0;
        File flist[] = file.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;

    }

    //删除文件中的图片
    public static void deletePics(File file) {

        if (file != null && file.exists() && file.isDirectory()) {
            for (File item : file.listFiles()) {
                item.delete();
            }
        }

    }

}
