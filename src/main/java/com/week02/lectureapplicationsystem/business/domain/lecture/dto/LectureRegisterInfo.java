package com.week02.lectureapplicationsystem.business.domain.lecture.dto;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;

import java.time.LocalDateTime;

public record LectureRegisterInfo(
        String title,
        String speaker,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime
){

    private static final int INITIAL_CAPACITY = 0;

    public Lecture toEntity() {
        return Lecture.builder()
                .title(this.title)
                .speaker(this.speaker)
                .description(this.description)
                .capacity(INITIAL_CAPACITY)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }

}
