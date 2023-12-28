package com.lms.example.lecture.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String lectureName;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private Long maximumNumber;
    @Column(nullable = false)
    private Integer score;
    private String lectureComment;
    @Column(nullable = false)
    private LocalDateTime lectureDate;
    @Enumerated(EnumType.STRING)
    private Semester semester;
    @Column
    private Integer year;
    @ElementCollection
    @Column(nullable = false)
    private List<String> majorNames;
    @Column(nullable = false)
    private String professorId;
    @Column(nullable = false)
    private String professorName;
    @Column(nullable = false)
    private Integer startTime;
    @Column
    private LocalDateTime acceptedAt;
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;


    @Override
    public String toString() {
        return "Lecture{" +
                "id=" + id +
                ", lectureName='" + lectureName + '\'' +
                ", status=" + status +
                ", maximumNumber=" + maximumNumber +
                ", score=" + score +
                ", lectureComment='" + lectureComment + '\'' +
                ", lectureDate=" + lectureDate +
                ", semester=" + semester +
                ", year=" + year +
                ", professorName=" + professorName +
                ", aceeptedAt=" + acceptedAt +
                '}';
    }
}
