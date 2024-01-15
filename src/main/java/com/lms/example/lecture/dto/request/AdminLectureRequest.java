package com.lms.example.lecture.dto.request;

import com.lms.example.lecture.domain.entity.Lecture;
import com.lms.example.lecture.domain.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLectureRequest {
    private List<Integer> lectureIds;
    private String memberId;


}
