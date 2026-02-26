package namedEntity.heuristic;

/**
 * A heuristic implementation that classifies words as named entities based on
 * simple rules.
 * <p>
 * This heuristic returns the category "OTHER" for words that start with an
 * uppercase letter, have length greater than 1, and are not in the stop words
 * list. Otherwise, it returns null.
 * </p>
 */
public class QuickHeuristic extends Heuristic {

    @Override
    public String getCategory(String word) {
        if (word.length() > 1
                && Character.isUpperCase(word.charAt(0))
                && !stopWords.contains(word.toLowerCase())) {
            return "OTHER";
        }
        return null;
    }

}
