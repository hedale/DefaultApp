package com.hele.defaultapp.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hele on 16/8/17.
 * android 3.0之后才能用app 下的 fragment
 * 如果要兼容2.X版本的fragment 则应使用v4 包下的fragment
 * 同时activity 也必须继承fragment Activity
 */
public abstract class BaseFragment extends Fragment{

    protected View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(setContentView(),null);
        findView(rootView);
        init(rootView);
        return rootView;
    }

    protected abstract int setContentView();

    protected abstract void findView(View rootView);

    protected abstract void init(View rootView);
}
