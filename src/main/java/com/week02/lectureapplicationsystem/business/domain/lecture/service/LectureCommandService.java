package com.week02.lectureapplicationsystem.business.domain.lecture.service;

import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureRegisterInfo;
import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureResult;
import com.week02.lectureapplicationsystem.business.domain.lecture.entity.Lecture;
import com.week02.lectureapplicationsystem.business.domain.lecture.persistence.LectureCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 강의 등록, 조회
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureCommandService {

    private final LectureCommandRepository lectureCommandRepository;

    /**
     * 강의 등록
     * @param lectureRegisterInfo
     * @return
     */
    @Transactional
    public LectureResult saveLecture(LectureRegisterInfo lectureRegisterInfo) {
        if (lectureCommandRepository.existsByTitle(lectureRegisterInfo.title())) {
            throw new IllegalArgumentException("강의 제목은 중복될 수 없습니다.");
        }

        Lecture saveLecture = lectureCommandRepository.saveLecture(lectureRegisterInfo.toEntity());

        return LectureResult.from(saveLecture);
    }

    /**
     * 단일 강의 조회
     * @param lectureId
     * @return
     */
    public LectureResult findLecture(Long lectureId) {
        Lecture lecture = lectureCommandRepository.findSingleLectureWithLock(lectureId);

        return LectureResult.from(lecture);
    }

    /**
     * 신청 가능한 강의 목록 조회
     * @return
     */
    public List<LectureResult> getAvailableLectures() {
        List<Lecture> lectures = lectureCommandRepository.getAvailableLectures();

        return lectures.stream()
                .map(LectureResult::from)
                .toList();
    }
}
