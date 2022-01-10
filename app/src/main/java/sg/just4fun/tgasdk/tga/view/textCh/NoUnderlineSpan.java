package sg.just4fun.tgasdk.tga.view.textCh;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

public class NoUnderlineSpan extends ClickableSpan {


    @Override
    public void onClick(@NonNull View view) {

    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(Color.BLUE); // 设置超链接文字颜色
        ds.setUnderlineText(true); // 去掉超链接的下划线
    }
}
