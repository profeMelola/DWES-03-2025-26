package es.daw.productoapirest.dto;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {
    private boolean sucess;
    private String message;
    private Map<String, Object> details = new HashMap<>();

    public ApiResponse(boolean sucess, String message) {
        this.sucess = sucess;
        this.message = message;
    }

    public void addDetail(String key, Object value) {
        details.put(key, value);
    }
}
