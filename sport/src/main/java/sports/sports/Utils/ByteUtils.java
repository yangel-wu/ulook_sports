package sports.sports.Utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * Created by yangle on 2015/7/21 0021.
 *
 */
public class ByteUtils {
    /**
     *
     * @param bytes
     * @return
     */

    public static String bytes2kb(long bytes) {

        DecimalFormat    df   = new DecimalFormat("######0.00");

        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
//        if (returnValue > 1) {
             return (df.format(returnValue) + "MB");
//        }else{
//            BigDecimal kilobyte = new BigDecimal(1024);
//            returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
//                    .floatValue();
//            return (df.format(returnValue) + "KB");
//        }
    }

}
