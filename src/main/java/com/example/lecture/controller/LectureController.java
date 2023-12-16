package com.example.lecture.controller;

import com.example.global.response.LmsResponse;
import com.example.lecture.domain.entity.Lecture;
import com.example.lecture.domain.request.ProfessorLectureCancelRequest;
import com.example.lecture.domain.request.ProfessorLectureRequest;
import com.example.lecture.domain.response.AllLectureRes;
import com.example.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;


    //요청강의 조회(교수)
    @GetMapping("/{id}")
    public LmsResponse<List<AllLectureRes>> agreeLectureFindById(@PathVariable("id") Long id) {
        List<AllLectureRes> allLectureRes = lectureService.agreeLectureFindById(id);
        if (allLectureRes.size() ==0){
            return new LmsResponse<>(HttpStatus.OK, new ArrayList<>(), "서비스 실패", "에러 발생", LocalDateTime.now());
        }
        return new LmsResponse<>(HttpStatus.OK, allLectureRes, "서비스 성공", "", LocalDateTime.now());
    }

    //강의 요청(교수)
    @PostMapping
    public LmsResponse<String> requestLecture(@RequestBody ProfessorLectureRequest request) {
        Lecture lecture = lectureService.requestLecture(request);
        if (lecture == null){
            return new LmsResponse<>(HttpStatus.OK, "","서비스 실패", "에러 발생", LocalDateTime.now());
        }
        return new LmsResponse<>(HttpStatus.OK, lecture.toString(), "서비스 성공", "", LocalDateTime.now());
    }


    //강의 요청 취소(교수)
    @PostMapping("/delete")
    public LmsResponse<String> cancelLecture(@RequestBody ProfessorLectureCancelRequest request) {
        lectureService.cancelLecture(request);
        return new LmsResponse<>(HttpStatus.OK, null, "서비스 성공", "", LocalDateTime.now());
    }



    //강의 조회(모든 유저)
    @GetMapping
    public LmsResponse<List<AllLectureRes>> findAllecture() {
        List<AllLectureRes> lectures = lectureService.findAllLecture();
        if (lectures.size() ==0){
            return new LmsResponse<>(HttpStatus.OK, new ArrayList<>(), "서비스 실패", "에러 발생", LocalDateTime.now());
        }
        return new LmsResponse<>(HttpStatus.OK, lectures, "서비스 성공", "", LocalDateTime.now());
    }



}





