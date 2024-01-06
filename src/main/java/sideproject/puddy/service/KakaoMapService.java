package sideproject.puddy.service;

import org.springframework.beans.factory.annotation.Value;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import sideproject.puddy.dto.kakao.Coordinates;

@Service
public class KakaoMapService {
    @Value("${kakao.url}")
    private String uri;
    @Value("${kakao.api.key}")
    private String kakaoLocalKey;
    public Coordinates getCoordinate(String address){
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
        JSONArray documents = json.getJSONArray("documents");
        Double lat = documents.getJSONObject(0).getDouble("y");
        Double lng = documents.getJSONObject(0).getDouble("x");
        return new Coordinates(lat, lng);
    }
}
