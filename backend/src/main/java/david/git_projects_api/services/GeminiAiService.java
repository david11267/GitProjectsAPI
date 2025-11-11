package david.git_projects_api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import david.git_projects_api.dtos.RepoAnalysisDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeminiAiService {

    private final Client client;
    private final ObjectMapper objectMapper;
    JsonSchemaGenerator schemaGenerator;


    public GeminiAiService() throws JsonProcessingException {
        String apiKey = System.getenv("GOOGLE_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Google API key is required. Please set the GOOGLE_API_KEY environment variable."
            );
        }

        this.client = new Client(); // Assumes Client reads GOOGLE_API_KEY automatically
        this.objectMapper = new ObjectMapper();
        schemaGenerator = new JsonSchemaGenerator(objectMapper);
        JsonSchema jsonSchema = schemaGenerator.generateSchema(RepoAnalysisDto.class);
        String prettySchema =  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);

        System.out.println("🔑 Google Gemini client initialized successfully");
    }

    public List<RepoAnalysisDto> generateContent(ArrayList<ObjectNode> rawJson) {
        try {
            // Convert list of ObjectNodes into a JSON array string
            String repoDataJson = objectMapper.writeValueAsString(rawJson);

            // Define the instruction prompt
            String instructionPrompt = """
You are a software analysis system.
Given a JSON object representing a GitHub repository (with properties such as "repo", "description", "languages", "tree", and "readme"), analyze it and produce a structured JSON response describing the repository’s technologies, frameworks, architecture, tools, integrations, and development setup.
Infer frameworks and tools based on folder names, file names, and extensions.
Follow this exact output format and return only valid JSON:
{
  "repository": {"name": "string", "description": "string", "overview": "string"},
  "languages": [{"name": "string", "bytes": number, "role": "string"}],
  "frameworks": {"backend": ["string"], "frontend": ["string"], "testing": ["string"], "build": ["string"]},
  "tools_and_integrations": [{"name": "string", "category": "api | auth | deployment | ai | devops | config | other", "description": "string"}],
  "architecture": {"type": "monorepo | single-service | microservices | unknown", "modules": [{"name": "string", "role": "backend | frontend | shared | infra", "key_paths": ["string"]}], "design_patterns": ["string"]},
  "external_services": [{"name": "string", "purpose": "string"}],
  "build_and_deployment": {"build_tools": ["string"], "containerization": ["string"], "ci_cd": ["string"]},
  "developer_experience": {"code_organization": "string", "maintainability": "string", "missing_files": ["string"]}
}
Only return the JSON object. Do not add explanations or markdown formatting. When des
""";

            // Call Gemini with both the prompt and the repo data
            GenerateContentResponse response = client.models
                    .generateContent("gemini-2.5-flash", instructionPrompt +"Multiple repo Data: "+ repoDataJson,null);

            // ✅ Parse into a list of RepoAnalysisDto
            List<RepoAnalysisDto> dtoList = objectMapper.readValue(
                    response.text(),
                    new TypeReference<List<RepoAnalysisDto>>() {}
            );
            return dtoList;

        } catch (Exception e) {
            throw new RuntimeException("Error generating Gemini content", e);
        }
    }
}
