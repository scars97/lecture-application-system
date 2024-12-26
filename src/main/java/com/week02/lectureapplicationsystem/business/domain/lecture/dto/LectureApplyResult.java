package com.week02.lectureapplicationsystem.business.domain.lecture.dto;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.LectureApplication;

import java.time.LocalDateTime;

public record LectureApplyResult (
        Long id,
        String memberId,
        LocalDateTime appliedAt,
        LectureResult lecture
) {

    public static LectureApplyResult of(LectureApplication lectureApplication) {
        return new LectureApplyResult(
                lectureApplication.getId(),
                lectureApplication.getMemberId(),
                lectureApplication.getAppliedAt(),
                LectureResult.from(lectureApplication.getLecture())
        );
    }
}
