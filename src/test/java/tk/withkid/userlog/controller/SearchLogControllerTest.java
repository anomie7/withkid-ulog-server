package tk.withkid.userlog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.HeaderResultMatchers;
import tk.withkid.userlog.domain.SearchLog;
import tk.withkid.userlog.repository.FIrestoreRepository;
import tk.withkid.userlog.service.SearchLogService;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SearchLogController.class)
public class SearchLogControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private SearchLogService searchLogService;

    @Test
    public void testSetSearchLog() throws Exception {
        String accessToken = "ddss";
        String updateTime = "2019-03-11T05:58:39.451161Z";
        SearchLog searchLog = SearchLog.builder().category("Mu").region("서울").build();

        given(searchLogService.saveSearchLog(accessToken, searchLog)).willReturn(updateTime);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(searchLog);

        mvc.perform(post("/searchLog").header("Authorization", accessToken).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andDo(print());
    }
}