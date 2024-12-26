package com.week02.lectureapplicationsystem.business.domain.lecture.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class LectureApplicationTest {

    LocalDateTime startTime;
    LocalDateTime endTime;
    Lecture lecture;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.now().withSecond(0).withNano(0);
        endTime = LocalDateTime.now().plusMinutes(30).withSecond(0).withNano(0);

        lecture = Lecture.builder()
                .title("강의1")
                .speaker("강연자A")
                .description("이런저런 내용을 설명하려해.")
                .capacity(0)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    @DisplayName("신청 강의 생성")
    @Test
    void createLectureApplication() {
        // given
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        LectureApplication lectureApplication = LectureApplication.builder()
                .memberId("test1234")
                .appliedAt(now)
                .build();
        lectureApplication.addLecture(lecture);

        // when //then
        assertThat(lectureApplication)
                .extracting("memberId", "appliedAt")
                .containsExactly("test1234", now);
        assertThat(lectureApplication.getLecture()).isEqualTo(lecture);
    }

}