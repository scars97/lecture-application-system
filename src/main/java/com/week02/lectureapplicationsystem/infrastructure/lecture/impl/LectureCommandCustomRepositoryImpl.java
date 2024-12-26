package com.week02.lectureapplicationsystem.infrastructure.lecture.impl;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureCommandRepository;
import com.week02.lectureapplicationsystem.infrastructure.lecture.LectureCommandJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class LectureCommandCustomRepositoryImpl implements LectureCommandRepository {

    private final LectureCommandJpaRepository jpaRepository;

    @Override
    public Lecture saveLecture(Lecture lecture) {
        return jpaRepository.save(lecture);
    }

    @Override
    public Lecture findSingleLectureWithLock(Long lectureId) {
        return jpaRepository.findByIdWithLock(lectureId).orElseThrow(() -> new NoSuchElementException("등록되지 않은 강의입니다."));
    }

    @Override
    public List<Lecture> getAvailableLectures() {
        return jpaRepository.getAvailableLectures();
    }

    @Override
    public boolean existsByTitle(String title) {
        return jpaRepository.existsByTitle(title);
    }
}
