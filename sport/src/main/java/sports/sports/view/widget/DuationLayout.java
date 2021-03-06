package sports.sports.view.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import tv.liangzi.quantum.R;
import tv.liangzi.quantum.utils.LogUtils;


/**
 * Created by yifeiyuan on 15/6/19.
 */
public class DuationLayout extends RelativeLayout{

    private static final String TAG = "FavorLayout";

    private Interpolator line = new LinearInterpolator();//线性
    private Interpolator acc = new AccelerateInterpolator();//加速
    private Interpolator dce = new DecelerateInterpolator();//减速
    private Interpolator accdec = new AccelerateDecelerateInterpolator();//先加速后减速
    private Interpolator[] interpolators ;

    private int mHeight;
    private int mWidth;

    private LayoutParams lp;
    private Context mContext;

    public DuationLayout(Context context) {
        super(context);
        mContext=context;
    }

    public DuationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        setBackgroundColor(getResources().getColor(R.color.transparent));
        init();
    }

    private Drawable red ;
    private Drawable yellow ;
    private Drawable orange ;
    private Drawable pink ;
    private Drawable cyan ;
    private Drawable blue ;
    private Drawable[] drawables ;

    private Random random = new Random();

    private int dHeight;
    private int dWidth;

    private void init() {

        //初始化显示的图片。
        drawables = new Drawable[6];
        red = getResources().getDrawable(R.drawable.three_duration);
//        yellow = getResources().getDrawable(R.drawable.orange);
//        blue = getResources().getDrawable(R.drawable.red);
//        orange = getResources().getDrawable(R.drawable.pink);
//        pink = getResources().getDrawable(R.drawable.blue);
//        cyan = getResources().getDrawable(R.drawable.cyan);

        drawables[0]=red;
//        drawables[1]=yellow;
//        drawables[2]=blue;
//        drawables[3]=orange;
//        drawables[4]=pink;
//        drawables[5]=cyan;
        //获取图的宽高 用于后面的计算
        //注意 我这里3张图片的大小都是一样的,所以我只取了一个
        dHeight = red.getIntrinsicHeight();
        dWidth = red.getIntrinsicWidth();

        //底部 并且 水平居中
        lp = new LayoutParams(dp2px(60), dp2px(60));
        lp.addRule(CENTER_HORIZONTAL, TRUE);//这里的TRUE 要注意 不是true
        lp.addRule(ALIGN_PARENT_BOTTOM, TRUE);

        // 初始化插补器
        interpolators = new Interpolator[4];
        interpolators[0] = acc;
        interpolators[1] = acc;
        interpolators[2] = acc;
        interpolators[3] = acc;

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取本身的宽高 这里要注意,测量之后才有宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }


    public void addFavor(String txMoney) {

//        ImageView imageView = new ImageView(getContext());
        TextView textView=new TextView(getContext());
        textView.setBackgroundResource(R.drawable.three_duration);
        textView.setText("￥"+txMoney);
        textView.setTextSize(12);
        textView.setTextColor(mContext.getResources().getColor(R.color.white));
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(lp);
        //随机选一个
//        imageView.setImageDrawable(drawables[0]);
//        imageView.setLayoutParams(lp);

        addView(textView);
        Log.v(TAG, "add后子view数:"+getChildCount());

        Animator set = getAnimator(textView);
        set.addListener(new AnimEndListener(textView));
        set.start();

    }

    private Animator getAnimator(View target){
        AnimatorSet set = getEnterAnimtor(target);
        ValueAnimator bezierValueAnimator = getBezierValueAnimator(target);
        AnimatorSet animatorSet= stopEnterAnimtor(target);
        AnimatorSet finalSet = new AnimatorSet();
//        finalSet.playSequentially(set);
        finalSet.playTogether(set, animatorSet);
//        finalSet.playSequentially(set, bezierValueAnimator);
        finalSet.setInterpolator(acc);
        finalSet.setTarget(target);
        return finalSet;
    }

    private AnimatorSet getEnterAnimtor(final View target) {

        ObjectAnimator alpha = ObjectAnimator.ofFloat(target,View.ALPHA, 0.2f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 1f, 2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 1f, 2f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(6000);
        enter.setInterpolator(acc);
        enter.playTogether(alpha, scaleX, scaleY);
        enter.setTarget(target);
        return enter;
    }
    private AnimatorSet stopEnterAnimtor(final View target) {

//        ObjectAnimator alpha = ObjectAnimator.ofFloat(target,View.ALPHA, 0.2f, 0.5f);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target,View.TRANSLATION_Y, 0, 300);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target,View.SCALE_Y, 0.5f, 1f);
        AnimatorSet enter = new AnimatorSet();
        ObjectAnimator translationY = ObjectAnimator.ofFloat(target,View.TRANSLATION_Y, 0f, -900f);
        translationY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float temp = (Float) valueAnimator.getAnimatedValue();
//                target.setX(temp);\
                LogUtils.e("Y", temp + "");
                target.setY(temp);
                // 这里顺便做一个alpha动画
//                target.setAlpha(1-valueAnimator.getAnimatedFraction());
            }
        });
        translationY.setInterpolator(acc);
        enter.setDuration(9000);
//        enter.setInterpolator(interpolators[0]);
        enter.playTogether(translationY);
        enter.setTarget(target);
        return enter;
    }
    private ValueAnimator getBezierValueAnimator(View target) {

        //初始化一个贝塞尔计算器- - 传入
        BezierEvaluator evaluator = new BezierEvaluator(getPointF(1),getPointF(1));

        //这里最好画个图 理解一下 传入了起点 和 终点
        ValueAnimator animator = ValueAnimator.ofObject(evaluator,new PointF(200,800),new PointF(random.nextInt(200),1000));
        animator.addUpdateListener(new BezierListenr(target));
        animator.setTarget(target);
        animator.setDuration(15000);
        return animator;
    }

    /**
     * 获取中间的两个 点
     * @param scale
     */
    private PointF getPointF(int scale) {

        PointF pointF = new PointF();
        pointF.x = random.nextInt(1);//减去100 是为了控制 x轴活动范围,看效果 随意~~
        //再Y轴上 为了确保第二个点 在第一个点之上,我把Y分成了上下两半 这样动画效果好一些  也可以用其他方法
        pointF.y = random.nextInt((100))/scale;
        return pointF;
    }

    private class BezierListenr implements ValueAnimator.AnimatorUpdateListener {

        private View target;

        public BezierListenr(View target) {
            this.target = target;
        }
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            // 这里顺便做一个alpha动画
            target.setAlpha(1-animation.getAnimatedFraction());
        }
    }


    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //因为不停的add 导致子view数量只增不减,所以在view动画结束后remove掉
            removeView((target));
            Log.v(TAG, "removeView后子view数:"+getChildCount());
        }
    }
    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
