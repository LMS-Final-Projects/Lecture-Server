package com.lms.example.global.kafka;

import com.lms.example.lecture.domain.entity.Semester;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KafkaLecture {

    private String memberId;
    private Integer lectureId;
    private String lectureName;
    private String professorName;
    private Integer score;
    private Integer startTime;
    private List<Integer> classTimes; // 해당 교시
    private DayOfWeek dayOfWeek;
    private Integer maximumNumber;
    private Semester semester;
    private Integer year;
    private String contents;
    private KafkaAction kafkaAction;

}

