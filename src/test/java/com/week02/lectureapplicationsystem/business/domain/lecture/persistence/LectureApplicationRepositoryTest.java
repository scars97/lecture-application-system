package com.week02.lectureapplicationsystem.business.domain.lecture.persistence;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;
import com.week02.lectureapplicationsystem.business.domain.lecture.entity.LectureApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Sql("classpath:tableInit.sql")
class LectureApplicationRepositoryTest {

    @Autowired
    private LectureCommandRepository lectureCommandRepository;
    @Autowired
    private LectureApplicationRepository lectureApplicationRepository;

    @BeforeEach
    void setUp() {
        Lecture lecture = Lecture.builder()
                    .title("강의1")
                    .speaker("강연자A")
                    .description("이런저런 내용을 설명하려해1.")
                    .capacity(0)
                    .startTime(LocalDateTime.now().plusHours(3))
                    .endTime(LocalDateTime.now().plusHours(3).plusMinutes(30))
                    .build();
        lectureCommandRepository.saveLecture(lecture);

        LectureApplication lectureApplication1 = LectureApplication.builder()
                .memberId("신청자A")
                .appliedAt(LocalDateTime.now().minusDays(2))
                .build();
        LectureApplication lectureApplication2 = LectureApplication.builder()
                .memberId("신청자B")
                .appliedAt(LocalDateTime.now().minusHours(4))
                .build();
        LectureApplication lectureApplication3 = LectureApplication.builder()
                .memberId("신청자C")
                .appliedAt(LocalDateTime.now().minusMinutes(20))
                .build();
        lectureApplication1.addLecture(lecture);
        lectureApplication2.addLecture(lecture);
        lectureApplication3.addLecture(lecture);
        lectureApplicationRepository.saveApplyLecture(lectureApplication1);
        lectureApplicationRepository.saveApplyLecture(lectureApplication2);
        lectureApplicationRepository.saveApplyLecture(lectureApplication3);
    }

    @DisplayName("신청 완료된 강의 중 특정 강의에 대한 목록이 조회된다.")
    @Test
    @Transactional
    void getAppliedLecturesByLectureId() {
        // given
        Long lectureId = 1L;

        // when
        List<LectureApplication> appliedLecturesByLectureId = lectureApplicationRepository.getAppliedLecturesByLectureId(lectureId);

        //then
        assertThat(appliedLecturesByLectureId).hasSize(3)
                .extracting("id", "memberId")
                .containsExactly(
                        tuple(1L, "신청자A"),
                        tuple(2L, "신청자B"),
                        tuple(3L, "신청자C")
                );
        assertThat(appliedLecturesByLectureId.get(0))
                .extracting("lecture")
                .extracting("id", "title", "speaker")
                .containsExactly(1L, "강의1", "강연자A");
    }

    @DisplayName("특정 회원이 신청한 강의 목록이 조회된다.")
    @Test
    @Transactional
    void getAppliedLecturesByMemberId() {
        // given
        String memberId = "신청자A";

        // when
        List<LectureApplication> appliedLecturesByMemberId = lectureApplicationRepository.getAppliedLecturesByMemberId(memberId);

        //then
        assertThat(appliedLecturesByMemberId).hasSize(1)
                .extracting("id", "memberId")
                .containsExactly(
                        tuple(1L, "신청자A")
                );
        assertThat(appliedLecturesByMemberId.get(0).getLecture())
                .extracting("id", "title", "speaker")
                .containsExactly(1L, "강의1", "강연자A");
    }

    @DisplayName("동일한 강의에 대해 이미 신청한 경우 true를 반환한다.")
    @Test
    void existsByLectureIdAndMemberId_thenTrue() {
        // given
        Long lectureId = 1L;
        String memberId = "신청자A";

        // when
        boolean result = lectureApplicationRepository.existsByLectureIdAndMemberId(lectureId, memberId);

        //then
        assertThat(result).isTrue();
    }
}