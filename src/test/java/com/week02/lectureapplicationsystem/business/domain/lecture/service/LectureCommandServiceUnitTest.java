package com.week02.lectureapplicationsystem.business.domain.lecture.service;

import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureRegisterInfo;
import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureResult;
import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureCommandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class LectureCommandServiceUnitTest {

    @InjectMocks
    private LectureCommandService lectureCommandService;

    @Mock
    private LectureCommandRepository lectureCommandRepository;

    private Lecture availableLecture;
    private LectureRegisterInfo lectureRegisterInfo;

    @BeforeEach
    void setUp() {
        availableLecture = new Lecture(
                1L,
                "강의1",
                "강연자A",
                "이런저런 내용을 설명하려해.",
                0,
                LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusHours(3).plusMinutes(30)
        );

        lectureRegisterInfo = new LectureRegisterInfo(
                "강의제목",
                "강연자",
                "강의 설명",
                LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusHours(3).plusMinutes(30));
    }

    @DisplayName("강의 저장에 성공한다.")
    @Test
    void saveLecture() {
        // given
        when(lectureCommandRepository.saveLecture(any(Lecture.class))).thenReturn(availableLecture);

        // when
        LectureResult lectureResult = lectureCommandService.saveLecture(lectureRegisterInfo);

        //then
        assertThat(lectureResult.id()).isOne();
        verify(lectureCommandRepository, times(1)).saveLecture(any(Lecture.class));
    }

    @DisplayName("강의 제목 중복 시 예외가 발생한다.")
    @Test
    void duplicateTitle_thenThrowException() {
        // given
        LectureRegisterInfo duplicateLecture = new LectureRegisterInfo(
                "강의제목",
                "강연자",
                "강의 설명",
                LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusHours(3).plusMinutes(30));

        when(lectureCommandRepository.existsByTitle(duplicateLecture.title())).thenReturn(true);

        // when //then
        assertThatThrownBy(() -> lectureCommandService.saveLecture(duplicateLecture))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("강의 제목은 중복될 수 없습니다.");
    }

    @DisplayName("등록된 강의 목록이 조회된다.")
    @Test
    void getAvailableLectures() {
        // given
        when(lectureCommandRepository.getAvailableLectures()).thenReturn(List.of(availableLecture));

        // when
        List<LectureResult> lectureResults = lectureCommandService.getAvailableLectures();

        //then
        assertThat(lectureResults).isNotEmpty();
        assertThat(lectureResults).hasSize(1);
        verify(lectureCommandRepository, times(1)).getAvailableLectures();
    }

}