package com.lms.example.lecture.dto.request;

import com.lms.example.lecture.domain.entity.Lecture;
import com.lms.example.lecture.domain.entity.Semester;
import com.lms.example.lecture.domain.entity.Status;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorLectureRequest {

    private String professorName;
    private String professorId;
    private List<String> majorNames;
    private String lectureName;
    private String lectureComment;
    private Long maximumNumber;
    private Integer score;
    private Semester semester;
    private DayOfWeek dayOfWeek;
    private Integer startTime;
    private Integer year;

    public Lecture toEntity(){
        return Lecture
                .builder()
                .professorId(professorId)
                .professorName(professorName)
                .majorNames(majorNames)
                .lectureName(lectureName)
                .lectureComment(lectureComment)
                .score(score)
                .semester(semester)
                .maximumNumber(maximumNumber)
                .status(Status.HOLDING)
                .dayOfWeek(dayOfWeek)
                .startTime(startTime)
                .year(year)
                .lectureDate(LocalDateTime.now())
                .build();

    }

}
