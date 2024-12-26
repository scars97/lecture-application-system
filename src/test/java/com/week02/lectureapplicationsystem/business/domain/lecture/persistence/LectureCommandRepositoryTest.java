package com.week02.lectureapplicationsystem.business.domain.lecture.persistence;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;
import com.week02.lectureapplicationsystem.infrastructure.lecture.impl.LectureCommandCustomRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Sql("classpath:tableInit.sql")
class LectureCommandRepositoryTest {

    @Autowired
    private LectureCommandRepository lectureCommandRepository;

    @BeforeEach
    void setUp() {
        Lecture lecture1 = Lecture.builder()
                .title("강의1")
                .speaker("강연자A")
                .description("이런저런 내용을 설명하려해1.")
                .capacity(0)
                .startTime(LocalDateTime.now().plusHours(3))
                .endTime(LocalDateTime.now().plusHours(3).plusMinutes(30))
                .build();
        Lecture lecture2 = Lecture.builder()
                .title("신청 불가능한 강의")
                .speaker("강연자B")
                .description("이런저런 내용을 설명하려해2.")
                .capacity(0)
                .startTime(LocalDateTime.now().minusHours(3).minusMinutes(30))
                .endTime(LocalDateTime.now().minusHours(3))
                .build();

        lectureCommandRepository.saveLecture(lecture1);
        lectureCommandRepository.saveLecture(lecture2);
    }

    @DisplayName("신청 가능한 강의 목록이 조회된다.")
    @Test
    void getAvailableLectures() {
        // when
        List<Lecture> availableLectures = lectureCommandRepository.getAvailableLectures();

        //then
        assertThat(availableLectures).hasSize(1)
            .extracting("id", "title", "speaker")
            .containsExactly(
                    tuple(1L, "강의1", "강연자A")
            );
    }

    @DisplayName("등록되지 않은 강의 조회 시 예외가 발생한다.")
    @Test
    @Transactional
    void findNotRegisteredLecture_thenThrowException() {
        // given
        Long invalidLectureId = 99L;

        // when //then
        assertThatThrownBy(() -> lectureCommandRepository.findSingleLectureWithLock(invalidLectureId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("등록되지 않은 강의입니다.");
    }

    @DisplayName("중복된 강의 제목이 존재하는 경우 true를 반환한다.")
    @Test
    void existsByTitle_thenTrue() {
        // given
        String duplicateTitle = "강의1";

        // when
        boolean result = lectureCommandRepository.existsByTitle(duplicateTitle);

        //then
        assertThat(result).isTrue();
    }
}