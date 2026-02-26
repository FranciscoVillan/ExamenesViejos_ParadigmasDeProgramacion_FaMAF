package namedEntity.heuristic;

/**
 * A heuristic that randomly assigns a category to a word from a predefined list
 * of categories.
 *
 * <p>
 * Categories include "person", "country", "other", and {@code null} to
 * represent no category.</p>
 *
 * <p>
 * This heuristic is useful for testing pipelines that require a
 * {@code Heuristic} implementation without needing a real classification
 * model.</p>
 */
public class RandomHeuristic extends Heuristic {

    /**
     * Array of possible categories to assign. By default includes "person",
     * "country", "other", and {@code null}.
     */
    private final String[] categories = {"PERSON", "COUNTRY", "OTHER", null};

    /**
     * Returns a random category for the given word.
     *
     * @param word the word to categorize
     * @return a random category ("person", "country", "other") or {@code null}
     * if no category is assigned
     */
    @Override
    public String getCategory(String word) {
        if (stopWords.contains(word.toLowerCase())) {
            return null;
        }
        int randomIndex = (int) (Math.random() * categories.length);
        return categories[randomIndex];
    }
}
