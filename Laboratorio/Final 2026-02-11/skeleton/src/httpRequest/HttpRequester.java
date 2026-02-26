package httpRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Utility class for making HTTP requests.
 * <p>
 * {@code HttpRequester} provides methods to fetch the content of web resources
 * over HTTP using simple GET requests, returning the raw content as a
 * {@code String}.
 * It is used to retrieve RSS or Atom feeds in the feed reader system.
 * </p>
 */
public class HttpRequester {

    /**
     * Makes a GET request to the given URL and returns its content as a string.
     *
     * If the request is successful (200 response code), reads the input stream
     * line by line and appends it to the result buffer. If the request fails,
     * prints an error message.
     *
     * @param urlString The URL to fetch.
     * @return The content of the URL as a string.
     */
    public String getUrlContent(String urlString) {
        StringBuilder result = new StringBuilder();
        try {
            URI uri = URI.create(urlString);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 200 response code indicates success
            if (conn.getResponseCode() == 200) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line).append("\n");
                    }
                }
            } else {
                System.err.println("Error fetching url " + url + ": " + conn.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

}
