package com.example.lecture.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Integer maximumNumber;
    @Column(nullable = false)
    private Integer score;
    private String lectureComment;
    @Column(nullable = false)
    private LocalDateTime lectureDate;
    @Enumerated(EnumType.STRING)
    private Semester semester;
    @Column
    private int year;
    @Column
    private String majorName;
    @Column
    private String professorName;
    @Column
    private LocalDateTime acceptedAt;

    public  void changeStatus(Status status){ this.status=status;}

    public  void changeAcceptedAt(LocalDateTime acceptedAt){ this.acceptedAt = acceptedAt;}

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
                ", majorName=" + majorName +
                ", professorName=" + professorName +
                ", aceeptedAt=" + acceptedAt +
                '}';
    }
}
