package com.taxidriver.santos.utils.format;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * @ProjectName: TaxiDriver
 * @Package: com.taxidriver.santos.utils.format
 * @ClassName: StrUtil
 * @Description:
 * @Author: TaxiDriverSantos
 * @CreateDate: 2019/11/5   16:24
 * @UpdateUser: TaxiDriverSantos
 * @UpdateDate: 2019/11/5   16:24
 * @UpdateRemark:
 * @Version: 1.0
 */
public class StrUtil {


    /**
     * 拼接字符串
     *
     * @param arg
     * @param args
     * @return 拼接完成的字符串
     */
    public static String join(Object arg, Object... args) {
        StringBuffer buffer = new StringBuffer(arg == null ? "" : arg.toString());
        for (Object s : args) {
            buffer.append(s == null ? "" : s.toString());
        }
        return buffer.toString();
    }


    /**
     * 设置多种颜色的字体
     *
     * @param msg
     * @param color1
     * @param color2
     * @param pos    两种颜色的分隔位置
     * @return
     */
    public static SpannableStringBuilder setColorful(String msg, int color1, int color2, int pos) {
        SpannableStringBuilder builder = new SpannableStringBuilder(msg);
        builder.setSpan(new ForegroundColorSpan(color1), 0, pos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(color2), pos + 1, msg.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return builder;
    }

    public static Spanned getColoredString(String color, String key, String value) {
        StringBuilder string = new StringBuilder();
        string.append(key);
        string.append("<font color=\"" + color + "\">");
        string.append(value);
        string.append("</font>");
        return Html.fromHtml(string.toString());
    }
}
