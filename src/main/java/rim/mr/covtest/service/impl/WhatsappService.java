package rim.mr.covtest.service.impl;

import java.util.Arrays;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rim.mr.covtest.service.dto.WhatsAppMessageResponse;

@Service
public class WhatsappService {
    private final String END_POINT = "https://app-server.wati.io";
    private final RestTemplate restTemplate;

    public WhatsappService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendMessageTo(String numberPhone, String message) {
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI2Y2M5NDAwMy0zNzBmLTQ3MzgtODQ1My0xYTZhYzdkNzg2YmQiLCJ1bmlxdWVfbmFtZSI6ImZvZGNhbXVAZ21haWwuY29tIiwibmFtZWlkIjoiZm9kY2FtdUBnbWFpbC5jb20iLCJlbWFpbCI6ImZvZGNhbXVAZ21haWwuY29tIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiVFJJQUwiLCJleHAiOjE2MTEyNzM2MDAsImlzcyI6IkNsYXJlX0FJIiwiYXVkIjoiQ2xhcmVfQUkifQ.9IfGYzfJ7fkh8o7Ei7a-seUaD5rSd5Q6bhNWXjqg07U"
        );
        headers.add(
            "user-agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36"
        );

        HttpEntity<Void> entity = new HttpEntity<Void>(headers);
        String url = END_POINT + "/api/v1/sendSessionMessage/" + numberPhone + "?messageText=" + message;
        HttpEntity<WhatsAppMessageResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, WhatsAppMessageResponse.class);

        System.out.println("is send or not " + response.getBody().isOk());
    }
}
