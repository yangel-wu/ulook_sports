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
 * @data: 2015-4-21
 * @version: V1.0
 */

public class SharePopupWindow extends PopupWindow implements OnClickListener {

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
    private ImageView share_dialog_close;
    private Live living;
    private RelativeLayout ll_subscribe_share;
    private RelativeLayout rl_red_bg;
    //分享相关
    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");

    public SharePopupWindow(Context cx, Handler handler, Live living) {
        this.context = cx;
        this.mHandler = handler;
        //拿到直播对象
        this.living = living;
    }


    public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.share_popupwindow, null);
        //三个类别的分享
        wechatCircle = (ImageView) view.findViewById(R.id.weichat_circle_share_icon);
        wechatFriends = (ImageView) view.findViewById(R.id.weichat_friends_share_icon);
        sina = (ImageView) view.findViewById(R.id.sina_share_icon);

        ll_subscribe_share = (RelativeLayout) view.findViewById(R.id.ll_subscribe_share);
        rl_red_bg = (RelativeLayout) view.findViewById(R.id.rl_red_bg);
        //取消分享
        share_dialog_close = (ImageView) view.findViewById(R.id.share_dialog_close);
        //添加点击事件
        share_dialog_close.setOnClickListener(this);
        ll_subscribe_share.setOnClickListener(this);
        wechatCircle.setOnClickListener(this);
        wechatFriends.setOnClickListener(this);
        rl_red_bg.setOnClickListener(this);
        sina.setOnClickListener(this);

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
//        SocializeListeners.SnsPostListener mSnsPostListener  = new SocializeListeners.SnsPostListener() {
//
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onComplete( SHARE_MEDIA platform, int stCode,
//                                    SocializeEntity entity) {
//                if (stCode == 200) {
//                    Toast.makeText(context,"分享成功", Toast.LENGTH_SHORT)
//                            .show();
//                } else {
//                    Toast.makeText(context,
//                            "分享失败 : error code : " + stCode, Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//        };
//        mController.registerListener(mSnsPostListener);

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
//                                    Toast.makeText(context, "分享失败[" + eCode + "] " +
//                                            eMsg,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                dismiss();
                break;
            case R.id.share_dialog_close:
                dismiss();
                break;
            case R.id.rl_red_bg:
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

        // 配置SSO  新浪微博
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.setShareContent(living.getNickName() + "用ULOOK要看直播发起了直播 " + living.getTitle() + "快来围观" + "http://share.ulook.tv:8080/liangzi/liveBack.html?personId=" + userId +"&videoId=" + living.getLiveId());


//        UMImage localImage = new UMImage(context, R.drawable.delete);
        UMImage urlImage = new UMImage(context, living.getImg());
//         UMImage resImage = new UMImage(getActivity(), R.drawable.icon);
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
        //设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
//设置分享文字
        weixinContent.setShareContent("ULOOK要看直播-让颜值和才华变现");
//设置title
        weixinContent.setTitle("\"" + living.getTitle() + "\"" + "-" + living.getNickName() + "发起的直播");
//设置分享内容跳转URL
        weixinContent.setTargetUrl("http://share.ulook.tv:8080/liangzi/liveBack.html?personId=" + userId + "&videoId=" + living.getLiveId());
//设置分享图片
        weixinContent.setShareImage(urlImage);
        mController.setShareMedia(weixinContent);

        //-----------------------------------------------------------------------
        //朋友圈

        //设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent("\"" + living.getTitle() + "\"" + "-" + living.getNickName() + "通过ULOOK要看直播发起的直播");
//设置朋友圈title
        circleMedia.setTitle("\"" + living.getTitle() + "\"" + "-" + living.getNickName() + "通过ULOOK要看直播发起的直播");
        weixinContent.setShareImage(urlImage);
        circleMedia.setShareImage(urlImage);
        circleMedia.setTargetUrl("http://share.ulook.tv:8080/liangzi/liveBack.html?personId=" + userId + "&videoId=" + living.getLiveId());
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
