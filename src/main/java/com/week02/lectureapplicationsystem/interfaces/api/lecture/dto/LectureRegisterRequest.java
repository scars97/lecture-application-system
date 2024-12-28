package com.week02.lectureapplicationsystem.interfaces.api.lecture.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureRegisterInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LectureRegisterRequest(
        @NotBlank(message = "제목은 필수 항목입니다.")
        String title,
        @NotBlank(message = "강연자 이름은 필수 항목입니다.")
        String speaker,
        String description,
        @NotNull(message = "강의 시작 시간은 필수 항목입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") 
        LocalDateTime startTime,
        @NotNull(message = "강의 종료 시간은 필수 항목입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm") 
        LocalDateTime endTime
){

    public LectureRegisterInfo toInfo() {
        return new LectureRegisterInfo(
                title,
                speaker,
                description,
                startTime,
                endTime
        );
    }

}
