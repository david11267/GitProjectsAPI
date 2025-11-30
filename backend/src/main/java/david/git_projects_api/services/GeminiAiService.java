package david.git_projects_api.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.genai.types.*;
import com.google.genai.Client;
import com.google.genai.types.Schema;
import david.git_projects_api.dtos.RepoSummaryCollection;
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

    public RepoSummaryCollection generateContent(ArrayList<ObjectNode> rawJson, String aiModel) {
        try {
            // Convert list of ObjectNodes into a JSON array string
            String repoDataJson = objectMapper.writeValueAsString(rawJson);



            String instructionPrompt = """
You analyze GitHub repositories and produce portfolio-quality project descriptions plus a complete technical summary.

Input:
A JSON array describing GitHub repositories. Each repository may include: repo, description, languages, tree, files, and readme.

Goal:
Produce a concise, compelling portfolio-style description for each project and fill every field of the existing schema I provide separately. Output must strictly follow the schema I send with the request.

Portfolio text:
- "name": just display the Github project name.
- "description": 1–5 sentence high-quality description (clear, confident, professional; no filler).

Technical inference:
Identify technologies, frameworks, build tools, architecture patterns, runtimes, dependencies, integrations, and notable files. Infer from:
- folder structure
- filenames + extensions
- manifest files (package.json, pom.xml, build.gradle, go.mod, requirements.txt, etc.)
- CI configs
- Dockerfiles
- README content
If information is missing, infer intelligently from project name or filenames and structure.

Icon rules:
Always return a valid https://cdn.jsdelivr.net icon URL.
Priority:
1. Devicon: https://cdn.jsdelivr.net/gh/devicons/devicon/icons/{name}/{name}-original.svg  
2. Simple-icons: https://cdn.jsdelivr.net/npm/simple-icons@v8/icons/{name}.svg  
3. Fallback generic code icon from jsDelivr.
Never leave iconUrl empty.

Strict rules:
- Respond ONLY with valid JSON.
- Start with "{" and end with "}".
- Inside each string response 
- No markdown, no backticks, no prose, no wrapping, no comments.
- No null values anywhere. Use "unknown" or empty arrays as appropriate.
- Only include the fields defined in the schema I supplied via the request.
- Maintain consistent casing and naming.
- Keep text compact but high quality.

Repository data:
%s
""".formatted(repoDataJson);


            Schema schema = RepoSummaryCollection.getRepoSummaryCollectionSchema();
            GenerateContentResponse response = client.models
                    .generateContent(aiModel, instructionPrompt, GenerateContentConfig.builder()
                            .responseMimeType("application/json")
                            .responseSchema(schema)
                            .temperature(0.05f)
                            .topK(20f)
                            .topP(0.8f)
                            .candidateCount(1)
                            .maxOutputTokens(1048576)
                            .build()
                    );

            String parsedJson = response.text()
                    .trim()
                    .replaceAll("^```json|^```|```$", "")
                    .replaceAll("^[`]+|[`]+$", "")
                    .trim();

            RepoSummaryCollection dtoList = objectMapper.readValue(
                    parsedJson, RepoSummaryCollection.class
            );

            return dtoList;

        } catch (Exception e) {
            throw new ApiException("Gemini error: "+e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
