package com.lms.example.lecture.dto.request;

import com.lms.example.lecture.domain.entity.Lecture;
import com.lms.example.lecture.domain.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLectureRequest {
    private Long lectureId;
    private String memberId;

    public Lecture toEntity() {
        return Lecture.builder()
                .id(lectureId)
                .status(Status.ACCEPT)
                .acceptedAt(LocalDateTime.now())
                .build();
    }
}
