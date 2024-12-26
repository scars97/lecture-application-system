package com.week02.lectureapplicationsystem.business.domain.lecture.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LectureApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private LocalDateTime appliedAt;

    @Builder
    public LectureApplication(String memberId, LocalDateTime appliedAt) {
        this.memberId = memberId;
        this.appliedAt = appliedAt;
    }

    public void addLecture(Lecture lecture) {
        this.lecture = lecture;
    }
}
