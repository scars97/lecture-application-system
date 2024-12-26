package com.week02.lectureapplicationsystem.business.domain.lecture.service;

import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureApplyInfo;
import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureApplyResult;
import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;
import com.week02.lectureapplicationsystem.business.domain.lecture.entity.LectureApplication;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureApplicationRepository;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureCommandRepository;
import com.week02.lectureapplicationsystem.business.validation.LectureApplyValidator;
import com.week02.lectureapplicationsystem.business.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 강의 신청, 신청 목록 조회
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureApplyService {

    private final LectureCommandRepository lectureCommandRepository;
    private final LectureApplicationRepository applicationRepository;
    private final Validator<LectureApplyInfo, Lecture> applyValidator;

    /**
     * 강의 신청
     * @param lectureApplyInfo
     */
    @Transactional
    public LectureApplyResult applyForLecture(LectureApplyInfo lectureApplyInfo) {
        Lecture lecture = lectureCommandRepository.findSingleLectureWithLock(lectureApplyInfo.lectureId());

        applyValidator.validate(lectureApplyInfo, lecture);

        lecture.incrementCapacity();

        LectureApplication lectureApplication = lectureApplyInfo.toEntity();
        lectureApplication.addLecture(lecture);

        return LectureApplyResult.of(applicationRepository.saveApplyLecture(lectureApplication));
    }

    /**
     * 특정 강의 신청 목록 조회
     * @param lectureId
     * @return
     */
    public List<LectureApplyResult> findAppliedLecturesByLectureId(Long lectureId) {
        List<LectureApplication> appliedLectures = applicationRepository.getAppliedLecturesByLectureId(lectureId);

        return appliedLectures.stream()
                .map(LectureApplyResult::of)
                .toList();
    }

    /**
     * 특정 회원이 신청한 강의 목록 조회
     * @param memberId
     * @return
     */
    public List<LectureApplyResult> findAppliedLecturesByMemberId(String memberId) {
        List<LectureApplication> appliedLectures = applicationRepository.getAppliedLecturesByMemberId(memberId);

        return appliedLectures.stream()
                .map(LectureApplyResult::of)
                .toList();
    }
}
