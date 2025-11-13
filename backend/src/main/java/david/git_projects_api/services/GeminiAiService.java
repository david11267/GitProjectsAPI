package david.git_projects_api.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import david.git_projects_api.dtos.RepoSummaryDtoCollection;
import david.git_projects_api.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

            String schema = """
            {
              "repoSummaryDtoCollections": [
                {
                  "name": "string",
                  "description": "string",
                  "languages": ["string"],
                  "frameworks": ["string"],
                  "tools": ["string"],
                  "architecture": "string",
                  "deployment": "string"
                }
              ]
            }
            """;

            String instructionPrompt = """
            You are an expert software repository analysis model.
            
            Input:
            A JSON array describing one or more GitHub repositories. Each repository contains keys like "repo", "description", "languages", "tree", and "readme".
            
            Task:
            Infer and summarize each repository’s technologies, frameworks, architecture, build tools, dependencies, and integrations. Use folder names, file names, and extensions to detect frameworks and tools.
            
            Output:
            Return a single JSON object with the key "repoSummaryDtoCollections". The value should be an array of objects strictly matching this schema:
            %s
            
            Rules:
            - Respond ONLY with a valid JSON object starting with { and ending with }.
            - Do NOT wrap the output in [], triple quotes, or any extra quotes.
            - Do NOT use backticks (`) or other non-JSON symbols.
            - No prose, no markdown, no code fences.
            - Do not explain reasoning or include extra fields.
            - Ensure JSON validity and consistent property naming.
            
            Repository data:
            %s
            """.formatted(schema, repoDataJson);

            GenerateContentResponse response = client.models
                    .generateContent("gemini-2.5-flash", instructionPrompt,null);

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
