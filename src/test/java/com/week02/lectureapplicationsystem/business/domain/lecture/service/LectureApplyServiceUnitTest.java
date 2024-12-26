package com.week02.lectureapplicationsystem.business.domain.lecture.service;

import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureApplyInfo;
import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureApplyResult;
import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;
import com.week02.lectureapplicationsystem.business.domain.lecture.entity.LectureApplication;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureApplicationRepository;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureCommandRepository;
import com.week02.lectureapplicationsystem.business.validation.LectureApplyValidator;
import com.week02.lectureapplicationsystem.business.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LectureApplyServiceUnitTest {

    @InjectMocks
    private LectureApplyService lectureApplyService;

    @Mock
    private LectureCommandRepository lectureCommandRepository;
    @Mock
    private LectureApplicationRepository applicationRepository;
    @Mock
    private Validator<LectureApplyInfo, Lecture> applyValidator;

    private Lecture lecture;
    private LectureApplication lectureApplication;
    private LectureApplyInfo applyInfo;
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDateTime.now();

        lecture = new Lecture(
                1L,
                "강의1",
                "강연자A",
                "이런저런 내용을 설명하려해.",
                0,
                LocalDateTime.now().plusHours(3),
                LocalDateTime.now().plusHours(3).plusMinutes(30)
        );

        lectureApplication = new LectureApplication(
                1L,
                lecture,
                "test1234",
                testDate);

        applyInfo = new LectureApplyInfo(1L, "test1234");
    }

    @DisplayName("강의 신청에 성공한다.")
    @Test
    void applyForLecture() {
        // given
        when(lectureCommandRepository.findSingleLectureWithLock(anyLong())).thenReturn(lecture);
        when(applicationRepository.saveApplyLecture(any(LectureApplication.class))).thenReturn(lectureApplication);

        // when
        LectureApplyResult applyResult = lectureApplyService.applyForLecture(applyInfo);

        //then
        assertThat(applyResult.id()).isEqualTo(1L);
        assertThat(applyResult.memberId()).isEqualTo("test1234");
        assertThat(applyResult.lecture().id()).isEqualTo(1L);
    }

    @DisplayName("특정 강의 신청 목록이 조회된다.")
    @Test
    void findAppliedLecturesByLectureId() {
        // given
        when(applicationRepository.getAppliedLecturesByLectureId(anyLong()))
            .thenReturn(List.of(lectureApplication));

        // when
        List<LectureApplyResult> results = lectureApplyService.findAppliedLecturesByLectureId(1L);

        //then
        assertThat(results).hasSize(1)
                .extracting("id", "memberId", "appliedAt")
                .containsExactly(
                        tuple(1L, "test1234", testDate)
                );
        assertThat(results.get(0).lecture())
                .extracting("id", "title", "speaker")
                .containsExactly(1L, "강의1", "강연자A");
    }

    @DisplayName("특정 회원이 신청한 강의 목록이 조회된다.")
    @Test
    void findAppliedLecturesByMemberId() {
        // given
        when(applicationRepository.getAppliedLecturesByMemberId(anyString()))
                .thenReturn(List.of(lectureApplication));

        // when
        List<LectureApplyResult> results = lectureApplyService.findAppliedLecturesByMemberId("test1234");

        //then
        assertThat(results).hasSize(1)
                .extracting("id", "memberId", "appliedAt")
                .containsExactly(
                        tuple(1L, "test1234", testDate)
                );
        assertThat(results.get(0).lecture())
                .extracting("id", "title", "speaker")
                .containsExactly(1L, "강의1", "강연자A");
    }
}