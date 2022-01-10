package sg.just4fun.tgasdk.conctart;

import android.app.Activity;

import java.util.Iterator;
import java.util.LinkedList;


public class SdkActivityDele {
    private  static LinkedList<Activity> activityList=new LinkedList<>();

    /**
     * 添加Activity到集合中
     */
    public static void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new LinkedList<>();

        }
        activityList.add(activity);
    }
    /**
     * 关闭指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activityList != null && activity != null && activityList.contains(activity)) {
            //使用迭代器安全删除
            for (Iterator<Activity> it = activityList.iterator(); it.hasNext(); ) {
                Activity temp = it.next();
                // 清理掉已经释放的activity
                if (temp == null) {
                    it.remove();
                    continue;
                }
                if (temp == activity) {
                    it.remove();
                }
            }
            activity.finish();
        }
    }

    /**
     * 关闭指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        if (activityList != null) {
            // 使用迭代器安全删除
            for (Iterator<Activity> it = activityList.iterator(); it.hasNext(); ) {
                Activity activity = it.next();
                // 清理掉已经释放的activity
                if (activity == null) {
                    it.remove();
                    continue;
                }
                if (activity.getClass().equals(cls)) {
                    it.remove();
                    activity.finish();
                }
            }
        }
    }
    /**
     * 关闭所有的Activity
     */
    public static void finishAllActivity() {
        if (activityList != null) {
            for (Iterator<Activity> it = activityList.iterator(); it.hasNext(); ) {
                Activity activity = it.next();
                if (activity != null) {
                    activity.finish();
                }
            }
            activityList.clear();
        }
    }
}
