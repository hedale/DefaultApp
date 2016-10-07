package com.hele.defaultapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hele.defaultapp.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hele on 16/10/5.
 */
public class MainListAdapter extends BaseAdapter {

    private List<String> data;
    private Context context;

    public MainListAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public void Refresh(List<String> data) {
        if (this.data == null) {
            this.data = new ArrayList<String>();
        }
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return CommonUtil.isEmpty(data) ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return i < data.size() - 1 ? data.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LinearLayout fLayout = new LinearLayout(context);
            holder.contentTv = new TextView(context);
            fLayout.addView(holder.contentTv);
            convertView = fLayout;
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.contentTv.setText(data.get(position));
        return convertView;
    }


    class ViewHolder {
        TextView contentTv;
    }
}
