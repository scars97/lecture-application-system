package com.week02.lectureapplicationsystem.business.domain.lecture.dto;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;

import java.time.LocalDateTime;

public record LectureResult (
        Long id,
        String title,
        String speaker,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int capacity
){

    public static LectureResult from(Lecture lecture) {
        return new LectureResult(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getSpeaker(),
                lecture.getDescription(),
                lecture.getStartTime(),
                lecture.getEndTime(),
                lecture.getCapacity());
    }
}
