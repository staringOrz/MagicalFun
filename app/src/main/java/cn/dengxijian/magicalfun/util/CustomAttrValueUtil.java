package cn.dengxijian.magicalfun.util;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Created by dengxijian on 2019/6/3.
 */

public class CustomAttrValueUtil {

    /**
     * 动态获取当前主题中的自定义颜色属性值
     *
     * @param attr
     *         e.g R.attr.colorAccent
     * @param defaultColor
     *         默认颜色值
     */
    public static int getAttrColorValue(int attr, int defaultColor, Context context) {

        int[] attrsArray = {attr};
        TypedArray typedArray = context.obtainStyledAttributes(attrsArray);
        int value = typedArray.getColor(0, defaultColor);
        typedArray.recycle();
        return value;
    }
}
