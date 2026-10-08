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

    public String ask(String youtubeUrl, String question) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=" + apiKey;

        String prompt = """
            You are a concise teaching assistant helping a student with this video.
            Answer their question using only the video's content. Keep your answer
            to 2-3 sentences maximum. No preamble, just the answer. If the video
            doesn't cover what they're asking, say so in one line instead of guessing.

            Student question:
            %s
            """.formatted(question);

        Map<String, Object> body = Map.of(
            "contents", new Object[] {
                Map.of("parts", new Object[] {
                    Map.of("text", prompt),
                    Map.of("file_data", Map.of("mime_type", "video/mp4", "file_uri", youtubeUrl))
                })
            }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return executeWithRetry(url, new HttpEntity<>(body, headers));
    }

    public String generateQuiz(String youtubeUrl) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=" + apiKey;

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

        Map<String, Object> body = Map.of(
            "contents", new Object[] {
                Map.of("parts", new Object[] {
                    Map.of("text", prompt),
                    Map.of("file_data", Map.of("mime_type", "video/mp4", "file_uri", youtubeUrl))
                })
            }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return executeWithRetry(url, new HttpEntity<>(body, headers));
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
                List<Map> candidates = (List<Map>) response.getBody().get("candidates");
                if (candidates == null || candidates.isEmpty()) {
                    throw new IllegalStateException("Gemini returned no candidates");
                }
                Map content = (Map) candidates.get(0).get("content");
                List<Map> parts = (List<Map>) content.get("parts");
                return (String) parts.get(0).get("text");
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
