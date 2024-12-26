package com.week02.lectureapplicationsystem.business.domain.lecture.persistence;

import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;

import java.util.List;

public interface LectureCommandRepository {

    Lecture saveLecture(Lecture lecture);

    Lecture findSingleLectureWithLock(Long lectureId);

    List<Lecture> getAvailableLectures();

    boolean existsByTitle(String title);
}
