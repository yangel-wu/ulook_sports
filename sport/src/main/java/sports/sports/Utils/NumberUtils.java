package sports.sports.Utils;

/**
 * Created by yangle on 2015/8/17 0017.
 */
public class NumberUtils {

    public static String change2standard(int number){
        String result = null;
        if(number>=1000&&number<9999){
            int kil = number/1000;
            int thousand = number%1000/100;
            result = kil+ "."+thousand+"k";
            return result;
        }else if(number>=10000&&number<99999){
            int w = number/10000;
            int kil = number%10000/1000;
            int thousand = number%10000%1000/100;
            result = w+"."+kil+thousand+"w";
            return result;
        }else{
           result = String .valueOf(number);
            return  result;
        }

    }

}
