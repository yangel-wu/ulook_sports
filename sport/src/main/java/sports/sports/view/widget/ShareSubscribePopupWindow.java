package sports.sports.view.widget;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import java.io.IOException;

import tv.liangzi.quantum.R;
import tv.liangzi.quantum.bean.Live;
import tv.liangzi.quantum.config.MyAapplication;
import tv.liangzi.quantum.utils.OkHttpUtil;

/**
 * @author yangle
 * @data: 2015-08-14
 * @version: V1.0
 */

public class ShareSubscribePopupWindow extends PopupWindow implements OnClickListener {

    private Context context;
    private ImageView wechatCircle;
    private ImageView wechatFriends;
    private ImageView sina;
    private Handler mHandler;
    private String platform;
    private SharedPreferences SearchSp;
    private String accessToken;
    private String userId;
    private String videoId;
    private String type;
    //    private TextView share_cancle;
    private Live living;
    //分享相关
    private ImageView share_close;
    //黑色背景
    private RelativeLayout ll_subscribe_share;
    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");

    public ShareSubscribePopupWindow(Context cx, Handler handler, Live living) {
        this.context = cx;
        this.mHandler = handler;
        //拿到直播对象
        this.living = living;
    }

    public ShareSubscribePopupWindow(Context cx, Handler handler, Live living, String type) {
        this.context = cx;
        this.mHandler = handler;
        //拿到直播对象
        this.living = living;
        this.type = type;
    }


    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.share_subscribe_popwindow, null);

        //三个类别的分享
        wechatCircle = (ImageView) view.findViewById(R.id.weichat_circle_share_icon);
        wechatFriends = (ImageView) view.findViewById(R.id.weichat_friends_share_icon);
        sina = (ImageView) view.findViewById(R.id.sina_share_icon);

        share_close = (ImageView) view.findViewById(R.id.share_close);
        ll_subscribe_share = (RelativeLayout) view.findViewById(R.id.ll_subscribe_share);


        ll_subscribe_share.setOnClickListener(this);
        wechatCircle.setOnClickListener(this);
        wechatFriends.setOnClickListener(this);
        sina.setOnClickListener(this);
        share_close.setOnClickListener(this);

        SearchSp = context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
        userId = SearchSp.getString("userId", "0");
        accessToken = SearchSp.getString("accessToken", "0");

        // 添加布局
        this.setContentView(view);
        // 设置SharePopupWindow宽度
        this.setWidth(LayoutParams.FILL_PARENT);
        // 设置SharePopupWindow高度
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置setFocusable可获取焦点
        this.setFocusable(true);
        // 设置setFocusable动画风格
        this.setAnimationStyle(R.style.AnimBottom);
        //画背景
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置背景
        this.setBackgroundDrawable(dw);
        configPlatforms();
        //分享部分
        setShareContent();


    }

    /**
     * 配置分享平台参数</br>
     */
    private void configPlatforms() {
        // 添加新浪SSO授权
        mController.getConfig().setSsoHandler(new SinaSsoHandler());

        // 添加微信、微信朋友圈平台
        addWXPlatform();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weichat_circle_share_icon:
                platform = "2";
                mController.postShare(context, SHARE_MEDIA.WEIXIN_CIRCLE,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(context, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    Thread shareThread = new Thread(new ShareThread());
                                    shareThread.start();
                                    Toast.makeText(context, "分享成功.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101) {
                                        eMsg = "没有授权";
                                    }
//                                    Toast.makeText(context, "分享失败[" + eCode + "] " +
//                                            eMsg,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                dismiss();
                break;
            case R.id.weichat_friends_share_icon:
                platform = "3";
                mController.postShare(context, SHARE_MEDIA.WEIXIN,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(context, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    Thread shareThread = new Thread(new ShareThread());
                                    shareThread.start();
//                                    Toast.makeText(context, "分享成功.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101) {
                                        eMsg = "没有授权";
                                    }
//                                    Toast.makeText(context, "分享失败[" + eCode + "] " +
//                                            eMsg,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                dismiss();
                break;
            case R.id.sina_share_icon:
                platform = "1";
                mController.postShare(context, SHARE_MEDIA.SINA,
                        new SocializeListeners.SnsPostListener() {
                            @Override
                            public void onStart() {
//                                Toast.makeText(context, "开始分享.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    Thread shareThread = new Thread(new ShareThread());
                                    shareThread.start();
                                    Toast.makeText(context, "分享成功.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101) {
                                        eMsg = "没有授权";
                                    }
                                }
                            }
                        });
                dismiss();
                break;
            case R.id.share_close:
                dismiss();
                break;
            case R.id.ll_subscribe_share:
                dismiss();
                break;
            default:
                break;
        }
    }


    private void setShareContent() {
//用户用ULOOK要看直播创建了节目节目名称，不过来看看吗？网页链接
        String sina = "";
        // 配置SSO  新浪微博
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        if (type.equals("playbackinfo")||type.equals("live")) {
            sina = living.getNickName() + "用ULOOK要看直播发起了直播 " + living.getTitle() + "快来围观" + "http://share.ulook.tv:8080/liangzi/liveBack.html?personId=" + userId + "&videoId=" + living.getLiveId();
        } else if (type.equals("scheduleinfo")) {
            sina = living.getNickName() + "用ULOOK要看直播发起了预约 " + living.getTitle() + "快来围观" + "http://share.ulook.tv:8080/liangzi/reserve.html?personId=" + userId  + "&videoId=" + living.getLiveId();
        } else if (type.equals("showinfo")) {
            sina = living.getNickName() + "用ULOOK要看直播创建了节目 " + living.getTitle() + "不过来看看吗？" + "http://share.ulook.tv:8080/liangzi/program.html?personId=" + userId +"&programId=" + living.getLiveId();
        }

        mController.setShareContent(sina);


//        UMImage localImage = new UMImage(context, R.drawable.delete);
        UMImage urlImage = new UMImage(context, living.getImg());
        // UMImage resImage = new UMImage(getActivity(), R.drawable.icon);
        mController.setShareMedia(urlImage);
        // 视频分享
//		UMVideo video = new UMVideo(
//				"http://v.youku.com/v_show/id_XNTc0ODM4OTM2.html");
        // vedio.setThumb("http://www.umeng.com/images/pic/home/social/img-1.png");
//		video.setTitle("友盟社会化组件视频");
//		video.setThumb(urlImage);
//
//		UMusic uMusic = new UMusic(
//				"http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
//		uMusic.setAuthor("umeng");
//		uMusic.setTitle("天籁之音");
////        uMusic.setThumb(urlImage);
//		uMusic.setThumb("http://www.umeng.com/images/pic/social/chart_1.png");

//        UMEmoji emoji = new UMEmoji(getActivity(), "http://www.pc6.com/uploadimages/2010214917283624.gif");
//        UMEmoji emoji = new UMEmoji(getActivity(), "/storage/sdcard0/emoji.gif");

    }


    /**
     * @return
     * @功能描述 : 添加微信平台分享
     */
    private void addWXPlatform() {


//        // 注意：在微信授权的时候，必须传递appSecret
//        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wx349ba941cb9119c7";
        String appSecret = "1c74a6f8f5c327dd3015d23b2626fdc7";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(context, appId, appSecret);
        wxHandler.addToSocialSDK();
//
//        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        UMImage urlImage = new UMImage(context, living.getImg());
//
//        // 设置微信好友分享内容
//        WeiXinShareContent weixinContent = new WeiXinShareContent();
//        // 设置分享文字

//        weixinContent.setShareContent("量子频道。" + living.getNickName() + "直播了" + living.getTitle());
//        // 设置title
//        weixinContent.setTitle("ULOOK");
//        // 设置分享内容跳转URL
//        weixinContent.setTargetUrl(living.getHlsPlayUrl());
//        // 设置分享图片
//        weixinContent.setShareImage(urlImage);
//        mController.setShareMedia(weixinContent);/设置微信好友分享内容

        //------------------------------------------------------------------------
        //“标题”-用户发起的直播
        //

        String friend = null;
        if (type.equals("playbackinfo")||type.equals("live")) {
            friend = "\"" + living.getTitle() + "\"" + "-" + living.getNickName() + "发起的直播";
        } else if (type.equals("scheduleinfo")) {
            friend = "\"" + living.getTitle() + "\"" + "-" + living.getNickName() + "发起的预约";
        } else if (type.equals("showinfo")) {
            friend = "\"" + living.getTitle() + "\"" + "-" + living.getNickName() + "创建的节目";
        }

        //设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
//设置分享文字
        weixinContent.setShareContent("ULOOK要看直播-让颜值和才华变现");
//设置title
        weixinContent.setTitle(friend);
//设置分享内容跳转URL
        String friendURL = "";
        if (type.equals("playbackinfo")||type.equals("live")) {
            friendURL = "http://share.ulook.tv:8080/liangzi/liveBack.html?personId=" + userId + "&videoId=" + living.getLiveId();
        } else if (type.equals("scheduleinfo")) {
            friendURL = "http://share.ulook.tv:8080/liangzi/reserve.html?personId=" + userId + "&videoId=" + living.getLiveId();
        } else if (type.equals("showinfo")) {
            friendURL = "http://share.ulook.tv:8080/liangzi/program.html?personId=" + userId + "&programId=" + living.getLiveId();

        }
        weixinContent.setTargetUrl(friendURL);
//设置分享图片
        weixinContent.setShareImage(urlImage);
        mController.setShareMedia(weixinContent);

        //-----------------------------------------------------------------------
        //“标题”-用户通过ULOOK要看直播发起的直播
        //朋友圈
        String circle = null;
        if (type.equals("playbackinfo")||type.equals("live")) {
            circle = "\"" + living.getTitle() + "\"" + "-" + living.getNickName() + "通过ULOOK要看直播发起的直播";
        } else if (type.equals("scheduleinfo")) {
            circle = "\"" + living.getTitle() + "\"" + "-" + living.getNickName() + "通过ULOOK要看直播发起的预约";
        } else if (type.equals("showinfo")) {
            circle = "\"" + living.getTitle() + "\"" + "-" + living.getNickName() + "通过ULOOK要看直播创建的节目";
        }
        //设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(circle);
        //设置朋友圈title
        circleMedia.setTitle(circle);
        weixinContent.setShareImage(urlImage);
        circleMedia.setShareImage(urlImage);
        circleMedia.setTargetUrl(friendURL);
        mController.setShareMedia(circleMedia);


    }


    class ShareThread implements Runnable {
        @Override
        public void run() {
            String url = MyAapplication.IP + "videoShare";
            try {
                sharePost(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //   备用子线程
            Log.e("log", "发出请求");

        }
    }

    /**
     * 用戶評論接口post
     *
     * @param url
     * @throws IOException
     */
    void sharePost(String url) throws IOException {
        RequestBody formBody = new FormEncodingBuilder()
                .add("userId", userId)
                .add("videoId", videoId)
                .add("toType", "0")
                .add("platform", platform)
                .add("accessToken", accessToken)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        OkHttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.getMessage();
            }

            @Override
            public void onResponse(Response response) throws IOException {

                if (response.isSuccessful()) {
//                    Message msgInfo = new Message();
//                    msgInfo.what = 5;
//                    mHandler.sendMessage(msgInfo);
                    Log.e("videoInfoActivity", "評論成功");

                } else {
                    Log.e("videoInfoActivity", "評論失敗，还需要对返回code作进一步处理");

                }
            }

        });


    }


}
