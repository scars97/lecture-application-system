package com.week02.lectureapplicationsystem.business.domain.lecture.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class LectureTest {

    LocalDateTime startTime;
    LocalDateTime endTime;
    Lecture lecture;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.now().withSecond(0).withNano(0);
        endTime = LocalDateTime.now().plusMinutes(30).withSecond(0).withNano(0);
    }

    @DisplayName("강의 생성 테스트")
    @Test
    void createLecture() {
        // given
        lecture = Lecture.builder()
                .title("강의1")
                .speaker("강연자A")
                .description("이런저런 내용을 설명하려해.")
                .capacity(0)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        // when //then
        assertThat(lecture)
            .extracting(
                "title", "speaker", "description", "capacity", "startTime", "endTime")
            .containsExactly(
                "강의1", "강연자A", "이런저런 내용을 설명하려해.", 0, startTime, endTime
            );
    }

    @DisplayName("강의 정원을 초과한 경우 true를 반환한다.")
    @Test
    void overCapacity_thenReturnTrue() {
        // given
        lecture = Lecture.builder()
                .title("강의1")
                .speaker("강연자A")
                .description("이런저런 내용을 설명하려해.")
                .capacity(30)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        // when
        boolean result = lecture.isOverCapacity();

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("진행 중이거나 종료된 강의인 경우 true를 반환한다.")
    @Test
    void onGoingOrEnd_thenReturnTrue() {
        // given
        LocalDateTime now = LocalDateTime.now();
        lecture = Lecture.builder()
                .title("강의1")
                .speaker("강연자A")
                .description("이런저런 내용을 설명하려해.")
                .capacity(30)
                .startTime(LocalDateTime.now().minusHours(1))
                .endTime(LocalDateTime.now().minusHours(2))
                .build();

        // when
        boolean result = lecture.isOnGoingOrEnded(now);

        //then
        assertThat(result).isTrue();
    }

}