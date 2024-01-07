package sideproject.puddy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sideproject.puddy.dto.kakao.Coordinates;

@Slf4j
@Service
public class KakaoMapService {
    @Value("${kakao.url}")
    private String uri;
    @Value("${kakao.api.key}")
    private String kakaoLocalKey;
    public Coordinates getCoordinate(String address){
        JSONArray documents = getDocuments(address);
        Double lat = documents.getJSONObject(0).getDouble("y");
        Double lng = documents.getJSONObject(0).getDouble("x");
        return new Coordinates(lat, lng);
    }

    public boolean isValidMainAddress(String address) {
        JSONArray documents = getDocuments(address);
        return !documents.isEmpty();
    }

    public JSONArray getDocuments(String address) {
        String apiKey = "KakaoAK " + kakaoLocalKey;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(uri)
                .queryParam("query",address)
                .build();
        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, String.class);
        String body = response.getBody();
        JSONObject json = new JSONObject(body);
        return json.getJSONArray("documents");
    }
}
