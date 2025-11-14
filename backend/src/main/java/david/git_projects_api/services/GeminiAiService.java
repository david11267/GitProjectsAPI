package david.git_projects_api.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.genai.types.*;
import com.google.genai.Client;
import com.google.genai.types.Schema;
import david.git_projects_api.dtos.RepoSummaryDtoCollection;
import david.git_projects_api.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class GeminiAiService {

    private final Client client;
    private final ObjectMapper objectMapper;

    public GeminiAiService() throws JsonProcessingException {
        String apiKey = System.getenv("GOOGLE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Google API key is required. Please set the GOOGLE_API_KEY environment variable."
            );
        }

        this.client = new Client(); // Assumes Client reads GOOGLE_API_KEY automatically
        this.objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);


        System.out.println("🔑 Google Gemini client initialized successfully");
    }

    public RepoSummaryDtoCollection generateContent(ArrayList<ObjectNode> rawJson) {
        try {
            // Convert list of ObjectNodes into a JSON array string
            String repoDataJson = objectMapper.writeValueAsString(rawJson);



            String instructionPrompt = """
            You are an expert software repository analysis model.
            
            Input:
            A JSON array describing one or more GitHub repositories. Each repository contains keys like "repo", "description", "languages", "tree", and "readme".
            
            Task:
            Visit the repository yourself and examine each file.
            Infer and summarize each repository’s technologies, frameworks, architecture, build tools, dependencies, and integrations. Use folder names, file names, and extensions to detect frameworks and tools.
            
            Output:
            Return a single JSON object with the key "repoSummaryDtoCollections" that matches the configured schema exactly.
            
            Rules:
            - Respond ONLY with a valid JSON object starting with { and ending with }.
            - Do NOT wrap the output in [], triple quotes, or any extra quotes.
            - Do NOT use backticks (`) or other non-JSON symbols.
            - No prose, no markdown, no code fences.
            - Do not explain reasoning or include extra fields.
            - Ensure JSON validity and consistent property naming.
            
            Repository data:
            %s
            """.formatted(repoDataJson);

            Schema schema = RepoSummaryDtoCollection.getRepoSummaryCollectionSchema();
            GenerateContentResponse response = client.models
                    .generateContent("gemini-2.5-flash", instructionPrompt, GenerateContentConfig.builder()
                            .responseMimeType("application/json")
                            .responseSchema(schema)
                            .temperature(0.05f)
                            .topK(20f)
                            .topP(0.8f)
                            .candidateCount(1)
                            .maxOutputTokens(8192)
                            .build()
                    );

            String parsedJson = response.text()
                    .trim()
                    .replaceAll("^```json|^```|```$", "")
                    .replaceAll("^[`]+|[`]+$", "")
                    .trim();

            RepoSummaryDtoCollection dtoList = objectMapper.readValue(
                    parsedJson, RepoSummaryDtoCollection.class
            );

            return dtoList;

        } catch (Exception e) {
            throw new ApiException("Gemini error: "+e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
