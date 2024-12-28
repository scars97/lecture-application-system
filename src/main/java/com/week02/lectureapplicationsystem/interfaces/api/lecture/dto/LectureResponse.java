package com.week02.lectureapplicationsystem.interfaces.api.lecture.dto;

import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureResult;

import java.time.LocalDateTime;

public record LectureResponse (
        Long id,
        String title,
        String speaker,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int capacity
){

    public static LectureResponse from(LectureResult result) {
        return new LectureResponse(
                result.id(),
                result.title(),
                result.speaker(),
                result.description(),
                result.startTime(),
                result.endTime(),
                result.capacity()
        );
    }
}
