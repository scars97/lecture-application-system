package com.week02.lectureapplicationsystem.interfaces.api.lecture.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureApplyRequest;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureApplyResponse;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureResponse;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.facade.LectureApplyFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LectureApplyController.class)
class LectureApplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LectureApplyFacade lectureApplyFacade;

    LectureApplyResponse lectureApplyResponse;

    @BeforeEach
    void setUp() {
        LocalDateTime startTime = LocalDateTime.now().plusHours(3).withSecond(0).withNano(0);
        LocalDateTime endTime = LocalDateTime.now().plusHours(3).plusMinutes(30).withSecond(0).withNano(0);
        LocalDateTime appliedAt = LocalDateTime.now();

        LectureResponse lectureResponse = new LectureResponse(
                1L,
                "강의1",
                "강연자A",
                "설명1",
                startTime,
                endTime,
                30
        );

        lectureApplyResponse = new LectureApplyResponse(
                1L,
                "test1234",
                appliedAt,
                lectureResponse
        );
    }

    @DisplayName("강의 신청에 성공한다.")
    @Test
    void request_applyForLecture() throws Exception {
        // given
        LectureApplyRequest request = new LectureApplyRequest(1L, "test1234");

        when(lectureApplyFacade.applyForLecture(request)).thenReturn(lectureApplyResponse);

        // when //then
        mockMvc.perform(post("/api/lecture-applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("신청된 강의 중 특정 강의 목록 조회에 성공한다.")
    @Test
    void request_findAppliedLecturesByLectureId() throws Exception {
        // given
        Long lectureId = 1L;

        when(lectureApplyFacade.findAppliedLecturesByLectureId(lectureId)).thenReturn(List.of(lectureApplyResponse));

        // when //then
        mockMvc.perform(get("/api/lecture-applications/{lectureId}", lectureId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("특정 회원의 신청 완료된 강의 목록 조회에 성공한다.")
    @Test
    void request_findAppliedLecturesByMemberId() throws Exception {
        // given
        String memberId = "test1234";

        when(lectureApplyFacade.findAppliedLecturesByMemberId(memberId)).thenReturn(List.of(lectureApplyResponse));

        // when //then
        mockMvc.perform(get("/api/lecture-applications/{memberId}/apply", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}