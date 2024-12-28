package com.week02.lectureapplicationsystem.infrastructure.lecture;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.LectureApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LectureApplicationJpaRepository extends JpaRepository<LectureApplication, Long> {

    @Query("select l from LectureApplication l where l.lecture.id = :lectureId")
    List<LectureApplication> findByLectureId(@Param("lectureId") Long lectureId);

    List<LectureApplication> findByMemberId(String memberId);

    boolean existsByLectureIdAndMemberId(Long lectureId, String memberId);

}
