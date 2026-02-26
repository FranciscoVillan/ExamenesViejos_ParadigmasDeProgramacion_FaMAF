package feedParser;

import feed.Feed;

/**
 * Abstract class representing a parser capable of extracting articles from a feed.
 * <p>
 * Subclasses of {@code FeedParser} must implement the {@link #parseFeed(String, int)} method
 * to parse the feed content and extract a {@link Feed} containing its articles.
 * </p>
 * <p>
 * Provides an overloaded {@link #parseFeed(String)} method for convenience, which parses
 * all available articles without limiting their number.
 * </p>
 */
public abstract class FeedParser {

    /**
     * Parses the provided feed content, extracting up to {@code limit} articles into a {@link Feed}.
     *
     * @param xmlContent the raw feed content as a {@code String}
     * @param limit the maximum number of articles to parse
     * @return a {@link Feed} containing the parsed articles
     * @throws Exception if parsing fails
     */
    public abstract Feed parseFeed(String xmlContent, int limit) throws Exception;

    /**
     * Parses the provided feed content, extracting all available articles without limit.
     *
     * @param content the raw feed content as a {@code String}
     * @return a {@link Feed} containing all parsed articles
     * @throws Exception if parsing fails
     */
    public Feed parseFeed(String content) throws Exception {
        return parseFeed(content, Integer.MAX_VALUE);
    }
}
