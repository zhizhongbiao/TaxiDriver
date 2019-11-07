package com.taxidriver.santos.utils;

import com.taxidriver.santos.utils.log.LogUtils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
	/** 关闭流 */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
				return true;
			} catch (IOException e) {
				LogUtils.e(e);
				return false;
			}
		}
		return false;
	}
}
