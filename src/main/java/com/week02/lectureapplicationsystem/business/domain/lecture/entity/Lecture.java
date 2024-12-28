package com.week02.lectureapplicationsystem.business.domain.lecture.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String speaker;

    private String description;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Builder
    public Lecture(String title, String speaker, String description, int capacity,
                   LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.speaker = speaker;
        this.description = description;
        this.capacity = capacity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void incrementCapacity() {
        this.capacity++;
    }

    public boolean isOverCapacity() {
        return this.capacity >= 30;
    }

    public boolean isOnGoingOrEnded(LocalDateTime time) {
        return this.startTime.isBefore(time);
    }
}
