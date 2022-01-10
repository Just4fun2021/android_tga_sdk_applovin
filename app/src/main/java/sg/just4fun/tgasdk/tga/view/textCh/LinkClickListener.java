package sg.just4fun.tgasdk.tga.view.textCh;

public interface LinkClickListener {
    /**
     * true  表示要自己处理  false 使用系统默认
     *
     * @param mURL
     * @return
     */
    boolean onLinkClick(String mURL);
}
