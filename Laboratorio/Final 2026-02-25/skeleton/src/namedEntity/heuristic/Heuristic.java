package namedEntity.heuristic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import namedEntity.NamedEntity;

/**
 * Abstract base class for heuristics that classify words into named entity
 * categories.
 * <p>
 * Subclasses must implement the {@code getCategory} method to determine the
 * category of a given word. This class also provides a method to extract named
 * entities from a text using the implemented classification heuristic.
 * </p>
 */
public abstract class Heuristic {

    protected static final List<String> stopWords = List.of(
            "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you",
            "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she",
            "her", "hers", "herself", "it", "its", "itself", "they", "them", "your",
            "their", "theirs", "themselves", "what", "which", "who", "whom",
            "this", "that", "these", "those", "am", "is", "are", "was", "were",
            "be", "been", "being", "have", "has", "had", "having", "do", "does",
            "did", "doing", "a", "an", "the", "and", "but", "if", "or",
            "because", "as", "until", "while", "of", "at", "by", "for", "with",
            "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out",
            "off", "over", "under", "again", "further", "then", "once", "here",
            "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not",
            "only", "own", "same", "so", "than", "too", "very", "s", "t", "can",
            "will", "just", "don", "should", "now", "on",
            // Contractions without '
            "im", "ive", "id", "ill", "Youre", "youd", "youve", "youll",
            "hes", "hed", "hell", "shes", "shed", "shell", "itd", "itll",
            "were", "wed", "weve", "well", "theyre", "theyd", "theyve", "theyll",
            "shouldnt", "couldnt", "musnt", "cant", "wont",
            // Common uppercase words
            "hi", "hello");

    /**
     * Returns the named entity category of the given word.
     *
     * @param word The word to classify.
     * @return The category name as a string, or {@code null} if the word is not
     *         a named entity.
     */
    public abstract String getCategory(String word);

    /**
     * Extracts named entities from the given text using simple heuristics. It
     * removes specific punctuation, replaces newlines with spaces, and
     * classifies words into categories if applicable, tracking their frequency.
     *
     * @param text The input text from which to extract named entities.
     * @return A List of NamedEntity objects found in the text, with their
     *         frequencies.
     */
    public List<NamedEntity> getNamedEntities(String text) {
        String charsToRemove = ".,;:()'!?’";

        // Replace newlines with spaces to prevent word concatenation
        text = text.replace("\n", " ");

        // Remove specified punctuation
        for (char c : charsToRemove.toCharArray()) {
            text = text.replace(String.valueOf(c), "");
        }

        Map<String, NamedEntity> namedEntities = new HashMap<>();

        // Split text into words by whitespace (handles multiple spaces)
        for (String word : text.split("\\s+")) {
            if (word.isEmpty()) {
                continue; // skip empty tokens
            }
            String namedEntityCategory = getCategory(word);
            if (namedEntityCategory == null) {
                continue; // skip words that are not named entities
            }

            // Update frequency if already seen, else add new entity
            namedEntities.compute(word, (k, v) -> {
                if (v == null) {
                    return new NamedEntity(word, namedEntityCategory, 1);
                } else {
                    v.increaseFrequency();
                    return v;
                }
            });
        }

        // Return a List of the computed named entities
        return new ArrayList<>(namedEntities.values());
    }
}
