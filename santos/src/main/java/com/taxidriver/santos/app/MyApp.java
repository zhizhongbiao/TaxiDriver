package com.taxidriver.santos.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Handler;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.taxidriver.santos.third_party.ui.toast.SmartToast;
import com.taxidriver.santos.utils.log.LogUtils;
import com.taxidriver.santos.utils.storage.SpUtil;
import com.taxidriver.santos.view.BaseActivity;

import java.util.ArrayList;

import io.objectbox.BoxStore;


public class MyApp extends Application {

    private static MyApp app;
    private static Handler mainThreadHandler = new Handler();
    //主线程
    private static Thread mainThread = Thread.currentThread();
    //主线程Id
    private static int mainThreadId = android.os.Process.myTid();

    private ArrayList<BaseActivity> activities = new ArrayList<>();
    private BoxStore boxStore;
    private  RefWatcher memoryWatcher;

    public static MyApp getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        app = this;
        initSp();
        initObjectBox();
        initSmartToast();
        initLeakCanary();
        super.onCreate();
    }

    private void initSp() {
        SpUtil.init(this);
    }

    private void initSmartToast() {
        //初始化Toast
        SmartToast.plainToast(this);
    }

    /**
     * 初始化LeakCanary,内存检测
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)|| !isApkDebugable()) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.

            LogUtils.e("LeakCanary.isInAnalyzerProcess(this)||!isApkDebugable(this) -- LeakCanary is not initialized");
            return;
        }
        memoryWatcher = LeakCanary.install(this);

    }

    private void initObjectBox() {
//        boxStore = MyObjectBox.builder().androidContext(this).build();
//        if (BuildConfig.DEBUG) {
//            //开启该选项，可以在浏览器指定地址查看数据库数据
//            new AndroidObjectBrowser(boxStore).start(this);
//        }
    }

    public BoxStore getBoxStore() {
        if (boxStore == null) {
            initObjectBox();
        }
        return boxStore;
    }

    public void addActivity(BaseActivity activity) {
        activities.add(activity);
    }


    public void removeActivity(BaseActivity activity) {
        activities.remove(activity);
    }

    public void killAllActivity() {
        for (BaseActivity activity : activities) {
            activity.finish();
        }
        LogUtils.e("killAllActivity");
    }

    public boolean isActivityShowing() {
        return !activities.isEmpty();
    }

    public int activitySize() {
        return activities.size();
    }


    public static Handler getMainThreadHandler() {
        return mainThreadHandler;
    }

    public static Thread getMainThread() {
        LogUtils.e("getMainThread   mainThread=" + mainThread);
        return mainThread;
    }

    public static int getMainThreadId() {
        LogUtils.e("getMainThreadId   mainThreadId=" + mainThreadId);
        return mainThreadId;
    }

    public static RefWatcher getMemoryWatcher(Context context) {
        MyApp app = (MyApp) context.getApplicationContext();
        return app.memoryWatcher;
    }


    public boolean isApkDebugable() {
        try {
            ApplicationInfo info = getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            LogUtils.e("isApkDebugable   Exception=" + e.getMessage());
        }
        return false;
    }

}
