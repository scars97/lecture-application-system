package com.week02.lectureapplicationsystem.interfaces.api.lecture.dto;

import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureApplyResult;

import java.time.LocalDateTime;

public record LectureApplyResponse (
        Long id,
        String memberId,
        LocalDateTime appliedAt,
        LectureResponse lecture
) {

    public static LectureApplyResponse from(LectureApplyResult lectureApplyResult) {
        return new LectureApplyResponse(
                lectureApplyResult.id(),
                lectureApplyResult.memberId(),
                lectureApplyResult.appliedAt(),
                LectureResponse.from(lectureApplyResult.lecture())
        );
    }
}
