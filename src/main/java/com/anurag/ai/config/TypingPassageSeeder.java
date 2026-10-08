package com.anurag.ai.config;

import com.anurag.ai.entity.TypingPassage;
import com.anurag.ai.repository.TypingPassageRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TypingPassageSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(TypingPassageSeeder.class);
    private final TypingPassageRepository typingPassageRepository;

    @Override
    public void run(String... args) {
        if (typingPassageRepository.count() > 0) {
            log.info("Typing passages already seeded. Skipping.");
            return;
        }

        List<TypingPassage> passages = new ArrayList<>();

        // 1. TEXT Category - Levels 1 to 4
        // Level 1: short, simple sentences, common words
        passages.add(createPassage("TEXT", 1,
                "The sun rises in the east and sets in the west. Every new day brings a chance to learn and grow. Practice typing every day to build your speed and confidence.",
                "Original Practice"));
        passages.add(createPassage("TEXT", 1,
                "Computers are tools that follow our instructions. Learning to code opens up many doors. Take your time and focus on accuracy before speed.",
                "Original Practice"));

        // Level 2: short sentences, common words, basic punctuation
        passages.add(createPassage("TEXT", 2,
                "Java is an object-oriented programming language designed to run anywhere. Write your code cleanly, test it frequently, and learn from every mistake you make along the way.",
                "Original Practice"));
        passages.add(createPassage("TEXT", 2,
                "Typing fast allows your thoughts to flow straight onto the screen without interruption. Good posture and ergonomic finger positioning keep your hands healthy during long coding sessions.",
                "Original Practice"));

        // Level 3: longer sentences, more varied vocabulary
        passages.add(createPassage("TEXT", 3,
                "Transitioning from high school to an engineering college presents both academic and personal challenges. You will encounter abstract concepts, intricate algorithms, and analytical paradigms that fundamentally reshape how you approach complex problem-solving scenarios.",
                "Original Practice"));
        passages.add(createPassage("TEXT", 3,
                "Efficient software systems depend on selecting optimal data structures for specialized operational requirements. Choosing between contiguous memory arrays and node-based linked representations dictates runtime complexity and system responsiveness under heavy workloads.",
                "Original Practice"));

        // Level 4: longer sentences, varied vocabulary, technical syntax
        passages.add(createPassage("TEXT", 4,
                "Operating systems orchestrate harmonious interaction between silicon microarchitecture and disparate user applications. By virtualizing physical random access memory through translation lookaside buffers and paging mechanisms, the kernel isolates memory spaces to guarantee reliability and prevent catastrophic faults.",
                "Original Practice"));
        passages.add(createPassage("TEXT", 4,
                "Distributed systems achieve resilience through decentralized consensus algorithms, fault-tolerant replication protocols, and idempotent communication channels. Engineering robust distributed architectures requires anticipating latency anomalies, intermittent partition events, and concurrent state mutations.",
                "Original Practice"));

        // 2. CODE Category - Levels 1 to 5+
        // Level 1: print statements, simple variable assignment
        passages.add(createPassage("CODE", 1,
                "int age = 18;\nString name = \"Aarav\";\nSystem.out.println(name);\nSystem.out.println(age);",
                "Basic Java"));
        passages.add(createPassage("CODE", 1,
                "double score = 95.5;\nboolean passed = true;\nSystem.out.println(\"Score: \" + score);\nSystem.out.println(\"Passed: \" + passed);",
                "Basic Java"));

        // Level 2: simple arithmetic, multiple variable assignment
        passages.add(createPassage("CODE", 2,
                "int width = 25;\nint height = 40;\nint area = width * height;\nint perimeter = 2 * (width + height);\nSystem.out.println(\"Area: \" + area);\nSystem.out.println(\"Perimeter: \" + perimeter);",
                "Java Variables"));
        passages.add(createPassage("CODE", 2,
                "double principal = 1000.0;\ndouble rate = 0.05;\nint time = 3;\ndouble interest = (principal * rate * time) / 100;\nSystem.out.println(\"Simple Interest: \" + interest);",
                "Java Calculations"));

        // Level 3: if/else conditions
        passages.add(createPassage("CODE", 3,
                "int marks = 75;\nif (marks >= 50) {\n    System.out.println(\"Student has passed the examination.\");\n} else {\n    System.out.println(\"Student needs improvement.\");\n}",
                "Conditionals"));
        passages.add(createPassage("CODE", 3,
                "int number = 42;\nif (number % 2 == 0) {\n    System.out.println(number + \" is even\");\n} else {\n    System.out.println(number + \" is odd\");\n}",
                "Conditionals"));

        // Level 4: if/else conditions with multiple branches and operators
        passages.add(createPassage("CODE", 4,
                "int marks = 88;\nchar grade;\nif (marks >= 90) {\n    grade = 'A';\n} else if (marks >= 75) {\n    grade = 'B';\n} else if (marks >= 50) {\n    grade = 'C';\n} else {\n    grade = 'F';\n}\nSystem.out.println(\"Grade: \" + grade);",
                "Multi-Branch"));
        passages.add(createPassage("CODE", 4,
                "int age = 20;\nboolean hasId = true;\nif (age >= 18 && hasId) {\n    System.out.println(\"Access granted to exam hall.\");\n} else if (age >= 18 && !hasId) {\n    System.out.println(\"Please show physical ID verification.\");\n} else {\n    System.out.println(\"Candidate ineligible.\");\n}",
                "Logical Operators"));

        // Level 5+: loops, nested brackets
        passages.add(createPassage("CODE", 5,
                "int[] numbers = {10, 20, 30, 40, 50};\nint total = 0;\nfor (int i = 0; i < numbers.length; i++) {\n    total += numbers[i];\n}\nSystem.out.println(\"Total sum: \" + total);",
                "Loops"));
        passages.add(createPassage("CODE", 5,
                "for (int i = 0; i < rows; i++) {\n    for (int j = 0; j < cols; j++) {\n        if (matrix[i][j] == target) {\n            System.out.println(\"Found target at (\" + i + \", \" + j + \")\");\n            return true;\n        }\n    }\n}\nreturn false;",
                "Nested Loops"));
        passages.add(createPassage("CODE", 6,
                "while (left <= right) {\n    int mid = left + (right - left) / 2;\n    if (array[mid] == key) {\n        return mid;\n    } else if (array[mid] < key) {\n        left = mid + 1;\n    } else {\n        right = mid - 1;\n    }\n}\nreturn -1;",
                "Binary Search Loop"));

        // 3. TEXT Category - Level 5+ from Public Domain Novels in /resources
        seedBookPassages(passages, "Wizard_Of_OZ.txt", "The Wonderful Wizard of Oz", 5);
        seedBookPassages(passages, "Gulliver's Travels.txt", "Gulliver's Travels", 6);
        seedBookPassages(passages, "Jungle_boo.txt", "The Jungle Book", 7);

        typingPassageRepository.saveAll(passages);
        log.info("Successfully seeded {} typing passages.", passages.size());
    }

    private void seedBookPassages(List<TypingPassage> targetList, String filename, String sourceTitle, int level) {
        String content = loadBookContent(filename);
        if (content == null || content.isBlank()) {
            log.warn("Expected book file '{}' for '{}' not found in resources. Skipping level {}+.",
                    filename, sourceTitle, level);
            return;
        }

        List<String> chunks = extractPassages(content, 150, 300, 6);
        if (chunks.isEmpty()) {
            log.warn("No passages could be extracted from '{}'.", filename);
            return;
        }

        for (String chunk : chunks) {
            targetList.add(createPassage("TEXT", level, chunk, sourceTitle));
            // Also seed at level 5 so level 5 always has full variety
            if (level != 5) {
                targetList.add(createPassage("TEXT", 5, chunk, sourceTitle));
            }
        }
        log.info("Extracted {} passages for '{}' (level {}).", chunks.size(), sourceTitle, level);
    }

    private String loadBookContent(String filename) {
        try {
            ClassPathResource resource = new ClassPathResource(filename);
            if (resource.exists()) {
                try (InputStream is = resource.getInputStream()) {
                    return new String(is.readAllBytes(), StandardCharsets.UTF_8);
                }
            }
        } catch (Exception ignored) {}

        // Fallback to checking direct relative directory
        String[] fallbackPaths = {
            "src/main/resources/" + filename,
            "resources/" + filename,
            filename
        };
        for (String path : fallbackPaths) {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                try {
                    return Files.readString(file.toPath(), StandardCharsets.UTF_8);
                } catch (Exception ignored) {}
            }
        }
        return null;
    }

    private List<String> extractPassages(String text, int minWords, int maxWords, int maxCount) {
        int start = text.indexOf("*** START OF");
        if (start != -1) {
            int lineEnd = text.indexOf("\n", start);
            if (lineEnd != -1) {
                text = text.substring(lineEnd + 1);
            }
        }
        int end = text.indexOf("*** END OF");
        if (end != -1) {
            text = text.substring(0, end);
        }

        String[] paragraphs = text.split("(\\r?\\n){2,}");
        List<String> result = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int wordCount = 0;

        for (String p : paragraphs) {
            String cleaned = p.replaceAll("\\s+", " ").trim();
            if (cleaned.length() < 40 || cleaned.startsWith("Chapter") || cleaned.startsWith("Contents")) {
                continue;
            }
            int wordsInPara = cleaned.split("\\s+").length;
            if (wordCount + wordsInPara <= maxWords) {
                if (buffer.length() > 0) buffer.append(" ");
                buffer.append(cleaned);
                wordCount += wordsInPara;
            } else {
                if (wordCount >= minWords) {
                    result.add(buffer.toString());
                    if (result.size() >= maxCount) break;
                }
                buffer = new StringBuilder(cleaned);
                wordCount = wordsInPara;
            }
        }

        if (wordCount >= minWords && result.size() < maxCount) {
            result.add(buffer.toString());
        }

        return result;
    }

    private TypingPassage createPassage(String category, int level, String content, String source) {
        TypingPassage passage = new TypingPassage();
        passage.setCategory(category);
        passage.setLevel(level);
        passage.setContent(content.trim());
        passage.setSource(source);
        return passage;
    }
}
