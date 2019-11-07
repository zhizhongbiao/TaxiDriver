package com.taxidriver.santos.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Filename :  TaskManager
 * Author   :  zhizhongbiao
 * Date     :  2018/7/2
 * Describe :
 */
public class TaskManager {
    private static TaskManager instance;
    private ExecutorService cpuThreadPoolExecutor;
    private ExecutorService ioThreadPoolExecutor;
    private ExecutorService serialTaskExecutor;

    public static TaskManager getInst() {
        if (instance == null) {
            synchronized (TaskManager.class) {
                if (instance == null) {
                    instance = new TaskManager();
                }
            }
        }
        return instance;
    }

    private TaskManager() {
        ioThreadPoolExecutor = Executors.newFixedThreadPool(2 * getAvailableCoresNum() + 1);
        cpuThreadPoolExecutor = Executors.newFixedThreadPool(getAvailableCoresNum() + 1);
        serialTaskExecutor = Executors.newSingleThreadExecutor();
    }

    public int getAvailableCoresNum() {
        return Runtime.getRuntime().availableProcessors();
    }

    public void postIOTask(Runnable runnable) {
        ioThreadPoolExecutor.execute(runnable);
    }

    public void postCPUTask(Runnable runnable) {
        cpuThreadPoolExecutor.execute(runnable);
    }


    public void cancelIOTask(Runnable r) {
        if (ioThreadPoolExecutor instanceof ThreadPoolExecutor) {
            ((ThreadPoolExecutor) ioThreadPoolExecutor).getQueue().remove(r);
        }
    }

    public void cancelAllIOTask() {
        if (ioThreadPoolExecutor instanceof ThreadPoolExecutor) {
            ((ThreadPoolExecutor) ioThreadPoolExecutor).getQueue().clear();
        }
    }

    public void cancelCPUTask(Runnable r) {
        if (cpuThreadPoolExecutor instanceof ThreadPoolExecutor) {
            ((ThreadPoolExecutor) cpuThreadPoolExecutor).getQueue().remove(r);
        }
    }

    public void cancelAllCUPTask() {
        if (cpuThreadPoolExecutor instanceof ThreadPoolExecutor) {
            ((ThreadPoolExecutor) cpuThreadPoolExecutor).getQueue().clear();
        }
    }


    public void enqueue(Runnable task) {
        serialTaskExecutor.execute(task);
    }

    public void shutdownIOTask() {
        ioThreadPoolExecutor.shutdown();
    }

    public void shutdownCPUTask() {
        cpuThreadPoolExecutor.shutdown();
    }

    public void shutdownSerialTask() {
        serialTaskExecutor.shutdown();
    }


    /**
     * 关闭所有任务线程
     */
    public void onDestory() {
        shutdownCPUTask();
        cpuThreadPoolExecutor = null;
        shutdownIOTask();
        ioThreadPoolExecutor = null;
        shutdownSerialTask();
        serialTaskExecutor = null;
        instance = null;
    }
}
