package com.week02.lectureapplicationsystem.interfaces.api.lecture.facade;

import com.week02.lectureapplicationsystem.business.domain.lecture.dto.LectureApplyResult;
import com.week02.lectureapplicationsystem.business.domain.lecture.service.LectureApplyService;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureApplyRequest;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureApplyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureApplyFacade {

    private final LectureApplyService lectureApplyService;

    public LectureApplyResponse applyForLecture(LectureApplyRequest request) {
        return LectureApplyResponse.from(lectureApplyService.applyForLecture(request.toInfo()));
    }

    public List<LectureApplyResponse> findAppliedLecturesByLectureId(Long lectureId) {
        List<LectureApplyResult> availableLectures = lectureApplyService.findAppliedLecturesByLectureId(lectureId);

        return availableLectures.stream()
                .map(LectureApplyResponse::from)
                .toList();
    }

    public List<LectureApplyResponse> findAppliedLecturesByMemberId(String memberId) {
        List<LectureApplyResult> availableLectures = lectureApplyService.findAppliedLecturesByMemberId(memberId);

        return availableLectures.stream()
                .map(LectureApplyResponse::from)
                .toList();
    }
}
