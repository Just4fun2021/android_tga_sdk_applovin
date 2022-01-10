package sg.just4fun.tgasdk.web.goPage;

public class StatusaAndNavigationModel {

    private String url;
    private  NavigationBarModel navigationBar;
    private  NavigationBarModel statusBarDisplay;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NavigationBarModel getNavigationBar() {
        return navigationBar;
    }

    public void setNavigationBar(NavigationBarModel navigationBar) {
        this.navigationBar = navigationBar;
    }

    public NavigationBarModel getStatusBarDisplay() {
        return statusBarDisplay;
    }

    public void setStatusBarDisplay(NavigationBarModel statusBarDisplay) {
        this.statusBarDisplay = statusBarDisplay;
    }

    public class NavigationBarModel{
       private boolean display;
       private String backgroundColor;

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public boolean isDisplay() {
            return display;
        }

        public void setDisplay(boolean display) {
            this.display = display;
        }
    }
}
