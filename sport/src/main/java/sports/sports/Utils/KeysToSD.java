package sports.sports.Utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import tv.liangzi.quantum.config.MyAapplication;

/**
 * Created by yangle on 2015/8/27 0027.
 */
public class KeysToSD {
        //存储为文件
   public static void String2SD(String str,Context context) throws IOException{
       FileOutputStream outStream = context.openFileOutput("policekeys.txt", Context.MODE_PRIVATE);
       outStream.write(str.getBytes());
       outStream.close();
   }
    //文件的读取并返回字符串数组
    public static String[] file2Array()throws IOException{

        FileInputStream inStream = MyAapplication.applicationContext.openFileInput("policekeys.txt");//只需传文件名
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();//
        //输出到内存
        int len=0;
        byte[] buffer = new byte[1024];
        while((len=inStream.read(buffer))!=-1){
            outStream.write(buffer, 0, len);
        }
        byte[] content_byte = outStream.toByteArray();
        String result = new String(content_byte);
        String [] str = result.split(",");
        return str;
    }
}


