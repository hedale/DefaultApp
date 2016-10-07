package com.hele.defaultapp.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hele on 16/8/6.
 * 维护当前的activity队列
 */
public class ExitAppUtil {

    private List<Activity> activityList = new LinkedList<Activity>();

    private static ExitAppUtil instance;

    private ExitAppUtil(){}

    public static ExitAppUtil getInstance(){
        if (instance == null){
            instance = new ExitAppUtil();
        }
        return instance;
    }

    public List<Activity> getActivityList(){
        return activityList;
    }

    public void addAct(Activity act){
        activityList.add(act);
    }

    public void delAct(Activity act){
        activityList.remove(act);
    }

    public int getListSize(){
        return activityList.size();
    }

    /*
     * 程序完美退出
     */
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }

        System.exit(0);
    }


    /*
     * 当前应用是否在前台展示
     */
    public boolean isForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获得task列表
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (taskInfo != null && taskInfo.size() > 0) {
            ComponentName componentInfo = taskInfo.get(taskInfo.size() - 1).topActivity;
            String packName = componentInfo.getPackageName();
            if (activityList.size() != 0
                    && activityList.get(activityList.size() - 1).getPackageName().equals(packName)) {
                return true;
            }
            return false;
        }
        return false;

    }
}
