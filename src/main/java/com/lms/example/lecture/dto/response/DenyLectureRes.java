package com.lms.example.lecture.dto.response;

import com.lms.example.lecture.domain.entity.Lecture;
import com.lms.example.lecture.domain.entity.Semester;
import com.lms.example.lecture.domain.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DenyLectureRes {

    private Integer id;
    private String lectureName;
    private Status status;
    private Integer maximumNumber;
    private Integer score;
    private String lectureComment;
    private LocalDateTime lectureDate;
    private Semester semester;
    private List<String> majorNames;
    private String professorName;

    public DenyLectureRes(Lecture lecture) {
        this.id = lecture.getId();
        this.lectureName = lecture.getLectureName();
        this.status = lecture.getStatus();
        this.maximumNumber = lecture.getMaximumNumber();
        this.score = lecture.getScore();
        this.lectureComment = lecture.getLectureComment();
        this.lectureDate = lecture.getLectureDate();
        this.semester = lecture.getSemester();
        this.majorNames = lecture.getMajorNames();
        this.professorName = lecture.getProfessorName();
    }

}
