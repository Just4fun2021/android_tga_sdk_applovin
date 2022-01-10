package sg.just4fun.tgasdk.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


public class SdkX5WebView extends WebView {

	TextView title;
	private WebViewClient client = new WebViewClient() {
		/**
		 * 防止加载网页时调起系统浏览器
		 */
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	};


	@SuppressLint("SetJavaScriptEnabled")
	public SdkX5WebView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		this.setWebViewClient(client);
		// this.setWebChromeClient(chromeClient);
		// WebStorage webStorage = WebStorage.getInstance();
		initWebViewSettings();
		this.setClickable(true);
	}

	private void initWebViewSettings() {
//		WebSettings webSetting = this.getSettings();
//		webSetting.setJavaScriptEnabled(true);
//		webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
//		webSetting.setAllowFileAccess(true);
//		webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//		webSetting.setSupportZoom(true);
//		webSetting.setBuiltInZoomControls(true);
//		webSetting.setUseWideViewPort(true);
//		webSetting.setSupportMultipleWindows(true);
//		// webSetting.setLoadWithOverviewMode(true);
//		webSetting.setAppCacheEnabled(true);
//		// webSetting.setDatabaseEnabled(true);
//		webSetting.setDomStorageEnabled(true);
//		webSetting.setGeolocationEnabled(true);
//		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
//		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
//		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
//		// webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
//		webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
////		webSetting.setMixedContentMode(WebSettings.);
//
//		// this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
//		// settings 的设计


		WebSettings webSetting = this.getSettings();
		webSetting.setJavaScriptEnabled(true);														//支持JS
		webSetting.setJavaScriptCanOpenWindowsAutomatically(true);									//通过JS打开新的窗口
		webSetting.setAllowFileAccess(true);														//设置可以访问文件
		webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);							    //适应内容大小
		webSetting.setSupportZoom(true);															//支持缩放
		webSetting.setBuiltInZoomControls(true);													//设置内置的缩放控件
		webSetting.setUseWideViewPort(true);														//将图片调整到适合webview的大小
		webSetting.setSupportMultipleWindows(true);													//设置webview是否支持多窗口
		// webSetting.setLoadWithOverviewMode(true);												//缩放至屏幕大小
		webSetting.setAppCacheEnabled(true);														//设置是否应该启用应用程序缓存API。默认值为false。注意，为了启用应用程序缓存API，还必须向setAppCachePath(String)提供有效的数据库路径。
		// webSetting.setDatabaseEnabled(true);
		webSetting.setDomStorageEnabled(true);														//设置是否启用DOM存储API
		webSetting.setGeolocationEnabled(true);														//设置是否启用地理定位
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);												//设置应用程序缓存内容的最大大小
		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);								//告诉WebView按需启用、禁用或拥有插件
		// webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染优先级
		webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);											//设置缓存方式

		// this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
		// settings 的设计
		//5.0以后解决WebView加载的链接为Https开头，但链接里的内容，如图片为http链接，图片会加载不出来
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			//两者都可以
			webSetting.setMixedContentMode(webSetting.getMixedContentMode());
			//mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}

	}

	public SdkX5WebView(Context arg0) {
//		super(getFixedContext(arg0));
		super(arg0);
		setBackgroundColor(85621);
	}
//	public static Context getFixedContext(Context context) {
//		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//			return context.createConfigurationContext(new Configuration());
//		} else {
//			return context;
//		}
//	}
}
