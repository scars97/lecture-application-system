package com.week02.lectureapplicationsystem.interfaces.api.lecture.controller;

import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureRegisterRequest;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.dto.LectureResponse;
import com.week02.lectureapplicationsystem.interfaces.api.lecture.facade.LectureCommandFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureCommandController {

    private final LectureCommandFacade lectureCommandFacade;

    @PostMapping("")
    public ResponseEntity<LectureResponse> createLecture(@RequestBody LectureRegisterRequest request) {
        return ResponseEntity.ok(lectureCommandFacade.createLecture(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectureResponse> findSingleLecture(@PathVariable("id") Long id) {
        return ResponseEntity.ok(lectureCommandFacade.findSingleLecture(id));
    }

    @GetMapping("")
    public ResponseEntity<List<LectureResponse>> getAvailableLectures() {
        return ResponseEntity.ok(lectureCommandFacade.getAvailableLectures());
    }

}
