package feed;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a feed retrieved from a subscription source.
 * <p>
 * A feed contains the name of the site and a list of articles
 * associated with that feed.
 * </p>
 */
public class Feed {

    String siteName;
    List<Article> articles;

    public Feed(String siteName) {
        super();
        this.siteName = siteName;
        this.articles = new ArrayList<>();
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article a) {
        this.getArticles().add(a);
    }

    public Article getArticle(int i) {
        return this.getArticles().get(i);
    }

    public int getNumberOfArticles() {
        return this.getArticles().size();
    }

    @Override
    public String toString() {
        return "Feed [siteName=" + getSiteName() + ", articles=" + getArticles() + "]";
    }

    public void prettyPrint() {
        for (Article a : this.getArticles()) {
            a.prettyPrint();
        }
    }
}
