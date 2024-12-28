package com.week02.lectureapplicationsystem.interfaces.api.lecture.facade;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureCommandRepository;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureRegisterRequest;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Sql("classpath:tableInit.sql")
class LectureCommandFacadeTest {

    @Autowired
    private LectureCommandFacade lectureCommandFacade;

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
                .startTime(LocalDateTime.now().minusHours(3).minusMinutes(30))
                .endTime(LocalDateTime.now().minusHours(3))
                .build();
        lectureCommandRepository.saveLecture(lecture1);
        lectureCommandRepository.saveLecture(lecture2);
    }

    @DisplayName("강의 등록에 성공한다.")
    @Test
    void createLecture_thenSuccessful() {
        // given
        LectureRegisterRequest request = new LectureRegisterRequest(
                "강의3",
                "강연자C",
                "설명3",
                startTime,
                endTime
        );

        // when
        LectureResponse response = lectureCommandFacade.createLecture(request);

        //then
        assertThat(response)
                .extracting("id", "title", "speaker", "description", "startTime", "endTime", "capacity")
                .containsExactly(3L, "강의3", "강연자C", "설명3", startTime, endTime, 0);
    }

    @DisplayName("단일 강의 조회에 성공한다.")
    @Test
    void findSingleLecture_thenSuccessful() {
        // given
        Long lectureId = 1L;

        // when
        LectureResponse response = lectureCommandFacade.findSingleLecture(lectureId);

        //then
        assertThat(response)
                .extracting("id", "title", "speaker", "description", "startTime", "endTime", "capacity")
                .containsExactly(1L, "강의1", "강연자A", "설명1", startTime, endTime, 0);
    }

    @DisplayName("신청 가능한 강의 목록 조회에 성공한다.")
    @Test
    void getAvailableLectures_thenSuccessful() {
        // given

        // when
        List<LectureResponse> responses = lectureCommandFacade.getAvailableLectures();

        //then
        assertThat(responses).hasSize(1)
                .extracting("id", "title", "speaker", "description", "startTime", "endTime", "capacity")
                .containsExactly(
                        tuple(1L, "강의1", "강연자A", "설명1", startTime, endTime, 0)
                );
    }

}