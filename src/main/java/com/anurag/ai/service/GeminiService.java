package com.anurag.ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GeminiService() {
        org.springframework.http.client.SimpleClientHttpRequestFactory factory = new org.springframework.http.client.SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(java.time.Duration.ofSeconds(15));
        factory.setReadTimeout(java.time.Duration.ofSeconds(90));
        this.restTemplate = new RestTemplate(factory);
    }

    private static final String INTERACTIONS_URL = "https://generativelanguage.googleapis.com/v1beta/interactions";
    private static final String MODEL = "gemini-3.8-flash";

    public String ask(String youtubeUrl, String question) {
        String prompt = """
            You are a concise teaching assistant helping a student with this video.
            Answer their question using only the video's content. Keep your answer
            to 2-3 sentences maximum. No preamble, just the answer. If the video
            doesn't cover what they're asking, say so in one line instead of guessing.

            Student question:
            %s
            """.formatted(question);

        return callInteractions(prompt, youtubeUrl);
    }

    public String generateQuiz(String youtubeUrl) {
        String prompt = """
            You are an expert computer science teacher. Based on this video, generate exactly 5 multiple-choice questions testing student comprehension.
            Respond strictly with a valid JSON array of 5 objects and no extra explanation, markdown fences, or text.
            Schema:
            [
              {
                "questionText": "Question text here",
                "optionA": "Option A text",
                "optionB": "Option B text",
                "optionC": "Option C text",
                "optionD": "Option D text",
                "correctOption": "A"
              }
            ]
            Rule: correctOption must strictly be one of: A, B, C, or D.
            """;

        return callInteractions(prompt, youtubeUrl);
    }

    // YouTube video input is only supported through the Interactions API
    // (generateContent's file_data.file_uri does not accept YouTube URLs).
    private String callInteractions(String promptText, String youtubeUrl) {
        Map<String, Object> body = Map.of(
            "model", MODEL,
            "input", new Object[] {
                Map.of("type", "text", "text", promptText),
                Map.of("type", "video", "uri", youtubeUrl)
            }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);

        return executeWithRetry(INTERACTIONS_URL, new HttpEntity<>(body, headers));
    }

    @SuppressWarnings("unchecked")
    private String executeWithRetry(String url, HttpEntity<Map<String, Object>> entity) {
        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
                if (response.getBody() == null) {
                    throw new IllegalStateException("Empty body from Gemini API");
                }
                List<Map> steps = (List<Map>) response.getBody().get("steps");
                if (steps == null) {
                    throw new IllegalStateException("Gemini response had no steps: " + response.getBody());
                }
                for (Map step : steps) {
                    if (!"model_output".equals(step.get("type"))) continue;
                    List<Map> content = (List<Map>) step.get("content");
                    if (content == null) continue;
                    for (Map part : content) {
                        if ("text".equals(part.get("type")) && part.get("text") != null) {
                            return (String) part.get("text");
                        }
                    }
                }
                throw new IllegalStateException("Gemini returned no text output: " + response.getBody());
            } catch (org.springframework.web.client.HttpStatusCodeException ex) {
                if (ex.getStatusCode().is5xxServerError() && attempt < maxAttempts) {
                    try {
                        Thread.sleep(2000L * attempt);
                    } catch (InterruptedException ignored) {}
                    continue;
                }
                throw ex;
            }
        }
        throw new IllegalStateException("Failed to communicate with Gemini API");
    }
}
