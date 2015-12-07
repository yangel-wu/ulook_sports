package sports.sports.view.widget;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import tv.liangzi.quantum.R;
import tv.liangzi.quantum.bean.PeopleDetails;
import tv.liangzi.quantum.bean.ScheduleReply;
import tv.liangzi.quantum.config.MyAapplication;
import tv.liangzi.quantum.utils.OkHttpUtil;

/**
 * @author yangle
 * @data: 2015-08-14
 * @version: V1.0
 */

public class UserInfoCardPopupWindow extends PopupWindow implements OnClickListener {

    private static final int FOLLOW_USER_SUCCESS = 100;
    private static final int FOLLOW_USER_FAILED = 1;
    private static final int REPLY_MESSAGE_SUCCESS = 2;
    private static final int REPLY_MESSAGE_FAILED = 3;


    private Context context;
    private SharedPreferences SearchSp;
    private String accessToken;
    private String userId;
    private PeopleDetails user;
    private ScheduleReply.Criticism criticism;
    private Boolean isFollow;
    //分享相关
    //黑色背景
    private TextView tv_infrom_police;
    private TextView tv_follow_user;
    private TextView tv_message_reply;
    private CircleImageView cir_user_head;
    private LinearLayout ll_bottom_message;
    private EditText et_write_messsage;
    private Button btn_send_message;
    private RelativeLayout rl_root_view;
    private RelativeLayout rl_share;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FOLLOW_USER_SUCCESS:
                    tv_follow_user.setVisibility(View.GONE);
                    break;
            }

        }
    };

    public UserInfoCardPopupWindow(Context context, Handler handler, ScheduleReply.Criticism criticism, Boolean isFollow) {
        this.context = context;
//        this.mHandler = handler;
        this.criticism = criticism;
        this.isFollow = isFollow;
    }


    public void showShareWindow() {

        View view = LayoutInflater.from(context).inflate(R.layout.userinfo_card_popwindow, null);

        initView(view);
        initListener();

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


    }

    public void initView(View view) {

        tv_infrom_police = (TextView) view.findViewById(R.id.tv_infrom_police);
        tv_follow_user = (TextView) view.findViewById(R.id.tv_follow_user);
        if (isFollow) {
            tv_follow_user.setVisibility(View.GONE);
        } else {
            tv_follow_user.setText("+ 关注");
        }
        tv_message_reply = (TextView) view.findViewById(R.id.tv_message_reply);
        cir_user_head = (CircleImageView) view.findViewById(R.id.cir_user_head);
        ll_bottom_message = (LinearLayout) view.findViewById(R.id.rl_bottom_message);
        et_write_messsage = (EditText) view.findViewById(R.id.et_write_messsage);
        btn_send_message = (Button) view.findViewById(R.id.btn_send_message);
        rl_root_view = (RelativeLayout) view.findViewById(R.id.rl_root_view);
        rl_share = (RelativeLayout) view.findViewById(R.id.rl_share);

        if (criticism.getCriticism().getUserPhoto() != null && !criticism.getCriticism().getUserPhoto().equals("")) {
            Picasso.with(context).load(criticism.getCriticism().getUserPhoto()).placeholder(R.drawable.default_head).error(R.drawable.default_head).into(cir_user_head);
        }

    }

    public void initListener() {
        tv_infrom_police.setOnClickListener(this);
        tv_follow_user.setOnClickListener(this);
        tv_message_reply.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_root_view:
                dismiss();
                break;
            case R.id.tv_infrom_police:
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_report_police, null);
                final MyCornerDialog builder = new MyCornerDialog(context, 0, 0, view, R.style.dialog);
                builder.show();
                TextView btn_police_confirm = (TextView) view.findViewById(R.id.btn_police_confirm);
                TextView btn_police_cancel = (TextView) view.findViewById(R.id.btn_police_cancel);
                final EditText et_information_police = (EditText) view.findViewById(R.id.et_information_police);

                btn_police_cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                btn_police_confirm.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                        final String text = et_information_police.getText().toString().trim();
                        if (text == null || text.equals("您提供的举报信息将交由ULOOK处理") || text.equals("")) {
                            Message msg = new Message();
                            msg.what = 16;
                            mHandler.sendMessage(msg);
                        } else {
                            Message msg1 = new Message();
                            msg1.what = 15;
                            mHandler.sendMessage(msg1);
                        }

                    }
                });
                break;
            case R.id.tv_follow_user:
                postThread postThread2 = new postThread();
                Thread thread2 = new Thread(postThread2);
                thread2.start();
                break;
            case R.id.tv_message_reply:
                et_write_messsage.setFocusable(true);
                et_write_messsage.setFocusableInTouchMode(true);
                et_write_messsage.setText("@" + criticism.getCriticism().getUserNickName());
                et_write_messsage.requestFocus();
                et_write_messsage.findFocus();
                rl_share.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_write_messsage, 0);
                break;
            default:
                break;
        }
    }

    /**
     * 添加关注接口
     */
    class postThread implements Runnable {
        @Override
        public void run() {
            String url = MyAapplication.IP + "follow";
            try {
                followPost(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //   备用子线程
            Log.e("log", "发出请求");
        }
    }

    /**
     * 添加关注方法
     *
     * @param url
     * @throws IOException
     */
    void followPost(String url) throws IOException {

        RequestBody formBody = new FormEncodingBuilder()
                .add("userId", userId)
                .add("toUserId", criticism.getCriticism().getUserId() + "")
                .add("accessToken", accessToken)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        OkHttpUtil.enqueue(request, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Message msg = new Message();
                msg.what = FOLLOW_USER_FAILED;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    isFollow = true;
                    Message msg = new Message();
                    msg.what = FOLLOW_USER_SUCCESS;
                    mHandler.sendMessage(msg);
                }
            }
        });
    }

//    /**
//     * 用户详情接口
//     */
//    public class getUserInfoThread implements Runnable {
//        @Override
//        public void run() {
//            String url = MyAapplication.IP + "user" + "?userId=" + Integer.parseInt(userId) + "&id=" + criticism.getCriticism().getUserId();
//            try {
//                getUserInfo(url);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            //   备用子线程
//            Log.e("log", "发出取消请求");
//        }
//    }
//
//    /**
//     * 获取用户详细信息
//     *
//     * @param url
//     * @throws IOException
//     */
//    public void getUserInfo(String url) throws IOException {
//        Request request = new Request.Builder().
//                url(url)
//                .build();
//        OkHttpUtil.enqueue(request, new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                e.getMessage();
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                Gson gson = new Gson();
//                if (response.isSuccessful()) {
//                    if (response.code() == 200) {
//                        user = gson.fromJson(response.body().charStream(), PeopleDetails.class);
//                        Message msg = new Message();
//                        msg.what = 100;
//                        msg.obj = user.isFollow();
//                        mHandler.sendMessage(msg);
//                    }
//                }
//            }
//        });
//    }

}
