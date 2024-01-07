package com.lms.example.lecture.dto.request;

import com.lms.example.lecture.domain.entity.Lecture;
import com.lms.example.lecture.domain.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDenyLectureRequest {
    private String memberId;
    private List<Integer> lectureIds;

    public List<Lecture> toEntities() {
        return lectureIds.stream()
                .map(id -> Lecture.builder().id(id).build())
                .collect(Collectors.toList());
    }

}
