package com.hele.defaultapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.hele.defaultapp.R;
import com.hele.defaultapp.util.ExitAppUtil;
/**
                        _ooOoo_
                       o8888888o
                       88" . "88
                       (| -_- |)
                       O\  =  /O
                    ____/`---'\____
                  .'  \\|     |//  `.
                 /  \\|||  :  |||//  \
                /  _||||| -:- |||||-  \
                |   | \\\  -  /// |   |
                | \_|  ''\---/''  |   |
                \  .-\__  `-`  ___/-. /
              ___`. .'  /--.--\  `. . __
           ."" '<  `.___\_<|>_/___.'  >'"".
          | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `-.   \_ __\ /__ _/   .-` /  /
    ======`-.____`-.___\_____/___.-`____.-'======
                       `=---='
 ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                佛祖保佑       永无BUG
 */

/**
 * all of act should be extends this class or its child
 * Created by hele on 16/8/6.
 */
public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        findView();
        init();
    }

    /**
     * do something like setContentView for ur act or something that u want running more quickly
     */
    protected abstract void setContentView();

    /**
     * do something like findViewByid() in this method
     */
    protected abstract void findView();

    /**
     * do something like initurView in this method
     */
    protected abstract void init();

    /**
     *
     * @param intent
     * @param isNeedAnim 是否需要跳转动画
     */
    protected void startActivity(Intent intent, boolean isNeedAnim){
        super.startActivity(intent);
        if (isNeedAnim){
            overridePendingTransition(R.anim.activity_start_int_anim,R.anim.activity_start_out_anim);
        }
    }

    /**
     *
     * @param isNeedAnim 是否需要跳转动画
     */
    protected void finish(boolean isNeedAnim){
        super.finish();
        if (isNeedAnim){
            overridePendingTransition(R.anim.activity_exit_in_anim,R.anim.activity_exit_out_anim);
        }
    }

    /**
     * 如何主页并未销毁并且当前的actList中只剩下当前act则可能是通过webView 或者是推送进来的 此时点击返回键应重新打开首评（相当于重启app）
     */
    @Override
    public void onBackPressed() {

        if (MainActivity.INSTANCE != null && ExitAppUtil.getInstance().getListSize()>1){
            finish(true);
        }else{
            //TODO 自行补充跳转首屏
            finish(true);
        }
    }

    /**
     * 统一处理app内部的backBtn oclick事件
     */
    protected void back(){
        onBackPressed();
    }



}
