package com.agms.zone_service.IOTClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class ExternalTokenManager {
    private String cachedToken;
    private String refreshToken;

    @Value("${external.api.username}") String username;
    @Value("${external.api.password}") String password;

    private final RestTemplate rest = new RestTemplate();

    public synchronized String getValidToken() {
        if (cachedToken == null) {
            login();
        }
        return cachedToken;
    }

    private void login() {
        ResponseEntity<Map> loginResp = rest.postForEntity(
                "http://104.211.95.241:8080/api/auth/login",
                Map.of("username", username, "password", password),
                Map.class
        );
        Map body = loginResp.getBody();
        cachedToken = (String) body.get("accessToken");
        refreshToken = (String) body.get("refreshToken");
    }

    @Scheduled(fixedDelay = 3500000) // refresh before expiry (1h)
    public void refreshToken() {
        ResponseEntity<Map> refreshResp = rest.postForEntity(
                "http://104.211.95.241:8080/api/auth/refresh",
                Map.of("refreshToken", refreshToken),
                Map.class
        );
        cachedToken = (String) refreshResp.getBody().get("accessToken");
    }
}