package cn.fulgens.test;

import cn.fulgens.entity.User;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TestRestTemplate {

    @Test
    public void testGet() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/user/{id}";
        String id = "0d7dfd1d3d2d420188f70edfc305b947";
        User user = restTemplate.getForObject(url, User.class, id);
        System.out.println(user);

        ResponseEntity<User> responseEntity = restTemplate.getForEntity(url, User.class, id);
        System.out.println(responseEntity.getStatusCode().value());
        System.out.println(responseEntity.getHeaders().getAccept());
        System.out.println(responseEntity.getBody().getUsername());
    }

}
