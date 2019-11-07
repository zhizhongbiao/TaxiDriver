package com.taxidriver.santos.manager;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import androidx.collection.ArrayMap;

import com.taxidriver.santos.cache.CacheResult;
import com.taxidriver.santos.cache.DiskLruCache;
import com.taxidriver.santos.utils.IOUtils;
import com.taxidriver.santos.utils.log.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author : WaterFlower.
 * Created on 2017/8/11.
 * Desc :
 */
public class CacheManager {
    private static CacheManager instance;
    private static DiskLruCache diskLruCache;

    private CacheManager() {

    }

    public static CacheManager getDefault() {
        if (instance == null) {
            synchronized (CacheManager.class) {
                instance = new CacheManager();
            }

            return instance;
        }
        return instance;
    }

    /**
     * 根据url生成的md5值加载缓存数据
     *
     * @param key            缓存数据的key
     * @param cacheValidTime 缓存有效时间
     * @return 缓存对象
     */
    public CacheResult readFromCache(String key, long cacheValidTime) {
        CacheResult cacheResult = new CacheResult();
        String[] result = readDataFromDiskCache(key);
        if (!TextUtils.isEmpty(result[0]) && TextUtils.isDigitsOnly(result[0])) { //缓存日期不能为空
            long cacheTime = Long.parseLong(result[0]);
            if (System.currentTimeMillis() - cacheTime > cacheValidTime * 1000) { //数据是否过期
                cacheResult.isValid = false;
            }
        } else {
            cacheResult.isValid = false;
        }

        cacheResult.cacheData = result[1];
        return cacheResult;
    }


    /**
     * 根据url生成的md5值缓存数据
     *
     * @param url  缓存数据的url
     * @param data 需要缓存的数据
     */
    public void writeToCache(final String url, final String data) {
        TaskManager.getInst().postIOTask(new Runnable() {
            @Override
            public void run() {
                if (writeDataToDiskCache(url, data)) {
                    //缓存数据读取成功，返回给UI；
                } else {
                    LogUtils.e("缓存数据失败！URL--->>>" + url);
                }
            }
        });
    }

    /**
     * 打开缓存
     *
     * @param context
     * @param dir
     * @return
     */
    public static DiskLruCache openCache(Context context, String dir) {
        if (context == null) {
            throw new IllegalArgumentException("context不能为空！");
        }

        DiskLruCache diskLruCache = null;
        try {
            File cacheDir = getDiskCacheDir(context, dir);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            diskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return diskLruCache;
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        if (context == null) {
            throw new IllegalArgumentException("context不能为空！");
        }

        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable() && context.getExternalCacheDir() != null) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public static int getAppVersion(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context不能为空！");
        }

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 将数据写入到磁盘缓存
     *
     * @param data
     * @param url
     * @return
     */
    public static boolean writeDataToDiskCache(String url, String data) {
        boolean succeed = false;
        OutputStream outputStream = null;
        BufferedWriter bufferedWriter = null;
        DiskLruCache.Editor editor = null;

        if (diskLruCache == null || diskLruCache.isClosed()) {
            return false;
        }

        try {
            String key = hashKeyForDisk(url);
            data = System.currentTimeMillis() + "#_#" + data;
            editor = diskLruCache.edit(key);
            if (editor != null) {
                outputStream = editor.newOutputStream(0);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(data);
                editor.commit();
                succeed = true;
            }

            diskLruCache.flush();
        } catch (IOException e) {
            try {
                if (editor != null) {
                    editor.abort();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return succeed;
    }

    /**
     * 从磁盘缓存读取数据
     *
     * @param url
     * @return
     */
    public static String[] readDataFromDiskCache(String url) {
        if (diskLruCache == null) {
            return new String[]{"", ""};
        }

        String data = "";
        BufferedReader bufferedReader = null;
        try {
            String key = hashKeyForDisk(url);
            DiskLruCache.Snapshot snapShot = diskLruCache.get(key);
            if (snapShot != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(snapShot.getInputStream(0)));
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(bufferedReader);
        }

        if (!TextUtils.isEmpty(data)) {
            return data.split("#_#");
        } else {
            return new String[2];
        }
    }

    /**
     * 关闭磁盘缓存
     */
    public void closeCache() {
        try {
            if (diskLruCache != null) {
                diskLruCache.close();
            }
        } catch (IOException e) {
            LogUtils.e("磁盘缓存关闭失败");
        }

        diskLruCache = null;
    }

    /**
     * 将请求URL以及参数排序组成完整的缓存数据key以保证数据的唯一性
     *
     * @param url
     * @param requestParams
     * @return
     */
    public String sortUrl(String url, ArrayMap<String, ?> requestParams) {
        StringBuilder urlBuilder = new StringBuilder(url);
        List<String> keys = new ArrayList<>();
        for (String key : requestParams.keySet()) {
            keys.add(key);
        }

        if (!keys.isEmpty()) {
            Collections.sort(keys);
            urlBuilder.append("?");
            for (String key : keys) {
                urlBuilder.append(key).append('=').append(requestParams.get(key)).append('&');
            }
            urlBuilder.deleteCharAt(urlBuilder.lastIndexOf("&"));
        }
        return urlBuilder.toString();
    }
}
