package com.educacional.schoolacademicperiods.utils;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class PerformRequest {

    public static ResultActions post(MockMvc mockMvc, String apiUrl, Object object) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(object)))
                .andDo(print());
    }

    public static ResultActions get(MockMvc mockMvc, String apiUrl) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());


    }

    public static ResultActions patch(MockMvc mockMvc, String apiUrl, Object object) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.patch(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(object)))
                .andDo(print());
    }

    public static ResultActions get(MockMvc mockMvc, String apiUrl, String uuid) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(apiUrl, uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());


    }

    public static ResultActions post(MockMvc mockMvc, String apiUrl, String body) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print());
    }

    public static ResultActions post(MockMvc mockMvc, String apiUrl, UUID uuid, Object object) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(apiUrl,uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(object)))
                .andDo(print());
    }

    public static ResultActions put(MockMvc mockMvc, String apiUrl, Object object) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonMapper.asJsonString(object)))
                .andDo(print());
    }

    public static ResultActions delete(MockMvc mockMvc, String apiUrl) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(apiUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
