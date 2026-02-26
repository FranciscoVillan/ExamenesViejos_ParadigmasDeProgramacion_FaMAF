package subscription;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class SubscriptionParser {

    public List<Subscription> parseFromFile(String filename) {
        String jsonString = readFileContents(filename);
        return parse(jsonString);
    }

    private String readFileContents(String filename) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public List<Subscription> parse(String jsonString) {
        List<Subscription> subscriptions = new ArrayList<>();

        // Parse the JSON string into a JSON array
        JSONArray jsonArray = new JSONArray(jsonString);

        // Loop through each object in the array
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject item = jsonArray.getJSONObject(i);

            // Extract fields explicitly
            String url = item.optString("url", null);
            String urlType = item.optString("urlType", null);

            // Create a Subscription object and add it to the list
            subscriptions.add(new Subscription(url, urlType));
        }

        return subscriptions;
    }
}
