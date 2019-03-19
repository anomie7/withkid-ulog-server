package tk.withkid.userlog.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class AuthServiceTest {
    @Autowired
    private AuthService authService;
    private String accessTkn = "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNTUyMzkwOTQ3NTE5LCJ0eXBlIjoiYWNjZXNzLXRva2VuIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyLWlkIjo1LCJleHAiOjE1ODQwMTY5NDcsImVtYWlsIjoiZGVwcm9tZWV0QHRyYWN0NC5jb20ifQ.J2DqpOYDEXfM-d49X2-G3qSdROZLKWmEgUxQOZMXp6w";

    @Test
    public void getUserId() {
        Long userId = this.authService.getUserId(accessTkn);

        assertTrue(userId.equals(5L));
    }
}