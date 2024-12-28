package com.week02.lectureapplicationsystem.interfaces.api.lecture.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureRegisterRequest;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureResponse;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.facade.LectureCommandFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LectureCommandController.class)
class LectureCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LectureCommandFacade lectureCommandFacade;

    LocalDateTime startTime;
    LocalDateTime endTime;
    LectureResponse lectureResponse;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.now().plusHours(3).withSecond(0).withNano(0);
        endTime = LocalDateTime.now().plusHours(3).plusMinutes(30).withSecond(0).withNano(0);

        lectureResponse = new LectureResponse(
                1L,
                "강의1",
                "강연자A",
                "설명1",
                startTime,
                endTime,
                30
        );
    }

    @DisplayName("강의 등록에 성공한다.")
    @Test
    void request_createLecture() throws Exception {
        // given
        LectureRegisterRequest request = new LectureRegisterRequest(
                "강의1",
                "강연자A",
                "설명1",
                startTime,
                endTime
        );

        when(lectureCommandFacade.createLecture(request)).thenReturn(lectureResponse);

        // when //then
        mockMvc.perform(post("/api/lectures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("강의 조회에 성공한다.")
    @Test
    void request_findSingleLecture() throws Exception {
        // given
        Long id = 1L;

        when(lectureCommandFacade.findSingleLecture(id)).thenReturn(lectureResponse);

        // when //then
        mockMvc.perform(get("/api/lectures/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}