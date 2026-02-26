
import feed.Article;
import feed.Feed;
import feedParser.FeedParser;
import feedParser.RSSParser;
import httpRequest.HttpRequester;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import namedEntity.NamedEntity;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import subscription.Subscription;
import subscription.SubscriptionParser;

public class FeedReaderMain {

    public static FeedParser getParser(String feedType) {
        switch (feedType) {
            case "rss" -> {
                return new RSSParser();
            }
            default ->
                throw new IllegalArgumentException("Unknown feed feedType: " + feedType);
        }
    }

    /**
     * Selects and returns a Heuristic based on the command-line arguments.
     *
     * @param args The command-line arguments.
     * @return The selected Heuristic implementation.
     */
    public static Heuristic getHeuristic(String[] args) {
        Heuristic heuristic = new QuickHeuristic(); // default

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-h") && i + 1 < args.length) {
                String heuristicArg = args[i + 1].toLowerCase();

                if (heuristicArg.equals("quick")) {
                    heuristic = new QuickHeuristic();
                } else {
                    System.out.println("Unknown heuristic: " + heuristicArg + ". Using default (QuickHeuristic).");
                }
            }
        }

        return heuristic;
    }

    /**
     * Prints the top 5 named entities (or fewer if there are fewer in the
     * list) sorted by frequency in descending order.
     *
     * @param namedEntities The list of named entities from which to print the
     *                      top entities and their frequencies.
     */
    public static void printEntityCounts(List<NamedEntity> namedEntities) {

        // Sort the list by frequency descending
        namedEntities.sort((e1, e2) -> Integer.compare(e2.getFrequency(), e1.getFrequency()));

        // Determine how many entities to print (max 5 or fewer if list is smaller)
        int entitiesToPrint = Math.min(5, namedEntities.size());

        // Print the top entities
        System.out.println("Top " + entitiesToPrint + " Named Entities by Frequency:");
        for (int i = 0; i < entitiesToPrint; i++) {
            NamedEntity entity = namedEntities.get(i);
            System.out.println(entity.getName() + " (" + entity.getCategory() + "): " + entity.getFrequency());
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("************* FeedReader version 2.0 *************");

        // Read file with subscriptions
        SubscriptionParser sp = new SubscriptionParser();
        List<Subscription> subscriptions = sp.parseFromFile("config/subscriptions.json");

        // For each subscription, fetch the feed
        HttpRequester requester = new HttpRequester();

        Heuristic heuristic = getHeuristic(args);

        for (Subscription subscription : subscriptions) {
            String feedContent = requester.getUrlContent(subscription.getUrl());
            FeedParser feedParser = getParser(subscription.getUrlType());
            Feed feed = feedParser.parseFeed(feedContent);

            // Map to accumulate named entities and frequencies for this feed only
            // The Key is the name of the entity
            Map<String, NamedEntity> feedEntityMap = new HashMap<>();

            // Extract named entities from all articles in this feed
            for (Article article : feed.getArticles()) {
                String text = article.getTitle() + "/n" + article.getText();
                List<NamedEntity> namedEntities = heuristic.getNamedEntities(text);

                // Merge extracted entities into feed map
                // Group them by name and sum their frequencies
                for (NamedEntity entity : namedEntities) {
                    String name = entity.getName();
                    if (feedEntityMap.containsKey(name)) {
                        // If already present, increase the frequency
                        NamedEntity existingEntity = feedEntityMap.get(name);
                        existingEntity.increaseFrequency();
                    } else {
                        // If not present, add it to the map
                        feedEntityMap.put(name, entity);
                    }
                }
            }

            // Print entities and their frequencies for this feed
            System.out.println("\n===============================================");
            System.out.println("Feed: " + subscription.getUrl());
            System.out.println("===============================================");
            printEntityCounts(new ArrayList<>(feedEntityMap.values()));
        }
    }

}
