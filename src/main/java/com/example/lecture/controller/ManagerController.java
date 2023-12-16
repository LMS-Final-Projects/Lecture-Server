package com.example.lecture.controller;


import com.example.global.response.LmsResponse;
import com.example.lecture.domain.request.AdminLectureRequest;
import com.example.lecture.domain.response.AllLectureRes;
import com.example.lecture.service.LectureService;
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

    //강의 조회(어드민)
    @GetMapping("/lectures/accept")
    public LmsResponse<List<AllLectureRes>> findAllHoldingLecture() {
        List<AllLectureRes> lectures = lectureService.findAllHoldingLecture();
        if (lectures.size() ==0){
            return new LmsResponse<>(HttpStatus.OK, new ArrayList<>(), "서비스 실패", "에러 발생", LocalDateTime.now());
        }
        return new LmsResponse<>(HttpStatus.OK, lectures, "서비스 성공", "", LocalDateTime.now());
    }



    //강의 수락(관리자)
    @PostMapping("/lectures/registration")
    public LmsResponse<Void> acceptLecture(@RequestBody AdminLectureRequest request) {
        lectureService.acceptLecture(request);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "", LocalDateTime.now());
    }

    //강의 거절(관리자)
    @PostMapping("/lectures/registration/deny")
    public LmsResponse<Void> denyLecture(@RequestBody AdminLectureRequest request) {
       lectureService.denyLecture(request);

        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "", LocalDateTime.now());
    }

}
