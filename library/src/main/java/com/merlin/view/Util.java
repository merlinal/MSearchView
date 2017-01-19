package com.merlin.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by zal on 17/1/19.
 */

public class Util {

    public static <T extends View> T findById(Activity activity, int id) {
        return (T) activity.getWindow().getDecorView().findViewById(id);
    }

    public static <T extends View> T findById(View view, int id) {
        return (T) view.findViewById(id);
    }

    /**
     * dp2px
     *
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * @param context
     * @param v
     */
    public static void hideSoftInput(Activity context, View v) {
        if (v != null && context != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public static void showSoftInput(Activity context) {
        if (context != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
