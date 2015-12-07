package sports.sports.config;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.widget.TextView;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.Poi;
import com.igexin.sdk.PushManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gdg.ninja.croplib.utils.FileUtils;
import sports.sports.LeanCloud.MessageHandler;
import sports.sports.Utils.LogUtils;

//import tv.liangzi.quantum.helper.DemoHXSDKHelper;

public class MyAapplication extends Application {
    public String EMuserId;
    public static Context applicationContext;
    private static MyAapplication instance;
//        public static final String IP = "http://101.200.173.120:8080/LiangZiServer/";//测试
//          public static final String IP="http://182.92.106.109/";//一号生产
    public static final String IP = "http://123.56.108.103/";//二号生产
//        public static final String IP="http://192.168.1.185:8080/LiangZiServer/";
//    public static final String IP = "http://192.168.1.186:8080/LiangZiServer/";
    public static final String HEAD_PATH = Environment.getExternalStorageDirectory() + File.separator + "Ulooktemp";
    private List<Activity> activities = new ArrayList<Activity>();
    // 用于存放倒计时时间  TimeButton;
    public static Map<String, Long> map;
    //百度地图
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    public TextView mLocationResult;
    public TextView logMsg;
    public String location = "";

    public void onCreate() {
        /**
         * 个推初始化
         */
        PushManager.getInstance().initialize(this.getApplicationContext());
        applicationContext = this;
        instance = this;
//        hxSDKHelper.onInit(applicationContext);
        LogUtils.i("APPLICATION", "没什么");
        FileUtils.newFolder(HEAD_PATH);
        AVOSCloud.initialize(applicationContext, "Wenf2l4M5T75GLGamzzolKBQ", "YDAxqF8ju6lH2fqwQPbvPCBk");
        AVOSCloud.setDebugLogEnabled(true);  // set false when release
        MessageHandler msgHandler = new MessageHandler(this);
        AVIMMessageManager.registerMessageHandler(AVIMTextMessage.class, msgHandler);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        super.onCreate();
    }

    public static MyAapplication getInstance() {
        return instance;
    }

    public static class CustomMessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {
        //接收到消息后的处理逻辑


        @Override
        public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
            super.onMessage(message, conversation, client);
            message.toString();
            message.getMessageType();
            LogUtils.e("leancloud", "!!!!___+++++onMessage成功");
        }

        @Override
        public void onMessageReceipt(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
            super.onMessageReceipt(message, conversation, client);
            message.toString();
            LogUtils.e("57 & 561c8c4b00b07c4ddd93cdd6", "!!!!___+++++onMessage成功onMessage成功");
            message.getMessageType();
        }
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void killActivities() {
        super.onTerminate();
        for (Activity activity : activities) {
            activity.finish();
        }
    }


    /**
     * 实现实时位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
//            sb.append("分享位置 : ");
//            sb.append("time : ");
//            sb.append(location.getTime());
//            sb.append("\nerror code : ");
//            sb.append(location.getLocType());
//            sb.append("\nlatitude : ");
//            sb.append(location.getLatitude());
//            sb.append("\nlontitude : ");
//            sb.append(location.getLongitude());
//            sb.append("\nradius : ");
//            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
//                sb.append("\nspeed : ");
//                sb.append(location.getSpeed());// 单位：公里每小时
//                sb.append("\nsatellite : ");
//                sb.append(location.getSatelliteNumber());
//                sb.append("\nheight : ");
//                sb.append(location.getAltitude());// 单位：米
//                sb.append("\ndirection : ");
//                sb.append(location.getDirection());
                sb.append("\naddr : ");
//                sb.append(location.getAddrStr());
//                sb.append("\ndescribe : ");
//                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
//                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
//                sb.append("\noperationers : ");
//                sb.append(location.getOperators());
//                sb.append("\ndescribe : ");
//                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
//            sb.append("\nlocationdescribe : ");// 位置语义化信息
//            sb.append(location.getLocationDescribe());
            List<Poi> list = location.getPoiList();// POI信息
            if (list != null) {
//                sb.append("\npoilist size = : ");
//                sb.append(list.size());
//                for (Poi p : list) {
//                    sb.append("\npoi= : ");
//                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
//                }
            }
            logMsg(sb.toString());
        }


    }


    /**
     * 显示请求字符串
     *
     * @param str
     */
    public void logMsg(String str) {
        try {
            location = str;
            if (mLocationResult != null)
                mLocationResult.setText(location);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
