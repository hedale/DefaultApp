package com.hele.defaultapp.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.hele.defaultapp.R;
import com.hele.defaultapp.adapter.MainListAdapter;
import com.hele.defaultapp.constant.NetConstant;
import com.hele.defaultapp.listener.IOnNetResultListener;
import com.hele.defaultapp.net.NetController;
import com.hele.defaultapp.util.ExitAppUtil;
import com.hele.defaultapp.widget.pulltofreshview.PullToRefreshBase;
import com.hele.defaultapp.widget.pulltofreshview.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hele on 16/8/6.
 */
public class MainActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2, IOnNetResultListener, AdapterView.OnItemClickListener {
    private static final int FLAG_UP = 0x123;

    private static final int FLAG_DOWN = 0x124;


    public static MainActivity INSTANCE = null;

    private PullToRefreshListView lv;

    private NetController mNetController;

    private MainListAdapter adapter;

    private List<String> data;


    @Override
    protected void setContentView() {
        if (INSTANCE == null) {
            INSTANCE = this;
        }
        ExitAppUtil.getInstance().addAct(this);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (INSTANCE == null) {
            INSTANCE = this;
        }
    }

    @Override
    protected void findView() {
        lv = (PullToRefreshListView) findViewById(R.id.activity_main_lv);
    }

    @Override
    protected void init() {
        data = new ArrayList<String>();

        //设置刷新模式
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新监听
        lv.setOnRefreshListener(this);

        lv.setOnItemClickListener(this);
        //刷新
        lv.setRefreshing(true);
    }

    /*
     * 网络请求
     */
    private void request(boolean isShowloadingView, int flag) {
        if (mNetController == null) {
            mNetController = new NetController();
        }
        //如果有参数的话  就设置 setKeys setValues 方法
        mNetController.requestNet(NetConstant.TEST_URL, NetController.HttpMethod.GET, flag, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (INSTANCE != null) {
            INSTANCE = null;
        }
        ExitAppUtil.getInstance().delAct(this);
    }


    /*
     * 下拉刷新的回调
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        request(false, FLAG_DOWN);
    }

    /*
     * 上拉加载的回调
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        request(false, FLAG_UP);
    }

    @Override
    public void onNetResult(int flag, String jsonResult) {
        //在这解析JSON  这不是在UI线程中执行的
        if (flag == FLAG_DOWN) {
            data.clear();
        }
        data.add(jsonResult);
    }

    @Override
    public void onNetComplete(int flag) {
        lv.onRefreshComplete();
        if (adapter == null) {
            adapter = new MainListAdapter(this, data);
            lv.setAdapter(adapter);
        } else {
            adapter.Refresh(data);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent  intent = new Intent(this,IntentAct.class);
        startActivity(intent,true);
    }
}
