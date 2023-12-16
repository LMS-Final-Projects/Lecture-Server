package com.example.lecture.domain.request;

import com.example.lecture.domain.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLectureRequest {

    private Lecture lecture;

    public Lecture toEntity() {
        return Lecture.builder()
                .id(lecture.getId())
                .build();
    }
}
