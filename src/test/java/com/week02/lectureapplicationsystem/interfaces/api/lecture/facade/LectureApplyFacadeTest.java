package com.week02.lectureapplicationsystem.interfaces.api.lecture.facade;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureCommandRepository;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureApplyRequest;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureApplyResponse;
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
class LectureApplyFacadeTest {

    @Autowired
    private LectureApplyFacade lectureApplyFacade;

    @Autowired
    private LectureCommandRepository lectureCommandRepository;

    LocalDateTime startTime;
    LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.now().plusHours(3).withSecond(0).withNano(0);
        endTime = LocalDateTime.now().plusHours(3).plusMinutes(30).withSecond(0).withNano(0);

        Lecture lecture1 = Lecture.builder()
                .title("강의1")
                .speaker("강연자A")
                .description("설명1")
                .capacity(0)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        Lecture lecture2 = Lecture.builder()
                .title("강의2")
                .speaker("강연자B")
                .description("설명2")
                .capacity(0)
                .startTime(LocalDateTime.now().plusHours(4).minusMinutes(30))
                .endTime(LocalDateTime.now().plusHours(4))
                .build();
        lectureCommandRepository.saveLecture(lecture1);
        lectureCommandRepository.saveLecture(lecture2);
    }

    @DisplayName("강의 신청에 성공한다.")
    @Test
    void applyForLecture_thenSuccessful() {
        // given
        LectureApplyRequest request = new LectureApplyRequest(1L, "test1234");

        // when
        LectureApplyResponse response = lectureApplyFacade.applyForLecture(request);

        //then
        assertThat(response).extracting("id", "memberId")
                .containsExactly(1L, "test1234");
        assertThat(response.lecture())
                .extracting("id", "title", "capacity")
                .containsExactly(1L, "강의1", 1);
    }

    @DisplayName("신청된 강의 중 특정 강의 조회에 성공한다.")
    @Test
    void findAppliedLecturesByLectureId_thenSuccessful() {
        // given
        Long lectureId = 1L;
        lectureApplyFacade.applyForLecture(new LectureApplyRequest(lectureId, "test1234"));
        lectureApplyFacade.applyForLecture(new LectureApplyRequest(lectureId, "qwer1234"));

        // when
        List<LectureApplyResponse> responses = lectureApplyFacade.findAppliedLecturesByLectureId(lectureId);

        //then
        assertThat(responses).hasSize(2)
                .extracting("id", "memberId")
                .containsExactly(
                        tuple(1L, "test1234"),
                        tuple(2L, "qwer1234")
                );
        assertThat(responses.get(0))
                .extracting("lecture")
                .extracting("id", "title", "capacity")
                .containsExactly(1L, "강의1", 2);
    }

    @DisplayName("특정 회원의 신청 완료된 강의 목록 조회에 성공한다.")
    @Test
    @Transactional
    void findAppliedLecturesByMemberId_thenSuccessful() {
        // given
        String memberId = "test1234";
        lectureApplyFacade.applyForLecture(new LectureApplyRequest(1L, memberId));
        lectureApplyFacade.applyForLecture(new LectureApplyRequest(2L, memberId));

        // when
        List<LectureApplyResponse> responses = lectureApplyFacade.findAppliedLecturesByMemberId(memberId);

        //then
        assertThat(responses).hasSize(2)
                .extracting("id", "memberId")
                .containsExactly(
                        tuple(1L, "test1234"),
                        tuple(2L, "test1234")
                );
        assertThat(responses)
                .extracting("lecture").hasSize(2)
                .extracting("id", "title", "capacity")
                .containsExactly(
                        tuple(1L, "강의1", 1),
                        tuple(2L, "강의2", 1)
                );
    }

}