package sg.just4fun.tgasdk.tga.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

public class ScreenUtils {
    public static float dp2Px(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * density(context);
    }

    public static float px2Dp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / density(context);
    }

    public static float density(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }


    public static int dp2PxInt(Context context, float dp) {
        return (int) (dp2Px(context, dp) + 0.5f);
    }

    public static float px2DpCeilInt(Context context, float px) {
        return (int) (px2Dp(context, px) + 0.5f);
    }


    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        Activity activity;
        if (!(context instanceof Activity) && context instanceof ContextWrapper) {
            activity = (Activity) ((ContextWrapper) context).getBaseContext();
        } else {
            activity = (Activity) context;
        }
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * 获取屏幕大小
     *
     * @param context
     * @return
     */
    public static int[] getScreenPixelSize(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }

    public static void hideSoftInputKeyBoard(Context context, View focusView) {
        if (focusView != null) {
            IBinder binder = focusView.getWindowToken();
            if (binder != null) {
                InputMethodManager imd = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                //imd.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_IMPLICIT_ONLY);
                imd.hideSoftInputFromWindow(binder, 0);
            }
        }
    }

    public static void showSoftInputKeyBoard(Context context, View focusView) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(focusView, InputMethodManager.SHOW_FORCED);
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取标题栏高度
     *
     * @param window
     * @return
     */
    public static int getTitleBarHeight(Window window) {
        int contentTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        return contentTop - getStatusBarHeight(window);
    }

    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取状态栏的高度
     *
     * @param view
     * @return
     */
    public static int getStatusBarHeight(View view) {
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     * 获取状态栏的高度
     *
     * @param window
     * @return
     */
    public static int getStatusBarHeight(Window window) {
        Rect frame = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    public static int getAppInScreenheight(Context context) {
        return getScreenHeight(context) - getStatusBarHeight(context);
    }

    /**
     * 计算字符串的绘制宽度
     *
     * @param paint
     * @param str
     * @return
     */
    public static int computeStringWidth(Paint paint, String str) {
        int iRet = 0;
        if (!TextUtils.isEmpty(str)) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil(widths[j]);
            }
        }
//        Log.d("edison","text width: "+iRet);
        return iRet;
    }

    /**
     * 计算字符串的绘制高度
     *
     * @param paint
     * @param string
     * @return 只有这种方法可以计算字符串的高度
     */
    public static int computeStringHeight(Paint paint, String string) {
        Rect rect = new Rect();

        //返回包围整个字符串的最小的一个Rect区域
        paint.getTextBounds(string, 0, 1, rect);
//        Log.d("edison","text height: "+rect.height());
        return rect.height();
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        //获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        //计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        //取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        //得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }
}
