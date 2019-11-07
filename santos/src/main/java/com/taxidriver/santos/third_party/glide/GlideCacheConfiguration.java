package com.taxidriver.santos.third_party.glide;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

import java.io.File;


/**
 * Author : WaterFlower.
 * Created on 2017/9/15.
 * Desc :   Glide缓存
 */

public class GlideCacheConfiguration implements GlideModule {

    public static final String FILE_ROOT_PATH = Environment.getExternalStorageDirectory().getPath();
    public static final String IMAGE_CACHE_PATH = "imageCache";
    public static final int MAX_DISK_IMAGE_CACHE_SIZE = 200 * 1024 * 1024;
    public static final int MAX_MEMORY_IMAGE_CACHE_SIZE = 10 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
//        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);  //新版本失效
        //内存缓存大小
        builder.setMemoryCache(new LruResourceCache(MAX_MEMORY_IMAGE_CACHE_SIZE));

//        此操作必须在APP启动时赋予读写权限才生效,因为本方法执行一次,用户不给于读写权限或过后才给都不能创建缓存文件夹
//        final File imageCache = new File(FILE_ROOT_PATH
//                + File.separator + context.getPackageName()
//                + File.separator + IMAGE_CACHE_PATH);

        final File imageCache = new File(context.getExternalCacheDir().getPath()
                + File.separator + IMAGE_CACHE_PATH);

        boolean isFileCreated = true;
        if (!imageCache.exists()) {
            isFileCreated = imageCache.mkdirs();
        }
//        TODO 用哪个???????
//        LogUtils.e("context.getExternalCacheDir()="+context.getExternalCacheDir().getPath());
//        LogUtils.e("Environment.getExternalStorageDirectory().getPath()/isFileCreated="+Environment.getExternalStorageDirectory().getPath()+"/"+isFileCreated);

        if (!isFileCreated) return;

        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                //硬盘缓存大小
                return DiskLruCacheWrapper.get(imageCache, MAX_DISK_IMAGE_CACHE_SIZE);
            }
        });
    }



    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}
