package com.example.lecture.domain.request;

import com.example.lecture.domain.entity.Lecture;
import com.example.lecture.domain.entity.Semester;
import com.example.lecture.domain.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorLectureRequest {

    private String professorName;
    private String majorName;
    private String lectureName;
    private String lectureComment;
    private Integer maximumNumber;
    private Integer score;
    private Semester semester;


    public Lecture toEntity(){
        return Lecture
                .builder()
                .professorName(professorName)
                .majorName(majorName)
                .lectureName(lectureName)
                .lectureComment(lectureComment)
                .score(score)
                .semester(semester)
                .maximumNumber(maximumNumber)
                .status(Status.HOLDING)
                .lectureDate(LocalDateTime.now())
                .build();
    }

}
