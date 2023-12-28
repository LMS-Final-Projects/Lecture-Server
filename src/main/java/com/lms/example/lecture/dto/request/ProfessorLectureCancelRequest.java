package com.lms.example.lecture.dto.request;

import com.lms.example.lecture.domain.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorLectureCancelRequest {
   private List<Long> lectureIds;
    public List<Lecture> toEntity(){
        return lectureIds.stream()
                .map(id-> Lecture.builder().id(id).build())
                .collect(Collectors.toList());
    }

}
