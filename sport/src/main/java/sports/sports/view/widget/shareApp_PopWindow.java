package sports.sports.view.widget;


import android.content.Context;
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
import tv.liangzi.quantum.config.MyAapplication;
import tv.liangzi.quantum.utils.OkHttpUtil;

/**
 * @data: 2015-4-21
 * @version: V1.0
 */

public class shareApp_PopWindow extends PopupWindow implements OnClickListener {

    private Context context;
    private ImageView wechatCircle;
    private ImageView wechatFriends;
    private ImageView sina;
    private Handler mHandler;
    private String platform;
    private ImageView share_close;
    private RelativeLayout ll_subscribe_share;


    private final UMSocialService mController = UMServiceFactory
            .getUMSocialService("com.umeng.share");

    public shareApp_PopWindow(Context cx, Handler handler) {
        this.context = cx;
        this.mHandler = handler;

    }


    public void shareApp_PopWindow() {

        View view = LayoutInflater.from(context).inflate(R.layout.share_subscribe_popwindow, null);

        ll_subscribe_share = (RelativeLayout) view.findViewById(R.id.ll_subscribe_share);
        wechatCircle = (ImageView) view.findViewById(R.id.weichat_circle_share_icon);
        wechatFriends = (ImageView) view.findViewById(R.id.weichat_friends_share_icon);
        sina = (ImageView) view.findViewById(R.id.sina_share_icon);
        share_close = (ImageView) view.findViewById(R.id.share_close);

        wechatCircle.setOnClickListener(this);
        wechatFriends.setOnClickListener(this);
        sina.setOnClickListener(this);
        ll_subscribe_share.setOnClickListener(this);
        share_close.setOnClickListener(this);


        this.setContentView(view);
        this.setWidth(LayoutParams.FILL_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AnimBottom);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        configPlatforms();
        setShareContent();


    }

    /**
     */
    private void configPlatforms() {
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
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
//                                Toast.makeText(context, "��ʼ����.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    Thread shareThread = new Thread(new ShareThread());
                                    shareThread.start();
//                                    Toast.makeText(context, "����ɹ�.", Toast.LENGTH_SHORT).show();
                                } else {
                                    String eMsg = "";
                                    if (eCode == -101) {
//                                        eMsg = "û����Ȩ";
                                    }
//                                    Toast.makeText(context, "����ʧ��[" + eCode + "] " +
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
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    Thread shareThread = new Thread(new ShareThread());
                                    shareThread.start();
                                    Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
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
//                                Toast.makeText(context, "��ʼ����.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                                if (eCode == 200) {
                                    Thread shareThread = new Thread(new ShareThread());
                                    shareThread.start();
                                    Toast.makeText(context, "分享成功", Toast.LENGTH_SHORT).show();
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
            case R.id.ll_subscribe_share:
            case R.id.share_close:
                dismiss();
            default:
                break;
        }
    }


    private void setShareContent() {

        // ����SSO  ����΢��
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.setShareContent("点击下载ULOOK要看直播，帅哥美女陪你嗨，现金打赏赚不停 http://www.pgyer.com/Rgs9");


//        UMImage localImage = new UMImage(context, R.drawable.delete);
        UMImage urlImage = new UMImage(context, R.drawable.app_share_icon);


        // UMImage resImage = new UMImage(getActivity(), R.drawable.icon);
        mController.setShareMedia(urlImage);
        // ��Ƶ����
//		UMVideo video = new UMVideo(
//				"http://v.youku.com/v_show/id_XNTc0ODM4OTM2.html");
        // vedio.setThumb("http://www.umeng.com/images/pic/home/social/img-1.png");
//		video.setTitle("������ữ�����Ƶ");
//		video.setThumb(urlImage);
//
//		UMusic uMusic = new UMusic(
//				"http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
//		uMusic.setAuthor("umeng");
//		uMusic.setTitle("����֮��");
////        uMusic.setThumb(urlImage);
//		uMusic.setThumb("http://www.umeng.com/images/pic/social/chart_1.png");

//        UMEmoji emoji = new UMEmoji(getActivity(), "http://www.pc6.com/uploadimages/2010214917283624.gif");
//        UMEmoji emoji = new UMEmoji(getActivity(), "/storage/sdcard0/emoji.gif");

        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent("ULOOK要看直播-让颜值和才华变现");
        weixinContent.setTitle("下载ULOOK要看直播，高颜值的人都在这里");
        weixinContent.setTargetUrl("http://www.pgyer.com/Rgs9");
        weixinContent.setShareMedia(urlImage);
//        mController.setShareMedia(urlImage);

        // ����  ����Ȧ  ���������
//        CircleShareContent circleMedia = new CircleShareContent();
//        circleMedia.setShareContent("����������ữ�����SDK�����ƶ�Ӧ�ÿ�������罻���?�ܣ�����Ȧ");
//        circleMedia.setTitle("ULOOK");
//        circleMedia.setTargetUrl(living.getHlsPlayUrl());
//        circleMedia.setShareImage(urlImage);

        //����΢������Ȧ��������
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent("下载ULOOK要看直播，高颜值的人都在这里");
        circleMedia.setTitle("下载ULOOK要看直播，高颜值的人都在这里");
        circleMedia.setShareImage(urlImage);
        circleMedia.setTargetUrl("http://www.pgyer.com/Rgs9");
        mController.setShareMedia(circleMedia);

    }


    /**
     * @return
     * @�������� : ���΢��ƽ̨����
     */
    private void addWXPlatform() {
        // ע�⣺��΢����Ȩ��ʱ�򣬱��봫��appSecret
        // wx967daebe835fbeac������΢�ſ���ƽ̨ע��Ӧ�õ�AppID, ������Ҫ�滻����ע���AppID
        String appId = "wx349ba941cb9119c7";
        String appSecret = "1c74a6f8f5c327dd3015d23b2626fdc7";
        // ���΢��ƽ̨
        UMWXHandler wxHandler = new UMWXHandler(context, appId, appSecret);
        wxHandler.addToSocialSDK();

        // ֧��΢������Ȧ
        UMWXHandler wxCircleHandler = new UMWXHandler(context, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();


        UMImage urlImage = new UMImage(context, R.drawable.app_share_icon);

        // ����΢�ź��ѷ�������
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        // ���÷�������
        weixinContent.setShareContent("ULOOK要看直播-让颜值和才华变现");
        weixinContent.setTitle("下载ULOOK要看直播，高颜值的人都在这里");
        // ���÷���������תURL
        weixinContent.setTargetUrl("http://www.pgyer.com/Rgs9");
        // ���÷���ͼƬ
        weixinContent.setShareImage(urlImage);
        mController.setShareMedia(weixinContent);
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
            //   �������߳�
            Log.e("log", "��������");

        }
    }

    /**
     * �Ñ��uՓ�ӿ�post
     *
     * @param url
     * @throws IOException
     */
    void sharePost(String url) throws IOException {
        RequestBody formBody = new FormEncodingBuilder()
                .add("toType", "0")
                .add("platform", platform)
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
                    Log.e("videoInfoActivity", "评论成功");

                } else {
                    Log.e("videoInfoActivity", "评论失败，还需要对返回code作进一步处理");

                }
            }

        });
    }
}
