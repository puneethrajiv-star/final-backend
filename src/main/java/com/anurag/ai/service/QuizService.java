package com.anurag.ai.service;

import com.anurag.ai.dto.VideoQuestionResponse;
import com.anurag.ai.entity.Video;
import com.anurag.ai.entity.VideoQuestion;
import com.anurag.ai.repository.VideoQuestionRepository;
import com.anurag.ai.repository.VideoRepository;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private static final Logger log = LoggerFactory.getLogger(QuizService.class);

    private final VideoQuestionRepository videoQuestionRepository;
    private final VideoRepository videoRepository;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public List<VideoQuestionResponse> getQuizByVideoId(Long videoId) {
        List<VideoQuestion> cached = videoQuestionRepository.findByVideo_Id(videoId);
        if (!cached.isEmpty()) {
            log.info("Serving {} quiz questions for videoId {} from cache.", cached.size(), videoId);
            return cached.stream().map(VideoQuestionResponse::from).toList();
        }

        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Video not found with id: " + videoId));

        String rawResponse = geminiService.generateQuiz(video.getUrl());
        String cleanJson = extractCleanJson(rawResponse);

        try {
            JsonNode rootNode = objectMapper.readTree(cleanJson);
            JsonNode arrayNode = rootNode.isArray() ? rootNode : rootNode.get("questions");

            if (arrayNode == null || !arrayNode.isArray()) {
                for (JsonNode node : rootNode) {
                    if (node.isArray()) {
                        arrayNode = node;
                        break;
                    }
                }
            }

            if (arrayNode == null || !arrayNode.isArray() || arrayNode.isEmpty()) {
                throw new IllegalStateException("AI response did not contain an array of questions: " + cleanJson);
            }

            List<VideoQuestion> questions = new ArrayList<>();
            for (JsonNode qNode : arrayNode) {
                VideoQuestion vq = new VideoQuestion();
                vq.setVideo(video);

                String questionText = extractField(qNode, "questionText", "question_text", "question", "text", "Question");
                vq.setQuestionText(questionText != null ? questionText : "Question text unavailable");

                // Options can be distinct fields or an array under "options"
                JsonNode optionsArray = qNode.get("options");
                if (optionsArray != null && optionsArray.isArray() && optionsArray.size() >= 4) {
                    vq.setOptionA(optionsArray.get(0).asText());
                    vq.setOptionB(optionsArray.get(1).asText());
                    vq.setOptionC(optionsArray.get(2).asText());
                    vq.setOptionD(optionsArray.get(3).asText());
                } else {
                    vq.setOptionA(extractField(qNode, "optionA", "option_a", "option1", "A", "a"));
                    vq.setOptionB(extractField(qNode, "optionB", "option_b", "option2", "B", "b"));
                    vq.setOptionC(extractField(qNode, "optionC", "option_c", "option3", "C", "c"));
                    vq.setOptionD(extractField(qNode, "optionD", "option_d", "option4", "D", "d"));
                }

                // Default fallbacks if any option is empty
                if (vq.getOptionA() == null) vq.setOptionA("Option A");
                if (vq.getOptionB() == null) vq.setOptionB("Option B");
                if (vq.getOptionC() == null) vq.setOptionC("Option C");
                if (vq.getOptionD() == null) vq.setOptionD("Option D");

                String rawCorrect = extractField(qNode, "correctOption", "correct_option", "answer", "correct", "correctAnswer");
                vq.setCorrectOption(normalizeCorrectOption(rawCorrect));

                questions.add(vq);
            }

            List<VideoQuestion> saved = videoQuestionRepository.saveAll(questions);
            log.info("Parsed and saved {} quiz questions for videoId {}.", saved.size(), videoId);
            return saved.stream().map(VideoQuestionResponse::from).toList();

        } catch (Exception e) {
            log.error("Failed to parse quiz JSON from Gemini for videoId {}. Raw response: {}", videoId, rawResponse, e);
            throw new RuntimeException("Failed to generate and parse quiz: " + e.getMessage(), e);
        }
    }

    private String extractCleanJson(String raw) {
        if (raw == null) return "[]";
        String trimmed = raw.trim();

        // 1. Strip markdown fences if present
        if (trimmed.startsWith("```json")) {
            trimmed = trimmed.substring(7);
        } else if (trimmed.startsWith("```")) {
            trimmed = trimmed.substring(3);
        }
        if (trimmed.endsWith("```")) {
            trimmed = trimmed.substring(0, trimmed.length() - 3);
        }
        trimmed = trimmed.trim();

        // 2. Locate first JSON array bracket '[' and last ']'
        int firstBracket = trimmed.indexOf('[');
        int lastBracket = trimmed.lastIndexOf(']');
        if (firstBracket != -1 && lastBracket != -1 && lastBracket > firstBracket) {
            return trimmed.substring(firstBracket, lastBracket + 1).trim();
        }

        // 3. Fallback to outer JSON object '{' and '}'
        int firstBrace = trimmed.indexOf('{');
        int lastBrace = trimmed.lastIndexOf('}');
        if (firstBrace != -1 && lastBrace != -1 && lastBrace > firstBrace) {
            return trimmed.substring(firstBrace, lastBrace + 1).trim();
        }

        return trimmed;
    }

    private String extractField(JsonNode node, String... candidateKeys) {
        for (String key : candidateKeys) {
            JsonNode val = node.get(key);
            if (val != null && !val.isNull()) {
                return val.asText();
            }
        }
        return null;
    }

    private String normalizeCorrectOption(String raw) {
        if (raw == null || raw.isBlank()) return "A";
        String upper = raw.trim().toUpperCase();
        for (char ch : upper.toCharArray()) {
            if (ch >= 'A' && ch <= 'D') {
                return String.valueOf(ch);
            }
        }
        return "A";
    }
}
