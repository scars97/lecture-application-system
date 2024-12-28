package com.week02.lectureapplicationsystem.interfaces.api.lecture.facade;

import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureResult;
import com.week02.lectureapplicationsystem.business.domain.lecture.service.LectureCommandService;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureRegisterRequest;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureCommandFacade {

    private final LectureCommandService lectureCommandService;

    public LectureResponse createLecture(LectureRegisterRequest request) {
        return LectureResponse.from(lectureCommandService.saveLecture(request.toInfo()));
    }

    public LectureResponse findSingleLecture(Long lectureId) {
        return LectureResponse.from(lectureCommandService.findLecture(lectureId));
    }

    public List<LectureResponse> getAvailableLectures() {
        List<LectureResult> availableLectures = lectureCommandService.getAvailableLectures();

        return availableLectures.stream()
                .map(LectureResponse::from)
                .toList();
    }
}
