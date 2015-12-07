package sports.sports.view.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import tv.liangzi.quantum.R;
import tv.liangzi.quantum.activity.UlookActivity;
import tv.liangzi.quantum.exception.CustomException;
import tv.liangzi.quantum.utils.LogUtils;

/**
 * �ײ�����
 *
 * @author dewyze
 */
public class MyTabWidget extends LinearLayout {

    private static final String TAG = "MyTabWidget";
    private int[] mDrawableIds = new int[]{
            R.drawable.bg_live, R.drawable.bg_schedule, R.drawable.bg_find, R.drawable.bg_my};
//    R.drawable.bg_live, R.drawable.bg_schedule, R.drawable.bg_sned,
//    R.drawable.bg_find, R.drawable.bg_schedule};

    private List<CheckedTextView> mCheckedList = new ArrayList<CheckedTextView>();
    private List<View> mViewList = new ArrayList<View>();
    private List<ImageView> mIndicateImgs = new ArrayList<ImageView>();

    private CharSequence[] mLabels;

    public MyTabWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TabWidget, defStyle, 0);

        mLabels = a.getTextArray(R.styleable.TabWidget_bottom_labels);

        if (null == mLabels || mLabels.length <= 0) {
            try {
                throw new CustomException("....");
            } catch (CustomException e) {
                e.printStackTrace();
            } finally {
                LogUtils.i(TAG, MyTabWidget.class.getSimpleName() + " ..");
            }
            a.recycle();
            return;
        }

        a.recycle();

        init(context);
    }

    public MyTabWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTabWidget(Context context) {
        super(context);
        init(context);
    }

    /**
     *
     */
    private void init(final Context context) {
        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setBackgroundColor(getResources().getColor(R.color.white));
        LayoutInflater inflater = LayoutInflater.from(context);
        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        params.gravity = Gravity.CENTER;
        int size = mLabels.length + 1;
        for (int i = 0; i < size; i++) {
            if (i == 2) {
                ImageView ulook = new ImageView(context);
                LayoutParams params1 = new LayoutParams(
                        100, LayoutParams.MATCH_PARENT);
                params1.weight = 1f;
                params1.gravity = Gravity.CENTER;
                ulook.setImageResource(R.drawable.icon_send_unpressed);
                ulook.setLayoutParams(params1);
                this.addView(ulook, params1);
                ulook.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, UlookActivity.class));
                    }
                });
            } else {
                final int index = i;
                final View view = inflater.inflate(R.layout.tab_item, null);
                final CheckedTextView itemName = (CheckedTextView) view
                        .findViewById(R.id.item_name);

                if (index > 2) {
                    itemName.setCompoundDrawablesWithIntrinsicBounds(null, context
                            .getResources().getDrawable(mDrawableIds[index - 1]), null, null);
                    itemName.setText(mLabels[index - 1]);
                } else {
                    itemName.setCompoundDrawablesWithIntrinsicBounds(null, context
                            .getResources().getDrawable(mDrawableIds[index]), null, null);
                    itemName.setText(mLabels[index]);
                }
                this.addView(view, params);
                itemName.setTag(index);
                mCheckedList.add(itemName);
                mViewList.add(view);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (index == 2) {
//                        }else {
                        if (index > 2) {
//                                setTabsDisplay(context, index-1);
                            if (null != mTabListener) {
                                setTabsDisplay(context, index);
                                mTabListener.onTabSelected(index);
                            }
                        } else if (index < 2) {
//                                setTabsDisplay(context, index);
                            if (null != mTabListener) {
                                setTabsDisplay(context, index);
                                mTabListener.onTabSelected(index);
                            }
                        }

//                        }
                    }
                });
                //
                if (i == 0) {
                    itemName.setChecked(true);
                    itemName.setTextColor(Color.rgb(0, 0, 0));
//				view.setBackgroundColor(Color.rgb(240, 241, 242));
                } else {
                    itemName.setChecked(false);
                    itemName.setTextColor(getResources().getColor(R.color.app_bg));
//				view.setBackgroundColor(Color.rgb(250, 250, 250));
                }
            }


        }
    }

    public void setIndicateDisplay(Context context, int position,
                                   boolean visible) {
        int size = mIndicateImgs.size();
        if (size <= position) {

            return;
        }
        ImageView indicateImg = mIndicateImgs.get(position);
        indicateImg.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    /**
     *
     */
    public void setTabsDisplay(Context context, int index) {
        int size = mCheckedList.size();
        for (int i = 0; i < size; i++) {
            CheckedTextView checkedTextView = mCheckedList.get(i);
            if ((Integer) (checkedTextView.getTag()) == index) {
//                if ((Integer) (checkedTextView.getTag()) == 2) {
//                    LogUtils.i(TAG, mLabels[index] + " is selected...");
//                    checkedTextView.setChecked(false);
//                    checkedTextView.setTextColor(Color.rgb(181, 186, 175));
//				mViewList.get(i).setBackgroundColor(Color.rgb(240, 241, 242));
//                } else {
//                    LogUtils.i(TAG, mCheckedList[index] + " is selected...");
                checkedTextView.setChecked(true);
                checkedTextView.setTextColor(getResources().getColor(R.color.app_bg));
//				mViewList.get(i).setBackgroundColor(Color.rgb(240, 241, 242));
//                }

            } else {
                checkedTextView.setChecked(false);
                checkedTextView.setTextColor(Color.rgb(181, 186, 175));
//				mViewList.get(i).setBackgroundColor(Color.rgb(250, 250, 250));
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode != MeasureSpec.EXACTLY) {
            widthSpecSize = 0;
        }

        if (heightSpecMode != MeasureSpec.EXACTLY) {
            heightSpecSize = 0;
        }

        if (widthSpecMode == MeasureSpec.UNSPECIFIED
                || heightSpecMode == MeasureSpec.UNSPECIFIED) {
        }

        int width;
        int height;
        width = Math.max(getMeasuredWidth(), widthSpecSize);
        height = Math.max(this.getBackground().getIntrinsicHeight(),
                heightSpecSize);
        setMeasuredDimension(width, height);
    }

    private OnTabSelectedListener mTabListener;

    public interface OnTabSelectedListener {
        void onTabSelected(int index);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        this.mTabListener = listener;
    }

}
