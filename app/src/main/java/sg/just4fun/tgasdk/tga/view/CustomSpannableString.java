package sg.just4fun.tgasdk.tga.view;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

public class CustomSpannableString extends SpannableString {

    private static SpannableString ss;

    // 字体
    public static final String TYPE_MONOSPACE = "monospace";
    public static final String TYPE_DEFAULT = "default";
    public static final String TYPE_DEFAULT_BOLD = "default-bold";
    public static final String TYPE_SERIF = "serif";
    public static final String TYPE_SANS_SERIF = "sans-serif";


    // 字体样式
    public static final int TYPEFACE_NORMAL = android.graphics.Typeface.NORMAL;
    public static final int TYPEFACE_BOLD = android.graphics.Typeface.BOLD;
    public static final int TYPEFACE_ITALIC = android.graphics.Typeface.ITALIC;
    public static final int TYPEFACE_BOLD_ITALIC = android.graphics.Typeface.BOLD_ITALIC;


    private static int mStart;
    private static int mEnd;

    private CustomSpannableString(CharSequence source){
        super(source);
    }

    // 创建一个 CustomSpannableString 对象
    public static CustomSpannableString getInstance(CharSequence source, int start, int end){
        ss = new CustomSpannableString(source);
        mStart = start;
        mEnd = end;
        return (CustomSpannableString)ss;
    }

    // 设置网络链接
    public CustomSpannableString setTextURL(String url){
        ss.setSpan(new URLSpan(url), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return (CustomSpannableString)ss;
    }

    // 设置字体
    public CustomSpannableString setTextType(String type){
        ss.setSpan(new TypefaceSpan(type), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return (CustomSpannableString)ss;
    }

    //设置字体大小
    public CustomSpannableString setTextSize(int size, Boolean useDp){
        if (useDp == null){
            ss.setSpan(new AbsoluteSizeSpan(size), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            // 第二个参数boolean dp，如果为true，表示前面的字体大小单位为dp，否则为像素。
            ss.setSpan(new AbsoluteSizeSpan(size,useDp), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return (CustomSpannableString)ss;
    }

    //设置字体颜色
    public CustomSpannableString setTextColor(int color){
        ss.setSpan(new ForegroundColorSpan(color), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return (CustomSpannableString)ss;
    }

    //设置背景颜色
    public CustomSpannableString setTextBackgroundColor(int color){
        ss.setSpan(new BackgroundColorSpan(color), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return (CustomSpannableString)ss;
    }

    // 设置字体样式
    public CustomSpannableString setTextTypeface(int typeface){
        ss.setSpan(new StyleSpan(typeface), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return (CustomSpannableString)ss;
    }

    // 设置下划线
    public CustomSpannableString setTextUnderline(){
        ss.setSpan(new UnderlineSpan(), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return (CustomSpannableString)ss;
    }

    // 设置删除线
    public CustomSpannableString setTextStrikeThrough(){
        ss.setSpan(new StrikethroughSpan(), mStart, mEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return (CustomSpannableString)ss;
    }

    // 设置上标
    public CustomSpannableString setTextSuperscript(int start, int end){
        ss.setSpan(new SuperscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return (CustomSpannableString)ss;
    }

    // 设置下标
    public CustomSpannableString setTextSubscript(int start, int end){
        ss.setSpan(new SubscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return (CustomSpannableString)ss;
    }
}
