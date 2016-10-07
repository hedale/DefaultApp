package com.hele.defaultapp.ui;

import com.hele.defaultapp.R;
import com.hele.defaultapp.util.ExitAppUtil;

/**
 * Created by hele on 16/10/5.
 */
public class IntentAct extends BaseTouchBackAct{
    @Override
    protected void setContentView() {
        setContentView(R.layout.act_intent);
        ExitAppUtil.getInstance().addAct(this);
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExitAppUtil.getInstance().delAct(this);
    }
}
