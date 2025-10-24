package david.git_projects_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, Object>> handleApiExceptions(ApiException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("error", "API Error");
        error.put("message", ex.getMessage());
        error.put("status", ex.getStatus());
        error.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    @ExceptionHandler(InvalidApiKeyException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidApiKey(InvalidApiKeyException ex) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("error", "Invalid API key");
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
