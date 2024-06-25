package com.evri.interview;

import com.evri.interview.model.Courier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/couriers";
    }

    @Test
    void getAll() {
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(baseUrl, List.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    void getActive() {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("isActive", true)
                .toUriString();
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        Map courier = (Map) responseEntity.getBody().get(0);
        assertEquals("Ben Askew", courier.get("name"));
        assertEquals(true, courier.get("active"));
    }

    @Test
    @DirtiesContext
    void putPresent() {
        Courier foobar = Courier.builder()
                .name("Foo Bar")
                .active(true)
                .build();
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("courierId", 2)
                .toUriString();

        HttpEntity<Courier> httpEntity = new HttpEntity<>(foobar);
        ResponseEntity<Courier> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Courier.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void putAbsent() {
        Courier foobar = Courier.builder()
                .name("Foo Bar")
                .active(true)
                .build();
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("courierId", 100)
                .toUriString();

        HttpEntity<Courier> httpEntity = new HttpEntity<>(foobar);
        ResponseEntity<Courier> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Courier.class);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

}
