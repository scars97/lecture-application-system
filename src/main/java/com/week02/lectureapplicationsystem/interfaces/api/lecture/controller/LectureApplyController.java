package com.week02.lectureapplicationsystem.interfaces.api.lecture.controller;

import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureApplyRequest;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureApplyResponse;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.facade.LectureApplyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecture-applications")
@RequiredArgsConstructor
public class LectureApplyController {

    private final LectureApplyFacade lectureApplyFacade;

    @PostMapping("")
    public ResponseEntity<LectureApplyResponse> applyForLecture(@RequestBody LectureApplyRequest request) {
        return ResponseEntity.ok(lectureApplyFacade.applyForLecture(request));
    }

    @GetMapping("/{lectureId}")
    public ResponseEntity<List<LectureApplyResponse>> findAppliedLecturesByLectureId(@PathVariable("lectureId") Long lectureId) {
        return ResponseEntity.ok(lectureApplyFacade.findAppliedLecturesByLectureId(lectureId));
    }

    @GetMapping("/{memberId}/apply")
    public ResponseEntity<List<LectureApplyResponse>> findAppliedLecturesByMemberId(@PathVariable("memberId") String memberId) {
        return ResponseEntity.ok(lectureApplyFacade.findAppliedLecturesByMemberId(memberId));
    }
}
