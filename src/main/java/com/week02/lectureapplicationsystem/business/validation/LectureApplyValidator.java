package com.week02.lectureapplicationsystem.business.validation;

import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureApplyInfo;
import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LectureApplyValidator implements Validator<LectureApplyInfo, Lecture> {

    private final LectureApplicationRepository applicationRepository;

    @Override
    public void validate(LectureApplyInfo lectureApplyInfo, Lecture lecture) {
        if (lecture.isOverCapacity()) {
            throw new IllegalArgumentException("정원이 초과되어 신청에 실패했습니다.");
        }
        if (lecture.isOnGoingOrEnded(LocalDateTime.now())) {
            throw new IllegalArgumentException("진행 중이거나 종료된 강의입니다.");
        }
        if (applicationRepository.existsByLectureIdAndMemberId(lectureApplyInfo.lectureId(), lectureApplyInfo.memberId())) {
            throw new IllegalArgumentException("이미 해당 강의에 신청하셨습니다.");
        }
    }
}
