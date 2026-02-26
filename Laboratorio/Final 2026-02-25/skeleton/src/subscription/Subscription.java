package subscription;

public class Subscription {

    private String url;
    private String urlType;

    public Subscription(String url, String urlType) {
        this.url = url;
        this.urlType = urlType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    @Override
    public String toString() {
        return "{url=" + getUrl() + ", urlType=" + getUrlType() + "}";
    }

    public void prettyPrint() {
        System.out.println(this.toString());
    }

}
