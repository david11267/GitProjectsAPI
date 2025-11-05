package david.git_projects_api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeminiAiService {

    private final Client client;
    private final ObjectMapper objectMapper;

    public GeminiAiService() {
        String apiKey = System.getenv("GOOGLE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Google API key is required. Please set the GOOGLE_API_KEY environment variable."
            );
        }

        this.client = new Client(); // Assumes Client reads GOOGLE_API_KEY automatically
        this.objectMapper = new ObjectMapper();
        System.out.println("🔑 Google Gemini client initialized successfully");
    }

    public GenerateContentResponse generateContent(ArrayList<ObjectNode> rawJson) {
        try {
            // Convert list of ObjectNodes into a JSON array string
            String repoDataJson = objectMapper.writeValueAsString(rawJson);

            // Define the instruction prompt
            String instructionPrompt = "You are a software analysis system.\n" +
                            "Given a JSON object representing a GitHub repository (with properties such as \"repo\", \"description\", \"languages\", \"tree\", and \"readme\"), " +
                            "analyze it and produce a structured JSON response describing the repository’s technologies, frameworks, architecture, tools, integrations, and development setup.\n" +
                            "Infer frameworks and tools based on folder names, file names, and extensions (e.g., `.tsx` → React, `pom.xml` → Spring Boot, `vite.config.ts` → Vite, `Dockerfile` → Docker, `.env` → configuration, etc.).\n" +
                            "Follow this exact output format and return only valid JSON:\n" +
                            "{\n" +
                            "  \"repository\": {\"name\": \"string\", \"description\": \"string\", \"overview\": \"string\"},\n" +
                            "  \"languages\": [{\"name\": \"string\", \"bytes\": number, \"role\": \"string\"}],\n" +
                            "  \"frameworks\": {\"backend\": [\"string\"], \"frontend\": [\"string\"], \"testing\": [\"string\"], \"build\": [\"string\"]},\n" +
                            "  \"tools_and_integrations\": [{\"name\": \"string\", \"category\": \"api | auth | deployment | ai | devops | config | other\", \"description\": \"string\"}],\n" +
                            "  \"architecture\": {\"type\": \"monorepo | single-service | microservices | unknown\", \"modules\": [{\"name\": \"string\", \"role\": \"backend | frontend | shared | infra\", \"key_paths\": [\"string\"]}], \"design_patterns\": [\"string\"]},\n" +
                            "  \"external_services\": [{\"name\": \"string\", \"purpose\": \"string\"}],\n" +
                            "  \"build_and_deployment\": {\"build_tools\": [\"string\"], \"containerization\": [\"string\"], \"ci_cd\": [\"string\"]},\n" +
                            "  \"developer_experience\": {\"code_organization\": \"string\", \"maintainability\": \"string\", \"missing_files\": [\"string\"]}\n" +
                            "}\n" +
                            "Only return the JSON object. Do not add explanations or markdown formatting.";

            // Call Gemini with both the prompt and the repo data
            GenerateContentResponse response = client.models
                    .generateContent("gemini-2.5-flash", instructionPrompt +"Multiple repo Data: "+ repoDataJson,null);

            System.out.println("✅ Gemini response: " + response.text());
            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error generating Gemini content", e);
        }
    }
}
