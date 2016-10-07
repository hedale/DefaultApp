package com.hele.defaultapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.hele.defaultapp.R;
import com.hele.defaultapp.util.ExitAppUtil;
import com.hele.defaultapp.widget.slidemenu.lib.SlidingMenu;
import com.hele.defaultapp.widget.slidemenu.lib.app.SlidingActivityHelper;
import com.hele.defaultapp.widget.slidemenu.lib.app.SlidingFragmentActivity;

/**
 * if ur act need 2 touch back u can extends this class
 * Created by hele on 16/8/6.
 */
public abstract class BaseTouchBackAct extends SlidingFragmentActivity implements SlidingMenu.OnOpenedListener {

    protected SlidingMenu mSlidingMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mHelper = new SlidingActivityHelper(this);
        mHelper.onCreate(savedInstanceState);

        // 这里借用了SlidingMenu的setBehindContentView方法来设置一个透明菜单
        View behindView = new View(this);
        behindView.setLayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        behindView.setBackgroundColor(getResources().getColor(R.color.transparent));

        mSlidingMenu = getSlidingMenu();
        // 设置阴影宽度为10个px
        mSlidingMenu.setShadowWidth(10);
        // 设置阴影
        mSlidingMenu.setShadowDrawable(R.drawable.slide_shadow);
        // 设置下面的布局，也就是我们上面定义的透明菜单离右边屏幕边缘的距离为0，也就是滑动开以后菜单会全屏幕显示
        mSlidingMenu.setBehindOffset(0);
        mSlidingMenu.setFadeDegree(0.35f);

        // 设置手势滑动方向，因为我们要实现微信那种右滑动的效果，这里设置成SlidingMenu.LEFT模式
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        // 因为微信是只有边缘滑动，我们设置成TOUCHMODE_MARGIN模式，如果你想要全屏幕滑动，只需要把这个改成TOUCHMODE_FULLSCREEN就OK了
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        // 菜单打开监听，因为菜单打开后我们要finish掉当前的Activity
        mSlidingMenu.setOnOpenedListener(this);

        setBehindContentView(behindView);
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onOpened() {
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 在当前activity栈中只有一个activity时应禁用手势返回，否则会造成黑屏
        if (ExitAppUtil.getInstance().getActivityList() != null && ExitAppUtil.getInstance().getActivityList().size() >= 2) {
            mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        } else {
            mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

    }

}

