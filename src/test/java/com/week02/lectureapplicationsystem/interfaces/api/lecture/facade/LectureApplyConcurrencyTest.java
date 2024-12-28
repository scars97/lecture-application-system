package com.week02.lectureapplicationsystem.interfaces.api.lecture.facade;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureApplicationRepository;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureCommandRepository;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureApplyRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Sql("classpath:tableInit.sql")
public class LectureApplyConcurrencyTest {

    @Autowired
    private LectureApplyFacade lectureApplyFacade;

    @Autowired
    private LectureCommandRepository lectureCommandRepository;
    @Autowired
    private LectureApplicationRepository applicationRepository;

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
    }

    @DisplayName("40명이 동시에 강의 신청 시, 30명까지 성공하고 나머지 10명은 실패해야 한다.")
    @Test
    //@Transactional
    void shouldAcceptUpTo30Applications_when40ApplyConcurrently() throws InterruptedException {
        // given
        Long lectureId = 1L;
        int capacity = 30;
        int totalMembers = 40;

        ExecutorService executor = Executors.newFixedThreadPool(totalMembers);
        CountDownLatch latch = new CountDownLatch(totalMembers);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        // when
        for (int i = 0; i < totalMembers; i++) {
            String memberId = "test" + (i + 1);

            executor.submit(() -> {
                try {
                    lectureApplyFacade.applyForLecture(new LectureApplyRequest(lectureId, memberId));
                    successCount.incrementAndGet();
                } catch (IllegalArgumentException e) {
                    failureCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        //then
        assertThat(successCount.get()).isEqualTo(capacity);
        assertThat(failureCount.get()).isEqualTo(totalMembers - capacity);
    }
}
