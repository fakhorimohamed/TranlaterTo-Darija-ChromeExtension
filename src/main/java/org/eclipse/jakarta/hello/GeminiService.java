package org.eclipse.jakarta.hello;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@ApplicationScoped
public class GeminiService {

    private static final String API_KEY = System.getenv("GEMINI_API_KEY");
    
    private static final String GEMINI_URL = 
    	    "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

    public String translateToDarija(String englishText) {
        try {
        	String prompt = "Translate the following English text to Moroccan Arabic (Darija): '" 
        + englishText + "'. Provide ONLY the translated word or phrase, no explanation, no pronunciation, no bullet points.";

            
        	System.out.println("DEBUG: Sending request to: " + GEMINI_URL);
        	
            JsonObject requestBody = Json.createObjectBuilder()
                .add("contents", Json.createArrayBuilder()
                    .add(Json.createObjectBuilder()
                        .add("parts", Json.createArrayBuilder()
                            .add(Json.createObjectBuilder().add("text", prompt)))))
                .build();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GEMINI_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, 
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            try (JsonReader reader = Json.createReader(new StringReader(response.body()))) {
                JsonObject json = reader.readObject();

                if (json.containsKey("error")) {
                    return "ERROR: " + json.getJsonObject("error").getString("message");
                }

                JsonArray candidates = json.getJsonArray("candidates");
                if (candidates != null && !candidates.isEmpty()) {
                    JsonObject content = candidates.getJsonObject(0).getJsonObject("content");
                    if (content != null) {
                        JsonArray parts = content.getJsonArray("parts");
                        if (parts != null && !parts.isEmpty()) {
                        	
                            return parts.getJsonObject(0).getString("text").trim();
                        }
                    }
                }
                return "ERROR: No translation found.";
            }
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
