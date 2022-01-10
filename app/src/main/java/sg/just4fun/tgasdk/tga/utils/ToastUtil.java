package sg.just4fun.tgasdk.tga.utils;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import sg.just4fun.tgasdk.R;
import sg.just4fun.tgasdk.web.TgaSdk;


public class ToastUtil {

    private static Toast toast;//实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长

    /**
     * 短时间显示Toast【居下】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToast(String msg) {
        if (TgaSdk.mContext != null) {
            if (toast == null) {
                toast = Toast.makeText(TgaSdk.mContext, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            //1、setGravity方法必须放到这里，否则会出现toast始终按照第一次显示的位置进行显示（比如第一次是在底部显示，那么即使设置setGravity在中间，也不管用）
            //2、虽然默认是在底部显示，但是，因为这个工具类实现了中间显示，所以需要还原，还原方式如下：
            toast.setGravity(Gravity.BOTTOM, 0, ScreenUtils.dp2PxInt(TgaSdk.mContext, 64));
            toast.show();
        }
    }

    /**
     * 短时间显示Toast【居中】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToastCenter(String msg) {
        if (TgaSdk.mContext != null) {
//            if (toast == null) {
//                toast = Toast.makeText(TGASDK.mContext, msg, Toast.LENGTH_SHORT);
//            } else {
//                toast.setText(msg);
//            }
//            View view = toast.getView();
//            view .setBackgroundColor(Color.parseColor("#99000000"));


            Toast toast=new Toast(TgaSdk.mContext);
            TextView view = new TextView(TgaSdk.mContext);
            view.setBackgroundResource(R.drawable.toast_pyty);
            view.setTextColor(Color.WHITE);
            view.setText(msg);
            view.setPadding(40, 26, 40, 26);
            toast.setGravity(Gravity.CENTER, 0, 40);
            toast.setView(view);
            toast.show();
        }
    }

    /**
     * 短时间显示Toast【居上】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToastTop(String msg) {
        if (TgaSdk.mContext != null) {
            if (toast == null) {
                toast = Toast.makeText(TgaSdk.mContext, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast【居下】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToast(String msg) {
        if (TgaSdk.mContext != null) {
            if (toast == null) {
                toast = Toast.makeText(TgaSdk.mContext, msg, Toast.LENGTH_LONG);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.BOTTOM, 0, ScreenUtils.dp2PxInt(TgaSdk.mContext, 64));
            toast.show();
        }
    }

    /**
     * 长时间显示Toast【居中】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToastCenter(String msg) {
        if (TgaSdk.mContext != null) {
            if (toast == null) {
                toast = Toast.makeText(TgaSdk.mContext, msg, Toast.LENGTH_LONG);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 长时间显示Toast【居上】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToastTop(String msg) {
        if (TgaSdk.mContext != null) {
            if (toast == null) {
                toast = Toast.makeText(TgaSdk.mContext, msg, Toast.LENGTH_LONG);
            } else {
                toast.setText(msg);
            }
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }
}
