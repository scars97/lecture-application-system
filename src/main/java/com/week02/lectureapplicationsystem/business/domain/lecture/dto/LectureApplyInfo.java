package com.week02.lectureapplicationsystem.business.domain.lecture.dto;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.LectureApplication;

import java.time.LocalDateTime;

public record LectureApplyInfo (
        Long lectureId,
        String memberId
) {

    public LectureApplication toEntity() {
        return LectureApplication.builder()
                .memberId(memberId)
                .appliedAt(LocalDateTime.now())
                .build();
    }
}
