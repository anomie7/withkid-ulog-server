package tk.withkid.userlog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tk.withkid.userlog.domain.EventLog;
import tk.withkid.userlog.dto.EventDto;
import tk.withkid.userlog.dto.Quration;
import tk.withkid.userlog.service.EventLogService;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EventLogController.class)
public class EventLogControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventLogService eventLogService;

    @Test
    public void getEvents() throws Exception {
        String accessToken = "ddss";
        Quration content = new Quration("mock quration", Collections.singletonList(EventDto.builder().eventId(1L).name("name").build()));
        given(eventLogService.getEvents(accessToken)).willReturn(content);

        MvcResult result = mvc.perform(get("/eventLog").header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andDo(print()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        Quration quration = mapper.readValue(result.getResponse().getContentAsString(), Quration.class);
        Assert.assertEquals(content.getClass(), quration.getClass());
    }

    @Test
    public void setEventhLog() throws Exception {
        String accessToken = "ddss";
        String updateTime = "2019-03-11T05:58:39.451161Z";
        EventLog eventLog = EventLog.builder().eventId(1L).build();
        given(eventLogService.saveEventLog(accessToken, eventLog)).willReturn(updateTime);

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(eventLog);

        mvc.perform(post("/eventLog").header("Authorization", accessToken).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andDo(print());
    }
}