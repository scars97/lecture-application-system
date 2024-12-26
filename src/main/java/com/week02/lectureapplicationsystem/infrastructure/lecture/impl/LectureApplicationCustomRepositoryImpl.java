package com.week02.lectureapplicationsystem.infrastructure.lecture.impl;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.LectureApplication;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureApplicationRepository;
import com.week02.lectureapplicationsystem.infrastructure.lecture.LectureApplicationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureApplicationCustomRepositoryImpl implements LectureApplicationRepository {

    private final LectureApplicationJpaRepository jpaRepository;

    @Override
    public LectureApplication saveApplyLecture(LectureApplication lectureApplication) {
        return jpaRepository.save(lectureApplication);
    }

    @Override
    public List<LectureApplication> getAppliedLecturesByLectureId(Long lectureId) {
        return jpaRepository.findByLectureId(lectureId);
    }

    @Override
    public List<LectureApplication> getAppliedLecturesByMemberId(String memberId) {
        return jpaRepository.findByMemberId(memberId);
    }

    @Override
    public boolean existsByLectureIdAndMemberId(Long lectureId, String memberId) {
        return jpaRepository.existsByLectureIdAndMemberId(lectureId, memberId);
    }
}
