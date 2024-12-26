package com.week02.lectureapplicationsystem.infrastructure.lecture;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureCommandJpaRepository extends JpaRepository<Lecture, Long>  {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select l from Lecture l where l.id = :lectureId")
    Optional<Lecture> findByIdWithLock(@Param("lectureId") Long lectureId);

    @Query("select l from Lecture l where l.capacity < 30 and l.startTime > CURRENT_TIMESTAMP")
    List<Lecture> getAvailableLectures();

    boolean existsByTitle(String title);
}
