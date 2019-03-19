package tk.withkid.userlog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpStatusCodeException;
import tk.withkid.userlog.domain.SearchLog;
import tk.withkid.userlog.service.SearchLogService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchLogController.class)
public class SearchLogControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private SearchLogService searchLogService;

    @Test
    public void setSearchLogTest() throws Exception {
        String accessToken = "ddss";
        String updateTime = "2019-03-11T05:58:39.451161Z";
        SearchLog searchLog = SearchLog.builder().kindOf("Mu").region("서울").build();

        given(searchLogService.saveSearchLog(accessToken, searchLog)).willReturn(updateTime);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(searchLog);

        mvc.perform(post("/searchLog").header("Authorization", accessToken).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getmaxKeyBunchTest() throws Exception {
        String accessToken = "";
        Map<String, String> maxKeyBunch = new HashMap<>();
        maxKeyBunch.put("region", "서울");
        maxKeyBunch.put("kindOf", "Mu");
        given(searchLogService.getMaxSearchLogKeys(accessToken)).willReturn(maxKeyBunch);

        mvc.perform(get("/maxSearchLogKey").header("Authorization", accessToken).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}