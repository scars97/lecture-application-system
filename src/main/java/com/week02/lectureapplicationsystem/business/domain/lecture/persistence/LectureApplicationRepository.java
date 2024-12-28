package com.week02.lectureapplicationsystem.business.domain.lecture.persistence;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.LectureApplication;

import java.util.List;

public interface LectureApplicationRepository {

    LectureApplication saveApplyLecture(LectureApplication lectureApplication);

    List<LectureApplication> getAppliedLecturesByLectureId(Long lectureId);

    List<LectureApplication> getAppliedLecturesByMemberId(String memberId);

    boolean existsByLectureIdAndMemberId(Long lectureId, String memberId);
}
