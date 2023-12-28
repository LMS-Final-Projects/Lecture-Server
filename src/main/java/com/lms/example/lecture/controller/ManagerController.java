package com.lms.example.lecture.controller;


import com.lms.example.global.response.LmsResponse;
import com.lms.example.lecture.dto.request.AdminDenyLectureRequest;
import com.lms.example.lecture.dto.request.AdminLectureRequest;
import com.lms.example.lecture.dto.response.AllLectureRes;
import com.lms.example.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final LectureService lectureService;

    //강의 조회(관리자)
    @GetMapping("/lectures/holding")
    public LmsResponse<List<AllLectureRes>> findAllHoldingLecture() {
        List<AllLectureRes> lectures = lectureService.findAllHoldingLecture();
        return new LmsResponse<>(HttpStatus.OK, lectures, "서비스 성공", "", LocalDateTime.now());
    }



    //강의 수락(관리자)
    @PostMapping("/lectures/registration")
    public LmsResponse<Boolean> acceptLecture(@RequestBody AdminLectureRequest request) {
        boolean b = lectureService.acceptLecture(request);
        return new LmsResponse<>(HttpStatus.OK, b, "서비스 성공", "", LocalDateTime.now());
    }

    //강의 거절(관리자)
    @PostMapping("/lectures/registration/deny")
    public LmsResponse<Boolean> denyLecture(@RequestBody AdminDenyLectureRequest request) {
        boolean b = lectureService.denyLecture(request);

        return new LmsResponse<>(HttpStatus.OK, b, "서비스 성공", "", LocalDateTime.now());
    }

}
