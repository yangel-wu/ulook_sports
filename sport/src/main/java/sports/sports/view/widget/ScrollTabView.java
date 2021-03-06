package sports.sports.view.widget;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import tv.liangzi.quantum.adapter.TabAdapter;

/**
 * Created by yangle on 2015/9/18 0018.
 */
public class ScrollTabView extends HorizontalScrollView implements ViewPager.OnPageChangeListener{

    private TabAdapter tabAdapter;

    private Context mContext;

    private LinearLayout container;

    private ViewPager viewPager;

    public ScrollTabView(Context context) {
        this(context,null);
        // TODO Auto-generated constructor stub
    }
    public ScrollTabView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        // TODO Auto-generated constructor stub
    }

    public ScrollTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        mContext=context;
        container=new LinearLayout(mContext);
        container.setOrientation(LinearLayout.HORIZONTAL);
        addView(container);
    }

    public TabAdapter getAdapter() {
        return tabAdapter;
    }

    public void setAdapter(TabAdapter tabAdapter) {
        this.tabAdapter = tabAdapter;
        initTabs();
    }

    public void setViewPager(ViewPager viewPager){
        this.viewPager=viewPager;
        viewPager.setOnPageChangeListener(this);
    }

    private void initTabs(){
        for(int i=0;i<tabAdapter.getCount();i++){
            final int position=i;
            View tab=tabAdapter.getView(i);
            container.addView(tab);
            tab.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    selectedTab(position);
                    viewPager.setCurrentItem(position);
                }

            });
        }
        //默认选中0
        selectedTab(0);
    }
    public void selectedTab(int position){
        for(int i=0;i<container.getChildCount();i++){
            container.getChildAt(i).setSelected(position==i);
        }
        int w=container.getChildAt(0).getWidth();
        smoothScrollTo(w*(position-1), 0);			//不用考虑position=0,scrollTo(-x,0)相当于scrollTo(0,0)滚动不会超过视图边界
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        selectedTab(position);
    }
}

