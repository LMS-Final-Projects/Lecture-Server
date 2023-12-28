package com.lms.example.global.kafka;

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
    private Long lectureId;
    private String lectureName;
    private String professorName;
    private Integer score;
    private Integer startTime;
    private List<Integer> classTimes; // 해당 교시
    private DayOfWeek dayOfWeek;
    private Long maximumNumber;
    private KafkaAction kafkaAction;

}

