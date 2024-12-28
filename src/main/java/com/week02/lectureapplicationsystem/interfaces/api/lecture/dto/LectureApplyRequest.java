package com.week02.lectureapplicationsystem.interfaces.api.lecture.dto;

import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureApplyInfo;
import jakarta.validation.constraints.NotBlank;

public record LectureApplyRequest (
        @NotBlank(message = "강의 ID는 필수 항목입니다.")
        Long lectureId,
        @NotBlank(message = "회원 ID는 필수 항목입니다.")
        String memberId
) {

    public LectureApplyInfo toInfo() {
        return new LectureApplyInfo(this.lectureId, this.memberId);
    }
}
