package tk.withkid.userlog.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.withkid.userlog.exception.AuthorizationUnavailableException;

import java.lang.reflect.UndeclaredThrowableException;

import static org.junit.Assert.assertTrue;
@RunWith(SpringRunner.class)
@SpringBootTest
//@Ignore
public class AuthServiceTest {
    @Autowired
    private AuthService authService;
    private String accessTkn = "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNTU5MjMyODExMjc0LCJ0eXBlIjoiYWNjZXNzLXRva2VuIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VyLXR5cGUiOiJFIiwidXNlci1pZCI6MSwiZXhwIjoxNTU5MjM2NDExLCJlbWFpbCI6InRlc3QifQ.ELskK5tqxQHmWrhxkxV5A6TWKMGe_SAdvqC2lv9VOHI";

    @Test
    public void getUserId() throws AuthorizationUnavailableException {
        Long userId = this.authService.getUserId(accessTkn);

        assertTrue(userId.equals(1L));
    }

    @Test(expected = UndeclaredThrowableException.class)
    public void getUserIdExpectException() {
        accessTkn = "asdf";
        Long userId = this.authService.getUserId(accessTkn);

        assertTrue(userId.equals(1L));
    }
}